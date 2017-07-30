package dev.project.app.com.fieldtripbuddies.AsyncTasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import dev.project.app.com.fieldtripbuddies.Activities.ConfirmationActivity;
import dev.project.app.com.fieldtripbuddies.Intents.ConfirmationIntent;
import dev.project.app.com.fieldtripbuddies.Interfaces.IAPI_Call;
import dev.project.app.com.fieldtripbuddies.Interfaces.IAPI_Candidate;
import dev.project.app.com.fieldtripbuddies.Interfaces.IAPI_Order;
import dev.project.app.com.fieldtripbuddies.Objects.API_Order;
import dev.project.app.com.fieldtripbuddies.Utils;

/**
 * Created by gabe on 7/29/2017.
 */

public class API_POST_Order  extends AsyncTask<String, Void, String>  implements IAPI_Call{

        private final String USER_AGENT = "Mozilla/5.0";
    private String  server_response_raw;
    private IAPI_Candidate candidate;

    private Activity act;

        public API_POST_Order(Activity act, IAPI_Candidate cand,String server_response ) {
            this.act = act;
            this.server_response_raw = server_response;
            this.candidate = cand;
        }


        @Override
        protected String doInBackground(String... data_array) {
            // Create data variable for sent values to server
            String err = null;
            try {
                String server_response = "";
                URL url = new URL(Utils.ACCURATE_BACKGROUND_ORDER);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                String auth = getB64Auth(Utils.API_KEY, Utils.API_SECRET_KEY);
                conn.setUseCaches(false);
                // conn.setRequestProperty("User-Agent", USER_AGENT);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", auth);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                //conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty( "Content-type", "application/x-www-form-urlencoded");
                conn.setInstanceFollowRedirects(false);
                API_Order order = new API_Order(server_response_raw);
                String data_post= order.POSTify();
                if (Utils.isDEBUG) {
                    Log.e("DATA POST ORDER: ",data_post);
                } else {
                    //
                }
                conn.setRequestProperty("Content-Length", Integer.toString(data_post.getBytes().length));
                conn.setRequestProperty("Content-Language", "en-US");
                conn.connect();

                //setup send
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                writer.write(data_post);
                //clean up
                writer.flush();
                writer.close();
                int responseCode = conn.getResponseCode();
                if(Utils.isDEBUG){
                    Log.e("response code : ",responseCode+"");
                }
                switch (responseCode) {
                    case HttpsURLConnection.HTTP_OK:
                        return readFromServer(conn);
                    default:
                        Toast.makeText(this.act,"Network error :(",Toast.LENGTH_LONG).show();
                        return readFromServer(conn);
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
                err = "Exception Protocol...."+e.getMessage();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                err = "Exception MAL FORMED URL...."+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                err = "Exception IO ERR...."+ e.getMessage();
                err += "\n localized err : "+e.getLocalizedMessage();
            } catch (Exception e) {
                e.printStackTrace();
                err = "Exception GENERAL ERROR.... : "+e.getMessage();
            }
            return new String("Exception: " + err);

        }

        public String readFromServer(HttpsURLConnection conn) throws IOException {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            conn.getInputStream()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
                // break;
            }
            in.close();
            conn.disconnect();
            return sb.toString();
        }
        @Override
        protected void onPostExecute(String result) {
            boolean submission = false;
            if(result==null || result.toLowerCase().contains("exception")){
                Log.e("err server response","api server error"); //
            }else {
                submission = true;
                if (Utils.isDEBUG) {
                    Log.e("server: ORDER", "Response: " + result);

                } else {

                }
            }
                Intent i = new Intent(act, ConfirmationActivity.class);
                i.putExtra(ConfirmationIntent.CONFIRMATION_INTENT,new ConfirmationIntent(this.candidate,submission));
                act.startActivity(i);
                act.finish();

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    @Override
        public String getB64Auth(String login, String pass) {
            String source = login + ":" + pass;
            String ret = "Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
            return ret;
        }

        public String getPostDataString(JSONObject params) throws Exception {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            Iterator<String> itr = params.keys();
            while (itr.hasNext()) {
                String key = itr.next();
                Object value = params.get(key);
                if (first)
                    first = false;
                else
                    result.append("&");
                //Log.e("OBJ.....","KEY: ["+key+"]"+"VAL: ["+value.toString()+"]");
                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
            }
            return result.toString();
        }
    public String readFromServer(HttpURLConnection conn) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream()));
        StringBuffer sb = new StringBuffer("");
        String line = "";
        while ((line = in.readLine()) != null) {
            sb.append(line);
            // break;
        }
        in.close();
        conn.disconnect();
        return sb.toString();
    }
    @Override
    public API_POST_Candidate apiCandidateCall(Activity act, String val) {
        return null;
    }
    @Override
    public API_POST_Order apiOrderCall(Activity act, IAPI_Candidate cand ,String server_reponse) {
        return null;
    }
}

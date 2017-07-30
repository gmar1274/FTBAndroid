package dev.project.app.com.fieldtripbuddies.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import bolts.Task;
import dev.project.app.com.fieldtripbuddies.Interfaces.IAPI_Call;
import dev.project.app.com.fieldtripbuddies.Interfaces.IAPI_Candidate;
import dev.project.app.com.fieldtripbuddies.Utils;

/**
 * Created by gabe on 7/19/2017.
 */

public class API_POST_Candidate extends AsyncTask<String, Void, String>  implements IAPI_Call {
    private final String USER_AGENT = "Mozilla/5.0";
    private HashMap<String, String> params;
    private IAPI_Candidate candidate;
    private Activity act;

    public API_POST_Candidate(Activity act, IAPI_Candidate cand) {
        this.act = act;
        this.candidate = cand;
    }

    //#Example of  application/json posted in to /candidate to create a candidate
    /**
     * json = "{\n" +
     "  \"firstName\": \"Elmer\",\n" +
     "  \"lastName\": \"Fudd\",\n" +
     "  \"middleName\": \"Fiddlesticks\",\n" +
     "  \"dateOfBirth\": \"1945-01-01\",\n" +
     "  \"ssn\": \"800-50-7111\",\n" +
     "  \"email\": \"Elmer@wb.com\",\n" +
     "  \"phone\": \"666-444-5656\",\n" +
     "  \"address\": \"123 Silly Street\",\n" +
     "  \"city\": \"Lousville\",\n" +
     "  \"region\": \"KY\",\n" +
     "  \"country\": \"US\",\n" +
     "  \"postalCode\": \"91234\",\n" +
     "  \"governmentId\": {\n" +
     "    \"country\": \"US\",\n" +
     "    \"type\": \"SpyCard\",\n" +
     "    \"number\": \"ABC12345\"\n" +
     "  }\n" +
     "}";
     *
     *
     *
     */
    private String example_json =
        "{\n" +
        "  \"firstName\": \"Bugs\",\n" +
        "  \"lastName\": \"Bunny\",\n" +
        "  \"middleName\": null,\n" +
        "  \"dateOfBirth\": \"1973-09-10\",\n" +
        "  \"ssn\": \"444-40-1223\",\n" +
        "  \"email\": \"bugs@bunny.com\",\n" +
        "  \"phone\": \"206-369-5205\",\n" +
        "  \"address\": \"12345 Mockingbird\",\n" +
        "  \"city\": \"Hollywood\",\n" +
        "  \"region\": \"WA\",\n" +
        "  \"country\": \"US\",\n" +
        "  \"postalCode\": \"98117\",\n" +
        "  \"governmentId\": {\n" +
        "    \"country\": null,\n" +
        "    \"type\": null,\n" +
        "    \"number\": null\n" +
        "  },\n" +
        "  \"prevEmployed\": null,\n" +
        "  \"employments\": [],\n" +
        "  \"licenses\": [],\n" +
        "  \"references\": [],\n" +
        "\"aliases\": [{\n" +
        "        \"firstName\": \"Wascally\",\n" +
        "        \"lastName\": \"Wabbit\",\n" +
        "        \"middleName\": \"M\"\n" +
        "    }],\n" +
        "\"educations\": [{\n" +
        "       \"school\": \"Cooper Union\",\n" +
        "       \"country\": \"US\",\n" +
        "       \"region\": \"NY\",\n" +
        "       \"city\": \"Brooklyn\",\n" +
        "       \"degree\": \"Master of Deception\",\n" +
        "       \"major\": \"Misdirection\",\n" +
        "       \"startDate\": \"1940-05\",\n" +
        "       \"endDate\": \"1945-06\",\n" +
        "       \"graduated\": \"true\",\n" +
        "       \"graduationDate\": \"1945-06\",\n" +
        "       \"presentlyEnrolled\": \"false\"\n" +
        "   }],\n" +
        "\"convicted\": \"true\",\n" +
        "    \"convictions\": [\n" +
        "        {\n" +
        "        \"convictionDate\": \"1952-05-13\",\n" +
        "        \"description\": \"Fraud and Larceny\",\n" +
        "        \"country\": \"US\",\n" +
        "        \"region\": \"CA\",\n" +
        "        \"region2\": \"Hollywood\",\n" +
        "        \"city\": \"Los Angeles\"\n" +
        "        }\n" +
        "    ]\n" +
        "}\n";
    @Override
    protected String doInBackground(String... data_array) {
        // Create data variable for sent values to server
        String err = null;
        try {
            JSONObject data = candidate.jsonify();
            String server_response = "";
            URL url = new URL(Utils.ACCURATE_BACKGROUND_CANDIDATE);
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
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
                JSONObject o = new JSONObject(example_json);
                String data_post= candidate.POSTify();

            conn.setRequestProperty("Content-Length", Integer.toString(data_post.getBytes().length));
            conn.setRequestProperty("Content-Language", "en-US");
            conn.connect();

            if (Utils.isDEBUG) {
                Log.e("OBJECT PARAM json:", data.toString());
                Log.e("DATA POST: ",data_post);
            } else {
                //
            }
            //setup send

                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));

               // String escaped = UrlEscapers.urlFragmentEscaper().escape(data_post);
                //writer.write(escaped);
                writer.write(data_post);
                //clean up
                writer.flush();
                writer.close();

            int responseCode = conn.getResponseCode();

            switch (responseCode) {
                case HttpsURLConnection.HTTP_OK:
                    return readFromServer(conn);
                default:
                    Toast.makeText(this.act,"Network error :(",Toast.LENGTH_LONG).show();
                    return readFromServer(conn);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
            err = "Protocol...."+e.getMessage();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            err = "MAL FORMED URL...."+e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            err = "IO ERR...."+ e.getMessage();
            err += "\n localized err : "+e.getLocalizedMessage();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
    protected void onPostExecute(final String result) {
        if(result==null){
            Log.e("err server response","api server error"); //
        }else{
            if(Utils.isDEBUG) {
                Log.e("server: ", "Response: " + result);
            }else{

            }
            Task.BACKGROUND_EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    API_POST_Order order = apiOrderCall(act, candidate ,result);
                    order.execute();
                    if(Utils.isDEBUG)Log.e("Finished in task", "status done..");
                }
            });
        }

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

    @Override
    public API_POST_Candidate apiCandidateCall(Activity act , String raw) {
        return null;
    }

    @Override
    public API_POST_Order apiOrderCall(Activity act,IAPI_Candidate cand ,String server_response) {
       API_POST_Order order = new API_POST_Order(act,cand,server_response);
        return order;
    }
}

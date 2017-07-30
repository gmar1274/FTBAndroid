package dev.project.app.com.fieldtripbuddies.Objects;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import dev.project.app.com.fieldtripbuddies.Interfaces.IAPI_Order;
import dev.project.app.com.fieldtripbuddies.Utils;

/**
 * Created by gabe on 7/29/2017.
 *
 * API order model.
 */

public class API_Order implements IAPI_Order{
    private static final String COUNTRY ="country" ;
    private static final String REGION = "region";
    private static final String CITY = "city";
    private static final String ID = "id";

    private JSONObject response;
    public API_Order(String server_response_raw) {
        try {
            response = decodeResponseJSON(server_response_raw);
        } catch (JSONException e) {
            Log.e("json null","constructor null");
            e.printStackTrace();
            response = null;
        }
    }
    private JSONObject decodeResponseJSON(String server) throws JSONException {
        JSONObject response = new JSONObject(server);
        return response;
    }

    @Override
    public JSONObject getServerResponse() {
        return this.response;
    }

    @Override
    public String getID() {
        try {
            return this.response.getString(ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private final String[] attributes = {"candidateId","packageType","workflow","jobLocation.city" ,"jobLocation.region" , "jobLocation.country"};
//Package Options: [PKG_BASIC, PKG_STANDARD, PKG_PRO, PKG_EMPTY] Workflow Options: [EXPRESS, INTERACTIVE]
    @Override
    public String POSTify() throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();
        boolean first = true;
        if(Utils.isDEBUG){
            for(int i = 0 ; i < attributes.length ; ++i) {
                if (first) {
                    first = false;
                }
                else {
                    result.append("&");
                }
                String key = attributes[i];
                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                switch (i+1){
                    case 1:
                        result.append(URLEncoder.encode(this.getID(), "UTF-8"));
                        break;
                    case 2:
                        result.append(URLEncoder.encode("PKG_PRO", "UTF-8"));
                        break;
                    case 3:
                        result.append(URLEncoder.encode("INTERACTIVE", "UTF-8"));
                        break;
                    case 4:
                        result.append(URLEncoder.encode(this.getCity(), "UTF-8"));
                        break;
                    case 5:
                        result.append(URLEncoder.encode(this.getRegion(), "UTF-8"));
                        break;
                    case 6:
                        result.append(URLEncoder.encode(this.getCountry(), "UTF-8"));
                        break;
                    default:
                        result.append(URLEncoder.encode("null", "UTF-8"));
                        break;
                }
            }
            return result.toString();
        }
        else {

        }
       /* for (String key: this.values.keySet()) {
            if (first) {
                first = false;
            }
            else {
                result.append("&");
            }
            //Log.e("OBJ.....","KEY: ["+key+"]"+"VAL: ["+value.toString()+"]");
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(this.values.get(key), "UTF-8"));
        }*/
        return result.toString();
    }

    @Override
    public String getCountry() {
        try {
            return this.response.getString(COUNTRY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getRegion() {
        try {
            return this.response.getString(REGION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getCity() {
        try {
            return this.response.getString(CITY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}

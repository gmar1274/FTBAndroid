package dev.project.app.com.fieldtripbuddies.Interfaces;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by gabe on 7/29/2017.
 *
 * API order response. Create an order ...

 */
public interface IAPI_Order {
    public JSONObject getServerResponse();
    String getID();
    String POSTify() throws UnsupportedEncodingException;
    String getCountry();
    String getRegion();
    String getCity();
}

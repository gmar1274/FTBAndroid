package dev.project.app.com.fieldtripbuddies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import dev.project.app.com.fieldtripbuddies.AsyncTasks.DownloadProfilePicutureTask;

/**
 * Created by gabe on 6/25/2017.
 */
/**
 * ACCURATE BACKGROUND APIS
 * https://api.accuratebackground.com/v3
 */
public class Utils {
    public static final boolean isDEBUG=true;
    public static final String ACCURATE_BACKGROUND_BASEURL= "https://api.accuratebackground.com/v3";//Base url
    public static final String ACCURATE_BACKGROUND_CANDIDATE= "https://api.accuratebackground.com/v3/candidate";//create a candidate
    public static final String FIREBASE_STORAGE_USER_DIRECTORY = "user/id/image/";//just append ID of image
    public static final String INTENT = "MyIntent";//custom Intent LoggedInIntent, used to indicate way of user logged in. point of entry to app...
    private static final String FB_PROFILE_PIC = "picture";//FB graphi api
    public static final String GOOGLE_INTENT = "GOOGLE_SIGNIN";//google log in intent info

    public static final String FACEBOOK_INTENT = "FACEBOOK_SIGNIN";//logged in via fb
    public static final String USER_INFO_INTENT = "USER_PROFILE_INFO" ;// LoggedInIntent, same thing as intent except meant to used as a way for the app knows user after logged in
    public static final String API_KEY = "d076429f-09a5-4a73-8706-522e091bb630";
    public static final String API_SECRET_KEY = "ef83f919-9c9c-44d3-86e6-f7d296fa891d";
    public static final String ACCURATE_BACKGROUND_ORDER = "https://api.accuratebackground.com/v3/order";//create a candidate
    ;

    /**
     * @param url
     * @param iv
     * @return
     */
    public static void setFacebookProfilePic(ImageView iv, String url) {
        new DownloadProfilePicutureTask(iv).execute(url);
    }
    /**
     *
     * @param tv text view
     * @param id id of user
     */
    public static void setFBEmail(TextView tv, String id) {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {
                            String email ="";
                            email = object.getString("email");
                            Log.e("EMAIL: ", email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        request.executeAsync();
    }
}
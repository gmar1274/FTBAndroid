package dev.project.app.com.fieldtripbuddies.Interfaces;

import android.app.Activity;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import dev.project.app.com.fieldtripbuddies.AsyncTasks.API_POST_Candidate;
import dev.project.app.com.fieldtripbuddies.AsyncTasks.API_POST_Order;

/**
 * Created by gabe on 7/29/2017.
 */

public interface IAPI_Call {
    API_POST_Candidate apiCandidateCall(Activity act, String val);
    API_POST_Order apiOrderCall(Activity act, IAPI_Candidate candidate ,String server_reponse);

    String readFromServer(HttpsURLConnection https) throws IOException;
    String readFromServer(HttpURLConnection http) throws IOException;
    String getB64Auth(String login, String pass);
}

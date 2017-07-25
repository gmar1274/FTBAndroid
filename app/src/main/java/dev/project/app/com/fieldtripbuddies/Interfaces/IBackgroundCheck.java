package dev.project.app.com.fieldtripbuddies.Interfaces;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by gabe on 7/16/2017.
 */

public interface IBackgroundCheck {
    void profilePic(Context c);
    AsyncTask<String,Void,String> apiCall(IAPI_Candidate candidate);
}

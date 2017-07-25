package dev.project.app.com.fieldtripbuddies.Interfaces;

import android.content.Intent;

import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by gabe on 6/24/2017.
 */

public interface ILogin {
    void loginFromFacebook(LoginResult loginResult,Profile fb);
    void loginFromGoogle(GoogleSignInAccount signInAccount,Intent data);
    void goToMainActivity(Intent i);

}

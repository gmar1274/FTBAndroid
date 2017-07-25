package dev.project.app.com.fieldtripbuddies.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import dev.project.app.com.fieldtripbuddies.Intents.LoggedInIntent;
import dev.project.app.com.fieldtripbuddies.Interfaces.ILogin;
import dev.project.app.com.fieldtripbuddies.R;
import dev.project.app.com.fieldtripbuddies.Utils;

public class LoginActivity extends AppCompatActivity implements ILogin {

    private static final int RC_SIGN_IN = 99, FB_SIGN_IN=98;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    private final List<String> FB_LIST = Arrays.asList("email","public_profile","user_birthday");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /////////////////////GOOGLE LOAD
        // Configure sign-in to request the user's ID, email address, and basic profile. ID and
// basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
// Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this,"Something went wrong :(",Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        //signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        ///////////
        //////FACeBOOK
        LoginButton btn = (LoginButton)this.findViewById(R.id.login_button);
        //btn.setHeight(100);
        btn.setReadPermissions(FB_LIST);
        btn.setAllCaps(true);
        callbackManager = CallbackManager.Factory.create();
        btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            ProfileTracker mProfileTracker;
            @Override
            public void onSuccess(final LoginResult loginResult) {
                if (Profile.getCurrentProfile()==null) {
                   mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile old_profile, Profile curr_profile) {
                            // profile2 is the new profile
                            mProfileTracker.stopTracking();
                            loginFromFacebook(loginResult, curr_profile);
                        }
                    };
                        // no need to call startTracking() on mProfileTracker
                        // because it is called by its constructor, internally.

                } else {
                    loginFromFacebook(loginResult, Profile.getCurrentProfile());
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        /////////////
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from
        //   GoogleSignInApi.getSignInIntent(...);
        switch (requestCode){
            case RC_SIGN_IN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    GoogleSignInAccount acct = result.getSignInAccount();
                    loginFromGoogle(acct,data);
                }else{
                    Toast.makeText(LoginActivity.this,"Login failed.",Toast.LENGTH_LONG).show();
                }
                break;
            default:
               callbackManager.onActivityResult(requestCode,resultCode,data);
                break;
        }
    }

    @Override
    public void loginFromFacebook(LoginResult loginResult,final Profile fb) {

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                LoggedInIntent l = new LoggedInIntent(object, fb);
                i.putExtra(Utils.INTENT, l);
                goToMainActivity(i);
            }
        });
        request.executeAsync();
    }

    /**
     * Default from google sign in
     * @param signInAccount
     * @param data
     */
    @Override
    public void loginFromGoogle(GoogleSignInAccount signInAccount, Intent data) {

        Intent i = new Intent(this,MainActivity.class);
        LoggedInIntent l = new LoggedInIntent(signInAccount);
        i.putExtra(Utils.INTENT,l);
        i.putExtra(Utils.GOOGLE_INTENT,data);
        this.goToMainActivity(i);
    }

    @Override
    public void goToMainActivity(Intent i) {
        this.startActivity(i);
        this.finish();
    }
}

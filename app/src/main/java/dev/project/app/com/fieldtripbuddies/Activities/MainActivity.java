package dev.project.app.com.fieldtripbuddies.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;

import dev.project.app.com.fieldtripbuddies.Intents.LoggedInIntent;
import dev.project.app.com.fieldtripbuddies.Interfaces.IAuthControl;
import dev.project.app.com.fieldtripbuddies.Interfaces.IBackgroundCheck;
import dev.project.app.com.fieldtripbuddies.Interfaces.ILoggedInUser;
import dev.project.app.com.fieldtripbuddies.Interfaces.IRegisterUser;
import dev.project.app.com.fieldtripbuddies.Interfaces.IUserProfilePic;
import dev.project.app.com.fieldtripbuddies.R;
import dev.project.app.com.fieldtripbuddies.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IUserProfilePic, IAuthControl,IRegisterUser {

    private LoggedInIntent loggedIn; //custom intent see details...
private GoogleApiClient googleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Fieldtrip Buddies", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        Intent intent = getIntent();
        LoggedInIntent logged =  (LoggedInIntent) intent.getParcelableExtra(Utils.INTENT);
        loggedIn = logged;
        if(logged != null ) {
            Toast.makeText(this, "Hey " + logged.getName() + "!!", Toast.LENGTH_LONG).show();
            //Log.e("OBJ LOGGED:: ", logged.toString());
        }else{
            Toast.makeText(this, "NULL..."+ Profile.getCurrentProfile().getName(), Toast.LENGTH_LONG).show();
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initNavView(navigationView , logged);

    }

    /**
     * Method used to inflate drawer
     * @param nv
     * @param logged
     */
    private void initNavView(NavigationView nv, LoggedInIntent logged) {
        View navigationView = nv.getHeaderView(0);
        if(logged==null || navigationView==null)return;
        try {
            TextView name = (TextView) navigationView.findViewById(R.id.nav_name);
            name.setText(logged.getName());
            TextView email = (TextView) navigationView.findViewById(R.id.nav_email);
            if(logged.getEmail() != null) email.setText(logged.getEmail());
            ImageView iv = (ImageView)navigationView.findViewById(R.id.nav_imageView);
            fetchUserProfilePic(iv,logged);//load image

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.menu_log_out:
                signOut(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.background_check_leader) {
            becomeLeader(this);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void fetchUserProfilePic(ImageView iv, ILoggedInUser user) {
        if(user.isFB()){
            Utils.setFacebookProfilePic(iv,user.getImageURL());
        }else{//GOOGLE
            googleApiClient =  new GoogleApiClient.Builder(this).build();
        }
    }

    @Override
    public void fetchUserEmailAddress(TextView tv, ILoggedInUser user) {

       if(user.isFB()) {
           Utils.setFBEmail(tv, user.getId());
       }else{

       }
    }

    @Override
    public boolean signOut(Activity act) {
        if(loggedIn.isFB()){
            LoginManager.getInstance().logOut();
        }else {
            Auth.GoogleSignInApi.signOut(googleApiClient);
        }
        Toast.makeText(this,"Logged Out!",Toast.LENGTH_LONG).show();
        act.finish();
        return true;
    }

    @Override
    public void becomeLeader(Activity act) {
        Intent i = new Intent(this,BackgroundCheckActivity.class);
        i.putExtra(Utils.USER_INFO_INTENT,loggedIn);
        act.startActivity(i);
    }

    @Override
    public void becomeDriver(Activity act) {

    }
}

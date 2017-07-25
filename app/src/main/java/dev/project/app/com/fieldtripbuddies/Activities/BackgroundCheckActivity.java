package dev.project.app.com.fieldtripbuddies.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

import dev.project.app.com.fieldtripbuddies.AsyncTasks.API_POST_REQUEST;
import dev.project.app.com.fieldtripbuddies.Intents.LoggedInIntent;
import dev.project.app.com.fieldtripbuddies.Interfaces.IAPI_Candidate;
import dev.project.app.com.fieldtripbuddies.Interfaces.IBackgroundCheck;
import dev.project.app.com.fieldtripbuddies.Objects.API_Candidate;
import dev.project.app.com.fieldtripbuddies.R;
import dev.project.app.com.fieldtripbuddies.Utils;

/**
 * ACCURATE BACKGROUND APIS
 * https://api.accuratebackground.com/v3/
 */
public class BackgroundCheckActivity extends AppCompatActivity implements IBackgroundCheck{

    private static final int CAMERA_REQUEST = 1;
    private ImageView imageView;
    private LoggedInIntent loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_check);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //imageView = (ImageView)this.findViewById(R.id.profilePicimageView);
        //imageView.setVisibility(View.GONE);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera(BackgroundCheckActivity.this);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });*/
        loggedIn = getIntent().getParcelableExtra(Utils.USER_INFO_INTENT);
        if(loggedIn != null){
          if(Utils.isDEBUG)  Log.e("object logged","NOT NULLLLLLL!!!");
        }else{
            Log.e("NULL","NULL LOGGEDDDDDDDDD");
        }
        final EditText email = (EditText)this.findViewById(R.id.editText_email);
        final EditText dob = (EditText)this.findViewById(R.id.editText_dob);
        final EditText ssn = (EditText)this.findViewById(R.id.editText_ssn);
        if(Utils.isDEBUG){
            email.setText("gabe1274@hotmail.com");
            dob.setText("1993-12-12");
            ssn.setText("123456789");
        }
        Button sub = (Button) this.findViewById(R.id.button_background_check);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> val = new HashMap<String, String>();
                if(Utils.isDEBUG){
                    val.put("ssn","123-22-3345");
                    val.put("dateOfBirth",dob.getText().toString());
                    val.put("email",email.getText().toString());
                    val.put("phone","909-123-4567");
                    String[] name  = loggedIn.getName().split(" ");
                    String fname=name[0];
                    String lname = name[1];
                    val.put("firstName", fname);
                    val.put("lastName", lname);
                    val.put("country","US");
                    val.put("address","2020 s boca raton ct.");
                    val.put("city","Ontario");
                    val.put("region","CA");
                    val.put("postalCode","91761");
                }else{
                    val.put("ssn",ssn.getText().toString());
                    val.put("dateOfBirth",dob.getText().toString());
                    val.put("email",email.getText().toString());
                    val.put("phone","9091234567");
                    String[] name  = loggedIn.getName().split(" ");
                    String fname=name[0];
                    String lname = name[1];
                    val.put("firstName", fname);
                    val.put("lastName", lname);

                }
                apiCall(new API_Candidate(val)).execute();
                Toast.makeText(BackgroundCheckActivity.this,"Submitted!",Toast.LENGTH_LONG).show();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case  CAMERA_REQUEST :
                if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                    try {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(photo);
                        imageView.setVisibility(View.VISIBLE);
                    }catch (Exception e){
                        Log.e("ERR pic","error with camera");
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }

    }
    @Override
    public void profilePic(Context c) {
        openCamera(c);
    }

    @Override
    public AsyncTask<String, Void, String> apiCall(IAPI_Candidate candidate) {
        return new API_POST_REQUEST(candidate);
    }

    private void openCamera(Context c) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();//NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

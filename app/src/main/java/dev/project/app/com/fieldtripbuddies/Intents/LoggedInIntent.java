package dev.project.app.com.fieldtripbuddies.Intents;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import dev.project.app.com.fieldtripbuddies.Interfaces.ILoggedInUser;
import dev.project.app.com.fieldtripbuddies.Utils;

/**
 * Created by gabe on 6/25/2017.
 */

public class LoggedInIntent implements ILoggedInUser, Parcelable {
    private String name,id, email;
    private String pic_url;
    private boolean isFB;

    public LoggedInIntent(GoogleSignInAccount googleSignInAccount)
    {
        this.name = googleSignInAccount.getDisplayName();
        this.pic_url = Utils.FIREBASE_STORAGE_USER_DIRECTORY+"/"+googleSignInAccount.getId()+"-"+googleSignInAccount.getDisplayName();
        this.id = googleSignInAccount.getId();
        this.email = googleSignInAccount.getEmail();
    }
    public LoggedInIntent(JSONObject object,Profile profile){
        try {
             id = object.getString("id");
            pic_url = new URL("https://graph.facebook.com/" + id + "/picture?type=large").toString();
            String bday = "", gender = "";
            if (object.has("email")) {
                email = object.getString("email");
            }else{
                email = "";
            }
            if (object.has("birthday"))
                bday = object.getString("birthday");
            if (object.has("gender"))
                gender = object.getString("gender");
        }catch (Exception e){
            e.printStackTrace();
        }
        isFB = true;
        this.name = profile.getName();
        //this.id = profile.getId();
       // this.pic_url = "https://graph.facebook.com/"+id+"/picture?type=large"; //profile.getId()+"-"+profile.getName();
    }


    protected LoggedInIntent(Parcel in) {
        name = in.readString();
        id = in.readString();
        email = in.readString();
        pic_url = in.readString();
        isFB = in.readByte() != 0;
    }

    public static final Creator<LoggedInIntent> CREATOR = new Creator<LoggedInIntent>() {
        @Override
        public LoggedInIntent createFromParcel(Parcel in) {
            return new LoggedInIntent(in);
        }

        @Override
        public LoggedInIntent[] newArray(int size) {
            return new LoggedInIntent[size];
        }
    };

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getImageURL() {
        return this.pic_url;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setImageURL(String url) {
       this.pic_url = url;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public boolean isFB(){
        return this.isFB;
    }
    @Override
    public String toString(){
        return "Name:"+name+", id:"+id+", "+ "url:"+pic_url+" email: "+email +", isFB: "+isFB;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeString(email);
        parcel.writeString(pic_url);
        parcel.writeByte((byte) (isFB ? 1 : 0));
    }
}

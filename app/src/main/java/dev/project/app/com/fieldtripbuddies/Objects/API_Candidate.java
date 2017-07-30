package dev.project.app.com.fieldtripbuddies.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import dev.project.app.com.fieldtripbuddies.Interfaces.IAPI_Candidate;

/**
 * Created by gabe on 7/19/2017.
 */

/**
 * This object is the model for creating 'candidate' for a background check to accurate background check API.
 * See API https://developer.accuratebackground.com/?#/apidoc   ... for use
 * after submission then the api server will send back an 'Order'
 */

public class API_Candidate implements IAPI_Candidate, Parcelable{
    private String ssn, dateOfBirth, email, firstName, lastName, phone;
    private String postalCode, regionCode, countryCode, city, address;
    private HashMap<String, String> values;

    public API_Candidate(HashMap<String, String> val) {
        /*for (String key: val.keySet()){
            if(key.equalsIgnoreCase())
        }*/
        this.values = val;
    }

    public API_Candidate() {
        values = new HashMap<>();
    }

    protected API_Candidate(Parcel in) {
        ssn = in.readString();
        dateOfBirth = in.readString();
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        phone = in.readString();
        postalCode = in.readString();
        regionCode = in.readString();
        countryCode = in.readString();
        city = in.readString();
        address = in.readString();
    }

    public static final Creator<API_Candidate> CREATOR = new Creator<API_Candidate>() {
        @Override
        public API_Candidate createFromParcel(Parcel in) {
            return new API_Candidate(in);
        }

        @Override
        public API_Candidate[] newArray(int size) {
            return new API_Candidate[size];
        }
    };

    @Override
    public void setRegionCode(String reg) {
        this.regionCode = reg;
    }

    @Override
    public void setCountryCode(String cc) {
        countryCode = cc;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setDateOfBirth(String dob) {
        this.dateOfBirth = dob;
    }

    @Override
    public void setSSN(String ssn) {
        this.ssn = ssn;
    }

    @Override
    public void setFirstName(String fname) {
        this.firstName = fname;
    }

    @Override
    public void setLastName(String lname) {
        this.lastName = lname;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getSSN() {
        return ssn;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String getRegionCode() {
        return regionCode;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public JSONObject jsonify() {
        return new JSONObject(values);
    }

    @Override
    public HashMap<String, String> getValues() {
        return this.values;
    }

    @Override
    public String POSTify() throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String key: this.values.keySet()) {
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
        }
        return result.toString();
    }

    /**
     * Security error for jsonify..
     *
     * @return
     */
    public String toString() {
        return this.jsonify().toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ssn);
        parcel.writeString(dateOfBirth);
        parcel.writeString(email);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(phone);
        parcel.writeString(postalCode);
        parcel.writeString(regionCode);
        parcel.writeString(countryCode);
        parcel.writeString(city);
        parcel.writeString(address);
    }
}

package dev.project.app.com.fieldtripbuddies.Interfaces;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by gabe on 7/19/2017.
 */

public interface IAPI_Candidate {

    void setRegionCode(String reg);
    void setCountryCode(String cc);
    void setAddress(String address);
    void setCity(String city);
    void setPostalCode(String postalCode);

    void setEmail(String email);
    void setDateOfBirth(String dob);
    void setSSN(String ssn);
    void setFirstName(String fname);
    void setLastName(String lname);
    void setPhone(String phone);
    String getDateOfBirth();
    String getEmail();
    String getSSN();
    String getFirstName();
    String getLastName();
    String getPhone();
    String getPostalCode();
    String getCountryCode();
    String getRegionCode();
    String getAddress();
    String getCity();
    JSONObject jsonify();
    HashMap<String,String> getValues();
}

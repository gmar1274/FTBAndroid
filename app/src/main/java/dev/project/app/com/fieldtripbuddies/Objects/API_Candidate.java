package dev.project.app.com.fieldtripbuddies.Objects;

import org.json.JSONObject;

import java.util.HashMap;

import dev.project.app.com.fieldtripbuddies.Interfaces.IAPI_Candidate;

/**
 * Created by gabe on 7/19/2017.
 */

public class API_Candidate implements IAPI_Candidate {
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

    /**
     * Security error for jsonify..
     *
     * @return
     */
    public String toString() {
        return this.jsonify().toString();
    }
}

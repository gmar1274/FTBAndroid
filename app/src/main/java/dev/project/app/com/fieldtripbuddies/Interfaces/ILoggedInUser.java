package dev.project.app.com.fieldtripbuddies.Interfaces;

import java.util.ArrayList;

/**
 * Created by gabe on 6/25/2017.
 * This is all the authenticated information about any user that logs in to the app.
 */

public interface ILoggedInUser {
    String getEmail();
    void setEmail(String email);
    String getName();
    String getImageURL();
    void setName(String name);
    void setImageURL(String url);
    String getId();
    void setId(String id);
    boolean isFB();
    //ArrayList<String> getPic_url_list();
    //void setPic_url_list(ArrayList<String> list);

}

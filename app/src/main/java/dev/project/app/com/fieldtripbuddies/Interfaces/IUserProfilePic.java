package dev.project.app.com.fieldtripbuddies.Interfaces;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gabe on 7/12/2017.
 */

public interface IUserProfilePic {
    /**
     * Async call
     * @param iv image view
     * @param user data on location to fetch user profile pic
     */
    void fetchUserProfilePic(ImageView iv, ILoggedInUser user);

    /**
     * Async call
     * @param tv text view
     * @param user data on location to get email
     */
    void fetchUserEmailAddress(TextView tv, ILoggedInUser user);

}

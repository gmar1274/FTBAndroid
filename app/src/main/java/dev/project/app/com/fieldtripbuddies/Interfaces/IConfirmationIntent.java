package dev.project.app.com.fieldtripbuddies.Interfaces;

import android.content.Intent;

import dev.project.app.com.fieldtripbuddies.Intents.ConfirmationIntent;

/**
 * Created by gabe on 7/30/2017.
 */

public interface IConfirmationIntent {
    ConfirmationIntent getIntent(Intent intent);
}

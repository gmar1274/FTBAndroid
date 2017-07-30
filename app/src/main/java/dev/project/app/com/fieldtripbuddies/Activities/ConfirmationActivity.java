package dev.project.app.com.fieldtripbuddies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import dev.project.app.com.fieldtripbuddies.Intents.ConfirmationIntent;
import dev.project.app.com.fieldtripbuddies.Interfaces.IConfirmationIntent;
import dev.project.app.com.fieldtripbuddies.R;

public class ConfirmationActivity extends AppCompatActivity implements IConfirmationIntent {
    private ConfirmationIntent confirm_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.confirm_intent = this.getIntent(this.getIntent());
        TextView tv = (TextView)this.findViewById(R.id.confirmationTextView);
        if(confirm_intent.isFlag()){
            tv.setText("Success! Thank you for your submission. We'll be keeping in contact with you via email. We'll notify once we've confirm our final decision.");
        }else{
            tv.setText("There was an error in your background submission. You did not meet the qualifications need to be affiliated with Fieldtrip Buddies.");
        }

    }

    @Override
    public ConfirmationIntent getIntent(Intent intent) {
        return intent.getParcelableExtra(ConfirmationIntent.CONFIRMATION_INTENT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item ){
        switch (item.getItemId()){
            case android.R.id.home:
                super.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

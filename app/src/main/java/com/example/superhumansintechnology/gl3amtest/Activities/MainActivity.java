package com.example.superhumansintechnology.gl3amtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //BUTTON FOR NEW DESTINATION ACTIVITY
        Button newDestinationButton = (Button) findViewById(R.id.newDestination_button);
            newDestinationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newDestinationIntent = new Intent(getApplicationContext(), NewDestinationActivity.class);
                    startActivity(newDestinationIntent);
                }
            });


        //BUTTON FOR SAVED DESTINATIONS ACTIVITY
        Button savedDestinationButton = (Button) findViewById(R.id.savedDestinations_button);
        savedDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent savedDestinationIntent = new Intent(getApplicationContext(), SavedDestinations.class);
                startActivity(savedDestinationIntent);
            }
        });


        //BUTTON FOR PREFERENCES ACTIVITY
        Button preferencesButton = (Button) findViewById(R.id.preferences_button);
        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preferencesIntent = new Intent(getApplicationContext(), Preferences.class);
                startActivity(preferencesIntent);
            }
        });


        //BUTTON FOR CREDITS ACTIVITY
        Button creditsButton = (Button) findViewById(R.id.credits_button);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creditsIntent = new Intent(getApplicationContext(), Credits.class);
                startActivity(creditsIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

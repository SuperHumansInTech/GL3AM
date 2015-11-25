package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import superheroesintechnology.gl3am.Models.Destination;
import superheroesintechnology.gl3am.R;

public class UpdateActivity extends Activity {


    public int activationDistance;
    public Destination testDest = new Destination("3208 Marsh Rd\nSanta Rosa, CA 95403", 38.462135, -122.761644, Double.parseDouble(Integer.toString(activationDistance)));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        SharedPreferences activationDistancePrefs = getSharedPreferences("activationDistance", Context.MODE_PRIVATE);
        activationDistance = activationDistancePrefs.getInt("activationDist", 0);


        TextView destinationTextView = (TextView)findViewById(R.id.destinationResultTextView);
        TextView actDistTextView = (TextView)findViewById(R.id.defDistResultTextView);
        final TextView distFromDefTextView = (TextView)findViewById(R.id.distFromDestResultTextView);

        //for testing Destination Class only. Can be removed
        final TextView boolTextView = (TextView)findViewById((R.id.booleanResultTextView));

        destinationTextView.setText(testDest.getAddressString());
        actDistTextView.setText(Integer.toString(activationDistance));


        final Handler locationUpdateHandler = new Handler();
        locationUpdateHandler.post(new Runnable() {
            @Override
            public void run() {
                SharedPreferences startStopPrefs = getSharedPreferences("AlamrPrefernceFile", 0);
                final boolean isPressed = startStopPrefs.getBoolean("isPressed", false);

                SharedPreferences sharedLocationPref = getSharedPreferences("currentLocation", Context.MODE_PRIVATE);
                final double currentLongitude = Double.parseDouble(sharedLocationPref.getString("currentLongitude", "0.0"));
                final double currentLatitude = Double.parseDouble(sharedLocationPref.getString("currentLatitude", "0.0"));

                if (testDest.verifyDistance(currentLongitude, currentLatitude)) {
                    boolTextView.setText("True");
                    return;
                } else {
                    boolTextView.setText("False");
                }

                distFromDefTextView.setText(testDest.getDistFromCurLoc());

                if (isPressed) {
                    locationUpdateHandler.postDelayed(this, 2000);
                }
            }
        });




        ImageView alarmImageView = (ImageView)findViewById(R.id.alarmStatusImage);
        ImageView homeImageView = (ImageView)findViewById(R.id.homeStatusImage);
        ImageView favoritesImageView = (ImageView)findViewById(R.id.favoritesStatusImage);
        ImageView messageImageView = (ImageView)findViewById(R.id.messageStatusImage);



        alarmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateActivity.this, AlarmActivity.class));
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateActivity.this, MapsActivity.class));
            }
        });

        favoritesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateActivity.this, FavoritesActivity.class));
            }
        });

        messageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateActivity.this, MessageActivity.class));
            }
        });

    }
}

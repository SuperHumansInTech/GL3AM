package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import superheroesintechnology.gl3am.Activities.MapsActivity;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.StorageClient;


public class SplashActivity extends Activity {

    private TextView titleTextView;
    private ImageView targetImageView;
    private ImageView alarmImageView;
    private ImageView messageImageView;
    private ImageView leftLineImageView;
    private ImageView rightLineImageView;
    private Location Curr_location = new Location("");
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Curr_location.setLatitude(0);
        Curr_location.setLongitude(0);

        final LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final LocationListener listener = new LocationListener() {


            @Override
            public void onLocationChanged(Location location) {

                Curr_location.setLatitude(location.getLatitude());
                Curr_location.setLongitude(location.getLongitude());
                //latitude = location.getLatitude();
                //longitude = location.getLongitude();

                SharedPreferences initLocationPrefs = getSharedPreferences("initialLocation", Context.MODE_PRIVATE);
                SharedPreferences.Editor initLocationPrefsEditor = initLocationPrefs.edit();
                initLocationPrefsEditor.putString("initialLat", Double.toString(Curr_location.getLatitude()));
                initLocationPrefsEditor.putString("initialLng", Double.toString(Curr_location.getLongitude()));
                initLocationPrefsEditor.apply();




                //sharedLocationEditor.putString("currentLatitude", Double.toString(Curr_location.getLat()));
                //sharedLocationEditor.putString("currentLongitude", Double.toString(Curr_location.getLng()));

                //editor.putString("currentLatitude", Double.toString(latitude));
                //editor.putString("currentLongitude", Double.toString(longitude));
                //sharedLocationEditor.apply();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (Curr_location.getLatitude() != 0 && Curr_location.getLongitude() != 0) {
            locManager.removeUpdates(listener);
        } else {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        }

        SharedPreferences initLocationPrefs = getSharedPreferences("initialLocation", Context.MODE_PRIVATE);
        SharedPreferences.Editor initLocationPrefsEditor = initLocationPrefs.edit();
        initLocationPrefsEditor.putString("initialLat", Double.toString(Curr_location.getLatitude()));
        initLocationPrefsEditor.putString("initialLng", Double.toString(Curr_location.getLongitude()));
        initLocationPrefsEditor.apply();


        final StorageClient StoreClient = new StorageClient(this, "default");
        StoreClient.purgeCurrent();

        Typeface titleTypeFace = Typeface.createFromAsset(getAssets(), "Agency_FB.ttf");
        titleTextView = (TextView) findViewById(R.id.appTitleSplashTextView);
        targetImageView = (ImageView) findViewById(R.id.targetSplashImageView);
        alarmImageView = (ImageView) findViewById(R.id.alarmSplashImageView);
        messageImageView = (ImageView) findViewById(R.id.messageSplashImageView);
        leftLineImageView = (ImageView) findViewById(R.id.leftLineImageView);
        rightLineImageView = (ImageView) findViewById(R.id.right_line);

        titleTextView.setTypeface(titleTypeFace);

        titleTextView.setVisibility(View.VISIBLE);
        targetImageView.setVisibility(View.VISIBLE);
        alarmImageView.setVisibility(View.VISIBLE);
        messageImageView.setVisibility(View.VISIBLE);
        leftLineImageView.setVisibility(View.VISIBLE);
        rightLineImageView.setVisibility(View.VISIBLE);


        Animation targetAnimationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);
        targetImageView.startAnimation(targetAnimationFadeIn);

        targetAnimationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.alarm_fade_in);
        alarmImageView.startAnimation(targetAnimationFadeIn);

        targetAnimationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.message_fade_in);
        messageImageView.startAnimation(targetAnimationFadeIn);

        targetAnimationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.left_line_fadein);
        leftLineImageView.startAnimation(targetAnimationFadeIn);

        targetAnimationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.right_line_fadein);
        rightLineImageView.startAnimation(targetAnimationFadeIn);

        targetAnimationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.title_fade_in);
        titleTextView.startAnimation(targetAnimationFadeIn);

//
        targetAnimationFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                finish();
                startActivity(new Intent(getBaseContext(), MapsActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }

}


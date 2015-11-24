package superheroesintechnology.gl3am.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import superheroesintechnology.gl3am.Models.Destination;
import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.R;

public class AlarmActivity extends Activity{

    private double longitude;
    private double latitude;
    private SeekBar seekBar;
    private TextView distanceText;
    private ImageView startCancelImageView;
    private static final String ALARM_PREFS = "AlamrPrefernceFile";
    private TextView startCancelTextView;
    private boolean isPressed = false;
    public boolean isSeekChanged = false;
    public int activationDistance = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);



        final Destination testDest = new Destination("3208 Marsh Rd\nSanta Rosa, CA 95403", 38.462135, -122.761644, activationDistance);

        startCancelImageView = (ImageView) findViewById(R.id.startStopAlarmImageView);
        startCancelTextView = (TextView) findViewById(R.id.startStopText);

        View.OnClickListener startCancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


<<<<<<< HEAD
=======

>>>>>>> 0aec91eda06acc87d9beaaca2686eeee12193897
                SharedPreferences startStopPrefs = getSharedPreferences(ALARM_PREFS, 0);
                SharedPreferences.Editor startStopEditor = startStopPrefs.edit();
                //testing to see if I can disable something as clickable on another activity
//                ImageView unclickableTest = (ImageView)findViewById(R.id.homeAlarmImage);

                if (getIsPressed()) {
                    boolean makeFalse = false;
                    setIsPressed(makeFalse);
                    startCancelImageView.setBackgroundResource(R.drawable.start);
                    startCancelTextView.setText(R.string.start);
                    startStopEditor.putBoolean("isPressed", false);

                    startStopEditor.putBoolean("bool", makeFalse);
                    startStopEditor.putString("textState", startCancelTextView.getText().toString());
                    startStopEditor.commit();} else {
                    boolean makeTrue = true;
                    setIsPressed(makeTrue);
                    startStopEditor.putBoolean("isPressed", true);

                    final SharedPreferences sharedLocationPref = getSharedPreferences("currentLocation", Context.MODE_PRIVATE);


                    LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    //CREATE LOCATION LISTENER
                    LocationListener listener = new LocationListener() {


                        @Override
                        public void onLocationChanged(Location location) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();



                            SharedPreferences.Editor editor = sharedLocationPref.edit();
                            editor.putString("currentLatitude", Double.toString(latitude));
                            editor.putString("currentLongitude", Double.toString(longitude));
                            editor.apply();
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

                    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);

                    //PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                      //  PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CPUWakeLock");
                        //wl.acquire();
                    Runnable locationRunnable = new Runnable() {
                        @Override
                        public void run()  {


                            /*
                                THIS HANDLER ALLOWS THE LOCATION TO BE UPDATED EVERY X SECONDS,
                                LOOPING UNTIL THE "START BUTTON IS NO LONGER PRESSED. ELIMINATES
                                NEED FOR A FOR LOOP.
                             */
                            final Handler locationUpdateHandler = new Handler(Looper.getMainLooper());
                            locationUpdateHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SharedPreferences startStopPrefs = getSharedPreferences("AlamrPrefernceFile", 0);
                                    final boolean isPressed = startStopPrefs.getBoolean("isPressed", false);

                                    SharedPreferences sharedLocationPref = getSharedPreferences("currentLocation", Context.MODE_PRIVATE);
                                    final double currentLongitude = Double.parseDouble(sharedLocationPref.getString("currentLongitude", "0.0"));
                                    final double currentLatitude = Double.parseDouble(sharedLocationPref.getString("currentLatitude", "0.0"));


                                    if (testDest.verifyDistance(currentLongitude, currentLatitude)) {
                                        //PowerManager.WakeLock TempWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP |
                                          //      PowerManager.ON_AFTER_RELEASE, "TempWakeLock");
                                        //TempWakeLock.acquire();

                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                //alarmSound.start();
                                                Intent popUpTest = new Intent(AlarmActivity.this, AlarmLaunchActivity.class);
                                                startService(popUpTest);
                                                //alarmSound.pause();
                                            }
                                        });

                                        final SharedPreferences.Editor startStopEditor = startStopPrefs.edit();
                                        startStopEditor.putBoolean("bool", false);
                                        startStopEditor.commit();
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                boolean makeFalse = false;
                                                setIsPressed(makeFalse);
                                                //alarmSound.pause();
                                                //alarmSound.seekTo(0);
                                                startCancelImageView.setBackgroundResource(R.drawable.start);
                                                startCancelTextView.setText(R.string.start);
                                                startStopEditor.putBoolean("isPressed", false);
                                            }
                                        });

                                        //TempWakeLock.release();
                                        return;

                                    }

                                    if (isPressed) {
                                        //DELAY THE LOCATION UPDATE FOR 2 SECONDS
                                        locationUpdateHandler.postDelayed(this, 2000);
                                    }
                                }
                            });
                        }
                    };

                    Thread locationUpdateThread = new Thread(locationRunnable);
                    locationUpdateThread.start();
                    //wl.release();



                    startCancelImageView.setBackgroundResource(R.drawable.cancel);
                    startCancelTextView.setText(R.string.cancel);
                    startStopEditor.putBoolean("bool", makeTrue);
                    startStopEditor.putString("textState", startCancelTextView.getText().toString());
                    startStopEditor.commit();
                }

            }
        };

        startCancelImageView.setOnClickListener(startCancelListener);
        SharedPreferences sharedPreferences = getSharedPreferences(ALARM_PREFS, 0);

        if (sharedPreferences.contains("bool")){
            setIsPressed(sharedPreferences.getBoolean("bool", false));
            if(getIsPressed()){
                startCancelImageView.setBackgroundResource(R.drawable.cancel);
                startCancelTextView.setText(R.string.cancel);
            }
            else{
                startCancelImageView.setBackgroundResource(R.drawable.start);
                startCancelTextView.setText(R.string.start);
            }
        }


        seekBar = (SeekBar)findViewById(R.id.distanceAlarmSeekbar);
        distanceText = (TextView)findViewById(R.id.distanceAlarmTextView);

        if (sharedPreferences.contains("miles")){
            seekBar.setProgress(sharedPreferences.getInt("miles", 1));
        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            SharedPreferences seekBarPrefeernces = getSharedPreferences(ALARM_PREFS, 0);
            SharedPreferences.Editor seekBarEditor = seekBarPrefeernces.edit();
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final int MIN_VALUE = 1;

//              THIS IF-STATEMENT ONLY SETS testDest's ACTIVATION DISTANCE
//                  IF THE START BUTTON HAS NOT BEEN PRESSED
                if (!isPressed) {
                    testDest.setDistFromDest(progress);
                }

                activationDistance = seekBar.getProgress();
                isSeekChanged = true;
                SharedPreferences activationDistancePrefs = getSharedPreferences("activationDistance", Context.MODE_PRIVATE);
                SharedPreferences.Editor activationDistanceEditor = activationDistancePrefs.edit();
                activationDistanceEditor.putInt("activationDist", activationDistance);
                activationDistanceEditor.commit();

                if(progress < MIN_VALUE){
                    seekBar.setProgress(MIN_VALUE);

                }
                distanceText.setText(seekBar.getProgress() + "");
                seekBarEditor.putInt("miles", seekBar.getProgress());
                seekBarEditor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                //Toast.makeText(getApplicationContext(), "Seekbar in use", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final ImageView homeImageView = (ImageView)findViewById(R.id.homeAlarmImage);
        ImageView favoritesImageView = (ImageView)findViewById(R.id.favoritesAlarmImage);
        ImageView statusImageView = (ImageView)findViewById(R.id.statusAlarmImage);
        ImageView messageImageView = (ImageView)findViewById(R.id.messageAlarmImage);



        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlarmActivity.this, MapsActivity.class));
            }
        });

        favoritesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AlarmActivity.this, FavoritesActivity.class));
            }
        });

        statusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlarmActivity.this, UpdateActivity.class));
            }
        });

        messageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlarmActivity.this, MessageActivity.class));
            }
        });
    }

    public void setIsPressed(boolean useThis){
        this.isPressed = useThis;
    }

    public boolean getIsPressed(){
        return this.isPressed;
    }

}

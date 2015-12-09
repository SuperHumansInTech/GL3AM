package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import superheroesintechnology.gl3am.Models.AlarmModel;
import superheroesintechnology.gl3am.Models.Destination;
import superheroesintechnology.gl3am.Models.LatLngModel;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.StorageClient;

public class UpdateActivity extends Activity {


    private boolean isPressed = false;
    public int counter = 4;
    private static final String ALARM_PREFS = "AlarmPreferenceFile";
    private LatLngModel Curr_location = new LatLngModel();
    public int activationDistance;
    private ImageView startCancelImageView;
    private TextView startCancelTextView;
    private AlarmModel Alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final StorageClient StoreClient = new StorageClient(this, "default");
        setContentView(R.layout.activity_update);
        Alarm = StoreClient.getCurrAlarm(this);


        Intent intent = getIntent();
        final boolean sendSMSBool = intent.getBooleanExtra("msg?", false);
        final boolean alrmBool = intent.getBooleanExtra("alrm?", true);

        final LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        startCancelImageView = (ImageView) findViewById(R.id.startStopAlarmImageView);
        startCancelTextView = (TextView) findViewById(R.id.startStopText);

        //SharedPreferences activationDistancePrefs = getSharedPreferences("activationDistance", Context.MODE_PRIVATE);
        //activationDistance = activationDistancePrefs.getInt("activationDist", 1);
        //final Destination testDest = new Destination("3208 Marsh Rd\nSanta Rosa, CA 95403", 38.462135, -122.761644, Double.parseDouble(Integer.toString(activationDistance)));



        TextView destinationTextView = (TextView) findViewById(R.id.destinationResultTextView);
        TextView actDistTextView = (TextView) findViewById(R.id.defDistResultTextView);
        final TextView distFromDefTextView = (TextView) findViewById(R.id.distFromDestResultTextView);

        //for testing Destination Class only. Can be removed
        final TextView boolTextView = (TextView) findViewById((R.id.booleanResultTextView));

        destinationTextView.setText(Alarm.getAddress_string());
        actDistTextView.setText(Double.toString(Alarm.getActivation_distance()) + " mi.");


        final Handler locationUpdateHandler = new Handler();
        locationUpdateHandler.post(new Runnable() {
            @Override
            public void run() {
                SharedPreferences startStopPrefs = getSharedPreferences("AlamrPrefernceFile", 0);
                final boolean isPressed = startStopPrefs.getBoolean("isPressed", false);

//                SharedPreferences sharedLocationPref = getSharedPreferences("currentLocation", Context.MODE_PRIVATE);
//                final double currentLongitude = Double.parseDouble(sharedLocationPref.getString("currentLongitude", "0.0"));
//                final double currentLatitude = Double.parseDouble(sharedLocationPref.getString("currentLatitude", "0.0"));

                if (Alarm.verifyDistance()) {
                    boolTextView.setText("True");
                    return;
                } else {
                    boolTextView.setText("False");
                }

                distFromDefTextView.setText(Alarm.getDistance_leftString() + " mi.");

                if (isPressed) {
                    locationUpdateHandler.postDelayed(this, 2000);
                }
            }
        });

        View.OnClickListener startCancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences startStopPrefs = getSharedPreferences(ALARM_PREFS, 0);
                SharedPreferences.Editor startStopEditor = startStopPrefs.edit();
                //testing to see if I can disable something as clickable on another activity
//                ImageView unclickableTest = (ImageView)findViewById(R.id.homeAlarmImage);

                if (getIsPressed()) {
                    // boolean makeFalse = false;
                    setIsPressed(false);
                    startCancelImageView.setBackgroundResource(R.drawable.start);
                    startCancelTextView.setText(R.string.start);
                    startStopEditor.putBoolean("isPressed", false);

                    startStopEditor.putBoolean("bool", false);
                    startStopEditor.putString("textState", startCancelTextView.getText().toString());
                    startStopEditor.commit();
                } else {
                    //boolean makeTrue = true;
                    setIsPressed(true);
                    startStopEditor.putBoolean("isPressed", true);


                    //LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    //CREATE LOCATION LISTENER
                    LocationListener listener = new LocationListener() {


                        @Override
                        public void onLocationChanged(Location location) {

                            Curr_location.setLat(location.getLatitude());
                            Curr_location.setLng(location.getLongitude());
                            StoreClient.setCurrLocation(Curr_location); //Temporary, this will be updating the AlarmModel's current location LatLng rather than this. - ZBrester

                            //latitude = location.getLatitude();
                            //longitude = location.getLongitude();


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

                    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);

                    //PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    //  PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CPUWakeLock");
                    //wl.acquire();
                    Runnable locationRunnable = new Runnable() {
                        @Override
                        public void run() {


                                /*
                                    THIS HANDLER ALLOWS THE LOCATION TO BE UPDATED EVERY X SECONDS,
                                    LOOPING UNTIL THE "START BUTTON IS NO LONGER PRESSED. ELIMINATES
                                    NEED FOR A FOR LOOP.
                                 */
                            final Handler locationUpdateHandler = new Handler(Looper.getMainLooper());
                            locationUpdateHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    SharedPreferences startStopPrefs = getSharedPreferences("AlarmPreferenceFile", 0);
                                    final boolean isPressed = startStopPrefs.getBoolean("isPressed", false);

                                    //SharedPreferences sharedLocationPref = getSharedPreferences("currentLocation", Context.MODE_PRIVATE);
                                    //final double currentLongitude = Double.parseDouble(sharedLocationPref.getString("currentLongitude", "0.0"));
                                    //final double currentLatitude = Double.parseDouble(sharedLocationPref.getString("currentLatitude", "0.0"));


                                    if (Alarm.verifyDistance()) {
                                        //PowerManager.WakeLock TempWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP |
                                        //      PowerManager.ON_AFTER_RELEASE, "TempWakeLock");
                                        //TempWakeLock.acquire();

                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                //alarmSound.start();
                                                Intent popUpTest = new Intent(UpdateActivity.this, AlarmLaunchActivity.class);
                                                if (sendSMSBool) {
                                                    if (alrmBool) {
                                                        popUpTest.putExtra("alrm?", true);
                                                    } else {
                                                        popUpTest.putExtra("alrm?", false);
                                                    }
                                                    popUpTest.putExtra("msg?", true);
                                                    startService(popUpTest);
                                                } if (!sendSMSBool) {
                                                    if (alrmBool) {
                                                        popUpTest.putExtra("alrm?", true);
                                                    } else {
                                                        popUpTest.putExtra("alrm?", false);
                                                    }
                                                    popUpTest.putExtra("msg?", false);
                                                    startService(popUpTest);
                                                }

                                                //alarmSound.pause();
                                            }
                                        });


                                        final SharedPreferences.Editor startStopEditor = startStopPrefs.edit();
                                        startStopEditor.putBoolean("bool", false);
                                        startStopEditor.commit();
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                //boolean makeFalse = false;
                                                setIsPressed(false);
                                                //alarmSound.pause();
                                                //alarmSound.seekTo(0);
                                                startCancelImageView.setBackgroundResource(R.drawable.start);
                                                startCancelTextView.setText(R.string.start);
                                                startStopEditor.putBoolean("isPressed", false);
                                            }
                                        });

                                        //TempWakeLock.release();
                                        return;

                                    } else {
                                        counter++;
                                        if (counter >= 5) {


                                            Toast.makeText(getApplicationContext(), "Distance remaining: "
                                                    + Alarm.getDistance_left(), Toast.LENGTH_LONG).show();
                                            counter = 0;
                                        }
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
                    startStopEditor.putBoolean("bool", true);
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

    public void setIsPressed(boolean useThis){
        this.isPressed = useThis;
    }

    public boolean getIsPressed(){
        return this.isPressed;
    }

//    public void trackLocation () {
//        final LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        LocationListener listener = new LocationListener() {
//
//
//            @Override
//            public void onLocationChanged(Location location) {
//
//                Curr_location.setLat(location.getLatitude());
//                Curr_location.setLng(location.getLongitude());
//                //StoreClient.setCurrLocation(Curr_location); //Temporary, this will be updating the AlarmModel's current location LatLng rather than this. - ZBrester
//
//                //latitude = location.getLatitude();
//                //longitude = location.getLongitude();
//
//
//
//
//                //sharedLocationEditor.putString("currentLatitude", Double.toString(Curr_location.getLat()));
//                //sharedLocationEditor.putString("currentLongitude", Double.toString(Curr_location.getLng()));
//
//                //editor.putString("currentLatitude", Double.toString(latitude));
//                //editor.putString("currentLongitude", Double.toString(longitude));
//                //sharedLocationEditor.apply();
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//
//        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
//
//        //PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        //  PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CPUWakeLock");
//        //wl.acquire();
//        Runnable locationRunnable = new Runnable() {
//            @Override
//            public void run()  {
//
//
//                            /*
//                                THIS HANDLER ALLOWS THE LOCATION TO BE UPDATED EVERY X SECONDS,
//                                LOOPING UNTIL THE "START BUTTON IS NO LONGER PRESSED. ELIMINATES
//                                NEED FOR A FOR LOOP.
//                             */
//                final Handler locationUpdateHandler = new Handler(Looper.getMainLooper());
//                locationUpdateHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        SharedPreferences startStopPrefs = getSharedPreferences("AlarmPreferenceFile", 0);
//                        final boolean isPressed = startStopPrefs.getBoolean("isPressed", false);
//
//                        //SharedPreferences sharedLocationPref = getSharedPreferences("currentLocation", Context.MODE_PRIVATE);
//                        //final double currentLongitude = Double.parseDouble(sharedLocationPref.getString("currentLongitude", "0.0"));
//                        //final double currentLatitude = Double.parseDouble(sharedLocationPref.getString("currentLatitude", "0.0"));
//
//
//                        if (destination.verifyDistance(Curr_location.getLng(), Curr_location.getLat())) {
//                            //PowerManager.WakeLock TempWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP |
//                            //      PowerManager.ON_AFTER_RELEASE, "TempWakeLock");
//                            //TempWakeLock.acquire();
//
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//                                    //alarmSound.start();
//                                    Intent popUpTest = new Intent(UpdateActivity.this, AlarmLaunchActivity.class);
//                                    startService(popUpTest);
//                                    //alarmSound.pause();
//                                }
//                            });
//
//
//                            final SharedPreferences.Editor startStopEditor = startStopPrefs.edit();
//                            startStopEditor.putBoolean("bool", false);
//                            startStopEditor.commit();
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//                                    //boolean makeFalse = false;
//                                    setIsPressed(false);
//                                    //alarmSound.pause();
//                                    //alarmSound.seekTo(0);
//                                    startCancelImageView.setBackgroundResource(R.drawable.start);
//                                    startCancelTextView.setText(R.string.start);
//                                    startStopEditor.putBoolean("isPressed", false);
//                                }
//                            });
//
//                            //TempWakeLock.release();
//                            return;
//
//                        }
//
//                        else {
//                            counter++;
//                            if (counter >= 5) {
//
//
//                                Toast.makeText(getApplicationContext(), "Distance remaining: "
//                                        + destination.getDistFromCurLoc(), Toast.LENGTH_LONG).show();
//                                counter = 0;
//                            }
//                        }
//
//
//                        if (isPressed) {
//                            //DELAY THE LOCATION UPDATE FOR 2 SECONDS
//                            locationUpdateHandler.postDelayed(this, 2000);
//                        }
//                    }
//                });
//            }
//        };
//
//        Thread locationUpdateThread = new Thread(locationRunnable);
//        locationUpdateThread.start();
//
//        startCancelImageView.setBackgroundResource(R.drawable.cancel);
//        startCancelTextView.setText(R.string.cancel);
//        startStopEditor.putBoolean("bool", true);
//        startStopEditor.putString("textState", startCancelTextView.getText().toString());
//        startStopEditor.commit();
//    }
}




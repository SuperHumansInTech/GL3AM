package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import superheroesintechnology.gl3am.Models.Destination;
import superheroesintechnology.gl3am.Models.LatLngModel;
import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.Models.SearchResultModel;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.APIClient;
import superheroesintechnology.gl3am.Services.StorageClient;


public class AlarmActivity extends Activity{

    //private double longitude;
    //private double latitude;
    private LatLngModel Curr_location = new LatLngModel();
    private LatLngModel Dest_coords;
    private SeekBar seekBar;
    private TextView distanceText;
    private ImageView startCancelImageView;
    private static final String ALARM_PREFS = "AlarmPreferenceFile";
    private TextView startCancelTextView;
    private boolean isPressed = false;
    public boolean isSeekChanged = false;
    public int activationDistance = 1;
    private Button searchButton;
    private EditText searchLoc;
    private Button smsButton;
    public int counter = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final StorageClient StoreClient = new StorageClient(this, "default");
        StoreClient.purgeCurrent(); //Avoid sending old SMS.
        final LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location temp_loc = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        //final SharedPreferences sharedLocationPref = getSharedPreferences("currentLocation", Context.MODE_PRIVATE);
        //final SharedPreferences.Editor sharedLocationEditor = sharedLocationPref.edit();
        final Gson gson = new GsonBuilder().create();


        if(temp_loc != null) {
            Curr_location.setLat(temp_loc.getLatitude());
            Curr_location.setLng(temp_loc.getLongitude());
            StoreClient.setCurrLocation(Curr_location);
            //I'm not sure if we really even need a Curr_location variable... - ZBrester
            //sharedLocationEditor.putString("currentLatitude", Double.toString(Curr_location.getLat()));
            //sharedLocationEditor.putString("currentLongitude", Double.toString(Curr_location.getLng()));
            //sharedLocationEditor.apply();
        }
        else {
            //Curr_location.setLat(38);
           // Curr_location.setLng(-122.8);
           // sharedLocationEditor.putString("currentLatitude", Double.toString(Curr_location.getLat()));
           // sharedLocationEditor.putString("currentLongitude", Double.toString(Curr_location.getLng()));
           // sharedLocationEditor.apply();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

// SET UP EDITTEXT FIELDS FOR SMS
        final EditText smsNumber = (EditText) findViewById(R.id.smsNumberField);
        final EditText smsText = (EditText) findViewById(R.id.smsTextField);
        final TextView smsNumberView = (TextView) findViewById(R.id.smsNumberField);
        final TextView smsTextView = (TextView) findViewById(R.id.smsTextField);
        smsButton = (Button) findViewById(R.id.saveSmsButton);

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// IF USER HAS NOT ENTERED SMS DATA, RETURN
                if (smsNumberView.getText() == null || smsTextView.getText() == null) {
                    return;
                }

// IF USER HAS ENTERED SMS DATA, GET THE DATA
                //final String smsNumberString = smsNumber.getText().toString();
                //final String smsTextString = smsText.getText().toString();

                SMSMessage stored = new SMSMessage(smsNumber.getText().toString(), smsText.getText().toString() );

// SAVE SMS INFO (USING SHAREDPREFS): NUMBER AND TEXT
                StoreClient.setCurrSMS(stored);
                StoreClient.addSMS(stored);
                //String SMSJson = gson.toJson(stored);
                //Toast.makeText(getApplicationContext(), SMSJson, Toast.LENGTH_LONG).show();
                //SharedPreferences sharedSMSPrefs = getSharedPreferences("smsInfo", Context.MODE_PRIVATE);
                //SharedPreferences.Editor sharedSMSEditor = sharedSMSPrefs.edit();
                //sharedSMSEditor.putString("sms", SMSJson);
                //sharedSMSEditor.commit();
                /*
                SharedPreferences sharedSMSPrefs = getSharedPreferences("smsInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor sharedSMSEditor = sharedSMSPrefs.edit();
                sharedSMSEditor.putString("number", smsNumberString);
                sharedSMSEditor.putString("text", smsTextString);
                sharedSMSEditor.commit();
                */

            }
        });

        searchLoc = (EditText)findViewById(R.id.locationSearchFieldAlarm);
        searchButton = (Button) findViewById(R.id.destSearchButton);



        //final Destination destination = new Destination("3208 Marsh Rd\nSanta Rosa, CA 95403", 38.462135, -122.761644, activationDistance);
        final Destination destination = new Destination();

        startCancelImageView = (ImageView) findViewById(R.id.startStopAlarmImageView);
        startCancelTextView = (TextView) findViewById(R.id.startStopText);
        final TextView searchDestTextView = (TextView) findViewById(R.id.locationSearchFieldAlarm);

//      SETTING UP DESTINATION SEARCH BUTTON onCLICKLISTENER
        searchButton.setOnClickListener(new View.OnClickListener() {

//          MAKES API CALL onCLICK

            @Override
            public void onClick(View v) {
                if(searchDestTextView.getText() == null) {
                    return;
                }



                APIClient.getDirectionsProvider()
                        .getDirections(StoreClient.getCurrLocation().getCoordHtmlString(), TextUtils.htmlEncode(searchDestTextView.getText().toString()))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<SearchResultModel>() {

                            @Override
                            public void onCompleted() {

                            }
                            @Override
                            public void onError(Throwable e) {int i = 0; }

                            @Override
                            public void onNext(SearchResultModel searchResultModel) {
                                Dest_coords = searchResultModel.getDestinationCoords();
                                destination.setDoubLat(Dest_coords.getLat());
                                destination.setDoubLong(Dest_coords.getLng());
                                destination.setAddressString(searchResultModel.getSearchResults().get(0).getLeg(0).getEnd_address());
                                Toast.makeText(getApplicationContext(), "API Call successful. Destination coordinates:"
                                        + Dest_coords.getCoordString(), Toast.LENGTH_LONG).show();
                            }
                        });
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
                    startStopEditor.commit();}
                else {
                    //This checks to make sure there is an actual destination coordinate before comparing.
                    //Sends a message if there is not. The 1000 is the default, un-assigned value of the lat and long.
                    //Check Destination.java for why. ZBrester
                    if(destination.getDoubLat() == 1000 | destination.getDoubLong() == 1000 ) {
                        Toast.makeText(getApplicationContext(), "Error! No assigned destination." , Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(StoreClient.getCurrSMS() == null) {
                        Toast.makeText(getApplicationContext(), "Error! No SMS Message set." , Toast.LENGTH_LONG).show();
                        return;
                    }
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
                                    SharedPreferences startStopPrefs = getSharedPreferences("AlarmPreferenceFile", 0);
                                    final boolean isPressed = startStopPrefs.getBoolean("isPressed", false);

                                    //SharedPreferences sharedLocationPref = getSharedPreferences("currentLocation", Context.MODE_PRIVATE);
                                    //final double currentLongitude = Double.parseDouble(sharedLocationPref.getString("currentLongitude", "0.0"));
                                    //final double currentLatitude = Double.parseDouble(sharedLocationPref.getString("currentLatitude", "0.0"));


                                    if (destination.verifyDistance(StoreClient.getCurrLocation().getLng(), StoreClient.getCurrLocation().getLat())) {
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

                                    }

                                    else {
                                        counter++;
                                        if (counter >= 5) {


                                            Toast.makeText(getApplicationContext(), "Distance remaining: "
                                                    + destination.getDistFromCurLoc(), Toast.LENGTH_LONG).show();
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


        seekBar = (SeekBar)findViewById(R.id.distanceAlarmSeekbar);
        distanceText = (TextView)findViewById(R.id.distanceAlarmTextView);

        if (sharedPreferences.contains("miles")){
            seekBar.setProgress(sharedPreferences.getInt("miles", 1));
        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            SharedPreferences seekBarPreferences = getSharedPreferences(ALARM_PREFS, 0);
            SharedPreferences.Editor seekBarEditor = seekBarPreferences.edit();
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final int MIN_VALUE = 1;

//              THIS IF-STATEMENT ONLY SETS destination's ACTIVATION DISTANCE
//                  IF THE START BUTTON HAS NOT BEEN PRESSED
                if (!isPressed) {
                    destination.setActivation_distance(progress);
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

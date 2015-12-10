package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import superheroesintechnology.gl3am.Models.AlarmModel;
import superheroesintechnology.gl3am.Models.LatLngModel;
import superheroesintechnology.gl3am.Models.LegModel;
import superheroesintechnology.gl3am.Models.RouteModel;
import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.Models.SearchResultModel;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.APIClient;
import superheroesintechnology.gl3am.Services.StorageClient;


public class AlarmActivity extends Activity implements AdapterView.OnItemSelectedListener {


    private LatLngModel Curr_location = new LatLngModel();
    private SeekBar seekBar;
    private TextView distanceText;
    private static final String ALARM_PREFS = "AlarmPreferenceFile";
    private boolean isPressed = false;
    public boolean isSeekChanged = false;
    private ImageView searchButton;
    private EditText searchLoc;
    private Spinner spinner;
    private String[] alarmSpinnerElems = {"Alarm Only", "Message Only", "Alarm & Message"};
    public AlarmModel currAlarmModel;
    private boolean saveInfoBool = false;
    private boolean useInfoBool = false;
    private ImageView nextButton;
    private ImageView saveToFavButton;
    private  StorageClient StoreClient = null;
    int alarm_flags = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StoreClient = new StorageClient(this, "default");
        //StoreClient.purgeCurrent(); //Avoid sending old SMS.

// Get user's initial location from SplashActivity (Using SharedPreferences)
        //SharedPreferences initLocationPrefs = getSharedPreferences("initialLocation", Context.MODE_PRIVATE);
        //initialLocation.setLatitude(Double.parseDouble(initLocationPrefs.getString("initialLat", "")));
       //initialLocation.setLongitude(Double.parseDouble(initLocationPrefs.getString("initialLng", "")));

        final LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location temp_loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //final SharedPreferences sharedLocationPref = getSharedPreferences("currentLocation", Context.MODE_PRIVATE);
        //final SharedPreferences.Editor sharedLocationEditor = sharedLocationPref.edit();
        //final Gson gson = new GsonBuilder().create();


        if(temp_loc != null) {
            Curr_location.setLat(temp_loc.getLatitude());
            Curr_location.setLng(temp_loc.getLongitude());
            StoreClient.setCurrLocation(Curr_location);
            //StoreClient.setCurrLocation(Curr_location);
            //I'm not sure if we really even need a Curr_location variable... - ZBrester
            //sharedLocationEditor.putString("currentLatitude", Double.toString(Curr_location.getLat()));
            //sharedLocationEditor.putString("currentLongitude", Double.toString(Curr_location.getLng()));
            //sharedLocationEditor.apply();
        }
        else {
            Curr_location = StoreClient.getCurrLocation();
            if(Curr_location.getLat() == 0 && Curr_location.getLng() == 0 ) {
                Curr_location.setLat(38);
                Curr_location.setLng(-122.8);
                StoreClient.setCurrLocation(Curr_location);
            }
            //Curr_location.setLat(38);
            //Curr_location.setLng(-122.8);
            //StoreClient.setCurrLocation(Curr_location);

           // sharedLocationEditor.putString("currentLatitude", Double.toString(Curr_location.getLat()));
           // sharedLocationEditor.putString("currentLongitude", Double.toString(Curr_location.getLng()));
           // sharedLocationEditor.apply();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        //Curr_location.setLat(38);
        //Curr_location.setLng(-122.8);



// Initialize and set up the spinner to pick alarm, msg, or alarm and msg
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.row_spinner, R.id.spinnerTxt, alarmSpinnerElems);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

// SET UP EDITTEXT FIELDS FOR SMS
        //final EditText smsNumber = (EditText) findViewById(R.id.smsNumberField);
        // final EditText smsText = (EditText) findViewById(R.id.smsTextField);
        //final TextView smsNumberView = (TextView) findViewById(R.id.smsNumberField);
        // final TextView smsTextView = (TextView) findViewById(R.id.smsTextField);

// smsButton = (Button) findViewById(R.id.saveSmsButton);
      /*  //smsButton.setVisibility(View.INVISIBLE);

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        //alarmSound.start();
                        Intent popUpTest = new Intent(AlarmActivity.this, SMSPopActivity.class);
                        startActivity(popUpTest);
                        //alarmSound.pause();
                    }
                });
// IF USER HAS NOT ENTERED SMS DATA, RETURN

                if (smsNumberView.getText() == null || smsTextView.getText() == null) {
                    return;
                }
                else {


// IF USER HAS ENTERED SMS DATA, GET THE DATA
                    //final String smsNumberString = smsNumber.getText().toString();
                    //final String smsTextString = smsText.getText().toString();

                    SMSMessage stored = new SMSMessage(smsNumber.getText().toString(), smsText.getText().toString());

// SAVE SMS INFO (USING SHAREDPREFS): NUMBER AND TEXT
                    StoreClient.setCurrSMS(stored);
                    StoreClient.addSMS(stored);
                }
                //String SMSJson = gson.toJson(stored);
                //Toast.makeText(getApplicationContext(), SMSJson, Toast.LENGTH_LONG).show();
                //SharedPreferences sharedSMSPrefs = getSharedPreferences("smsInfo", Context.MODE_PRIVATE);
                //SharedPreferences.Editor sharedSMSEditor = sharedSMSPrefs.edit();
                //sharedSMSEditor.putString("sms", SMSJson);
                //sharedSMSEditor.commit();

                SharedPreferences sharedSMSPrefs = getSharedPreferences("smsInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor sharedSMSEditor = sharedSMSPrefs.edit();
                sharedSMSEditor.putString("number", smsNumberString);
                sharedSMSEditor.putString("text", smsTextString);
                sharedSMSEditor.commit();


            }
        });
        */

        searchLoc = (EditText)findViewById(R.id.locationSearchFieldAlarm);
        searchButton = (ImageView) findViewById(R.id.destSearchButton);



        //final Destination destination = new Destination("3208 Marsh Rd\nSanta Rosa, CA 95403", 38.462135, -122.761644, activationDistance);
        //final Destination destination = new Destination();

        //startCancelImageView = (ImageView) findViewById(R.id.startStopAlarmImageView);
        //startCancelTextView = (TextView) findViewById(R.id.startStopText);
        saveToFavButton = (ImageView) findViewById(R.id.saveAlarmToFav);

        saveToFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (searchLoc.getText().toString().equals("")) {
                    return;
                }
                StoreClient.addAlarm(currAlarmModel);
            }
        });
        //final TextView searchDestTextView = (TextView) findViewById(R.id.locationSearchFieldAlarm);

//      SETTING UP DESTINATION SEARCH BUTTON onCLICKLISTENER
        searchButton.setOnClickListener(new View.OnClickListener() {

//          MAKES API CALL onCLICK

            @Override
            public void onClick(View v) {
                if(searchLoc.getText().toString().equals("")) {
                    return;
                }


                currAlarmModel = new AlarmModel(AlarmActivity.this, searchLoc.getText().toString());
                currAlarmModel.setActivation_distance(seekBar.getProgress() * .5);
                currAlarmModel.setFlags(alarm_flags,0, 0, false);

                /*
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
                                RouteModel route = searchResultModel.getSearchResults().get(0);
                                LegModel leg = route.getLeg(0);
                                currAlarmModel = leg.getDuration();
                                if(first) {
                                    address_string = leg.getEnd_address();
                                    initial_time_left = time_left.getValue();
                                }
                                Dest_coords = searchResultModel.getDestinationCoords();
                                //Dest_coords.setLat(38.0);
                                //Dest_coords.setLng(-122.8);
                                //destination.setDoubLat(Dest_coords.getLat());
                                //destination.setDoubLong(Dest_coords.getLng());
                                //destination.setAddressString(searchResultModel.getSearchResults().get(0).getLeg(0).getEnd_address());
                                currAlarmModel.setDestination(Dest_coords);
                                currAlarmModel.setAddress_string();
                                Toast.makeText(getApplicationContext(), "API Call successful. Destination coordinates:"
                                        + Dest_coords.getCoordString(), Toast.LENGTH_LONG).show();
                            }
                        });
                        */
            }
        });

//        View.OnClickListener startCancelListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                SharedPreferences startStopPrefs = getSharedPreferences(ALARM_PREFS, 0);
//                SharedPreferences.Editor startStopEditor = startStopPrefs.edit();
//                //testing to see if I can disable something as clickable on another activity
////                ImageView unclickableTest = (ImageView)findViewById(R.id.homeAlarmImage);
//
//                if (getIsPressed()) {
//                   // boolean makeFalse = false;
//                    setIsPressed(false);
//                    startCancelImageView.setBackgroundResource(R.drawable.start);
//                    startCancelTextView.setText(R.string.start);
//                    startStopEditor.putBoolean("isPressed", false);
//
//                    startStopEditor.putBoolean("bool", false);
//                    startStopEditor.putString("textState", startCancelTextView.getText().toString());
//                    startStopEditor.commit();}
//                else {
//                    //This checks to make sure there is an actual destination coordinate before comparing.
//                    //Sends a message if there is not. The 1000 is the default, un-assigned value of the lat and long.
//                    //Check Destination.java for why. ZBrester
//                    if(destination.getDoubLat() == 1000 | destination.getDoubLong() == 1000 ) {
//                        Toast.makeText(getApplicationContext(), "Error! No assigned destination." , Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                    if(StoreClient.getCurrSMS() == null) {
//                        Toast.makeText(getApplicationContext(), "Error! No SMS Message set." , Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                    //boolean makeTrue = true;
//                    setIsPressed(true);
//                    startStopEditor.putBoolean("isPressed", true);
//
//
//
//
//                    //LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//                    //CREATE LOCATION LISTENER
//                    LocationListener listener = new LocationListener() {
//
//
//                        @Override
//                        public void onLocationChanged(Location location) {
//
//                            Curr_location.setLat(location.getLatitude());
//                            Curr_location.setLng(location.getLongitude());
//                            StoreClient.setCurrLocation(Curr_location); //Temporary, this will be updating the AlarmModel's current location LatLng rather than this. - ZBrester
//
//                            //latitude = location.getLatitude();
//                            //longitude = location.getLongitude();
//
//
//
//
//                            //sharedLocationEditor.putString("currentLatitude", Double.toString(Curr_location.getLat()));
//                            //sharedLocationEditor.putString("currentLongitude", Double.toString(Curr_location.getLng()));
//
//                            //editor.putString("currentLatitude", Double.toString(latitude));
//                            //editor.putString("currentLongitude", Double.toString(longitude));
//                            //sharedLocationEditor.apply();
//                        }
//
//                        @Override
//                        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//                        }
//
//                        @Override
//                        public void onProviderEnabled(String provider) {
//
//                        }
//
//                        @Override
//                        public void onProviderDisabled(String provider) {
//
//                        }
//                    };
//
//                    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
//
//                    //PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//                      //  PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CPUWakeLock");
//                        //wl.acquire();
//                    Runnable locationRunnable = new Runnable() {
//                        @Override
//                        public void run()  {
//
//
//                            /*
//                                THIS HANDLER ALLOWS THE LOCATION TO BE UPDATED EVERY X SECONDS,
//                                LOOPING UNTIL THE "START BUTTON IS NO LONGER PRESSED. ELIMINATES
//                                NEED FOR A FOR LOOP.
//                             */
//                            final Handler locationUpdateHandler = new Handler(Looper.getMainLooper());
//                            locationUpdateHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    SharedPreferences startStopPrefs = getSharedPreferences("AlarmPreferenceFile", 0);
//                                    final boolean isPressed = startStopPrefs.getBoolean("isPressed", false);
//
//                                    //SharedPreferences sharedLocationPref = getSharedPreferences("currentLocation", Context.MODE_PRIVATE);
//                                    //final double currentLongitude = Double.parseDouble(sharedLocationPref.getString("currentLongitude", "0.0"));
//                                    //final double currentLatitude = Double.parseDouble(sharedLocationPref.getString("currentLatitude", "0.0"));
//
//
//                                    if (destination.verifyDistance(StoreClient.getCurrLocation().getLng(), StoreClient.getCurrLocation().getLat())) {
//                                        //PowerManager.WakeLock TempWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP |
//                                          //      PowerManager.ON_AFTER_RELEASE, "TempWakeLock");
//                                        //TempWakeLock.acquire();
//
//                                        runOnUiThread(new Runnable() {
//                                            public void run() {
//                                                //alarmSound.start();
//                                                Intent popUpTest = new Intent(AlarmActivity.this, AlarmLaunchActivity.class);
//                                                startService(popUpTest);
//                                                //alarmSound.pause();
//                                            }
//                                        });
//
//
//                                        final SharedPreferences.Editor startStopEditor = startStopPrefs.edit();
//                                        startStopEditor.putBoolean("bool", false);
//                                        startStopEditor.commit();
//                                        runOnUiThread(new Runnable() {
//                                            public void run() {
//                                                //boolean makeFalse = false;
//                                                setIsPressed(false);
//                                                //alarmSound.pause();
//                                                //alarmSound.seekTo(0);
//                                                startCancelImageView.setBackgroundResource(R.drawable.start);
//                                                startCancelTextView.setText(R.string.start);
//                                                startStopEditor.putBoolean("isPressed", false);
//                                            }
//                                        });
//
//                                        //TempWakeLock.release();
//                                        return;
//
//                                    }
//
//                                    else {
//                                        counter++;
//                                        if (counter >= 5) {
//
//
//                                            Toast.makeText(getApplicationContext(), "Distance remaining: "
//                                                    + destination.getDistFromCurLoc(), Toast.LENGTH_LONG).show();
//                                            counter = 0;
//                                        }
//                                    }
//
//
//                                    if (isPressed) {
//                                        //DELAY THE LOCATION UPDATE FOR 2 SECONDS
//                                        locationUpdateHandler.postDelayed(this, 2000);
//                                    }
//                                }
//                            });
//                        }
//                    };
//
//                    Thread locationUpdateThread = new Thread(locationRunnable);
//                    locationUpdateThread.start();
//                    //wl.release();
//
//
//
//                    startCancelImageView.setBackgroundResource(R.drawable.cancel);
//                    startCancelTextView.setText(R.string.cancel);
//                    startStopEditor.putBoolean("bool", true);
//                    startStopEditor.putString("textState", startCancelTextView.getText().toString());
//                    startStopEditor.commit();
//                }
//
//            }
//        };

//        startCancelImageView.setOnClickListener(startCancelListener);
        SharedPreferences sharedPreferences = getSharedPreferences(ALARM_PREFS, 0);
//
//        if (sharedPreferences.contains("bool")){
//            setIsPressed(sharedPreferences.getBoolean("bool", false));
//            if(getIsPressed()){
//                startCancelImageView.setBackgroundResource(R.drawable.cancel);
//                startCancelTextView.setText(R.string.cancel);
//            }
//            else{
//                startCancelImageView.setBackgroundResource(R.drawable.start);
//                startCancelTextView.setText(R.string.start);
//            }
//        }


        seekBar = (SeekBar)findViewById(R.id.distanceAlarmSeekbar);
        distanceText = (TextView)findViewById(R.id.distanceAlarmTextView);
        if (sharedPreferences.contains("miles")){
            seekBar.setProgress(sharedPreferences.getInt("miles", 1));
            distanceText.setText(String.valueOf((sharedPreferences.getInt("miles", 1) * .5)));
        }
        else {
            seekBar.setProgress(1);
            distanceText.setText("1");
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
                    if(currAlarmModel != null && !currAlarmModel.error) {
                        currAlarmModel.setActivation_distance(progress * 0.5);
                    }
                }

//                activationDistance = seekBar.getProgress();
                isSeekChanged = true;
//                SharedPreferences activationDistancePrefs = getSharedPreferences("activationDistance", Context.MODE_PRIVATE);
//                SharedPreferences.Editor activationDistanceEditor = activationDistancePrefs.edit();
//                activationDistanceEditor.putInt("activationDist", activationDistance);
//                activationDistanceEditor.commit();

                if(progress < MIN_VALUE){
                    seekBar.setProgress(MIN_VALUE);

                }
                distanceText.setText(seekBar.getProgress() * .5 + "");
                seekBarEditor.putInt("miles", seekBar.getProgress());
                seekBarEditor.commit();
                if(currAlarmModel != null && !currAlarmModel.error) {
                    currAlarmModel.setActivation_distance((double) seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                Toast.makeText(getApplicationContext(), "Seekbar in use", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final ImageView homeImageView = (ImageView)findViewById(R.id.homeAlarmImage);
        ImageView favoritesImageView = (ImageView)findViewById(R.id.favoritesAlarmImage);
        ImageView statusImageView = (ImageView)findViewById(R.id.statusAlarmImage);
        ImageView messageImageView = (ImageView)findViewById(R.id.messageAlarmImage);
        nextButton = (ImageView)findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currAlarmModel == null || currAlarmModel.getDestination() == null) {
                        Toast.makeText(getApplicationContext(), "Error! No assigned destination." , Toast.LENGTH_LONG).show();
                        return;
                    }
                else {
                    currAlarmModel.setFlags(alarm_flags, 0, 0, false);
                    StoreClient.setCurrAlarm(currAlarmModel);
                    /*
                    if (LoadSMSActivity.loadedSMS != null) {
                        currAlarmModel.setSMS(LoadSMSActivity.loadedSMS);
                    }
                    */
                }
                //2 (0010) signals that it must be either Alarm and Message or Message Only)
                if ( (alarm_flags&2) != 0 /*msgOnly || alrmAndMsg*/) {

                    if (currAlarmModel.getSMS() == null) {
                        startActivity(new Intent(AlarmActivity.this, SMSPopActivity.class));
                    } else {
                        startActivity(new Intent(AlarmActivity.this, UpdateActivity.class));
                    }

                    /*Intent destinationIntent = new Intent(AlarmActivity.this, SMSPopActivity.class);
                    destinationIntent.putExtra("source", "alarmActivity");
                    destinationIntent.putExtra("msg?", true);
                    if (msgOnly) {
                        destinationIntent.putExtra("alrm?", false);
                    } else {
                        destinationIntent.putExtra("alrm?", true);
                    }

                    startActivity(destinationIntent);
                    */

                } else {
                    /*
                    Intent intent = new Intent(AlarmActivity.this, UpdateActivity.class);
                    intent.putExtra("msg?", false);
                    intent.putExtra("alrm?", true);
                    startActivity(intent);
                    */
                    startActivity(new Intent(AlarmActivity.this, UpdateActivity.class));
                }
            }
        });



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

//        Button confirmInfoButton = (Button) findViewById(R.id.confirmAlarmInfoButton);
//        //smsButton.setVisibility(View.INVISIBLE);
//
//        confirmInfoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (msgOnly || alrmAndMsg) {
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            //alarmSound.start();
//                            Intent popUpTest = new Intent(AlarmActivity.this, SMSPopActivity.class);
//                            startActivity(popUpTest);
//                            //alarmSound.pause();
//                        }
//                    });
//                }
//            }
//        });
    }

    public void setIsPressed(boolean useThis){
        this.isPressed = useThis;
    }

    public boolean getIsPressed(){
        return this.isPressed;
    }


    // Set boolean value based on which spinner item is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //1 is sound, 2 is message, 3(1&2) is alarm and message. This actually adds up perfectly to do this:
        alarm_flags = position+1;
        /*switch (position) {
            case 0: //Alarm Only
                alrmOnly = true;
                msgOnly = alrmAndMsg = false;
                //smsButton.setVisibility(View.INVISIBLE);
                break;
            case 1: //Message Only
                msgOnly = true;
                alrmOnly = alrmOnly = false;
                //smsButton.setVisibility(View.VISIBLE);
                break;
            case 2: //Alarm and Message
                alrmAndMsg = true;
                alrmOnly = msgOnly = false;
                //smsButton.setVisibility(View.VISIBLE);
                break;
        }
        */
    }

    // Set a default spinner item in case none are selected
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //alrmOnly = true;
        alarm_flags = 1;
        //smsButton.setVisibility(View.INVISIBLE);
    }




    public void onSaveOrUseClick(View view) {
        if (saveInfoBool) {
            Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_LONG).show();
        }
        if (useInfoBool) {
            Toast.makeText(getApplicationContext(), "Use", Toast.LENGTH_LONG).show();
        }
        if (!saveInfoBool && !useInfoBool) {
            Toast.makeText(getApplicationContext(), "Error! You must choose whether or not to save info!",
                    Toast.LENGTH_LONG).show();
        }
    }
}

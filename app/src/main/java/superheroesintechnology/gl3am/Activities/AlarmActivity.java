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
import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.R;
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
    private ImageView addSMSButton;
    private ImageView loadSMSButton;
    private StorageClient StoreClient = null;
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


        if (temp_loc != null) {
            Curr_location.setLat(temp_loc.getLatitude());
            Curr_location.setLng(temp_loc.getLongitude());
            StoreClient.setCurrLocation(Curr_location);
            //StoreClient.setCurrLocation(Curr_location);
            //I'm not sure if we really even need a Curr_location variable... - ZBrester
            //sharedLocationEditor.putString("currentLatitude", Double.toString(Curr_location.getLat()));
            //sharedLocationEditor.putString("currentLongitude", Double.toString(Curr_location.getLng()));
            //sharedLocationEditor.apply();
        } else {
            Curr_location = StoreClient.getCurrLocation();
            if (Curr_location.getLat() == 0 && Curr_location.getLng() == 0) {
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

        searchLoc = (EditText) findViewById(R.id.locationSearchFieldAlarm);
        searchButton = (ImageView) findViewById(R.id.destSearchButton);


        addSMSButton = (ImageView) findViewById(R.id.newSMS);
        loadSMSButton = (ImageView) findViewById(R.id.addSMS);


        addSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currAlarmModel != null) {
                    StoreClient.setCurrAlarm(currAlarmModel);
                    Intent intent = new Intent(AlarmActivity.this, SMSPopActivity.class);
                    intent.putExtra("ReturnTo", "None");
                    intent.putExtra("Mode", "Add");
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error! No assigned destination.", Toast.LENGTH_LONG).show();
                }
            }
        });

        loadSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currAlarmModel != null) {
                    StoreClient.setCurrAlarm(currAlarmModel);
                    startActivity(new Intent(AlarmActivity.this, LoadSMSActivity.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error! No assigned destination.", Toast.LENGTH_LONG).show();
                }


            }
        });
        saveToFavButton = (ImageView) findViewById(R.id.saveAlarmToFav);

        saveToFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currAlarmModel == null || currAlarmModel.getDestination() == null) {
                    Toast.makeText(getApplicationContext(), "Error! No assigned destination.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    currAlarmModel.setFlags(alarm_flags, 0, 0, false);

                }

                //2 (0010) signals that it must be either Alarm and Message or Message Only)
                if ((alarm_flags & 2) != 0 /*msgOnly || alrmAndMsg*/) {

                    if (currAlarmModel.getSMS() == null) {

                        Intent intent = new Intent(AlarmActivity.this, SMSPopActivity.class);
                        intent.putExtra("ReturnTo", "None");
                        intent.putExtra("Mode", "Add");
                        startActivity(intent);
                    } else {
                        StoreClient.addAlarm(currAlarmModel);
                    }

                }

            }
        });
        //final TextView searchDestTextView = (TextView) findViewById(R.id.locationSearchFieldAlarm);

//      SETTING UP DESTINATION SEARCH BUTTON onCLICKLISTENER
        searchButton.setOnClickListener(new View.OnClickListener() {

//          MAKES API CALL onCLICK

            @Override
            public void onClick(View v) {
                if (searchLoc.getText().toString().equals("")) {
                    return;
                }

                AlarmModel temp = new AlarmModel(AlarmActivity.this, searchLoc.getText().toString());

                temp.setActivation_distance(seekBar.getProgress() * .5);
                temp.setFlags(alarm_flags, 0, 0, false);
                if(currAlarmModel != null && currAlarmModel.getSMS() != null) {
                    temp.setSMS(currAlarmModel.getSMS());
                }
                currAlarmModel = temp;
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(ALARM_PREFS, 0);


        seekBar = (SeekBar) findViewById(R.id.distanceAlarmSeekbar);
        distanceText = (TextView) findViewById(R.id.distanceAlarmTextView);
        if (sharedPreferences.contains("miles")) {
            seekBar.setProgress(sharedPreferences.getInt("miles", 1));
            distanceText.setText(String.valueOf((sharedPreferences.getInt("miles", 1) * .5)));
        } else {
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
                    if (currAlarmModel != null && !currAlarmModel.error) {
                        currAlarmModel.setActivation_distance(progress * 0.5);
                    }
                }

//                activationDistance = seekBar.getProgress();
                isSeekChanged = true;
//                SharedPreferences activationDistancePrefs = getSharedPreferences("activationDistance", Context.MODE_PRIVATE);
//                SharedPreferences.Editor activationDistanceEditor = activationDistancePrefs.edit();
//                activationDistanceEditor.putInt("activationDist", activationDistance);
//                activationDistanceEditor.commit();

                if (progress < MIN_VALUE) {
                    seekBar.setProgress(MIN_VALUE);

                }
                distanceText.setText(seekBar.getProgress() * .5 + "");
                seekBarEditor.putInt("miles", seekBar.getProgress());
                seekBarEditor.commit();
                if (currAlarmModel != null && !currAlarmModel.error) {
                    currAlarmModel.setActivation_distance((double) seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                //Toast.makeText(getApplicationContext(), "Seekbar in use", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final ImageView homeImageView = (ImageView) findViewById(R.id.homeAlarmImage);
        ImageView favoritesImageView = (ImageView) findViewById(R.id.favoritesAlarmImage);
        ImageView statusImageView = (ImageView) findViewById(R.id.statusAlarmImage);
        ImageView messageImageView = (ImageView) findViewById(R.id.messageAlarmImage);
        nextButton = (ImageView) findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currAlarmModel == null || currAlarmModel.getDestination() == null) {
                    Toast.makeText(getApplicationContext(), "Error! No assigned destination.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    currAlarmModel.setFlags(alarm_flags, 0, 0, false);
                    StoreClient.setCurrAlarm(currAlarmModel);
                    /*
                    if (LoadSMSActivity.loadedSMS != null) {
                        currAlarmModel.setSMS(LoadSMSActivity.loadedSMS);
                    }
                    */
                }
                //2 (0010) signals that it must be either Alarm and Message or Message Only)
                if ((alarm_flags & 2) != 0 /*msgOnly || alrmAndMsg*/) {

                    if (currAlarmModel.getSMS() == null) {
                        Intent intent = new Intent(AlarmActivity.this, SMSPopActivity.class);
                        intent.putExtra("Mode", "Add");
                        intent.putExtra("ReturnTo", "Update");
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(AlarmActivity.this, UpdateActivity.class));
                    }
                }
                else {
                    startActivity(new Intent(AlarmActivity.this, UpdateActivity.class));
                    finish();
                }
            }
        });


        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlarmActivity.this, MapsActivity.class));
                finish();
            }
        });

        favoritesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AlarmActivity.this, FavoritesActivity.class));
                finish();
            }
        });

        statusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlarmActivity.this, UpdateActivity.class));
                finish();
            }
        });

        messageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlarmActivity.this, MessageActivity.class));
                finish();
            }
        });
    }

    public void setIsPressed(boolean useThis) {
        this.isPressed = useThis;
    }

    public boolean getIsPressed() {
        return this.isPressed;
    }


    // Set boolean value based on which spinner item is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //1 is sound, 2 is message, 3(1&2) is alarm and message. This actually adds up perfectly to do this:
        alarm_flags = position + 1;
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

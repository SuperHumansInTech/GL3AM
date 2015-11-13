package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import superheroesintechnology.gl3am.R;

public class AlarmActivity extends Activity {

    private SeekBar seekBar;
    private TextView distanceText;
    private ImageView startCancelImageView;
    private static final String ALARM_PREFS = "AlamrPrefernceFile";
    private TextView startCancelTextView;
    private boolean isPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        startCancelImageView = (ImageView)findViewById(R.id.startStopAlarmImageView);
        startCancelTextView = (TextView)findViewById(R.id.startStopText);

        View.OnClickListener startCancelListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                SharedPreferences startStopPrefs = getSharedPreferences(ALARM_PREFS, 0);
                SharedPreferences.Editor startStopEditor = startStopPrefs.edit();
                //testing to see if I can disable something as clickable on another activity
//                ImageView unclickableTest = (ImageView)findViewById(R.id.homeAlarmImage);
                Intent popUpTest = new Intent(AlarmActivity.this, AlarmLaunchActivity.class);

                if(getIsPressed()){
                    boolean makeFalse = false;
                    setIsPressed(makeFalse);
                    startCancelImageView.setBackgroundResource(R.drawable.start);
                    startCancelTextView.setText(R.string.start);

                    startStopEditor.putBoolean("bool", makeFalse);
                    startStopEditor.putString("textState", startCancelTextView.getText().toString());
                    startStopEditor.commit();
//                    unclickableTest.setClickable(true);
                }
                else{
                    boolean makeTrue = true;
                    setIsPressed(makeTrue);
                    startCancelImageView.setBackgroundResource(R.drawable.cancel);
                    startCancelTextView.setText(R.string.cancel);
                    startStopEditor.putBoolean("bool", makeTrue);
                    startStopEditor.putString("textState", startCancelTextView.getText().toString());
                    startStopEditor.commit();
//                    unclickableTest.setClickable(false);
                    startService(popUpTest);
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
                if(progress < MIN_VALUE){
                    seekBar.setProgress(MIN_VALUE);

                }
                distanceText.setText(seekBar.getProgress() + "");
                seekBarEditor.putInt("miles", seekBar.getProgress());
                seekBarEditor.commit();
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



    @Override
    protected void onStart() {
        SharedPreferences destroyPrefs = getSharedPreferences(ALARM_PREFS, 0);
        SharedPreferences.Editor destroyEditor = destroyPrefs.edit();

        destroyEditor.putBoolean("bool", false);
        destroyEditor.putString("textState", "start");
        destroyEditor.putInt("miles", 1);
        destroyEditor.commit();

        Toast.makeText(getApplicationContext(), "In onStart()", Toast.LENGTH_LONG).show();
        super.onStart();


    }
}

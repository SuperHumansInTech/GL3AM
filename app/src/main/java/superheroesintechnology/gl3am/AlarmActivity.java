package superheroesintechnology.gl3am;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

public class AlarmActivity extends Activity {

    //NumberPicker alarmNoPicker = null;
    private SeekBar seekBar;
    private TextView distanceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        seekBar = (SeekBar)findViewById(R.id.distanceAlarmSeekbar);
        distanceText = (TextView)findViewById(R.id.distanceAlarmTextView);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final int MIN_VALUE = 1;
                if(progress < MIN_VALUE){
                    seekBar.setProgress(MIN_VALUE);

               }
                distanceText.setText(seekBar.getProgress() + " miles");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



//        //Number picker
//        alarmNoPicker = (NumberPicker)findViewById(R.id.numberPicker);
//        alarmNoPicker.setMaxValue(20);
//        alarmNoPicker.setMinValue(1);
//        alarmNoPicker.setWrapSelectorWheel(false);


        ImageView homeImageView = (ImageView)findViewById(R.id.homeAlarmImage);
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
}

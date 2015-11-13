package superheroesintechnology.gl3am;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class UpdateActivity extends Activity {

    public Destination testDest = new Destination("3208 Marsh Rd\nSanta Rosa, CA 95403", 38.462135, -122.761644, 5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        TextView destinationTextView = (TextView)findViewById(R.id.destinationResultTextView);
        TextView actDistTextView = (TextView)findViewById(R.id.defDistResultTextView);
        TextView distFromDefTextView = (TextView)findViewById(R.id.distFromDestResultTextView);

        //for testing Destination Class only. Can be removed
        TextView boolTextView = (TextView)findViewById((R.id.booleanResultTextView));

        destinationTextView.setText(testDest.getAddressString());
        actDistTextView.setText(testDest.getDistFromDestStr());

        //for testing Destination Class only. Can be removed
        if(testDest.verifyDistance(-122.675004, 38.338075)){
            boolTextView.setText("True");
        }else{
            boolTextView.setText("False");
        }

        distFromDefTextView.setText(testDest.getDistFromCurLoc());

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

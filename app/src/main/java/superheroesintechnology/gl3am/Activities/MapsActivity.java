package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import superheroesintechnology.gl3am.R;

public class MapsActivity extends Activity{

    //private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ImageView alarmImage = (ImageView) findViewById(R.id.alarmImage);
        ImageView messageImage = (ImageView) findViewById(R.id.textImage);
        ImageView favoritesImage = (ImageView) findViewById(R.id.favoritesImage);
        ImageView infoUpdateImage = (ImageView) findViewById(R.id.currentActivityImage);

        alarmImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MapsActivity.this, AlarmActivity.class));
            }

        });

        messageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this, MessageActivity.class));
            }
        });

        favoritesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(MapsActivity.this, FavoritesActivity.class));
                startActivity(new Intent(MapsActivity.this, APISearchActivity.class));
            }
        });

        infoUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MapsActivity.this, UpdateActivity.class));
            }
        });


    }
}

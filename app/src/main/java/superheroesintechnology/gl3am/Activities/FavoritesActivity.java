package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import superheroesintechnology.gl3am.R;

public class FavoritesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ImageView alarmImageView = (ImageView)findViewById(R.id.alarmFavoritesImage);
        ImageView homeImageView = (ImageView)findViewById(R.id.homeFavoritesImage);
        ImageView messageImageView = (ImageView)findViewById(R.id.messageFavoritesImage);
        ImageView statusImageview = (ImageView)findViewById(R.id.statusFavoritesImage);

        alarmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this, AlarmActivity.class));
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this, MapsActivity.class));
            }
        });

        messageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this, MessageActivity.class));
            }
        });

        statusImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this, UpdateActivity.class));
            }
        });
    }
}

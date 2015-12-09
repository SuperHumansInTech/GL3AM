package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import superheroesintechnology.gl3am.R;

public class MapsActivity extends Activity{

    private ImageView infoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        infoButton = (ImageView)findViewById(R.id.infoButton);
        final ImageView alarmImage = (ImageView) findViewById(R.id.alarmImage);
        final ImageView messageImage = (ImageView) findViewById(R.id.textImage);
        final ImageView favoritesImage = (ImageView) findViewById(R.id.favoritesImage);
        final ImageView infoUpdateImage = (ImageView) findViewById(R.id.currentActivityImage);

        infoButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {

                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final RelativeLayout r = (RelativeLayout)findViewById(R.id.main);
                final View popupView = layoutInflater.inflate(R.layout.activity_information, r, false);
                r.addView(popupView);

                alarmImage.setClickable(false);
                messageImage.setClickable(false);
                favoritesImage.setClickable(false);
                infoUpdateImage.setClickable(false);

                ImageView exitInfo = (ImageView) popupView.findViewById(R.id.exitInfoBtn);

                exitInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alarmImage.setClickable(true);
                        messageImage.setClickable(true);
                        favoritesImage.setClickable(true);
                        infoUpdateImage.setClickable(true);
                        r.removeView(popupView);
                    }

                });
            }
        });

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
                startActivity(new Intent(MapsActivity.this, FavoritesActivity.class));
               // startActivity(new Intent(MapsActivity.this, APISearchActivity.class));
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

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
    private ImageView alarmImage;
    private ImageView messageImage;
    private ImageView favoritesImage;
    private ImageView infoUpdateImage;
    private RelativeLayout r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //set main menu buttons
        setButtons();

        infoButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {

                //intialize information popup window
                final View popupView = onInflateInfo();
                ImageView exitInfo = (ImageView) popupView.findViewById(R.id.exitInfoBtn);

                //disable main menu buttons
                disableButtons();

                //close information popup window
                exitInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       onDeflateInfo(popupView);
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

    //**********************************************************************************************
    //initializes main menu buttons and main menu's relative layout
    public void setButtons(){
        infoButton = (ImageView)findViewById(R.id.infoButton);
        alarmImage = (ImageView) findViewById(R.id.alarmImage);
        messageImage = (ImageView) findViewById(R.id.textImage);
        favoritesImage = (ImageView) findViewById(R.id.favoritesImage);
        infoUpdateImage = (ImageView) findViewById(R.id.currentActivityImage);
        r = (RelativeLayout)findViewById(R.id.main);
    }

    //***********************************************************************************************
    //Set the inflation of the information window
    //disables main menu buttons
    public View onInflateInfo(){
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.activity_information, r, false);
        r.addView(popupView);

        return popupView;
    }

    //***********************************************************************************************
    //disables all main menu buttons
    public void disableButtons(){
        alarmImage.setClickable(false);
        messageImage.setClickable(false);
        favoritesImage.setClickable(false);
        infoUpdateImage.setClickable(false);
        infoButton.setClickable(false);
    }

    //***********************************************************************************************
    //Handles deflation of info popup
    //reenables main menu buttons
    public void onDeflateInfo(View v){
        alarmImage.setClickable(true);
        messageImage.setClickable(true);
        favoritesImage.setClickable(true);
        infoUpdateImage.setClickable(true);
        infoButton.setClickable(true);
        r.removeView(v);
    }
}

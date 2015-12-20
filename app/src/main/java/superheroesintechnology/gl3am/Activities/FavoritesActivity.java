package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import superheroesintechnology.gl3am.Activities.MapsActivity;
import superheroesintechnology.gl3am.Adapters.FavoriteAlarmListAdapter;
import superheroesintechnology.gl3am.Models.AlarmModel;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.StorageClient;

public class FavoritesActivity extends Activity {
    private ListView alarmList;
    private ArrayList<AlarmModel> alarmArrayList;
    private ArrayAdapter<AlarmModel> listAdapter;
    private ImageView alarmImageView;
    private ImageView homeImageView;
    private ImageView messageImageView;
    private ImageView statusImageview;
    private ImageView favoritesImageView;
    private StorageClient StoreClient;
    private RelativeLayout r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        setOnLaunch();

        alarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                alarmListClickInit();

                final View popUpView = onInflateConfirm();

                ImageView confirmButton = (ImageView) popUpView.findViewById(R.id.confirmAlarmButton);
                ImageView cancelButton = (ImageView) popUpView.findViewById(R.id.cancel_action);


                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onConfirm(StoreClient, position, popUpView);
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCancel(popUpView);
                    }
                });
            }
        });



        alarmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this, AlarmActivity.class));
                finish();
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this, MapsActivity.class));
                finish();
            }
        });

        messageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this, MessageActivity.class));
                finish();
            }
        });

        statusImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this, UpdateActivity.class));
                finish();
            }
        });
    }

    //**********************************************************************************************
    //Initializes FavoritesActivity

    public void setOnLaunch(){
        alarmImageView = (ImageView)findViewById(R.id.alarmFavoritesImage);
        homeImageView = (ImageView)findViewById(R.id.homeFavoritesImage);
        messageImageView = (ImageView)findViewById(R.id.messageFavoritesImage);
        statusImageview = (ImageView)findViewById(R.id.statusFavoritesImage);
        favoritesImageView = (ImageView)findViewById(R.id.favoritesFavoritesImage);
        alarmList = (ListView) findViewById(R.id.alarmListView);
        StoreClient = new StorageClient(this, "default");
        alarmArrayList = StoreClient.loadAlarmList();
        listAdapter = new FavoriteAlarmListAdapter(this, alarmArrayList);
        alarmList.setAdapter(listAdapter);
        r = (RelativeLayout)findViewById(R.id.mainFavorites);
    }

    //**********************************************************************************************
    //disables buttons clicks and the ability to select list items while popup is present

    public void alarmListClickInit(){
        alarmList.setEnabled(false);
        alarmImageView.setClickable(false);
        homeImageView.setClickable(false);
        messageImageView.setClickable(false);
        statusImageview.setClickable(false);
        favoritesImageView.setClickable(false);
    }

    //**********************************************************************************************
    //sets and returns inflated view

    public View onInflateConfirm(){

        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView = layoutInflater.inflate(R.layout.activity_confirm_favorite, r, false);
        r.addView(popUpView);
        TextView confirmOrCancelText = (TextView) popUpView.findViewById(R.id.confirmOrCancelTextView);
        confirmOrCancelText.setText("Use this service?");

        return popUpView;
    }

    //**********************************************************************************************
    //confirms selection, removes popup view, Re-enables buttons and listView, and starts UpdateActivity
    public void onConfirm(StorageClient sc, int position, View v){
        reenableButtons();
        sc.setCurrAlarm(alarmArrayList.get(position));//
        r.removeView(v);
        startActivity(new Intent(FavoritesActivity.this, UpdateActivity.class));
    }

    //**********************************************************************************************
    //Cancels item select and re-enables buttons and the ability to select list items
    public void onCancel(View v){
        reenableButtons();
        r.removeView(v);
    }

    //**********************************************************************************************
    //Re-enables buttons and list view
    public void reenableButtons(){
        alarmList.setEnabled(true);
        alarmImageView.setClickable(true);
        homeImageView.setClickable(true);
        messageImageView.setClickable(true);
        statusImageview.setClickable(true);
        favoritesImageView.setClickable(true);
    }
}

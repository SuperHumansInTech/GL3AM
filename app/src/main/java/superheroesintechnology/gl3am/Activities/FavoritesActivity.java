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
    ArrayAdapter<AlarmModel> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final StorageClient StoreClient = new StorageClient(this, "default");
        setContentView(R.layout.activity_favorites);

        final ImageView alarmImageView = (ImageView)findViewById(R.id.alarmFavoritesImage);
        final ImageView homeImageView = (ImageView)findViewById(R.id.homeFavoritesImage);
        final ImageView messageImageView = (ImageView)findViewById(R.id.messageFavoritesImage);
        final ImageView statusImageview = (ImageView)findViewById(R.id.statusFavoritesImage);
        final ImageView favoritesImageView = (ImageView)findViewById(R.id.favoritesFavoritesImage);


        alarmList = (ListView) findViewById(R.id.alarmListView);
        alarmArrayList = StoreClient.loadAlarmList();

        listAdapter = new FavoriteAlarmListAdapter(this, alarmArrayList);
        alarmList.setAdapter(listAdapter);

        alarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final RelativeLayout r = (RelativeLayout)findViewById(R.id.mainFavorites);
                final View popUpView = layoutInflater.inflate(R.layout.activity_confirm_favorite, r, false);
                r.addView(popUpView);

                alarmImageView.setClickable(false);
                homeImageView.setClickable(false);
                messageImageView.setClickable(false);
                statusImageview.setClickable(false);
                favoritesImageView.setClickable(false);

                ImageView confirmButton = (ImageView) popUpView.findViewById(R.id.confirmAlarmButton);
                ImageView cancelButton = (ImageView) popUpView.findViewById(R.id.cancel_action);
                TextView confirmOrCancelText = (TextView) popUpView.findViewById(R.id.confirmOrCancelTextView);
                confirmOrCancelText.setText("Use this service?");

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StoreClient.setCurrAlarm(alarmArrayList.get(position));
                        alarmImageView.setClickable(true);
                        homeImageView.setClickable(true);
                        messageImageView.setClickable(true);
                        statusImageview.setClickable(true);
                        favoritesImageView.setClickable(true);
                        r.removeView(popUpView);
                        startActivity(new Intent(FavoritesActivity.this, UpdateActivity.class));
                        FavoritesActivity.this.finish();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alarmImageView.setClickable(true);
                        homeImageView.setClickable(true);
                        messageImageView.setClickable(true);
                        statusImageview.setClickable(true);
                        favoritesImageView.setClickable(true);
                        r.removeView(popUpView);
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
}

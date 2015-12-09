package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

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

        alarmList = (ListView) findViewById(R.id.alarmListView);
        alarmArrayList = StoreClient.loadAlarmList();

        listAdapter = new FavoriteAlarmListAdapter(this, alarmArrayList);
        alarmList.setAdapter(listAdapter);

//        alarmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                return false;
//            }
//        });

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

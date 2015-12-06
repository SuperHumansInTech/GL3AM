package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import superheroesintechnology.gl3am.Adapters.SMSListAdapter;
import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.StorageClient;

public class MessageActivity extends Activity {
    private ListView smsList;
    private Button searchButton;
    private ArrayList<SMSMessage> message_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final StorageClient StoreClient = new StorageClient(this, "default");
        setContentView(R.layout.activity_message);

        smsList = (ListView)findViewById(R.id.smsList);
        searchButton = (Button) findViewById(R.id.SMSRefreshButton);


        message_list = StoreClient.loadSMSList();

        //smsList.setAdapter(new SMSListAdapter(MessageActivity.this, message_list));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message_list = StoreClient.loadSMSList();
                smsList.setAdapter(new SMSListAdapter(MessageActivity.this, message_list));
            }
        });



        ImageView alarmImageView = (ImageView)findViewById(R.id.alarmMessagesImage);
        ImageView homeImageView = (ImageView)findViewById(R.id.homeMessagesImage);
        ImageView favoritesImageView = (ImageView)findViewById(R.id.favoritesMessagesImage);
        ImageView statusImageView = (ImageView)findViewById(R.id.statusMessagesImage);

        alarmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, AlarmActivity.class));
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, MapsActivity.class));
            }
        });

        favoritesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, FavoritesActivity.class));
            }
        });

        statusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, UpdateActivity.class));
            }
        });
    }
}

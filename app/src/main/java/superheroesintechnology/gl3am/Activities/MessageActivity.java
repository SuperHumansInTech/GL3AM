package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import superheroesintechnology.gl3am.Adapters.SMSListAdapter;
import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.OnSwipeTouchListener;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.StorageClient;

public class MessageActivity extends Activity {
    private ListView smsList;
    private Button searchButton;
    private ArrayList<SMSMessage> message_list;
    private ImageView swipeLeft;
    ArrayAdapter<SMSMessage> listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final StorageClient StoreClient = new StorageClient(this, "default");
        setContentView(R.layout.activity_message);

//        final ImageView deleteSms = (ImageView) findViewById(R.id.deleteSMSButton);

        smsList = (ListView) findViewById(R.id.list);
//        LinearLayout smsItem = (LinearLayout) findViewById(R.id.smsListItem);
//        View v = smsList.findFocus();
        //searchButton = (Button) findViewById(R.id.SMSRefreshButton);


        message_list = StoreClient.loadSMSList();

        listAdapter = new SMSListAdapter(this, message_list);
        smsList.setAdapter(listAdapter);








        //smsList.setAdapter(new SMSListAdapter(MessageActivity.this, message_list));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAdapter.notifyDataSetChanged();
                message_list = StoreClient.loadSMSList();
                smsList.setAdapter(listAdapter);
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

//    public void alertMessage(final int position, final StorageClient StoreClient) {
//        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
//                        Toast.makeText(getApplicationContext(), "Yes Clicked", Toast.LENGTH_LONG).show();
//                        StoreClient.deleteSMS(position);
//                        listAdapter.notifyDataSetChanged();
//                        break;
//                    case DialogInterface.BUTTON_NEGATIVE: // No button clicked // do nothing
//                        Toast.makeText(getApplicationContext(), "No Clicked", Toast.LENGTH_LONG).show();
//                        break;
//                }
//            }
//        };
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you sure?")
//                .setPositiveButton("Yes", dialogClickListener)
//                .setNegativeButton("No", dialogClickListener).show();
//    }
}

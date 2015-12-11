package superheroesintechnology.gl3am.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import superheroesintechnology.gl3am.Adapters.SMSListAdapter;
import superheroesintechnology.gl3am.Models.AlarmModel;
import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.StorageClient;

public class LoadSMSActivity extends Activity {
    private ListView smsList;
    private ArrayList<SMSMessage> message_list;
    ArrayAdapter<SMSMessage> listAdapter;
//    public static SMSMessage loadedSMS;
    private  SMSMessage loadedSMS = new SMSMessage(null, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final StorageClient StoreClient = new StorageClient(this, "default");
        setContentView(R.layout.load_saved_sms);

        smsList = (ListView) findViewById(R.id.list);
//        ImageView confirmButton = (ImageView) findViewById(R.id.confirmLoadSMSButton);
//        ImageView cancelButton = (ImageView) findViewById(R.id.cancelLoadSMSButton);
        message_list = StoreClient.loadSMSList();

        listAdapter = new SMSListAdapter(this, message_list);
        smsList.setAdapter(listAdapter);

        smsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                StoreClient.getCurrAlarm(getApplicationContext()).setSMS(StoreClient.getSMS(position));
                loadedSMS = StoreClient.getSMS(position);

                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final RelativeLayout r = (RelativeLayout) findViewById(R.id.loadSMSPop);
                final View popUpView = layoutInflater.inflate(R.layout.activity_confirm_favorite, r, false);
                r.addView(popUpView);

                ImageView confirmButton = (ImageView) popUpView.findViewById(R.id.confirmAlarmButton);
                ImageView cancelButton = (ImageView) popUpView.findViewById(R.id.cancel_action);
                TextView confirmOrCancelText = (TextView) popUpView.findViewById(R.id.confirmOrCancelTextView);
                confirmOrCancelText.setText("Use this message?");

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (loadedSMS == null) {
                            Toast.makeText(getApplicationContext(), "You have not selected a message.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        AlarmModel currAlarm = StoreClient.getCurrAlarm(LoadSMSActivity.this);
                        currAlarm.setSMS(loadedSMS);
                        StoreClient.setCurrAlarm(currAlarm);
                        r.removeView(popUpView);
                        startActivity(new Intent(LoadSMSActivity.this, UpdateActivity.class));
                        LoadSMSActivity.this.finish();

                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        r.removeView(popUpView);
                    }
                });
            }
        });
    }
}

package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private SMSMessage loadedSMS;
    private StorageClient StoreClient;
    private RelativeLayout r;
    private View popUpView;
    private ImageView confirmButton;
    private ImageView popcancelButton;
    private TextView confirmOrCancelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityInit();



//        ImageView confirmButton = (ImageView) findViewById(R.id.confirmLoadSMSButton);
        //ImageView cancelButton = (ImageView) findViewById(R.id.cancelLoadSMSButton);

       /* cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadSMSActivity.this.finish();
            }

        });
        */


        smsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                StoreClient.getCurrAlarm(getApplicationContext()).setSMS(StoreClient.getSMS(position));


                popInit(position);

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onConfirm();
                    }
                });


                popcancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCancel();
                    }
                });
            }


        });
    }

    //**********************************************************************************************
    //Initializes Activity

    public void activityInit(){
        loadedSMS = new SMSMessage(null, null);
        StoreClient = new StorageClient(this, "default");
        setContentView(R.layout.load_saved_sms);
        smsList = (ListView) findViewById(R.id.list);
        message_list = StoreClient.loadSMSList();
        listAdapter = new SMSListAdapter(this, message_list);
        smsList.setAdapter(listAdapter);
    }

    //**********************************************************************************************
    //Initializes confirm/cancel popup

    public void popInit(int position){
        smsList.setEnabled(false);
        loadedSMS = StoreClient.getSMS(position);
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        r = (RelativeLayout) findViewById(R.id.loadSMSPop);
        popUpView = layoutInflater.inflate(R.layout.activity_confirm_favorite, r, false);
        r.addView(popUpView);
        confirmButton = (ImageView) popUpView.findViewById(R.id.confirmAlarmButton);
        popcancelButton = (ImageView) popUpView.findViewById(R.id.cancel_action);
        confirmOrCancelText = (TextView) popUpView.findViewById(R.id.confirmOrCancelTextView);
        confirmOrCancelText.setText("Use this message?");
    }

    //**********************************************************************************************
    //Confirm action for popup

    public void onConfirm(){
        if (loadedSMS == null) {
            Toast.makeText(getApplicationContext(), "You have not selected a message.", Toast.LENGTH_LONG).show();
            return;
        }

        AlarmModel currAlarm = StoreClient.getCurrAlarm(LoadSMSActivity.this);
        currAlarm.setSMS(loadedSMS);
        StoreClient.setCurrAlarm(currAlarm);
        r.removeView(popUpView);
        LoadSMSActivity.this.finish();
    }

    //**********************************************************************************************
    //cancel action for popup

    public void onCancel(){
        smsList.setEnabled(true);
        r.removeView(popUpView);
    }
}

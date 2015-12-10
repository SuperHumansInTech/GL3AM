package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.text.TextUtils;
import android.widget.Toast;

import superheroesintechnology.gl3am.Models.AlarmModel;
import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.StorageClient;

public class SMSPopActivity extends Activity {

    private ImageView confirmButton;
    private ImageView cancelButton;
    private ImageView saveSMSButton;
    private ImageView getSavedSMSButton;
    private EditText name;
    private EditText desc;
    private EditText number;
    private EditText message;
    private boolean fromMessageActivity;
    private boolean fromAlarmActivity;
    private boolean fromSaveToFavorites;

    private StorageClient storeClient;
   private String ReturnTo;
    private String Mode;
//
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finishAndRemoveTask();
    } */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //final boolean save = false;
        storeClient = new StorageClient(this, "default");
        Intent srcIntent = getIntent();
        ReturnTo = srcIntent.getStringExtra("ReturnTo");
        Mode = srcIntent.getStringExtra("Mode");

        setContentView(R.layout.activity_smspop);


        name = (EditText)findViewById(R.id.SMSName);
        desc = (EditText)findViewById(R.id.SMSDesc);
        number = (EditText)findViewById(R.id.SMSPhoneNum);
        message = (EditText)findViewById(R.id.SMSTextMess);


//// Initialize text in EditText fields to null
//        name.setText(null);
//        desc.setText(null);
//        number.setText(null);
//        message.setText(null);

        confirmButton = (ImageView)findViewById(R.id.confirmSMSButton);
        cancelButton = (ImageView)findViewById(R.id.SMScancelButton);
        saveSMSButton = (ImageView)findViewById(R.id.saveMessageButton);
        getSavedSMSButton = (ImageView)findViewById(R.id.getSavedMessageButton);
        DisplayMetrics popDM = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(popDM);

        int width = popDM.widthPixels;
        int height = popDM.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction(null, null, false);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSPopActivity.this.finish();
            }
        });


//        if (fromAlarmActivity || fromSaveToFavorites) {
//
//            if (fromAlarmActivity) {
                saveSMSButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        doAction("Save", "Stay", false);
                    }
                });
//            }


            getSavedSMSButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent = new Intent(getApplicationContext(), LoadSMSActivity.class);
                    startActivityForResult(new Intent(getApplicationContext(), LoadSMSActivity.class), 2);
                }
            });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode != 2) {
            return;
        }
        AlarmModel temp = storeClient.getCurrAlarm(SMSPopActivity.this);
        if(temp != null && temp.getSMS() != null) {
            doAction(null, null, true);
        }
       /* String name;
        String description;
        String number;
        String text;
        if (requestCode == 2) {
            data.getExtras();
            name = data.getStringExtra("name");
            description = data.getStringExtra("description");
            number = data.getStringExtra("number");
            text = data.getStringExtra("text");
            final SMSMessage loadedSMS = new SMSMessage (name, description, number, text);
            storeClient.setCurrSMS(loadedSMS);

        }*/
    }

    private void doAction(String ModeOverride, String ReturnOverride, boolean loaded) {
        if (!number.getText().toString().equals("") || loaded) {
            AlarmModel alarm = storeClient.getCurrAlarm(this);
            SMSMessage newSMS = new SMSMessage(name.getText().toString(), desc.getText().toString(), number.getText().toString(), message.getText().toString());

            String modetype = Mode;
            String returntype = ReturnTo;
            if(ModeOverride != null) {
                modetype = ModeOverride;
            }
            if(ReturnOverride != null) {
                returntype = ReturnOverride;
            }
            switch (modetype) {
                case "Save": {
                    storeClient.addSMS(newSMS);
                    Toast.makeText(getApplicationContext(), "SMS Saved", Toast.LENGTH_LONG).show();
                }

                case "Add": {
                    if(alarm == null) {
                        return;
                    }
                    alarm.setSMS(newSMS);
                    storeClient.setCurrAlarm(alarm);
                }
            }

            switch (returntype) {
                case "None" : {
                    SMSPopActivity.this.finish();
                    return;
                }
                case "Update" : {
                    startActivity( new Intent(SMSPopActivity.this, UpdateActivity.class));
                    SMSPopActivity.this.finish();
                    return;
                }
                case "Alarm" : {
                    startActivity( new Intent(SMSPopActivity.this, AlarmActivity.class));
                    SMSPopActivity.this.finish();
                    return;
                }
                case "Messages" : {
                    startActivity( new Intent(SMSPopActivity.this, MessageActivity.class));
                    SMSPopActivity.this.finish();
                    return;
                }
                case "Favorites" : {
                    startActivity( new Intent(SMSPopActivity.this, FavoritesActivity.class));
                    SMSPopActivity.this.finish();
                    return;
                }
                case "Stay" : {
                    return;
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "You must enter a phone number!", Toast.LENGTH_LONG).show();
        }
    }


}



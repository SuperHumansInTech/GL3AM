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

//
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finishAndRemoveTask();
    } */

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //final boolean save = false;
        final StorageClient storeClient = new StorageClient(this, "default");

        Intent srcIntent = getIntent();
        final String ReturnTo = srcIntent.getStringExtra("ReturnTo");
        final String Mode = srcIntent.getStringExtra("Mode");

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
                if (!number.getText().toString().equals("")) {
                    AlarmModel alarm = storeClient.getCurrAlarm(SMSPopActivity.this);
                    SMSMessage newSMS = new SMSMessage(name.getText().toString(), desc.getText().toString(), number.getText().toString(), message.getText().toString());

                    switch (Mode) {
                        case "Save": {
                            storeClient.addSMS(newSMS);
                        }

                        case "Add": {
                            alarm.setSMS(newSMS);
                            storeClient.setCurrAlarm(alarm);
                        }
                    }

                    switch (ReturnTo) {
                        case "None" : {
                            SMSPopActivity.this.finish();
                            return;
                        }
                        case "Update" : {
                            startActivity( new Intent(SMSPopActivity.this, UpdateActivity.class));
                            SMSPopActivity.this.finish();
                        }
                        case "Alarm" : {
                            startActivity( new Intent(SMSPopActivity.this, AlarmActivity.class));
                            SMSPopActivity.this.finish();
                        }
                        case "Messages" : {
                            startActivity( new Intent(SMSPopActivity.this, MessageActivity.class));
                            SMSPopActivity.this.finish();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "You must enter a phone number!", Toast.LENGTH_LONG).show();
                }
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

                        if(number.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "You must enter a phone number!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        SMSMessage newSMS = new SMSMessage(name.getText().toString(), desc.getText().toString(), number.getText().toString(), message.getText().toString());
                        storeClient.addSMS(newSMS);
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
        StorageClient storeClient = new StorageClient(this, "default");
        if(requestCode != 2) {
            return;
        }
        AlarmModel temp = storeClient.getCurrAlarm(SMSPopActivity.this);
        if(temp != null && temp.getSMS() != null) {
            startActivity( new Intent(SMSPopActivity.this, UpdateActivity.class));
            SMSPopActivity.this.finish();
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


}



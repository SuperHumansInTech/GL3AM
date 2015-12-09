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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        StorageClient storeClient = new StorageClient(this, "default");
//        String name;
//        String description;
//        String number;
//        String text;
//        if (requestCode == 2) {
//            data.getExtras();
//            name = data.getStringExtra("name");
//            description = data.getStringExtra("description");
//            number = data.getStringExtra("number");
//            text = data.getStringExtra("text");
//            final SMSMessage loadedSMS = new SMSMessage (name, description, number, text);
//            storeClient.setCurrSMS(loadedSMS);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finishAndRemoveTask();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//  SAVE TO FAVORITES BUTTON:
//          intent.putExtra("sourceForPop", getString(R.string.fromSaveToFavoritesButton));

//  FROM ALARM ACTIVITY'S NEXT BUTTON:
//          intent.putExtra("sourceForPop", getString(R.string.fromAlarmActivityNext));

//  FROM MESSAGE ACTIVITY
//          intent.putExtra("sourceForPop", getString(R.string.fromFavoriteMessages));


        //final boolean save = savedInstanceState.getBoolean("save", false);
        final boolean save = false;
        final StorageClient storeClient = new StorageClient(this, "default");

        Intent srcIntent = getIntent();
        final String sourceActivity = srcIntent.getStringExtra("source");
        final String sourceForAlternatePop = srcIntent.getStringExtra("sourceForPop");
        //final boolean sendSMSBool = srcIntent.getBooleanExtra("msg?", false);
        //final boolean alrmBool = srcIntent.getBooleanExtra("alrm?", true);

//        if (sourceForAlternatePop == getString(R.string.fromSaveToFavoritesButton)) {
//            setContentView(R.layout.save_alarm_to_favorites_pop);
//            fromSaveToFavorites = true;
//            fromAlarmActivity = fromMessageActivity = false;
//        } if (sourceForAlternatePop == getString(R.string.fromAlarmActivityNext)) {
            setContentView(R.layout.activity_smspop);
//            fromAlarmActivity = true;
//            fromSaveToFavorites = fromMessageActivity = false;
//        } if (sourceForAlternatePop == getString(R.string.fromFavoriteMessages)) {
//            setContentView(R.layout.save_sms_to_favorites_pop);
//            fromMessageActivity = true;
//            fromAlarmActivity = fromSaveToFavorites = false;
//        }

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

                    if (sourceActivity == "MessageActivity") {
                        storeClient.addSMS(newSMS);
                        SMSPopActivity.this.finish();
                    } else {

                        alarm.setSMS(newSMS);
                        storeClient.setCurrAlarm(alarm);
                        storeClient.setCurrSMS(newSMS);


                        /*
                        Intent intent = new Intent(SMSPopActivity.this, UpdateActivity.class);
                        if (sendSMSBool) {
                            intent.putExtra("msg?", true);
                        }
                        if (!alrmBool) {
                            intent.putExtra("alrm?", false);
                        }
 */
                        startActivity( new Intent(SMSPopActivity.this, UpdateActivity.class));
                        SMSPopActivity.this.finish();
                    }

                    //startActivity(new Intent(SMSPopActivity.this, AlarmActivity.class));
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
                    Intent intent = new Intent(getApplicationContext(), LoadSMSActivity.class);
                    startActivityForResult(intent, 2);
                }
            });


    }



}



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

import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.StorageClient;

public class SMSPopActivity extends Activity {

    private ImageView confirmButton;
    private EditText name;
    private EditText desc;
    private EditText number;
    private EditText message;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //final boolean save = savedInstanceState.getBoolean("save", false);
        final boolean save = false;
        final StorageClient storeClient = new StorageClient(this, "default");

        setContentView(R.layout.activity_smspop);
        name = (EditText)findViewById(R.id.SMSName);
        desc = (EditText)findViewById(R.id.SMSDesc);
        number = (EditText)findViewById(R.id.SMSPhoneNum);
        message = (EditText)findViewById(R.id.SMSTextMess);

        confirmButton = (ImageView)findViewById(R.id.confirmSMSButton);
        DisplayMetrics popDM = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(popDM);

        int width = popDM.widthPixels;
        int height = popDM.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.5));


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number.getText().toString().equals("")) {
                    SMSMessage newSMS = new SMSMessage(name.getText().toString(), desc.getText().toString(), number.getText().toString(), message.getText().toString());

                    if(save) {
                        storeClient.addSMS(newSMS);
                    }

                    else {
                        storeClient.setCurrSMS(newSMS);

                    }
                    SMSPopActivity.this.finish();
                    //startActivity(new Intent(SMSPopActivity.this, AlarmActivity.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), "You must enter a phone number!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
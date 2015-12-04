package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import superheroesintechnology.gl3am.R;

public class SMSPopActivity extends Activity {

    private ImageView confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smspop);

        confirmButton = (ImageView)findViewById(R.id.confirmSMSButton);
        DisplayMetrics popDM = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(popDM);

        int width = popDM.widthPixels;
        int height = popDM.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.5));


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SMSPopActivity.this, AlarmActivity.class));
            }
        });
    }
}

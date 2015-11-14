package superheroesintechnology.gl3am.Activities;


import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import superheroesintechnology.gl3am.R;

public class AlarmLaunchActivity extends Service {

    private WindowManager wm;
    private LinearLayout layout;
    private TextView alarmMessage;
    private ImageView stopButton;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        setContentView(R.layout.activity_alarm_launch);
//
//        DisplayMetrics dm = new DisplayMetrics();
//
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//
//        getWindow().setLayout((int)(width*.4
//        ), (int)(height*.15));
        ViewGroup.LayoutParams btnParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        alarmMessage = new TextView(this);
        alarmMessage.setText("Nearing Destination");
        alarmMessage.setTextColor(getResources().getColor(R.color.text_color));
        alarmMessage.setLayoutParams(btnParams);
        alarmMessage.setGravity(Gravity.CENTER);
        alarmMessage.setPadding(5, 5, 5, 40);



        stopButton = new ImageView(this);
//        stopButton.setText("OK");
//        stopButton.setTextColor(getResources().getColor(R.color.text_color));
        stopButton.setBackground(ResourcesCompat.getDrawable(getResources(),
                R.drawable.alarm54, null));

        stopButton.setLayoutParams(btnParams);
//        stopButton.setGravity(Gravity.CENTER);

        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              wm.removeView(layout);
                stopSelf();
            }
        });

        wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        layout = new LinearLayout(this);

        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layout.setBackgroundColor(getResources().getColor(R.color.popup));
        layout.setLayoutParams(llParams);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(8, 8, 8, 8);
        layout.setGravity(Gravity.CENTER);


        WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM, PixelFormat.OPAQUE);

        parameters.x = 0;
        parameters.y = 0;
        parameters.gravity = Gravity.CENTER | Gravity.CENTER;


        layout.addView(alarmMessage);
        layout.addView(stopButton);

        wm.addView(layout, parameters);
    }


}


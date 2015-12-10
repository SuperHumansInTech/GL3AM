 package superheroesintechnology.gl3am.Activities;


import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Vibrator;
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
import android.widget.Toast;

import com.google.gson.Gson;

import superheroesintechnology.gl3am.Models.AlarmModel;
import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.StorageClient;

public class AlarmLaunchActivity extends Service {




    private WindowManager wm;
    private LinearLayout layout;
    private TextView alarmMessage;
    private ImageView stopButton;
    private Uri notification;
    private MediaPlayer mp;
    private Vibrator vibrate;
    public String myPhoneNumber;
    public String smsText;
    //boolean sendSMSBool;
    //boolean alrmBool;
    AlarmModel alarm;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    @Override
//    protected void onHandleIntent(Intent intent) {
//        sendSMSBool = intent.getBooleanExtra("sendMsg?", false);
//        alrmBool = intent.getBooleanExtra("alrm?", true);
//    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final StorageClient StoreClient = new StorageClient(this, "default");

        final SMSMessage newMessage = StoreClient.getCurrSMS();

        alarm = StoreClient.getCurrAlarm(this);
        //final SMSMessage newMessage = StoreClient.getCurrSMS();
        //sendSMSBool = intent.getBooleanExtra("sendMsg?", false);
        //alrmBool = intent.getBooleanExtra("alrm?", false);

        if (alarm.getFlags("near", "sms")) {
            alarm.sendSMS("SMS");
            Toast.makeText(getApplicationContext(), "The text was sent!", Toast.LENGTH_LONG).show();
            /*
            if (newMessage.getPhoneNumber() != null && newMessage.getSmsTextMessage() != null) {
                //final SMSMessage newMessage = new SMSMessage(myPhoneNumber,smsText);
                Toast.makeText(getApplicationContext(), "The text was sent!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "No text sent. You did not enter a number or message!",
                        Toast.LENGTH_LONG).show();
            }
            */
        }

        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mp = MediaPlayer.create(getApplicationContext(), notification);
        mp.setLooping(true);

        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        if(alarm.getFlags("near", "sound")){

            if (am.getRingerMode() == AudioManager.RINGER_MODE_SILENT ||
                    am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE){
                vibrate.vibrate(new long[] { 0, 200, 0 }, 0);}
            else{
                mp.start();
            }
        }



        return START_STICKY;
    }

    @Override
    public void onCreate() {
        new AlarmLaunchActivity();
        super.onCreate();
       // final Gson gson = new Gson();
        //final StorageClient StoreClient = new StorageClient(this, "default");
// GET SMS INFO (USING SHAREDPREFS) FROM AlarmActivity
// SAVE THEM TO LOCAL NUMBER AND TEXT STRING VARIABLES
        //final SMSMessage newMessage = StoreClient.getCurrSMS();

        //SharedPreferences sharedSMSPrefs = getSharedPreferences("smsInfo", Context.MODE_PRIVATE);
        //myPhoneNumber = sharedSMSPrefs.getString("number", null);
        //smsText = sharedSMSPrefs.getString("text", null);
        //final SMSMessage newMessage = gson.fromJson(sharedSMSPrefs.getString("sms", null), SMSMessage.class);

// SEND SMS IF USER HAS ENTERED NUMBER AND TEXT DATA
// MAKE TOAST IF SENT
// ELSE, MAKE A TOAST: "DID NOT SEND TEXT"

//        if (sendSMSBool) {
//            if (newMessage.getPhoneNumber() != null && newMessage.getSmsTextMessage() != null) {
//                //final SMSMessage newMessage = new SMSMessage(myPhoneNumber,smsText);
//                newMessage.sendSMS();
//                Toast.makeText(getApplicationContext(), "The text was sent!", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(getApplicationContext(), "No text sent. You did not enter a number or message!",
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//
//
//
//        alarmSound = MediaPlayer.create(this, R.raw.sound);
//        if (alarmSound.getCurrentPosition() != 0) {
//            alarmSound.seekTo(0);
//        }
//
//        if (alrmBool) {
//            alarmSound.start();
//        }

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
        stopButton.setBackground(ResourcesCompat.getDrawable(getResources(),
                R.drawable.saveinfo, null));

        stopButton.setLayoutParams(btnParams);

        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                if (alarmSound.isPlaying()) {
//                    alarmSound.pause();
//                    alarmSound.release();
//                }
                mp.stop();
                vibrate.cancel();
              wm.removeView(layout);
                stopSelf();
                //RELEASE WAKELOCK
            }
        });

        wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        layout = new LinearLayout(this);

        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layout.setBackgroundColor(getResources().getColor(R.color.pop_up_background));
        layout.setLayoutParams(llParams);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(8, 8, 8, 8);
        layout.setGravity(Gravity.CENTER);

        //FLAG_ALT_FOCUSABLE_IM
        WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.OPAQUE);

        parameters.x = 0;
        parameters.y = 0;
        parameters.gravity = Gravity.CENTER | Gravity.CENTER;


        layout.addView(alarmMessage);
        layout.addView(stopButton);

        wm.addView(layout, parameters);


    }


}


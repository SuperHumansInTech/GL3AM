package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.StorageClient;


public class SplashActivity extends Activity {

    private TextView titleTextView;
    private ImageView targetImageView;
    private ImageView alarmImageView;
    private ImageView messageImageView;
    private ImageView leftLineImageView;
    private ImageView rightLineImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final StorageClient StoreClient = new StorageClient(this, "default");
        StoreClient.purgeCurrent();

        Typeface titleTypeFace = Typeface.createFromAsset(getAssets(), "Agency_FB.ttf");
        titleTextView = (TextView) findViewById(R.id.appTitleSplashTextView);
        targetImageView = (ImageView) findViewById(R.id.targetSplashImageView);
        alarmImageView = (ImageView) findViewById(R.id.alarmSplashImageView);
        messageImageView = (ImageView) findViewById(R.id.messageSplashImageView);
        leftLineImageView = (ImageView) findViewById(R.id.leftLineImageView);
        rightLineImageView = (ImageView) findViewById(R.id.right_line);

        titleTextView.setTypeface(titleTypeFace);

        titleTextView.setVisibility(View.VISIBLE);
        targetImageView.setVisibility(View.VISIBLE);
        alarmImageView.setVisibility(View.VISIBLE);
        messageImageView.setVisibility(View.VISIBLE);
        leftLineImageView.setVisibility(View.VISIBLE);
        rightLineImageView.setVisibility(View.VISIBLE);




        Animation targetAnimationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);
        targetImageView.startAnimation(targetAnimationFadeIn);

        targetAnimationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.alarm_fade_in);
        alarmImageView.startAnimation(targetAnimationFadeIn);

        targetAnimationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.message_fade_in);
        messageImageView.startAnimation(targetAnimationFadeIn);

        targetAnimationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.left_line_fadein);
        leftLineImageView.startAnimation(targetAnimationFadeIn);

        targetAnimationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.right_line_fadein);
        rightLineImageView.startAnimation(targetAnimationFadeIn);

        targetAnimationFadeIn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.title_fade_in);
        titleTextView.startAnimation(targetAnimationFadeIn);

//
        targetAnimationFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                finish();
                startActivity(new Intent(getBaseContext(), MapsActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


}


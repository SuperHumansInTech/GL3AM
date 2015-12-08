package superheroesintechnology.gl3am.Models;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.text.TextUtilsCompatJellybeanMr1;
import android.text.TextUtils;
import android.widget.Toast;

import java.net.URLEncoder;
import java.text.DecimalFormat;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import superheroesintechnology.gl3am.Activities.AlarmActivity;
import superheroesintechnology.gl3am.Services.APIClient;
import superheroesintechnology.gl3am.Services.StorageClient;

/**
 * Created by Zach on 12/1/2015.
 */
public class AlarmModel {

    //I guess Java doesn't have defines, huh?  Frequency of API Calls (number of 2 second cycles).
    // Note that it also drops to FREQUENT once they reach the set radial distance.- ZBrester
    public static final int CONSTANT = 2;
    public static final int FREQUENT = 5;
    public static final int NORMAL = 30;
    public static final int SLOW = 30;
    public static final int MINUTE = 60;

    private boolean play_sound = false;
    private boolean send_SMS = false;
    private boolean push_notification = false;

    private boolean late_sound = false;
    private boolean late_SMS = false;
    private boolean late_push = false;

    private boolean arrive_sound = false;
    private boolean arrive_SMS = false;
    private boolean arrive_push = false;

    private boolean minimize_API = false;

    private Context context;
    private SMSMessage SMS;     //Who to message upon arrival.
    private SMSMessage Late_SMS;
    private SMSMessage Arrive_SMS;
    //private Destination destination;
    private LatLngModel destination;
    private long late_threshold;
    private int initial_time_left;
    private TextValModel time_left;
    private double distance_left;
    private String address_string;
    private int check_frequency = NORMAL;
    private double activation_distance;
    private String caller;

    public boolean error = false;
    //private int notify_flags = this.SOUND|this.PUSH;    //By default, only alerts via sound and a push notification.

    public AlarmModel(Context C, String search_address) {
        this.context = C;
        caller = C.getClass().toString();
        updateRoute(true, search_address);
        setFlags(5, 0, 0, false);
    }

    public AlarmModel(Context C, LatLngModel destination) {
        this.destination = destination;
        updateRoute(true, null);
        this.context = C;
        setFlags(5, 0, 0, false);   //5 = 4+1, play_sound and push_notification
    }

    public AlarmModel(Context C, LatLngModel destination, SMSMessage SMS) {
        this.context = C;
        this.destination = destination;
        updateRoute(true, null);
        this.SMS = SMS;
        setFlags(7, 0, 0, false); // 7 = 4+2+1, all of them.
    }


    public AlarmModel(Context C, LatLngModel destination, SMSMessage SMS, int threshold_flags, int late_flags, int arrive_flags,String search_address) {
        this.context = C;
        this.destination = destination;
        updateRoute(true, null);
        this.SMS = SMS;
        setFlags(threshold_flags, late_flags, arrive_flags, false);
    }

    public void updateContext(Context C) {
        this.context = C;
    }
    public void sendSMS(String argument) {
        if(argument == "SMS") {
            SMS.sendSMS();
        }
        else if (argument == "LATE") {
            Late_SMS.sendSMS();
        }
        else if (argument == "ARRIVE") {
            Arrive_SMS.sendSMS();
        }
    }

    public void setFlags(int threshold_flags, int late_flags, int arrive_flags, boolean minimize) {
        if(threshold_flags != 0) {
            if((threshold_flags & 1) != 0) {this.play_sound = true;}
            if((threshold_flags & 2) != 0) {this.send_SMS = true;}
            if((threshold_flags & 4) != 0) {this.push_notification = true;}
        }

        if(late_flags != 0) {
            if((late_flags & 1) != 0) {this.late_sound = true;}
            if((late_flags & 2) != 0) {this.late_SMS = true;}
            if((late_flags & 4) != 0) {this.late_push = true;}
        }

        if(arrive_flags != 0) {
            if((arrive_flags & 1) != 0) {this.arrive_sound = true;}
            if((arrive_flags & 2) != 0) {this.arrive_SMS = true;}
            if((arrive_flags & 4) != 0) {this.arrive_push = true;}
        }
        this.minimize_API = minimize;
    }

    public void notifyNearing() {
        if(push_notification) {
            Toast.makeText(context.getApplicationContext(), "Nearing destination.", Toast.LENGTH_LONG).show();
        }
        if(send_SMS) {
            sendSMS("SMS");
        }
        if(play_sound) {

        }
    }

    public void updateRoute(final boolean first, String search_address) {
        StorageClient StoreClient = new StorageClient(context, "default");
        if(!first) {
            search_address = URLEncoder.encode(destination.getCoordString());
        }
        else {
            search_address = URLEncoder.encode(search_address);
        }
        String temp_address = StoreClient.getCurrLocation().getCoordHtmlString();

        APIClient.getDirectionsProvider()

                .getDirections(temp_address, search_address)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResultModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        error = true;
                        Toast.makeText(context.getApplicationContext(),
                                "API Call unsuccessful. Please use a proper street address, with City.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(SearchResultModel searchResultModel) {
                        RouteModel route = searchResultModel.getSearchResults().get(0);
                        LegModel leg = route.getLeg(0);
                        time_left = leg.getDuration();
                        if (first) {
                            address_string = leg.getEnd_address();
                            initial_time_left = time_left.getValue();
                        }
                        destination = leg.getEnd_location();
                        Toast.makeText(context.getApplicationContext(), "API Call successful. Destination coordinates:"
                                + destination.getCoordString(), Toast.LENGTH_LONG).show();
                        if(caller == "Alarm") {

                        }

                    }
                });
    }
    public boolean verifyDistance() {

        // boolean verifyDist = false;

        if (activation_distance >= calcDistanceFromDest()) {
            return true;
            //verifyDist = true;
        }
        return false;
        //return verifyDist;
    }
    private double calcDistanceFromDest() {
        StorageClient StoreClient = new StorageClient(context, "default");
        LatLngModel temp_coords = StoreClient.getCurrLocation();
        double[] latLongArray = {this.destination.getLat(), this.destination.getLng(),temp_coords.getLat() , temp_coords.getLng()};
        double theta = this.destination.getLng() - temp_coords.getLng();
        double radTheta = Math.PI * theta / 180;

        // Converts values to radians
        for (int i = 0; i < 4; i++) {
            latLongArray[i] = Math.toRadians(latLongArray[i]);
        }
        //Math.toRadians()
        double calcedDist = Math.sin(latLongArray[0]) * Math.sin(latLongArray[2]) +
                Math.cos(latLongArray[0]) * Math.cos(latLongArray[2]) * Math.cos(radTheta);

        calcedDist = Math.acos(calcedDist);
        calcedDist *= 180 / Math.PI;
        calcedDist *= 60 * 1.1515;

        //DecimalFormat form = new DecimalFormat("0.00");
        distance_left = calcedDist;

        return distance_left;
    }

    public double getDistance_left() {return distance_left;}
    public void setDistance_left(double distance_left) {this.distance_left = distance_left;}
    public String getDistance_leftString() {
        DecimalFormat form = new DecimalFormat("0.00");
        return String.valueOf(form.format(distance_left)); }


    public String getAddress_string() {return address_string;}
    public void setAddress_string(String address_string) {this.address_string = address_string;}


    public double getActivation_distance(){return activation_distance;}
    public void setActivation_distance(double activation_distance) {this.activation_distance = activation_distance;}

    public SMSMessage getSMS() {return SMS;}
    public void setSMS(SMSMessage SMS) {this.SMS = SMS;}

    public SMSMessage getLate_SMS() {return Late_SMS;}
    public void setLate_SMS(SMSMessage late_SMS) {Late_SMS = late_SMS;}

    public SMSMessage getArrive_SMS() {return Arrive_SMS;}
    public void setArrive_SMS(SMSMessage arrive_SMS) {Arrive_SMS = arrive_SMS;}


    public LatLngModel getDestination() {return destination;}
    public void setDestination(LatLngModel destination) {this.destination = destination;}

    public long getLate_threshold() {return late_threshold;}
    public void setLate_threshold(long late_threshold) {this.late_threshold = late_threshold;}

    public TextValModel getTime_left() {return time_left;}
    public void setTime_left(TextValModel time_left) {this.time_left = time_left;}

    public int getTimeValue() {return time_left.getValue();}
    public String getTimeString() {return time_left.getText();}

    public int getCheck_frequency() {return check_frequency;}
    public void setCheck_frequency(int check_frequency) {this.check_frequency = check_frequency;}
}

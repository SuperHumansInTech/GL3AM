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
        if(argument.equals("SMS")) {
            SMS.sendSMS();
        }
        else if (argument.equals("LATE")) {
            Late_SMS.sendSMS();
        }
        else if (argument.equals("ARRIVE")) {
            Arrive_SMS.sendSMS();
        }
    }
    //setFlags - ZBrester
    //Uses  3 bitflags (split into 3 to avoid excess confusion) to set the preferences of the alarm.
    //For threshold (Nearing destination), late (time to arrival has exceeded your tolerance for being on-time,
    //And arrival (You have arrived within X miles of the destination.), the flags are as follows:
    // Play Sound: 1  Binary: (0001) (It's probably like 32 or 64 bit, though, don't worry about it)
    // Send SMS: 2 (0010)
    // Top Bar Notification: 4 (0100). (Not implemented yet).
    //Note that for ease of use, each flag just sets the booleans properly.
    //To clear a flag, send a value that doesn't contain bits 1, 2, or 4. Most reliably, just use -1.
    //This is a little better than sending 9 arguments, and an array is bound to get confusing.
    //Plus, I like bitflags. - ZBrester
    public void setFlags(int threshold_flags, int late_flags, int arrive_flags, boolean minimize) {
        if(threshold_flags != 0) {
            this.play_sound = false;
            this.send_SMS = false;
            this.push_notification = false;
            if(threshold_flags > 0) {
                if ((threshold_flags & 1) != 0) {this.play_sound = true;}
                if ((threshold_flags & 2) != 0) {this.send_SMS = true;}
                if ((threshold_flags & 4) != 0) {this.push_notification = true;}
            }
        }

        if(late_flags != 0) {
            this.late_sound = false;
            this.late_SMS = false;
            this.late_push = false;
            if(late_flags > 0) {

                if ((late_flags & 1) != 0) {this.late_sound = true;}
                if ((late_flags & 2) != 0) {this.late_SMS = true;}
                if ((late_flags & 4) != 0) {this.late_push = true;}
            }
        }

        if(arrive_flags != 0) {
            this.arrive_sound = false;
            this.arrive_SMS = false;
            this.arrive_push = false;
            if(arrive_flags > 0) {
                if ((arrive_flags & 1) != 0) {this.arrive_sound = true;}
                if ((arrive_flags & 2) != 0) {this.arrive_SMS = true;}
                if ((arrive_flags & 4) != 0) {this.arrive_push = true;}
            }
        }
        this.minimize_API = minimize;
    }

    //getFlags -ZBrester
    //There is absolutely a better way to do this, but it's due in 2 days and I'm an absolute madman.
    public boolean getFlags(String model, String type) {
        switch (model) {
            case "near" : {
                switch (type) {
                    case "sms": {return this.send_SMS;}
                    case "sound": {return this.play_sound;}
                    case "notify": {return this.push_notification;}
                }
            }

            case "late" : {
                switch (type) {
                    case "sms": {return this.late_SMS;}
                    case "sound": {return this.late_sound;}
                    case "notify": {return this.late_push;}
                }
            }
            case "arrive" : {
                switch (type) {
                    case "sms": {return this.arrive_SMS;}
                    case "sound": {return this.arrive_sound;}
                    case "notify": {return this.arrive_push;}
                }
            }
        }
        return false;
    }
    //getFlagStrings(String which) - ZBrester
    //Returns a string describing the type of alarm set on the AlarmModel.
    //Argument which can be either "near", "late", or "arrive", which should be self-explanatory.
    //If which is not one of these, or the booleans are in error, it will return the string "Error"
    //Can only be used to return a string for a single alarm criteria (I.e. nearing, late, or arrival).
    //If you wish to do all three, you will have to call each in turn. - ZBrester
    public String getFlagStrings(String which) {
        int temp = 0;
        if (which.equals("near")){
            if(this.play_sound) {temp +=1;}
            if(this.send_SMS) {temp += 2;}
        }
        else if (which.equals("late")) {
            if(this.late_sound) {temp += 1;}
            if(this.late_SMS) {temp += 2;}
        }
        else if(which.equals("arrive")) {
            if(this.arrive_sound) {temp +=1;}
            if(this.arrive_SMS) {temp += 2;}
        }

        switch(temp) {
            case (1): {
                return "Alarm Only.";
            }
            case (2):  {
                return "Message Only.";
            }
            case (3): {
                return "Alarm and Message.";
            }
            default: {
                return "Error.";
            }
        }
    }


    public void updateRoute(final boolean first, String search_address) {
        StorageClient StoreClient = new StorageClient(context, "default");
        if(!first) {
            search_address = destination.getCoordHtmlString();
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
                            destination = leg.getEnd_location();
                            Toast.makeText(context.getApplicationContext(), "API Call successful. Destination coordinates:"
                                    + destination.getCoordString(), Toast.LENGTH_LONG).show();
                        }
                        distance_left = leg.getDistance().getValue();
                    }
                });
    }
    public boolean verifyDistance() {
        return (activation_distance >= calcDistanceFromDest());
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

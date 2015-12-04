package superheroesintechnology.gl3am.Models;

import android.widget.Toast;

/**
 * Created by Zach on 12/1/2015.
 */
public class AlarmModel {

    //I guess Java doesn't have defines, huh?  Frequency of updates in seconds. - ZBrester
    public static final int CONSTANT = 2;
    public static final int FREQUENT = 5;
    public static final int NORMAL = 10;
    public static final int SLOW = 30;
    public static final int MINUTE = 60;

    //Bitflags for notifications. Seems kinda wasteful to define them like this though. -ZBrester
    public static final int SOUND = 1;
    public static final int SEND = 2;
    public static final int PUSH = 4; //Push message
    public static final int SOUNDLATE = 8;
    public static final int SENDLATE = 16;
    public static final int SENDARRIVE = 32;
    public static final int MINIMIZE_API = 64;

    private SMSMessage SMS;     //Who to message upon arrival.
    private SMSMessage Late_SMS;
    private SMSMessage Arrive_SMS;
    private Destination destination;
    private long est_arrival;
    private long late_threshold;
    private long time_left;
    private int check_frequency = NORMAL;
    private int notify_flags = this.SOUND|this.PUSH;    //By default, only alerts via sound and a push notification.

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

    public SMSMessage getSMS() {return SMS;}
    public void setSMS(SMSMessage SMS) {this.SMS = SMS;}

    public SMSMessage getLate_SMS() {return Late_SMS;}
    public void setLate_SMS(SMSMessage late_SMS) {Late_SMS = late_SMS;}

    public SMSMessage getArrive_SMS() {return Arrive_SMS;}
    public void setArrive_SMS(SMSMessage arrive_SMS) {Arrive_SMS = arrive_SMS;}

    public Destination getDestination() {return destination;}
    public void setDestination(Destination destination) {this.destination = destination;}

    public long getEst_arrival() {return est_arrival;}
    public void setEst_arrival(long est_arrival) {this.est_arrival = est_arrival;}

    public long getLate_threshold() {return late_threshold;}
    public void setLate_threshold(long late_threshold) {this.late_threshold = late_threshold;}

    public long getTime_left() {return time_left;}
    public void setTime_left(long time_left) {this.time_left = time_left;}

    public int getCheck_frequency() {return check_frequency;}
    public void setCheck_frequency(int check_frequency) {this.check_frequency = check_frequency;}

    public void set_flags(int flags) { notify_flags = flags;}
    public int get_flags() { return notify_flags;}
}

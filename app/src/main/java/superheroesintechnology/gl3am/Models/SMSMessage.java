package superheroesintechnology.gl3am.Models;

import android.app.Activity;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by chadlewis on 11/18/15.
 */
public class SMSMessage {



    private String name;
    private String description;
    private String phoneNumber;
    private String smsTextMessage;


    public SMSMessage(String number, String message) {
        this.phoneNumber = number;
        this.smsTextMessage = message;
    }

    public SMSMessage(String name, String description, String number, String message, boolean append){
        if(name == null) {
            this.name = number;
        }
        else {
            this.name = name;
        }

        if(description == null) {
            this.description = "No description";
        }
        else {
            this.description = description;
        }

        this.phoneNumber = number;

        if(message == null || message.equals("")) {
            this.smsTextMessage = null;
        }
        else {
            this.smsTextMessage = message;
        }
        this.smsTextMessage += "This message sent automatically by Gl3AMS.";

    }
    public SMSMessage(String name, String description, String number, String message) {

        if(name.equals("")) {
            this.name = number;
        }
        else {
            this.name = name;
        }

        if(description.equals("")) {
            this.description = "No description";
        }
        else {
            this.description = description;
        }

        this.phoneNumber = number;

        if(message.equals("")) {
            this.smsTextMessage = "";
        }
        else {
            this.smsTextMessage = message;

        }
    }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}


    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}


    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmsTextMessage() {
        return smsTextMessage;
    }
    public void setSmsTextMessage(String smsTextMessage) {
        this.smsTextMessage = smsTextMessage;
    }


    public boolean sendSMS () {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(getPhoneNumber(),null, getSmsTextMessage() ,null,null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}

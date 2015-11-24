package superheroesintechnology.gl3am.Services;

import java.util.ArrayList;

import superheroesintechnology.gl3am.Models.Destination;

/**
 * Created by student on 11/23/15.
 */
public class SMSClient {


     private ArrayList<SMSModel>  SMS_list;

    //setSMS_list(ArrayList<SMSModel> list) - Completely copies a new list of SMSModels to SMS_list.
    //I figure we should have this for completion but it's not really something we should be doing.
     public setSMS_list(ArrayList<SMSModel> list) {
         SMS_list.clear();
         SMS_list = list;
     }

    public ArrayList<SMSModel> getSMS_list() {return SMS_list;}

     private SMSModel active_SMS;

    //getActive_SMS() - Returns the active_SMS object. Yep. - ZBrester
    public SMSModel getActive_SMS() {
        return active_SMS;
    }

    //setActive_SMS(int index) - Sets active_SMS to the index of SMS_list sent. Returns 0 and does
    //nothing if invalid index, otherwise sets it and returns 1. - ZBrester
    public int set_Active_SMS(int index) {
        if(index < 0 || SMS_list.size() < (index - 1)) {
            return ;
        }

        active_SMS = SMS_list.get(index);
        return 1;
    }


    //Send() - Sends the active SMS message.
    //This prevents users from (easily) spoofing a text message, although I'm not really sure why
    //they would want to.    - ZBrester
    public void Send() {
        sendText(active_SMS);
        return;
    }

    //sendText(SMSModel sendto) - Sends the parameter SMS message. Private, called by Send() to avoid
    //spoofing just in case. I mean, they can probably still spoof it, I'm not a security guy. - ZBrester
    private void sendText(SMSModel sendto) {
        //Send a text goes here.
    }

}

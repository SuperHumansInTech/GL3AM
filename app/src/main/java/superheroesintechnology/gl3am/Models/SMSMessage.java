package superheroesintechnology.gl3am.Models;

import android.app.Activity;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by chadlewis on 11/18/15.
 */
public class SMSMessage extends Activity {

    public String phoneNumber;
    public String smsTextMessage;

    public SMSMessage(String number, String message) {
        this.phoneNumber = number;
        this.smsTextMessage = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmsTextMessage() {
        return smsTextMessage;
    }

    public void setSmsTextMessage(String smsTextMessage) {
        this.smsTextMessage = smsTextMessage;
    }

    public void sendSMS () {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null,smsTextMessage,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

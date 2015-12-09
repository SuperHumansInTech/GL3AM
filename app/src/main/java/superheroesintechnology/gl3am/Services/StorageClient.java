package superheroesintechnology.gl3am.Services;

/**
 * Created by Zach on 12/2/2015.
 */
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import superheroesintechnology.gl3am.Models.AlarmModel;
import superheroesintechnology.gl3am.Models.LatLngModel;
import superheroesintechnology.gl3am.Models.SMSMessage;

public class StorageClient {
    //Constants.
    private static final String ALARM_LIST = "alarms";  //This is the data entry for the list of alarms.
    private static final String SMS_LIST = "SMS";   //This is the data entry for the list of saved SMS.
    private static final String DEFAULT_ALARM = "default";  //This is the data entry for default settings.

    private static final String CURR_ALARM = "curr_alarm";  //This is the current single alarm to transfer.
    private static final String CURR_SMS = "curr_SMS";  //This is the current single SMS for transfer.

    //Temporary, erase once completed.
    private static final String CURR_LOCATION = "curr_location";

    private String preference_setting;
    private Context context;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor PrefEditor = null;
    final Gson gson = new GsonBuilder().create();

    //Constructor. Creates a new Shared Preference and editor for the given string and context.
    public StorageClient(Context context, String preference) {
        if(preference != null) {
            this.context = context;
            this.preference_setting = preference;
            sharedPref = context.getSharedPreferences(preference_setting, Context.MODE_PRIVATE);
            PrefEditor = sharedPref.edit();
        }
    }
    //Refresh(boolean save) - ZBrester
    //Just a private function to refresh the shared preferences and editor. Used when changing preferences.
    //It may be used elsewhere but that's about all I can think of for it.
    //boolean save: If save is true, it will save all current changes before refreshing.
    private void Refresh(boolean save) {
        if(save == true) {
            PrefEditor.commit();
        }
        sharedPref = context.getSharedPreferences(preference_setting, Context.MODE_PRIVATE);
        PrefEditor = sharedPref.edit();
    }

    //changePreference(String preference, boolean save) - ZBrester
    //Used to change the Preference setting. Not utilized, but could be useful for changing profiles
    //Or something like that. String preference is the new preference profile to use.
    //boolean save is passed to Refresh, true - Save changes before changing preference profiles.
    public void changePreference(String preference, boolean save) {
        if(preference!= null) { //Does nothing if they called it without a valid preference.
            this.preference_setting = preference;
            this.Refresh(save);
        }
    }
    //Alarm list
    public void saveAlarmList(ArrayList<AlarmModel> AlarmList) {
        String json = gson.toJson(AlarmList);   //Convert the AlarmList to JSON.
        PrefEditor.putString(ALARM_LIST, json); //Put it in.
        PrefEditor.apply();
    }

    public ArrayList<AlarmModel> loadAlarmList() {
        //Extract JSON string.
        String json = sharedPref.getString(ALARM_LIST, null);
        if(json == null) {
            //Returns null if there is no entry.
            return new ArrayList<AlarmModel>();
        }
        else {
            //Converts the JSON String to an ArrayList of type AlarmModel, then returns it.
            return gson.fromJson(json, new TypeToken<List<AlarmModel>>(){}.getType());
        }
    }
    //Adds a single alarm to the list.
    public void addAlarm(AlarmModel alarm) {
        ArrayList<AlarmModel> alarm_list = loadAlarmList();
        if (alarm_list == null) {
            alarm_list = new ArrayList<AlarmModel>();
        }
        alarm.updateContext(null);
        alarm_list.add(alarm);
        saveAlarmList(alarm_list);
        PrefEditor.apply();
    }

    //getAlarm - ZBrester
    //Takes a position as an argument, and returns the AlarmModel in that position in the Alarm List.
    //Returns null if index out of bounds, or the alarm list is empty.
    //Does not remove the AlarmModel from the list, merely extracts a copy. -ZBrester
    public AlarmModel getAlarm(int position) {
        ArrayList<AlarmModel> alarm_list = loadAlarmList();
        if (alarm_list == null || position < 0 || position >= alarm_list.size()) {
            return null;
        }
        else {
            return alarm_list.get(position);
        }
    }

    //deleteAlarm(int position) -ZBrester
    //Deletes the AlarmModel in that position from the alarm list.
    //Returns without doing anything if null list or position out of bounds.
    public void deleteAlarm(int position) {
        ArrayList<AlarmModel> alarm_list = loadAlarmList();
        if (alarm_list == null || position < 0 || position >= alarm_list.size()) {
            return;
        }
        else {
            alarm_list.remove(position);
            saveAlarmList(alarm_list);
        }
    }

    //SMS Message list
    public void saveSMSList(ArrayList<SMSMessage> SMSList) {
        String json = gson.toJson(SMSList);
        PrefEditor.putString(SMS_LIST, json);
        PrefEditor.apply();
    }

    public ArrayList<SMSMessage> loadSMSList() {
        String json = sharedPref.getString(SMS_LIST, null);
        if(json == null) {
            return new ArrayList<SMSMessage>();
        }
        else {
            Type token = new TypeToken<ArrayList<SMSMessage>>(){}.getType();
            return gson.fromJson(json, token);
        }
    }

    //Add a single SMS to the list.
    public void addSMS(SMSMessage message) {
        ArrayList<SMSMessage> message_list = loadSMSList();
        if (message_list == null) {
            message_list = new ArrayList<SMSMessage>();
        }
        message_list.add(message);
        saveSMSList(message_list);
        PrefEditor.apply();
    }

    public SMSMessage getSMS(int position) {
        ArrayList<SMSMessage> message_list = loadSMSList();

        if (message_list == null || position < 0 || position >= message_list.size()) {
            return null;
        }
        else { return message_list.get(position);}
    }

    public void deleteSMS(int position) {
        ArrayList<SMSMessage> message_list = loadSMSList();
        if (message_list == null || position < 0 || position >= message_list.size()) {
            return;
        }
        message_list.remove(position);
        saveSMSList(message_list);
    }




    //Deal with data transfer between objects.

    public void setCurrAlarm(AlarmModel current) {
        AlarmModel temp = current;
        if(temp != null) {
            temp.updateContext(null);
        }
        String json = gson.toJson(current);
        PrefEditor.putString(CURR_ALARM, json);
        PrefEditor.apply();
    }

    public AlarmModel getCurrAlarm(Context context) {
        String json = sharedPref.getString(CURR_ALARM, null);
        if(json == null) {
            return null;
        }
        else {
            AlarmModel temp = gson.fromJson(json, AlarmModel.class);
            temp.updateContext(context);
            return temp;
        }
    }

    public void setCurrSMS(SMSMessage current) {
        String json = gson.toJson(current);
        PrefEditor.putString(CURR_SMS, json);
        PrefEditor.apply();
    }

    public SMSMessage getCurrSMS() {
        String json = sharedPref.getString(CURR_SMS, null);
        if(json == null) {
            return new SMSMessage(null, null);
        }
        else {
            return gson.fromJson(json, SMSMessage.class);
        }
    }


    public void setCurrLocation(LatLngModel location) {
        String json = gson.toJson(location);
        PrefEditor.putString(CURR_LOCATION, json);
        PrefEditor.apply();
    }

    public LatLngModel getCurrLocation() {
        String json = sharedPref.getString(CURR_LOCATION, null);
        if(json == null) {
            return new LatLngModel();
        }
        else {
            return gson.fromJson(json, LatLngModel.class);
        }
    }

    //Making this public strikes me as a bad idea, honestly... -ZBrester
    public void purgeCurrent() {
        //Directly setting strings to avoid applying 3 times, JSON Serializing null, etc.
        PrefEditor.putString(CURR_SMS, null);
        PrefEditor.putString(CURR_ALARM, null);
        PrefEditor.commit();
    }


}

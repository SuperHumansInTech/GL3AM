package superheroesintechnology.gl3am.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.R;

/**
 * Created by Zach on 12/3/2015.
 */
public class SMSListAdapter extends ArrayAdapter<SMSMessage> {



    public SMSListAdapter(Context context, ArrayList<SMSMessage> messages) {
        super(context, 0, messages);
    }

    public SMSListAdapter(Context context, int resource, List<SMSMessage> items){
        super(context, resource, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.sms_item, null);

        }

        SMSMessage message = getItem(position);

        if (message != null) {

            TextView nameText = (TextView) v.findViewById(R.id.nameText);
            TextView descText = (TextView) v.findViewById(R.id.descText);
            TextView phoneText = (TextView) v.findViewById(R.id.phoneText);
            TextView messageText = (TextView) v.findViewById(R.id.messageText);


            if(nameText != null) {
                if(message.getName() != null) {
                    nameText.setText("Name: " + message.getName());
                }
            }

            if(descText != null) {
                if(message.getDescription() != null) {
                    descText.setText("Description: " + message.getDescription());
                }
            }


            if (phoneText != null) {
                if (message.getPhoneNumber() != null) {
                    phoneText.setText("Number: " + message.getPhoneNumber());
                }
            }

            if (messageText != null) {
                if (message.getSmsTextMessage() != null) {
                    messageText.setText("Message: " + message.getSmsTextMessage());
                }
            }

        }

        return v;
    }
}

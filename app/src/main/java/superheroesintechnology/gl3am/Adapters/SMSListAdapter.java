package superheroesintechnology.gl3am.Adapters;

import android.content.Context;
import android.media.Image;
import android.telephony.PhoneNumberUtils;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.OnSwipeTouchListener;
import superheroesintechnology.gl3am.R;

/**
 * Created by Zach on 12/3/2015.
 */
public class SMSListAdapter extends ArrayAdapter<SMSMessage> {

    private GestureDetector gesture;



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
           // TextView messageText = (TextView) v.findViewById(R.id.messageText);


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
                    phoneText.setText("Number: " + PhoneNumberUtils.formatNumber(message.getPhoneNumber()));
                }
            }

//            if (messageText != null) {
//                if (message.getSmsTextMessage() != null) {
//                    messageText.setText("Message: " + message.getSmsTextMessage());
//                }
//            }

        }

        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout deleteLayout = (LinearLayout)v.findViewById(R.id.SMSdeleteLinLay);
        final View deleteView = layoutInflater.inflate(R.layout.delete_list_item, deleteLayout,
                false);


        final ImageView swipeDelete = (ImageView)v.findViewById(R.id.swipeImage);
        final ImageView deleteItem = (ImageView) v.findViewById(R.id.deleteItemView);



                swipeDelete.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

                    @Override
                    public void onSwipeLeft() {
                        swipeDelete.setImageResource(R.drawable.right_swipe);
                        deleteLayout.addView(deleteView);
                    }

                    @Override
                    public void onSwipeRight() {
                        swipeDelete.setImageResource(R.drawable.swipe_arrow);
                        deleteLayout.removeView(deleteView);
                    }
                });



        return v;
    }
}

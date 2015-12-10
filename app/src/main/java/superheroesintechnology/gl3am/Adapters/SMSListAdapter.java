package superheroesintechnology.gl3am.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.view.LayoutInflaterFactory;
import android.telephony.PhoneNumberUtils;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import superheroesintechnology.gl3am.Activities.MessageActivity;
import superheroesintechnology.gl3am.Models.SMSMessage;
import superheroesintechnology.gl3am.OnSwipeTouchListener;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.StorageClient;

/**
 * Created by Zach on 12/3/2015.
 */
public class SMSListAdapter extends ArrayAdapter<SMSMessage> {

    StorageClient storeClient = new StorageClient(this.getContext(), "default");
    private GestureDetector gesture;



    public SMSListAdapter(Context context, ArrayList<SMSMessage> messages) {
        super(context, 0, messages);
    }

    public SMSListAdapter(Context context, int resource, List<SMSMessage> items){
        super(context, resource, items);
    }

    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View v = convertView;


        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.sms_item, null);
            View deleteView = v;

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


        //The following allows the user to click an expansion button that reveals a delete button
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout deleteLayout = (LinearLayout)v.findViewById(R.id.SMSdeleteLinLay);
        final View deleteView = layoutInflater.inflate(R.layout.delete_list_item, deleteLayout,
                false);

        final ImageView swipeDelete = (ImageView)v.findViewById(R.id.swipeImage);


        swipeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swipeDelete.getTag().toString().equals("p")) {
                    swipeDelete.setImageResource(R.drawable.swipe_arrow);
                    deleteLayout.removeView(deleteView);
                    swipeDelete.setTag("nP");

                } else {
                    swipeDelete.setTag("p");
                    swipeDelete.setImageResource(R.drawable.right_swipe);
                    deleteLayout.addView(deleteView);
                    final ImageView deleteItem = (ImageView) deleteView.findViewById(R.id.deleteItemView);
                    deleteItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            swipeDelete.setImageResource(R.drawable.swipe_arrow);
                            deleteLayout.removeView(deleteView);
                            swipeDelete.setTag("nP");
                            storeClient.deleteSMS(position);

                            SMSListAdapter.this.remove(getItem(position));
                            SMSListAdapter.this.notifyDataSetChanged();

//                            parent.getChildAt(position).setVisibility(View.GONE);

                        }
                    });
                }
            }
        });


        return v;
    }
}

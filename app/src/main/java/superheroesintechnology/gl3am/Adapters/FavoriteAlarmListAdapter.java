package superheroesintechnology.gl3am.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import superheroesintechnology.gl3am.Models.AlarmModel;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.StorageClient;

/**
 * Created by chadlewis on 12/8/15.
 */
public class FavoriteAlarmListAdapter extends ArrayAdapter<AlarmModel> {
    StorageClient storeClient = new StorageClient(this.getContext(), "default");

    public FavoriteAlarmListAdapter(Context context, ArrayList<AlarmModel> alarms) {
        super(context, 0, alarms);
    }

    public FavoriteAlarmListAdapter(Context context, int resource, List<AlarmModel> items) {
        super(context, resource, items);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        int pos = position;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.alarm_item, null);
        }

        AlarmModel alarm = getItem(position);

        if (alarm != null) {

            TextView destText = (TextView) v.findViewById(R.id.destText);
            TextView typeText = (TextView) v.findViewById(R.id.typeText);
            TextView activationDistText =
                    (TextView) v.findViewById(R.id.activationDistText);
            ImageView typeImg = (ImageView) v.findViewById(R.id.servTypeImg);

            if (destText != null) {
                if (alarm.getAddress_string() != null) {
                    destText.setText(alarm.getAddress_string());
                }
            }

            if (typeText != null) {
                typeText.setText(alarm.getFlagStrings("near"));

                //Switch case statement loads proper service image to the service type
                switch (alarm.getFlagStrings("near")){

                    case "Alarm Only":{
                        typeImg.setImageResource(R.drawable.alarm54);
                        break;
                    }

                    case "Message Only" :{
                        typeImg.setImageResource(R.drawable.speehcbubble);
                        break;
                    }

                    case "Alarm and Message" :{
                        typeImg.setImageResource(R.drawable.alarm_text_icon);
                        break;
                    }
                }
            }

            if (activationDistText != null) {
                activationDistText.setText(String.valueOf(alarm.getActivation_distance()) + " mi");
            }

            //The following allows the user to click an expansion button that reveals a delete button
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout deleteLayout = (LinearLayout)v.findViewById(R.id.alarmDelLL);
            final View deleteView = layoutInflater.inflate(R.layout.delete_list_item, deleteLayout,
                    false);

            final ImageView deleteItem = (ImageView)v.findViewById(R.id.expandImage);

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteItem.getTag().toString().equals("p")) {
                        deleteItem.setImageResource(R.drawable.swipe_arrow);
                        deleteLayout.removeView(deleteView);
                        deleteItem.setTag("nP");

                    } else {
                        deleteItem.setTag("p");
                        deleteItem.setImageResource(R.drawable.right_swipe);
                        deleteLayout.addView(deleteView);
                        final ImageView deleteItem = (ImageView) deleteView.findViewById(R.id.deleteItemView);
                        deleteItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteItem.setImageResource(R.drawable.swipe_arrow);
                                deleteLayout.removeView(deleteView);
                                deleteItem.setTag("nP");

                                storeClient.deleteAlarm(position);

                                FavoriteAlarmListAdapter.this.remove(getItem(position));
                                FavoriteAlarmListAdapter.this.notifyDataSetChanged();

//                            parent.getChildAt(position).setVisibility(View.GONE);

                            }
                        });
                    }
                }
            });
        }

        return v;
    }
}

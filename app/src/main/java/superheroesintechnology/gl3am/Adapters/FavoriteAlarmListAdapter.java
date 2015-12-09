package superheroesintechnology.gl3am.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import superheroesintechnology.gl3am.Models.AlarmModel;
import superheroesintechnology.gl3am.R;

/**
 * Created by chadlewis on 12/8/15.
 */
public class FavoriteAlarmListAdapter extends ArrayAdapter<AlarmModel> {
    public FavoriteAlarmListAdapter(Context context, ArrayList<AlarmModel> alarms) {
        super(context, 0, alarms);
    }

    public FavoriteAlarmListAdapter(Context context, int resource, List<AlarmModel> items) {
        super(context, resource, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

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

            if (destText != null) {
                if (alarm.getAddress_string() != null) {
                    destText.setText(alarm.getAddress_string());
                }
            }

//            if (typeText != null) {
//                if (alarm.get)
//            }

            if (activationDistText != null) {
                activationDistText.setText(String.valueOf(alarm.getActivation_distance()));
            }
        }

        return v;
    }
}

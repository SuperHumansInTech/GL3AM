package superheroesintechnology.gl3am.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import superheroesintechnology.gl3am.Models.LegModel;
import superheroesintechnology.gl3am.Models.RouteModel;
import superheroesintechnology.gl3am.R;

/**
 * Created by Zach on 11/12/2015.
 */
public class DirectionListAdapter extends ArrayAdapter<RouteModel> {
    public DirectionListAdapter(Context context, ArrayList<RouteModel> routes) {
        super(context, 0, routes);
    }

    public DirectionListAdapter(Context context, int resource, ArrayList<RouteModel> items){
        super(context, resource, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.route_item, null);

        }

        RouteModel route = getItem(position);

        if(route != null) {
            TextView summaryText = (TextView) v.findViewById(R.id.summaryText);
            TextView durationText = (TextView) v.findViewById(R.id.durationText);
            TextView dAddressText = (TextView) v.findViewById(R.id.dAddressText);
            TextView dLatLngText = (TextView) v.findViewById(R.id.dLatLngText);
            TextView sAddressText = (TextView) v.findViewById(R.id.sAddressText);
            TextView sLatLngText = (TextView) v.findViewById(R.id.sLatLngText);

            if(summaryText != null) {
                summaryText.setText(route.getSummary());
            }
            if(route.legCount() >= 1) {
                LegModel firstleg = route.getLeg(0);
                if (firstleg != null) {

                }
                if(durationText != null) {
                    durationText.setText(firstleg.getDurationText());
                }
                if(dAddressText != null) {
                    dAddressText.setText(firstleg.getEnd_address());
                }
                if(dLatLngText != null) {
                    dLatLngText.setText(firstleg.getEnd_latlngString());

                }
                if(sAddressText != null) {
                    sAddressText.setText(firstleg.getStart_address());
                }
                if(sLatLngText != null) {
                    sLatLngText.setText(firstleg.getStart_latlngString());
                }

            }
        }
        return v;
    }

}

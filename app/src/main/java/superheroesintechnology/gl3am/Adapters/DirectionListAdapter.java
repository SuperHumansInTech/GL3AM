package superheroesintechnology.gl3am.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import superheroesintechnology.gl3am.Models.LatLngModel;
import superheroesintechnology.gl3am.Models.LegModel;
import superheroesintechnology.gl3am.Models.RouteModel;
import superheroesintechnology.gl3am.Models.TextValModel;
import superheroesintechnology.gl3am.R;

/**
 * Created by Zach on 11/12/2015.
 */
public class DirectionListAdapter extends ArrayAdapter<RouteModel> {
    public DirectionListAdapter(Context context, ArrayList<RouteModel> routes) {
        super(context, 0, routes);
    }

    public DirectionListAdapter(Context context, int resource, List<RouteModel> items){
        super(context, resource, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.route_item, null);

        }

        RouteModel route = getItem(position);

        if (route != null) {
            TextView summaryText = (TextView) v.findViewById(R.id.summaryText);
            TextView durationText = (TextView) v.findViewById(R.id.durationText);
            TextView dAddressText = (TextView) v.findViewById(R.id.dAddressText);
            TextView dLatLngText = (TextView) v.findViewById(R.id.dLatLngText);
            TextView sAddressText = (TextView) v.findViewById(R.id.sAddressText);
            TextView sLatLngText = (TextView) v.findViewById(R.id.sLatLngText);

            if (summaryText != null) {
                summaryText.setText(route.getSummary());
            }
            if (route.legCount() >= 1) {
                LegModel firstleg = route.getLeg(0);
                if (firstleg != null) {

                    if (durationText != null) {

                        TextValModel duration = firstleg.getDuration();
                        if(duration != null) {
                            durationText.setText(duration.getText());
                            if(durationText.getText() != null)
                                durationText.getText();
                        }
                    }

                    if (dAddressText != null) {

                        if(firstleg.getEnd_address() != null) {
                            dAddressText.setText(firstleg.getEnd_address());
                        }
                    }

                    if (dLatLngText != null) {

                        LatLngModel end_coords = firstleg.getEnd_location();
                        if (end_coords != null) {
                            dLatLngText.setText(firstleg.getEnd_latlngString());
                        }


                    }
                    if (sAddressText != null) {

                        if(firstleg.getStart_address() != null) {
                            sAddressText.setText(firstleg.getStart_address());
                        }
                    }
                    if (sLatLngText != null) {
                            sLatLngText.setText(firstleg.getStart_latlngString());

                    }


                }
            }
        }
        return v;
    }
}

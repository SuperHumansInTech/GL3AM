package superheroesintechnology.gl3am.Models;

import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by Zach on 11/11/2015.
 */
public class SearchResultModel {
    @SerializedName("status")
    private String status;

    @SerializedName("routes")
    private ArrayList<RouteModel> routeList;

    //Methods
    //
    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}


    public ArrayList<RouteModel> getSearchResults() { return routeList;}

    public void setSearchResults(ArrayList<RouteModel> routelist) {this.routeList = routelist;}

    //getDestinationCoords - A quick function for the obtaining of the coordinates. Temporary,
    //until I make a Business Model object. This one gets the first of both ArrayLists automatically.
    //- ZBrester
    public LatLngModel getDestinationCoords() {
        if(getSearchResults().isEmpty() || getSearchResults().get(0).getLegsArray().isEmpty()) {
            return null;
        }

        return  getSearchResults().get(0)
                .getLeg(0)
                .getEnd_location();
    }

    //getDestinationCoords(int route_index, int leg_index) - Same as above, however it takes an index
    //for route and for leg. Most results are only 1 route, 1 leg, but might be useful? - ZBrester
    public LatLngModel getDestinationCoords(int route_index, int leg_index) {

        if(route_index >= getSearchResults().size() || route_index < 0) {return null;}
        int legsize = getSearchResults().get(route_index).getLegsArray().size();
        if(leg_index >= legsize || leg_index < 0) { return null;}


        return getSearchResults().get(route_index)
                            .getLeg(leg_index)
                            .getEnd_location();

    }



}

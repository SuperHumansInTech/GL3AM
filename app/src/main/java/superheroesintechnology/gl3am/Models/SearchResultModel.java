package superheroesintechnology.gl3am.Models;

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



}

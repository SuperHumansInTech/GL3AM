package superheroesintechnology.gl3am.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Zach on 11/11/2015.
 */
public class RouteModel {

    @SerializedName("summary")
    private String Summary;

    public String getSummary() {return Summary;}
    public void setSummary(String summary) {Summary = summary;}


    @SerializedName("legs")
    private ArrayList<LegModel> legsArray;

    public ArrayList<LegModel> getLegsArray() {return legsArray;}
    public void setLegsArray(ArrayList<LegModel> legsArray) {this.legsArray = legsArray;}
    public int legCount() {return this.legsArray.size();}
    public LegModel getLeg(int position) {return this.legsArray.get(position);}


    @SerializedName("waypoint_order")
    private int[] waypointOrder;

    public int[] getWaypointOrder() {return waypointOrder;}
    public void setWaypointOrder(int[] waypointOrder) {this.waypointOrder = waypointOrder;}


    @SerializedName("copyrights")
    private String copyrights;

    public String getCopyrights() {return copyrights;}
    public void setCopyrights(String copyrights) {this.copyrights = copyrights;}


    @SerializedName("warnings")
    private ArrayList<String> warnings;

    public ArrayList<String> getWarnings() {return warnings;}
    public void setWarnings(ArrayList<String> warnings) {this.warnings = warnings;}


    @SerializedName("fare")
    private FareModel fare;

    public FareModel getFare() {return fare;}
    public void setFare(FareModel fare) {this.fare = fare;}
}

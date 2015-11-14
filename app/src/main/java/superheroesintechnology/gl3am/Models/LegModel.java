package superheroesintechnology.gl3am.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Leg Model.
 *
 * stepList: Arraylist of Steps, concrete directions (as in, "continue for 20 miles", "turn left at
 * the fork", etc. Has getter, setter, and a getStep(position), which returns the step in that
 * position. If an index out of bounds is given as the argument, it constrains to 0 or last index.
 *
 * Created by Zach on 11/11/2015.
 */
public class LegModel {

    @SerializedName("steps")
    private ArrayList<StepModel> stepList;

    public ArrayList<StepModel> getStepList() {return stepList;}
    public void setStepList(ArrayList<StepModel> stepList) {this.stepList = stepList;}
    public StepModel getStep(int position) {
        if(position > stepList.size())      //Sanity checks.
            return stepList.get(stepList.size() - 1);
        else if (position <= 0)
            return stepList.get(0);
        else
            return stepList.get(position);}


    @SerializedName("distance")
    private TextValModel distance;

    public TextValModel getDistance() {return distance;}
    public void setDistance(TextValModel distance) {this.distance = distance;}


    @SerializedName("duration")
    private TextValModel duration;

    public TextValModel getDuration() {return duration;}
    public void setDuration(TextValModel duration) {this.duration = duration;}
    public String getDurationText() {return duration.getText(); }
    public int getDurationValue() {return duration.getValue();}

    @SerializedName("duration_in_traffic")
    private TextValModel traffic_duration;

    public TextValModel getTraffic_duration() {return traffic_duration;}
    public void setTraffic_duration(TextValModel traffic_duration) {this.traffic_duration = traffic_duration;}


    @SerializedName("start_location")
    private LatLngModel start_location;

    public LatLngModel getStart_location() {return start_location;}
    public void setStart_location(LatLngModel start_location) {this.start_location = start_location;}
    public String getStart_latlngString() {return this.start_location.getCoordString();}

    @SerializedName("end_location")
    private LatLngModel end_location;

    public LatLngModel getEnd_location() {return end_location;}
    public void setEnd_location(LatLngModel end_location) {this.end_location = end_location;}
    public String getEnd_latlngString() {return this.end_location.getCoordString();}


    @SerializedName("start_address")
    private String start_address;

    public String getStart_address() {return start_address;}
    public void setStart_address(String start_address) {this.start_address = start_address;}



    @SerializedName("end_address")
    private String end_address;

    public String getEnd_address() {return end_address;}
    public void setEnd_address(String end_address) {this.end_address = end_address;}


    //@SerializedName("arrival_time")   //Only for transit.
    //private Time  arrival_time;

    //@SerializedName("departure_time") //Only for transit.
    //private Time departure_time;

}

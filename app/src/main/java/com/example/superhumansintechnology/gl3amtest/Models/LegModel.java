package com.example.superhumansintechnology.gl3amtest.Models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Zach on 11/5/2015.
 */
public class LegModel {

    @SerializedName("steps")
    private ArrayList<StepModel> stepList;

    @SerializedName("distance")
    private TextValModel distance;

    @SerializedName("duration")
    private TextValModel duration;

    @SerializedName("duration_in_traffic")
    private TextValModel traffic_duration;

    //@SerializedName("arrival_time")   //Only for transit.
    //private Time  arrival_time;

    //@SerializedName("departure_time") //Only for transit.
    //private Time departure_time;

    @SerializedName("start_location")
    private LatLngModel start_location;

    @SerializedName("end_location")
    private LatLngModel end_location;

    @SerializedName("start_address")
    private String start_address;

    @SerializedName("end_address")
    private String end_address;
}

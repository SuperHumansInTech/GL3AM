package com.example.superhumansintechnology.gl3amtest.Models;

/**
 * Created by Zach on 11/5/2015.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class RouteModel {
    @SerializedName("summary")
    private String Summary;

    @SerializedName("legs")
    private ArrayList<LegModel> legsArray;

    @SerializedName("waypoint_order")
    private int[] waypointOrder;

    @SerializedName("copyrights")
    private String copyrights;

    @SerializedName("warnings")
    private ArrayList<String> warnings;

    @SerializedName("fare")
    private FareModel fare;

}

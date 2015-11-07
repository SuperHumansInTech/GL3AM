package com.example.superhumansintechnology.gl3amtest.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zach on 11/5/2015.
 */
public class StepModel {
    @SerializedName("html_instructions")
    private String instructions;

    @SerializedName("distance")
    private TextValModel distance;

    @SerializedName("duration")
    private TextValModel duration;

    @SerializedName("start_location")
    private LatLngModel start_location;

    @SerializedName("end_location")
    private LatLngModel end_location;

}

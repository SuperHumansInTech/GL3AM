package com.example.superhumansintechnology.gl3amtest.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zach on 11/5/2015.
 */
public class LatLngModel {


    @SerializedName("lat")
    private float lat;

    @SerializedName("lng")
    private float lng;

    public float getLat() {return lat;};

    public void setLat(float lat) {this.lat = lat;};

    public float getLng() {return lng;};

    public void setLng(float lng) {this.lng = lng;};

    public void setCoords(float lat, float lng) { this.lat = lat ; this.lng = lng;};

    public String getCoordString() {String value = Float.toString(this.lat) + ", " + Float.toString(this.lng); return value;};

}

 package superheroesintechnology.gl3am.Models;



import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.net.URLEncoder;

 /**
 * A model to store Latitude and Longitude deserialized from JSON, as doubles.
 *
 * Includes methods to set it manually, return either, and return a string of both of the values separated by a comma (for API queries).
 * Created by Zach on 11/11/2015.
 */
public class LatLngModel {


    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    public double getLat() {return lat;}

    public void setLat(double lat) {this.lat = lat;}

    public double getLng() {return lng;}

    public void setLng(double lng) {this.lng = lng;}

    public void setCoords(double lat, double lng) { this.lat = lat ; this.lng = lng;}

    //Returns a string in the form "Latitude, Longitude". This is used for API calls.
    public String getCoordString() {return String.valueOf(this.lat) + ", " + String.valueOf(this.lng);}

    public String getCoordHtmlString() {return (String.valueOf(this.lat) + "," + String.valueOf(this.lng));}

}

package superheroesintechnology.gl3am.Models;

/**
 * Destination Class:
 *
 * This class is designed to collect the destination latitude and longitude and store them as both
 * doubles and strings. This class will also be able to input current location and calculate the
 * users distance from the destination.
 *
 * Created by jjols on 11/7/2015.
 */
public class Destination {



    // Longitude vars
    private double doubLong;
    private String stringLong;

    // Latitude vars
    private double doubLat;
    private String stringLat;

    // Destination Vars
    private String addressString;

    // Calculation values
    private double distFromDest; // User defined distance
    private String distFromDestStr;
    private String distFromCurLoc; // Value of current distance from destination as string type

    /*
     Constructor
     Input: Takes in destination latitude, longitude and user defined distance from destination as
     double types.
     Output: Stores values of doubles and also stores them as String types.
     */
    public Destination(String address, double doubLat, double doubLong, double distFromDest) {

        this.addressString = address;
        this.doubLat = doubLat;
        this.stringLat = String.valueOf(doubLat);
        this.doubLong = doubLong;
        this.stringLong = String.valueOf(doubLong);
        this.distFromDest = distFromDest;
        this.distFromDestStr = String.valueOf(distFromDest);
    }


    /*
     Setters:
     Input: There are three setters that take in double types for latitude, longitude, and user
            defined distance.
     Output: Stores double values in their respective values, and also stored as strings
     */
    public void setDoubLat(double doubLat) {
        this.doubLat = doubLat;
        this.stringLat = String.valueOf(doubLat);
    }

    public void setDoubLong(double doubLong) {
        this.doubLong = doubLong;
        this.stringLong = String.valueOf(doubLong);
    }

    public void setDistFromDest(double distFromDest) {
        this.distFromDest = distFromDest;
        this.distFromDestStr = String.valueOf(distFromDest);
    }

    public void setAddressString(String addressString){
        this.addressString = addressString;
    }

    /*
    Getters:
    Input: None
    Output: Returns values for double and string types for destinations lat and long. Also string
            type return for distance from current location to destination.
     */
    public double getDoubLat(){
        return doubLat;
    }

    public double getDoubLong() {
        return doubLong;
    }

    public String getStringLat() {
        return stringLat;
    }

    public String getStringLong() {
        return stringLong;
    }

    public String getAddressString(){
        return addressString;
    }

    public String getDistFromDestStr(){
        return distFromDestStr;
    }

    public String getDistFromCurLoc(){
        return distFromCurLoc;
    }

    //**********************************************************************************************

   /*
    verifyDistance():
    Input: takes in double values for current long and lat
    Output: returns a false boolean value if distance from destination is greater than the user
            defined distance threshold from destination and returns true if less
    */
    public boolean verifyDistance(double curLong, double curLat){

        boolean verifyDist = false;

        if (distFromDest > calcDistanceFromDest(curLong, curLat)){
            verifyDist = true;
        }

        return verifyDist;
    }

    /*
        calcDistanceFromDest():
        Input: Takes double types for current locations long and lats
        Output: Returns a double value for the distance from current location to the destination
     */
    private double calcDistanceFromDest(double curLong, double curLat){

        double[] latLongArray = {this.doubLat, this.doubLong, curLat, curLong};
        double theta = this.doubLong - curLong;
        double radTheta = Math.PI * theta/180;

        // Converts values to radians
        for (int i=0; i < 4; i++){
            latLongArray[i] = Math.toRadians(latLongArray[i]);
        }
            //Math.toRadians()
        double calcedDist = Math.sin(latLongArray[0]) * Math.sin(latLongArray[2]) +
                Math.cos(latLongArray[0]) * Math.cos(latLongArray[2]) * Math.cos(radTheta);

        calcedDist = Math.acos(calcedDist);
        calcedDist *= 180/Math.PI;
        calcedDist *= 60 * 1.1515;

        distFromCurLoc = String.valueOf(calcedDist);

        return calcedDist;
    }

}

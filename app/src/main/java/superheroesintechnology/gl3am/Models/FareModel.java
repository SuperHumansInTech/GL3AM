package superheroesintechnology.gl3am.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Fare Model. Contains a currency code (ISO 4217) of the currency of the fare, a total number value of the fare,
 * and a text field that contains the total fair amount, in the language the API call was made in.
 * Contains general getters and setters as methods.
 * Created by Zach on 11/11/2015.
 */
public class FareModel {
    @SerializedName("currency")
    private String currCode;

    @SerializedName("value")
    private float Amount;

    @SerializedName("text")
    private String text;

    //Methods

    //CurrCode
    public String getCurrCode() {return currCode;}

    public void setCurrCode(String currCode) {this.currCode = currCode;}

    //Amount
    public float getAmount() {return Amount;}

    public void setAmount(int toSet) {this.Amount = toSet;}

    //Text
    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

}

package com.example.superhumansintechnology.gl3amtest.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zach on 11/5/2015.
 */
public class FareModel {
    @SerializedName("currency")
    private String currCode;

    @SerializedName("value")
    private int Amount;

    @SerializedName("text")
    private String text;

    //Methods

    //CurrCode
    public String getCurrCode() {return currCode;}

    public void setCurrCode(String currCode) {this.currCode = currCode;}

    //Amount
    public int getAmount() {return Amount;}

    public void setAmount(int toSet) {this.Amount = toSet;}

    //Text
    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

}




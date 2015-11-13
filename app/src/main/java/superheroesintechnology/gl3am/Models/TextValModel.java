package superheroesintechnology.gl3am.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zach on 11/11/2015.
 */
public class TextValModel {
    //General class for one value, one string, since it's used repeatedly.

    @SerializedName("value")
    private int value;

    @SerializedName("text")
    private String text;

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    public int getValue() {return value;}

    public void setValue(int value) {this.value = value;}

}

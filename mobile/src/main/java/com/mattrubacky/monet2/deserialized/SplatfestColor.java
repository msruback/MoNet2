package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
public class SplatfestColor implements Parcelable {
    public SplatfestColor(){}
    @SerializedName("b")
    double b;
    @SerializedName("r")
    double r;
    @SerializedName("g")
    double g;
    @SerializedName("color")
    String color;

    protected SplatfestColor(Parcel in) {
        b = in.readDouble();
        r = in.readDouble();
        g = in.readDouble();
        color = in.readString();
    }

    public static final Creator<SplatfestColor> CREATOR = new Creator<SplatfestColor>() {
        @Override
        public SplatfestColor createFromParcel(Parcel in) {
            return new SplatfestColor(in);
        }

        @Override
        public SplatfestColor[] newArray(int size) {
            return new SplatfestColor[size];
        }
    };

    public String getColor(){
        if(color==null) {
            StringBuilder builder = new StringBuilder();
            builder.append("#");
            int colorNum = (int) (255 * r);
            builder.append(getHexDigit(colorNum / 16));
            builder.append(getHexDigit(colorNum % 16));
            colorNum = (int) (255 * g);
            builder.append(getHexDigit(colorNum / 16));
            builder.append(getHexDigit(colorNum % 16));
            colorNum = (int) (255 * b);
            builder.append(getHexDigit(colorNum / 16));
            builder.append(getHexDigit(colorNum % 16));
            color = builder.toString();

        }
        return color;
    }
    private String getHexDigit(int num){
        switch(num){
            case 10:
                return "a";
            case 11:
                return "b";
            case 12:
                return "c";
            case 13:
                return "d";
            case 14:
                return "e";
            case 15:
                return "f";
            default:
                return String.valueOf(num);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(b);
        dest.writeDouble(r);
        dest.writeDouble(g);
        dest.writeString(color);
    }
}

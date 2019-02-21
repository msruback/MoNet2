package com.mattrubacky.monet2.data.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 11/20/2017.
 */

public class Rates implements Parcelable{
    public Rates(){}

    @SerializedName("alpha")
    public int alpha;
    @SerializedName("bravo")
    public int bravo;

    protected Rates(Parcel in) {
        alpha = in.readInt();
        bravo = in.readInt();
    }

    public static final Creator<Rates> CREATOR = new Creator<Rates>() {
        @Override
        public Rates createFromParcel(Parcel in) {
            return new Rates(in);
        }

        @Override
        public Rates[] newArray(int size) {
            return new Rates[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(alpha);
        dest.writeInt(bravo);
    }
}

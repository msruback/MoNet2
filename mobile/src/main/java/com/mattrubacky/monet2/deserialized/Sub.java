package com.mattrubacky.monet2.deserialized;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class Sub implements Parcelable {
    public Sub(){
    }

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("image_a")
    String url;

    protected Sub(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Sub> CREATOR = new Creator<Sub>() {
        @Override
        public Sub createFromParcel(Parcel in) {
            return new Sub(in);
        }

        @Override
        public Sub[] newArray(int size) {
            return new Sub[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(url);
    }
}

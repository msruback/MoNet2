package com.mattrubacky.monet2.data.deserialized_entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by mattr on 10/17/2017.
 * This class represents an individual gear brand.
 * Brand images are stored in brand
 */

@Entity(tableName = "brand",
        foreignKeys = {
                @ForeignKey(entity = Skill.class,
                        parentColumns = "skill_id",
                        childColumns = "brand_skill")
        },
        indices = {
                @Index(name="brand_skill",
                        value = "brand_skill")
        })
public class Brand implements Parcelable{

    //GSON constructor
    @Ignore
    public Brand(){}

    //Rooms constructor
    public Brand(int id,String name, String url, Skill skill){
        this.id = id;
        this.name = name;
        this.url = url;
        this.skill = skill;
    }

    @PrimaryKey
    @ColumnInfo(name="brand_id")
    @SerializedName("id")
    public Integer id;

    //The name of the brand
    @ColumnInfo(name="brand_name")
    @SerializedName("name")
    public String name;

    //Url of the brand url
    @ColumnInfo(name="brand_image")
    @SerializedName("image")
    public String url;

    //The skill the brand is most likely to roll
    @ColumnInfo(name="brand_skill")
    @SerializedName("frequent_skill")
    public Skill skill;

    protected Brand(Parcel in) {
        name = in.readString();
        id = in.readInt();
        url = in.readString();
        skill = in.readParcelable(Skill.class.getClassLoader());
    }

    public static final Parcelable.Creator<Brand> CREATOR = new Parcelable.Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel in) {
            return new Brand(in);
        }

        @Override
        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeParcelable(skill, flags);
    }
}

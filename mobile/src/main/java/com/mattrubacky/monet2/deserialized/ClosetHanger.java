package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents a piece of gear stored in the "Closet"
 * Not a part of the Splatnet API
 */

public class ClosetHanger {
    public ClosetHanger(){
    }

    @SerializedName("gear")
    public Gear gear;
    @SerializedName("skills")
    public GearSkills skills;
    @SerializedName("last_use_time")
    public Long time;
}

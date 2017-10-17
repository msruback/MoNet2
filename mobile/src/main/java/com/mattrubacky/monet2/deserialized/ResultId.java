package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class ResultId{
    public ResultId(){
    }

    @SerializedName("battle_number")
    int id;
}

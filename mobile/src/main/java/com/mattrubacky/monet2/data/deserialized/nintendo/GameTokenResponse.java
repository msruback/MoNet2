package com.mattrubacky.monet2.data.deserialized.nintendo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 8/14/2018.
 */

public class GameTokenResponse {
    public GameTokenResponse(){}

    @SerializedName("result")
    public GameTokenResult result;
}

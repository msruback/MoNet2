package com.mattrubacky.monet2.deserialized.nintendo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 8/14/2018.
 */

public class WebAPIServerCredential {
    public WebAPIServerCredential(){}

    @SerializedName("accessToken")
    public String accessToken;
}

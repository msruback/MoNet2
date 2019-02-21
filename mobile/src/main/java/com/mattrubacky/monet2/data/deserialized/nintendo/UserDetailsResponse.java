package com.mattrubacky.monet2.data.deserialized.nintendo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 8/14/2018.
 */

public class UserDetailsResponse {

    public UserDetailsResponse(){}

    @SerializedName("birthday")
    public String birthday;

    @SerializedName("country")
    public String country;

    @SerializedName("language")
    public String language;
}

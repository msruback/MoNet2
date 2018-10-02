package com.mattrubacky.monet2.deserialized.nintendo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 8/21/2018.
 */

public class LoginResultResult {
    LoginResultResult(){}
    @SerializedName("webApiServerCredential")
    WebAPIServerCredential webAPIServerCredential;
}

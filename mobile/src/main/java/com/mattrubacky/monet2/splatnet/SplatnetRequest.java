package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by mattr on 12/6/2017.
 */

public abstract class SplatnetRequest {
    protected Splatnet splatnet;
    protected String cookie, uniqueID;

    public abstract void run() throws SplatnetUnauthorizedException,MalformedURLException,IOException;
    public void setup(Splatnet splatnet, String cookie, String uniqueID){
        this.splatnet = splatnet;
        this.cookie = cookie;
        this.uniqueID = uniqueID;
    }
    public Bundle result(Bundle bundle){
        return bundle;
    }
}

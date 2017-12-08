package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.IOException;
import java.net.MalformedURLException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public abstract class SplatnetRequest {
    protected Call call;

    protected abstract void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException;
    public abstract void setup(Splatnet splatnet, String cookie, String uniqueID);

    public void run() throws SplatnetUnauthorizedException,MalformedURLException,IOException{
        if(shouldUpdate()){
            Response response = call.execute();
            if (response.isSuccessful()) {
                manageResponse(response);
            } else {
                throw new SplatnetUnauthorizedException("User Authentication Failed");
            }
        }else{
            handleNoUpdate();
        }
    }

    public Bundle result(Bundle bundle){
        return bundle;
    }

    public boolean shouldUpdate(){
        return true;
    }
    public void handleNoUpdate(){}
}

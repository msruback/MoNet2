package com.mattrubacky.monet2.backend.api.splatnet;

import android.os.Bundle;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public abstract class SplatnetRequest {
    protected Call call;

    //SplatnetRequests that store data should retrieve that data in the constructor
    //In case of failure to reach Splatnet, this will ensure valid data is not overwritten

    //Manage response should handle any transformation and storing of the data once recieved
    protected abstract void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException,SplatnetMaintenanceException;

    //Setup should define the retrofit request "call" will be
    public abstract void setup(Splatnet splatnet, String cookie, String uniqueID);

    public void run() throws SplatnetUnauthorizedException,IOException,SplatnetMaintenanceException{
        if(shouldUpdate()){
            Response response = call.execute();
            if (response.isSuccessful()) {
                manageResponse(response);
            } else if(response.code()==200) {
                throw new SplatnetMaintenanceException("Splatnet is down for Maintenance");
            }else{
                throw new SplatnetUnauthorizedException("User Authentication Failed");
            }
        }
    }

    //If data is expected to be returned as a result, this method should be overridden and the data should be inserted into the bundle
    public Bundle result(Bundle bundle){
        return bundle;
    }

    //If one can determine if data is out of date, this method should be overridden by a method that determines if the currently stored data is out of date
    public boolean shouldUpdate(){
        return true;
    }
}

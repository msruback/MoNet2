package com.mattrubacky.monet2.backend.api.splatnet;

import android.os.Bundle;

import com.mattrubacky.monet2.data.deserialized.splatoon.Stage;

import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class StagesRequest extends SplatnetRequest{

    private ArrayList<Stage> stages;

    public StagesRequest(){}

    @Override
    protected void manageResponse(Response response) {
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getStages(cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelableArrayList("stages",stages);
        return bundle;
    }
}

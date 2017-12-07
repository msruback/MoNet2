package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.os.Bundle;

import com.mattrubacky.monet2.deserialized.Stage;
import com.mattrubacky.monet2.deserialized.Timeline;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class StagesRequest extends SplatnetRequest{

    private ArrayList<Stage> stages;

    public StagesRequest(){}

    @Override
    public void run() throws SplatnetUnauthorizedException, MalformedURLException, IOException {
        Call<ArrayList<Stage>> getStages = splatnet.getStages(cookie,uniqueID);
        Response response = getStages.execute();
        if(response.isSuccessful()){
            stages = (ArrayList<Stage>) response.body();
        }
    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelableArrayList("stages",stages);
        return bundle;
    }
}

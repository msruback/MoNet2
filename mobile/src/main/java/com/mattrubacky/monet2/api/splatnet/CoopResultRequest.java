package com.mattrubacky.monet2.api.splatnet;

import android.os.Bundle;

import com.mattrubacky.monet2.deserialized.splatoon.CoopResult;


import retrofit2.Response;

/**
 * Created by mattr on 10/24/2018.
 */

public class CoopResultRequest extends SplatnetRequest {
    private CoopResult job;
    private String id, referer;

    public CoopResultRequest(int id){
        this.id = String.valueOf(id);
        this.referer = "https://app.splatoon2.nintendo.net/coop_results/"+this.id;
    }

    @Override
    protected void manageResponse(Response response) {
        job = (CoopResult) response.body();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getSalmonResult(id,referer, cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelable("job",job);
        return bundle;
    }
}

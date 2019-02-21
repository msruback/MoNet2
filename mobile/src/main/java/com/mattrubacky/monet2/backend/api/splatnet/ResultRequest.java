package com.mattrubacky.monet2.backend.api.splatnet;

import android.os.Bundle;

import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;


import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class ResultRequest extends SplatnetRequest {

    private Battle battle;
    private String id, referer;

    public ResultRequest(int id){
        this.id = String.valueOf(id);
        this.referer = "https://app.splatoon2.nintendo.net/results/"+this.id;
    }

    @Override
    protected void manageResponse(Response response) {
        battle = (Battle) response.body();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getBattle(id,referer, cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelable("battle",battle);
        return bundle;
    }
}

package com.mattrubacky.monet2.splatnet;

import android.os.Bundle;

import com.mattrubacky.monet2.deserialized.Battle;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class ResultRequest extends SplatnetRequest {
    private int id;
    private Battle battle;

    public ResultRequest(int id){
        this.id = id;
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException {
        Battle battle = (Battle) response.body();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getBattle(String.valueOf(id), cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelable("battle",battle);
        return bundle;
    }
}

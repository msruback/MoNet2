package com.mattrubacky.monet2.api.splatnet;

import android.os.Bundle;

import com.mattrubacky.monet2.deserialized.splatoon.SplatfestVotes;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class SplatfestVoteRequest extends SplatnetRequest {

    private SplatfestVotes votes;
    private String id,referer;

    public SplatfestVoteRequest(int id){
        this.id = String.valueOf(id);
        this.referer = "https://app.splatoon2.nintendo.net/records/festival/"+this.id;
    }

    @Override
    protected void manageResponse(Response response){
        votes = (SplatfestVotes) response.body();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getSplatfestVotes(id,referer,cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelable("votes",votes);
        return bundle;
    }
}

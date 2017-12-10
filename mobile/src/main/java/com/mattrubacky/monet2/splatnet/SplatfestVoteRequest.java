package com.mattrubacky.monet2.splatnet;

import android.os.Bundle;

import com.mattrubacky.monet2.deserialized.SplatfestVote;
import com.mattrubacky.monet2.deserialized.SplatfestVotes;

import java.io.IOException;
import java.net.MalformedURLException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class SplatfestVoteRequest extends SplatnetRequest {

    private int id;
    private SplatfestVotes votes;

    public SplatfestVoteRequest(int id){
        this.id = id;
    }

    @Override
    protected void manageResponse(Response response){
        votes = (SplatfestVotes) response.body();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getSplatfestVotes(String.valueOf(id),cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelable("votes",votes);
        return bundle;
    }
}

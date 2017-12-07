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
    public void run() throws SplatnetUnauthorizedException, MalformedURLException, IOException {
        Call<SplatfestVotes> getVotes = splatnet.getSplatfestVotes(String.valueOf(id),cookie,uniqueID);
        Response response = getVotes.execute();
        if(response.isSuccessful()) {
            votes = (SplatfestVotes) response.body();
        }
    }

    @Override
    public Bundle result(Bundle bundle) {
        return bundle;
    }
}

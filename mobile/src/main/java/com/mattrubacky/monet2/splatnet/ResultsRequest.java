package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.os.Bundle;

import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.ResultList;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class ResultsRequest extends SplatnetRequest {
    ResultRequest resultRequest;
    ArrayList<Battle> list;
    Splatnet splatnet;
    String cookie,uniqueID;
    Context context;

    public ResultsRequest(Context context){
        this.context = context;
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException {
        ResultList results = (ResultList) response.body();

        SplatnetSQLManager database = new SplatnetSQLManager(context);

        list = new ArrayList<>();

        for (int i = 0; i < results.resultIds.size(); i++) {
            resultRequest = new ResultRequest(results.resultIds.get(i).id);
            resultRequest.run();
            list.add((Battle) resultRequest.result(new Bundle()).getParcelable("battle"));
        }
        database.insertBattles(list);
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.get50Results(cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelableArrayList("battles",list);
        return bundle;
    }
}

package com.mattrubacky.monet2.splatnet;

import android.os.Bundle;

import com.mattrubacky.monet2.deserialized.Record;

import java.io.IOException;
import java.net.MalformedURLException;

import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class RecordsRequest extends SplatnetRequest {

    private Record records;

    @Override
    protected void manageResponse(Response response){

        records = (Record) response.body();
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getRecords(cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelable("records",records);
        return bundle;
    }
}

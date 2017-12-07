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
    public void run() throws SplatnetUnauthorizedException, MalformedURLException, IOException {
        Response response = splatnet.getRecords(cookie,uniqueID).execute();
        if(response.isSuccessful()) {
            records = (Record) response.body();
        }
    }

    @Override
    public Bundle result(Bundle bundle) {
        bundle.putParcelable("records",records);
        return bundle;
    }
}

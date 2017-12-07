package com.mattrubacky.monet2.splatnet;

import android.os.Bundle;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by mattr on 12/6/2017.
 */

public class RecordsRequest extends SplatnetRequest {
    @Override
    public void run() throws SplatnetUnauthorizedException, MalformedURLException, IOException {

    }

    @Override
    public Bundle result(Bundle bundle) {
        return bundle;
    }
}

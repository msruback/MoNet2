package com.mattrubacky.monet2.splatnet;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class OrderRequest extends SplatnetRequest {

    private String id,referer;

    public OrderRequest(String id){
        this.id = id;
        this.referer = "https://app.splatoon2.nintendo.net/shop/"+id;
    }

    @Override
    protected void manageResponse(Response response) throws IOException, SplatnetUnauthorizedException {

    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {

        RequestBody override = RequestBody.create(MediaType.parse("text/plain"), "1");
        call = splatnet.orderMerch(id,override,referer,cookie,uniqueID);
    }
}

package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.Annie;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class ShopRequest extends SplatnetRequest {

    private Context context;
    private Annie shop;

    public ShopRequest(Context context){
        this.context = context;
    }

    @Override
    protected void manageResponse(Response response){
        shop = (Annie) response.body();
        SplatnetSQLManager database = new SplatnetSQLManager(context);
        ArrayList<Gear> gear = new ArrayList<>();
        for (int i = 0; i < shop.merch.size(); i++) {
            gear.add(shop.merch.get(i).gear);
        }
        database.insertGear(gear);
    }

    @Override
    public void setup(Splatnet splatnet, String cookie, String uniqueID) {
        call = splatnet.getShop(cookie,uniqueID);
    }

    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelable("shop",shop);
        return bundle;
    }
}

package com.mattrubacky.monet2.splatnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mattrubacky.monet2.deserialized.Annie;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by mattr on 12/6/2017.
 */

public class ShopRequest extends SplatnetRequest {

    private Context context;
    private Annie shop;
    private boolean override;

    public ShopRequest(Context context){
        this.context = context;
        override = false;
    }

    public ShopRequest(Context context,boolean override){
        this.context = context;
        this.override = override;
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
    public boolean shouldUpdate(){

        if(!override) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            Gson gson = new Gson();
            shop = gson.fromJson(settings.getString("shopState", ""), Annie.class);
            long now = new Date().getTime();
            if (shop.merch != null && shop.merch.size() > 0) {
                if ((shop.merch.get(0).endTime * 1000) < now) {
                    return true;
                }
                return false;
            }
        }

        return true;
    }

    @Override
    public Bundle result(Bundle bundle){
        bundle.putParcelable("shop",shop);
        return bundle;
    }
}

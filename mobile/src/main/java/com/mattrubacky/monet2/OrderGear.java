package com.mattrubacky.monet2;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.Annie;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.Ordered;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.Product;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 10/5/2017.
 */

public class OrderGear extends Service {

    Product buying;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        super.onStartCommand(intent,flags,startId);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        buying = intent.getExtras().getParcelable("product");
        Thread thread = new Thread(orderGear);
        thread.start();

        return START_NOT_STICKY;
    }
    private Runnable orderGear = new Runnable() {
        public void run() {
            try{
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String cookie = settings.getString("cookie","");
                String id = settings.getString("unique_id","");
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                RequestBody override = RequestBody.create(MediaType.parse("text/plain"), "1");
                Call<ResponseBody> buy = splatnet.orderMerch(buying.id,id,override,cookie);
                okhttp3.Request request = buy.request();
                Response response = buy.execute();
                Gson gson = new Gson();
                Annie shop = gson.fromJson(settings.getString("shopState",""),Annie.class);
                shop.ordered = new Ordered();
                shop.ordered.gear = buying.gear;
                shop.ordered.price = buying.price;
                shop.ordered.skill = buying.skill;

                SharedPreferences.Editor edit = settings.edit();

                edit.putString("shopState",gson.toJson(shop));
                edit.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}

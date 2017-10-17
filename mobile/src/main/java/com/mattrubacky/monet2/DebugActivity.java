package com.mattrubacky.monet2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.PastSplatfest;
import com.mattrubacky.monet2.com.mattrubacky.deserialized.ResultList;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_player_child);
        new UpdateDataV3().execute();
    }
    class UpdateDataV3 extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {}
        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String cookie = settings.getString("cookie","");

            try {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                Response response;
                ArrayList<Battle> list = new ArrayList<>();
                SplatnetSQL database = new SplatnetSQL(getApplicationContext());
                response = splatnet.get50Results(cookie).execute();
                if(response.isSuccessful()) {
                    ResultList results = (ResultList) response.body();
                    for (int i = 0; i < results.resultIds.size(); i++) {
                        response = splatnet.getBattle(String.valueOf(results.resultIds.get(i).id), cookie).execute();
                        Battle battle = (Battle) response.body();
                        list.add(battle);
                        if (battle.type.equals("fes")) {
                            database.deleteBattle(battle.id);
                        }
                        if (!database.existsIn(SplatnetContract.Battle.TABLE_NAME, SplatnetContract.Battle._ID, results.resultIds.get(i).id)) {
                            database.insertBattle(battle);
                        }
                    }
                }
                Call<PastSplatfest> getSplatfests = splatnet.getPastSplatfests(cookie);
                response = getSplatfests.execute();
                if(response.isSuccessful()){
                    PastSplatfest pastSplatfest = (PastSplatfest) response.body();
                    for(int i=0;i<pastSplatfest.splatfests.size();i++){
                        database.updateSplatfest(pastSplatfest.splatfests.get(i));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

    }

}

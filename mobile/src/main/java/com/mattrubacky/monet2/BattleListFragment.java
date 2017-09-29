package com.mattrubacky.monet2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 9/27/2017.
 */

public class BattleListFragment extends Fragment {
    ViewGroup rootView;
    SplatnetSQL database;
    android.os.Handler customHandler;
    ArrayList<Battle> battles;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_battle_list, container, false);

        customHandler = new android.os.Handler();
        database = new SplatnetSQL(getContext());


        customHandler.post(update2Hours);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(battles);
        edit.putString("recentBattles",json);
        edit.commit();
        customHandler.removeCallbacks(updateUI);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        battles = gson.fromJson(settings.getString("recentBattles",""),new TypeToken<ArrayList<Battle>>(){}.getType());
        if(battles!=null){
            customHandler.post(updateUI);
        }
    }

    private class BattleAdapter extends ArrayAdapter<Battle> {
        public BattleAdapter(Context context, ArrayList<Battle> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_battle, parent, false);
            }
            Battle battle = getItem(position);

            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

            RelativeLayout item = (RelativeLayout) convertView.findViewById(R.id.item);
            item.setClipToOutline(true);

            TextView mode = (TextView) convertView.findViewById(R.id.mode);
            TextView map = (TextView) convertView.findViewById(R.id.map);
            TextView result = (TextView) convertView.findViewById(R.id.result);

            ImageView weapon = (ImageView) convertView.findViewById(R.id.weapon);
            ImageView type = (ImageView) convertView.findViewById(R.id.type);

            map.setText(battle.stage.name);
            map.setTypeface(font);

            result.setText(battle.result.name);
            result.setTypeface(font);

            String modeString = "";
            switch(battle.rule.name){
                case "Turf War":
                    modeString = "TW";
                    break;
                case "Rainmaker":
                    modeString = "R";
                    break;
                case "Splat Zones":
                    modeString = "SZ";
                    break;
                case "Tower Control":
                    modeString = "TC";
                    break;
            }
            mode.setText(modeString);
            mode.setTypeface(font);

            switch(battle.type){
                case "regular":
                    type.setImageDrawable(getResources().getDrawable(R.drawable.battle_regular));
                    item.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.turf));
                    break;
                case "gachi":
                    type.setImageDrawable(getResources().getDrawable(R.drawable.battle_ranked));
                    item.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.ranked));
                    break;
                case "league":
                    type.setImageDrawable(getResources().getDrawable(R.drawable.battle_league));
                    item.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.league));
                    break;
                case "fes":
                    break;
            }
            String url = "https://app.splatoon2.nintendo.net"+battle.user.user.weapon.url;

            ImageHandler imageHandler = new ImageHandler();
            String imageDirName = battle.user.user.weapon.name.toLowerCase().replace(" ","_");

            if(imageHandler.imageExists("weapon",imageDirName,getContext())){
                weapon.setImageBitmap(imageHandler.loadImage("weapon",imageDirName));
            }else{
                Picasso.with(getContext()).load(url).into(weapon);
                imageHandler.downloadImage("weapon",imageDirName,url,getContext());
            }

            return convertView;
        }
    }

    private Runnable updateUI = new Runnable() {
        public void run() {

            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

            BattleAdapter battleAdapter = new BattleAdapter(getContext(),battles);
            ListView listView = (ListView) rootView.findViewById(R.id.battleList);
            listView.setAdapter(battleAdapter);

            TextView count = (TextView) rootView.findViewById(R.id.count);
            count.setTypeface(font);
            count.setText(String.valueOf(database.battleCount()));
        }
    };
    private Runnable updateBattleInfo = new Runnable() {
        public void run() {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
            String cookie = settings.getString("cookie","");

            try {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                Response response;
                ArrayList<Battle> list = new ArrayList<>();

                response = splatnet.get50Results(cookie).execute();
                ResultList results = (ResultList) response.body();
                for(int i = 0;i<results.resultIds.size();i++) {
                    response = splatnet.getBattle(String.valueOf(results.resultIds.get(i).id),cookie).execute();
                    Battle battle = (Battle) response.body();
                    list.add(battle);
                    if (!database.existsIn(SplatnetContract.Battle.TABLE_NAME, SplatnetContract.Battle._ID, results.resultIds.get(i).id)) {
                        database.insertBattle(battle);
                    }
                }
                battles = list;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable update2Hours = new Runnable()
    {
        public void run() {
            Thread t = new Thread(updateBattleInfo);
            customHandler.postDelayed(updateUI,20000);
            t.start();
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar nextUpdate = Calendar.getInstance();
            nextUpdate.setTimeInMillis(now.getTimeInMillis());
            int hour = now.get(Calendar.HOUR);
            hour+=1;
            int zero = 0;
            nextUpdate.set(Calendar.HOUR,hour);
            nextUpdate.set(Calendar.MINUTE,0);
            nextUpdate.set(Calendar.SECOND,0);
            nextUpdate.set(Calendar.MILLISECOND,0);
            Long nextUpdateTime = nextUpdate.getTimeInMillis()-now.getTimeInMillis();
            customHandler.postDelayed(this, nextUpdateTime);
        }
    };

}

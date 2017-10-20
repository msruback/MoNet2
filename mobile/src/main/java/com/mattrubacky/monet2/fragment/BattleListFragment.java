package com.mattrubacky.monet2.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.mattrubacky.monet2.BattleInfo;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.Splatnet;
import com.mattrubacky.monet2.sqlite.SplatnetContract;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.dialog.*;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 9/27/2017.
 */

public class BattleListFragment extends Fragment {
    ViewGroup rootView;
    SplatnetSQLManager database;
    android.os.Handler customHandler;
    ArrayList<Battle> battles;
    UpdateBattleData updateBattleData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_battle_list, container, false);

        customHandler = new android.os.Handler();
        database = new SplatnetSQLManager(getContext());
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        updateBattleData = new UpdateBattleData();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        battles = gson.fromJson(settings.getString("recentBattles",""),new TypeToken<ArrayList<Battle>>(){}.getType());

        RelativeLayout numberButton = (RelativeLayout) rootView.findViewById(R.id.NumberButton);

        TextView count = (TextView) rootView.findViewById(R.id.count);
        TextView numberButtonText = (TextView) rootView.findViewById(R.id.NumberButtonText);

        count.setTypeface(font);
        numberButtonText.setTypeface(font);

        numberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText battleNumber = (EditText) rootView.findViewById(R.id.BattleNumber);
                String idString = battleNumber.getText().toString();
                int id = Integer.parseInt(idString);
                if(database.existsIn(SplatnetContract.Battle.TABLE_NAME, SplatnetContract.Battle._ID,id)) {
                    new GetBattleData(id).execute();
                }else{
                    Toast.makeText(getContext(),"Invalid Battle Number",Toast.LENGTH_SHORT);
                    battleNumber.setText("");
                }
            }
        });


        updateUi();
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
        updateBattleData.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        battles = gson.fromJson(settings.getString("recentBattles",""),new TypeToken<ArrayList<Battle>>(){}.getType());
        if(battles!=null){
            updateUi();
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

            RelativeLayout fesMode = (RelativeLayout) convertView.findViewById(R.id.FesMode);
            RelativeLayout alpha = (RelativeLayout) convertView.findViewById(R.id.Alpha);
            RelativeLayout bravo = (RelativeLayout) convertView.findViewById(R.id.Bravo);
            RelativeLayout spots = (RelativeLayout) convertView.findViewById(R.id.Spots);

            TextView mode = (TextView) convertView.findViewById(R.id.mode);
            TextView map = (TextView) convertView.findViewById(R.id.map);
            TextView result = (TextView) convertView.findViewById(R.id.result);

            ImageView weapon = (ImageView) convertView.findViewById(R.id.weapon);
            ImageView type = (ImageView) convertView.findViewById(R.id.Type);

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
                    spots.setBackground(getResources().getDrawable(R.drawable.repeat_spots));
                    type.setImageDrawable(getResources().getDrawable(R.drawable.battle_regular));
                    item.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.turf));
                    fesMode.setVisibility(View.GONE);
                    type.setVisibility(View.VISIBLE);
                    break;
                case "gachi":
                    spots.setBackground(getResources().getDrawable(R.drawable.repeat_spots));
                    type.setImageDrawable(getResources().getDrawable(R.drawable.battle_ranked));
                    item.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.ranked));
                    fesMode.setVisibility(View.GONE);
                    type.setVisibility(View.VISIBLE);
                    break;
                case "league":
                    spots.setBackground(getResources().getDrawable(R.drawable.repeat_spots));
                    type.setImageDrawable(getResources().getDrawable(R.drawable.battle_league));
                    item.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.league));
                    fesMode.setVisibility(View.GONE);
                    type.setVisibility(View.VISIBLE);
                    break;
                case "fes":
                    spots.setBackground(getResources().getDrawable(R.drawable.repeat_spots_splatfest));
                    mode.setText("SP");
                    type.setVisibility(View.GONE);
                    fesMode.setVisibility(View.VISIBLE);
                    if(battle.myTheme.key.equals("alpha")){
                        alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                        bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                        spots.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                        item.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                    }else{
                        alpha.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                        bravo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                        spots.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.otherTheme.color.getColor())));
                        item.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(battle.myTheme.color.getColor())));
                    }
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

    private void updateUi(){
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        if (battles==null){
            battles = new ArrayList<>();
        }
        BattleAdapter battleAdapter = new BattleAdapter(getContext(),battles);
        ListView listView = (ListView) rootView.findViewById(R.id.battleList);
        listView.setAdapter(battleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int battleId = battles.get(position).id;
                new GetBattleData(battleId).execute();
            }
        });

        TextView count = (TextView) rootView.findViewById(R.id.count);
        count.setText(String.valueOf(database.battleCount()));
    }

    private class UpdateBattleData extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {}
        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
            String cookie = settings.getString("cookie","");

            try {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                Response response;
                ArrayList<Battle> list = new ArrayList<>();

                response = splatnet.get50Results(cookie).execute();
                if(response.isSuccessful()) {
                    ResultList results = (ResultList) response.body();
                    for (int i = 0; i < results.resultIds.size(); i++) {
                        response = splatnet.getBattle(String.valueOf(results.resultIds.get(i).id), cookie).execute();
                        Battle battle = (Battle) response.body();
                        list.add(battle);
                        if (!database.existsIn(SplatnetContract.Battle.TABLE_NAME, SplatnetContract.Battle._ID, results.resultIds.get(i).id)) {
                            database.insertBattle(battle);
                        }
                    }
                }
                battles = list;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            updateUi();
        }

    }

    private Runnable update2Hours = new Runnable()
    {
        public void run() {
            updateBattleData = new UpdateBattleData();
            updateBattleData.execute();
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar nextUpdate = Calendar.getInstance();
            nextUpdate.setTimeInMillis(now.getTimeInMillis());
            int hour = now.get(Calendar.HOUR);
            hour+=1;
            nextUpdate.set(Calendar.HOUR,hour);
            nextUpdate.set(Calendar.MINUTE,0);
            nextUpdate.set(Calendar.SECOND,0);
            nextUpdate.set(Calendar.MILLISECOND,0);
            Long nextUpdateTime = nextUpdate.getTimeInMillis()-now.getTimeInMillis();
            customHandler.postDelayed(this, nextUpdateTime);
        }
    };

    private class GetBattleData extends AsyncTask<Void,Void,Void> {

        int id;
        Bundle bundle;
        LoadingDialog dialog;

        public GetBattleData(int battleID){
            id = battleID;
        }

        @Override
        protected void onPreExecute() {
            bundle = new Bundle();
            dialog = new LoadingDialog(getActivity(),"Loading Battle "+id);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            Battle battle = database.selectBattle(id);
            bundle.putParcelable("battle",battle);
            if(battle.type.equals("fes")){
                Splatfest splatfest = database.selectSplatfest(battle.splatfestID);
                bundle.putParcelable("splatfest",splatfest);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            Intent intent = new Intent(getContext(),BattleInfo.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

}

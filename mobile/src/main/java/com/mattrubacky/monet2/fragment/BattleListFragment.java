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
import com.mattrubacky.monet2.adapter.BattleListAdapter;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.splatnet_interface.Splatnet;
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
                if(database.hasBattle(id)) {
                    LoadingDialog dialog = new LoadingDialog(getActivity(),"Loading Battle "+id);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    Bundle bundle = new Bundle();
                    Battle battle = database.selectBattle(id);
                    bundle.putParcelable("battle",battle);
                    if(battle.type.equals("fes")){
                        Splatfest splatfest = database.selectSplatfest(battle.splatfestID).splatfest;
                        bundle.putParcelable("splatfest",splatfest);
                    }
                    Intent intent = new Intent(getContext(),BattleInfo.class);
                    intent.putExtras(bundle);
                    dialog.dismiss();
                    startActivity(intent);
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

    private void updateUi(){
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        if (battles==null){
            battles = new ArrayList<>();
        }
        BattleListAdapter battleAdapter = new BattleListAdapter(getContext(),battles);
        ListView listView = (ListView) rootView.findViewById(R.id.battleList);
        listView.setAdapter(battleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int battleId = battles.get(position).id;
                LoadingDialog dialog = new LoadingDialog(getActivity(),"Loading Battle "+battleId);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                Bundle bundle = new Bundle();
                Battle battle = database.selectBattle(battleId);
                bundle.putParcelable("battle",battle);
                if(battle.type.equals("fes")){
                    Splatfest splatfest = database.selectSplatfest(battle.splatfestID).splatfest;
                    bundle.putParcelable("splatfest",splatfest);
                }
                Intent intent = new Intent(getContext(),BattleInfo.class);
                intent.putExtras(bundle);
                dialog.dismiss();
                startActivity(intent);
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

                    }
                    database.insertBattles(list);
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

}

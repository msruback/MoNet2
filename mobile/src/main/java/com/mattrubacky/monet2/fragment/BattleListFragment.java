package com.mattrubacky.monet2.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
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
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.splatnet.ResultsRequest;
import com.mattrubacky.monet2.splatnet.Splatnet;
import com.mattrubacky.monet2.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.splatnet.SplatnetConnector;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.mattrubacky.monet2.deserialized.*;
import com.mattrubacky.monet2.dialog.*;

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

public class BattleListFragment extends Fragment implements SplatnetConnected {
    ViewGroup rootView;
    SplatnetSQLManager database;
    ArrayList<Battle> battles;
    SplatnetConnector splatnetConnector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_battle_list, container, false);

        database = new SplatnetSQLManager(getContext());
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

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

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        splatnetConnector.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        splatnetConnector = new SplatnetConnector(this,getActivity(),getContext());
        splatnetConnector.addRequest(new ResultsRequest(getContext()));
        update(splatnetConnector.getCurrentData());
        splatnetConnector.execute();
    }

    private void updateUI(){
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

    @Override
    public void update(Bundle bundle) {
        battles = bundle.getParcelableArrayList("battles");
        updateUI();
    }
}

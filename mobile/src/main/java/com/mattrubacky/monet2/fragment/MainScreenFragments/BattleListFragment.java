package com.mattrubacky.monet2.fragment.MainScreenFragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mattrubacky.monet2.BattleInfo;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.BattleListPagerAdapter;
import com.mattrubacky.monet2.api.splatnet.ResultsRequest;
import com.mattrubacky.monet2.api.splatnet.SplatnetConnected;
import com.mattrubacky.monet2.api.splatnet.SplatnetConnector;
import com.mattrubacky.monet2.deserialized.splatoon.Battle;
import com.mattrubacky.monet2.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.mattrubacky.monet2.dialog.*;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 9/27/2017.
 */

public class BattleListFragment extends Fragment implements SplatnetConnected {
    private ViewGroup rootView;
    private SplatnetSQLManager database;
    private SplatnetConnector splatnetConnector;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView listView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_battle_list, container, false);

        database = new SplatnetSQLManager(getContext());
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        RelativeLayout numberButton = rootView.findViewById(R.id.NumberButton);

        listView = rootView.findViewById(R.id.battleList);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(listView);

        TextView count = rootView.findViewById(R.id.count);
        TextView numberButtonText = rootView.findViewById(R.id.NumberButtonText);

        count.setTypeface(font);
        numberButtonText.setTypeface(font);

        numberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText battleNumber = rootView.findViewById(R.id.BattleNumber);
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

        ArrayList<Battle> battles = database.getBattles();
        ArrayList<ArrayList<Battle>> battleLists = new ArrayList<>();
        for(int i=battles.size();i>0;i-=25){
            battleLists.add(sort(new ArrayList<>(battles.subList(Math.max(0, i - 25),i))));
        }
        BattleListPagerAdapter battleAdapter = new BattleListPagerAdapter(getContext(),battleLists);
        listView.setAdapter(battleAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        listView.setLayoutManager(linearLayoutManager);

        TextView count = rootView.findViewById(R.id.count);
        count.setText(String.valueOf(database.battleCount()));
    }

    @Override
    public void update(Bundle bundle) {
        updateUI();
    }
    private ArrayList<Battle> sort(ArrayList<Battle> data){
        if(data.size()<=1){
            return data;
        }
        if(data.size()==2){
            if(data.get(0).id>=data.get(1).id){
                return data;
            }else{
                Battle hold = data.get(0);
                data.remove(0);
                data.add(hold);
                return data;
            }
        }else{
            Battle pivot = data.get(0);
            ArrayList<Battle> lower = new ArrayList<>();
            ArrayList<Battle> upper = new ArrayList<>();
            for(int i=1;i<data.size();i++){
                if(pivot.id<data.get(i).id){
                    lower.add(data.get(i));
                }else{
                    upper.add(data.get(i));
                }
            }
            ArrayList<Battle> result = sort(lower);
            result.add(pivot);
            result.addAll(sort(upper));
            return result;
        }
    }
}

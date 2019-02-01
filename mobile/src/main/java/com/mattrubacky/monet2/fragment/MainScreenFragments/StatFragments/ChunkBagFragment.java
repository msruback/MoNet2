package com.mattrubacky.monet2.fragment.MainScreenFragments.StatFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.ChunkableAdapter;
import com.mattrubacky.monet2.deserialized.splatoon.Chunk;
import com.mattrubacky.monet2.deserialized.splatoon.Skill;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/20/2017.
 */

public class ChunkBagFragment extends Fragment {

    private ViewGroup rootView;
    private SharedPreferences settings;
    private ArrayList<Chunk> chunkBag;
    private RecyclerView abilityList;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_chunk_bag, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor edit = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(chunkBag);
        edit.putString("chunkbag",json);
        edit.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        Gson gson = new Gson();
        chunkBag = gson.fromJson(settings.getString("chunkbag",""),new TypeToken<ArrayList<Chunk>>(){}.getType());
        if(chunkBag==null||chunkBag.size()<=0){
            SplatnetSQLManager database = new SplatnetSQLManager(getContext());
            ArrayList<Skill> skills;
            skills = database.getChunkable();
            chunkBag = new ArrayList<>();
            Chunk chunk;
            for(int i=0;i<skills.size();i++){
                chunk = new Chunk();
                chunk.count=0;
                chunk.skill = skills.get(i);
                chunkBag.add(chunk);
            }
        }

        updateUi();

    }

    private void updateUi(){
        ChunkableAdapter chunkableAdapter = new ChunkableAdapter(getContext(), chunkBag);
        abilityList = rootView.findViewById(R.id.AbilityList);
        abilityList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        abilityList.setAdapter(chunkableAdapter);
    }

}

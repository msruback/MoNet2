package com.mattrubacky.monet2.ui.fragment.MainScreenFragments.StatFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.activities.SplatfestDetail;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.SplatfestAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.Record;
import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestDatabase;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestRecords;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestResult;
import com.mattrubacky.monet2.backend.api.splatnet.SplatnetConnected;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/15/2017.
 */

public class SplatfestStatsFragment extends Fragment implements SplatnetConnected{
    private ViewGroup rootView;
    private Record records;
    private ArrayList<SplatfestDatabase> splatfests;
    private RecyclerView splatfestList;

//    private SplatnetConnector splatnetConnector;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_splatfest_stats, container, false);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
//        splatnetConnector.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();

//        splatnetConnector = new SplatnetConnector(this,getActivity(),getContext());
//        splatnetConnector.addRequest(new PastSplatfestRequest(getContext()));
//        splatnetConnector.addRequest(new RecordsRequest(getContext()));
//        update(splatnetConnector.getCurrentData());
//        splatnetConnector.execute();
    }

    private void updateUI(){
        splatfestList = rootView.findViewById(R.id.SplatfestList);
        SplatfestAdapter splatfestAdapter = new SplatfestAdapter(getContext(), splatfests, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = splatfestList.getChildAdapterPosition(v);
                Intent intent = new Intent(getActivity(), SplatfestDetail.class);
                Bundle bundle = new Bundle();
                Splatfest splatfest = splatfests.get(itemPosition).splatfest;
                SplatfestResult result = splatfests.get(itemPosition).result;

                bundle.putParcelable("splatfest", splatfest);
                bundle.putParcelable("result", result);
                SplatfestRecords splatrec = records.records.splatfestRecords.get(splatfest.id);
                if (splatrec != null){
                    bundle.putString("grade", records.records.splatfestRecords.get(splatfest.id).grade.name);
                    bundle.putInt("power", records.records.splatfestRecords.get(splatfest.id).power);
                }
                //Need to get votes
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        splatfestList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        splatfestList.setAdapter(splatfestAdapter);
    }

    @Override
    public void update(Bundle bundle) {
        splatfests =  bundle.getParcelableArrayList("splatfests");
        splatfests = sort(splatfests);
        records = bundle.getParcelable("records");
        updateUI();
    }

    private ArrayList<SplatfestDatabase> sort(ArrayList<SplatfestDatabase> data){
        if(data.size()<=1){
            return data;
        }
        if(data.size()==2){
            if(data.get(0).splatfest.times.end>=data.get(1).splatfest.times.end){
                return data;
            }else{
                SplatfestDatabase hold = data.get(0);
                data.remove(0);
                data.add(hold);
                return data;
            }
        }else {
            SplatfestDatabase pivot = data.get(0);
            ArrayList<SplatfestDatabase> lower = new ArrayList<>();
            ArrayList<SplatfestDatabase> upper = new ArrayList<>();
            for (int i = 1; i < data.size(); i++) {
                if (pivot.splatfest.times.end < data.get(i).splatfest.times.end) {
                    lower.add(data.get(i));
                } else {
                    upper.add(data.get(i));
                }
            }
            ArrayList<SplatfestDatabase> result = sort(lower);
            result.add(pivot);
            result.addAll(sort(upper));
            return result;
        }
    }
}

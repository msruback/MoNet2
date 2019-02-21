package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.adapter.ListView.SalmonRunAdapter;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.ListViewViewHolder;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.SalmonDetailViewHolder;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRun;
import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by mattr on 1/14/2018.
 */

public class SalmonRotationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<SalmonRunDetail> input;
    private ArrayList<SalmonRun> salmonRun;
    private LayoutInflater inflater;
    private Context context;

    public SalmonRotationAdapter(Context context, ArrayList<SalmonRunDetail> input, ArrayList<SalmonRun> salmonRun) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.salmonRun = salmonRun;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new SalmonDetailViewHolder(inflater,parent,context);
            default:
                return new ListViewViewHolder(inflater,parent);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holderab, final int position) {
        if(holderab.getItemViewType()==0){
            SalmonRunDetail detail = input.get(position);
            SalmonDetailViewHolder holder = (SalmonDetailViewHolder) holderab;
            holder.manageHolder(detail);
        }else{
            ListViewViewHolder holder = (ListViewViewHolder) holderab;
            SalmonRunAdapter salmonRunAdapter = new SalmonRunAdapter(context,salmonRun);
            holder.itemList.setAdapter(salmonRunAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return input.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position!=input.size()){
            return 0;
        }
        return 1;
    }


}
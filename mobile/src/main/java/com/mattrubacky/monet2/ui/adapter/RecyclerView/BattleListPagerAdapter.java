package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.ListViewHolder;
import com.mattrubacky.monet2.data.deserialized.splatoon.Battle;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/21/2017.
 */

public class BattleListPagerAdapter extends RecyclerView.Adapter<ListViewHolder>{

    private ArrayList<ArrayList<Battle>> input;
    private LayoutInflater inflater;
    private Context context;

    public BattleListPagerAdapter(Context context, ArrayList<ArrayList<Battle>> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(inflater,parent);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {

        final ArrayList<Battle> battles = input.get(position);

        holder.itemList.setAdapter(new BattleListAdapter(context,battles,holder.itemList));
        holder.itemList.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));

    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}

package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mattrubacky.monet2.R;

/**
 * Created by mattr on 12/24/2017.
 */

public class ListViewViewHolder extends RecyclerView.ViewHolder{

    public ListView itemList;

    public ListViewViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_pager_list_view, parent, false));
        itemList = (ListView) itemView.findViewById(R.id.List);
    }
}
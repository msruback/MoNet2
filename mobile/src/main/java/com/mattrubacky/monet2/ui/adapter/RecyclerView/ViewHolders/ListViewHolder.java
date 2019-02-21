package com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 12/24/2017.
 */

public class ListViewHolder extends RecyclerView.ViewHolder{

    public RecyclerView itemList;

    public ListViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_pager_list, parent, false));
        itemList = itemView.findViewById(R.id.List);
    }
}

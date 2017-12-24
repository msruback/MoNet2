package com.mattrubacky.monet2.adapter.ViewHolders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.R;

/**
 * Created by mattr on 12/24/2017.
 */

public class ListViewHolder extends RecyclerView.ViewHolder{

    public RecyclerView itemList;

    public ListViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_user_detail, parent, false));
        itemList = (RecyclerView) itemView.findViewById(R.id.List);
    }
}

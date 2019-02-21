package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.ChunkableViewHolder;
import com.mattrubacky.monet2.data.deserialized.splatoon.Chunk;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/20/2017.
 */

public class ChunkableAdapter extends RecyclerView.Adapter<ChunkableViewHolder>{

    private ArrayList<Chunk> input;
    private LayoutInflater inflater;
    private Context context;

    public ChunkableAdapter(Context context, ArrayList<Chunk> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @NonNull
    @Override
    public ChunkableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChunkableViewHolder(inflater,parent,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChunkableViewHolder holder, final int position) {
        Chunk chunk = input.get(position);
        holder.manageHolder(chunk);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}

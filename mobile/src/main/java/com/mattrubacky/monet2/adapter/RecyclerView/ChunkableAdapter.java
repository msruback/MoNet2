package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.ChunkableViewHolder;
import com.mattrubacky.monet2.deserialized.splatoon.Chunk;

import java.util.ArrayList;

/**
 * Created by mattr on 11/20/2017.
 */

public class ChunkableAdapter extends RecyclerView.Adapter<ChunkableViewHolder>{

    private ArrayList<Chunk> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener onClick;

    public ChunkableAdapter(Context context, ArrayList<Chunk> input) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
    }
    @Override
    public ChunkableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChunkableViewHolder viewHolder = new ChunkableViewHolder(inflater,parent,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ChunkableViewHolder holder, final int position) {
        Chunk chunk = input.get(position);
        holder.manageHolder(chunk);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView skill;
        TextView name,addButton,count,subButton;


        public ViewHolder(View itemView) {
            super(itemView);

            skill = (ImageView) itemView.findViewById(R.id.SkillImage);
            name = (TextView) itemView.findViewById(R.id.Name);
            count = (TextView) itemView.findViewById(R.id.Count);
            addButton = (TextView) itemView.findViewById(R.id.Add);
            subButton = (TextView) itemView.findViewById(R.id.Sub);

        }

    }

}

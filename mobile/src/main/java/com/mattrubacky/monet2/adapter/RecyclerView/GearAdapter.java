package com.mattrubacky.monet2.adapter.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattrubacky.monet2.ClosetDetail;
import com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders.GearViewHolder;
import com.mattrubacky.monet2.helper.ClosetHanger;
import com.mattrubacky.monet2.helper.ImageHandler;

import java.util.ArrayList;

/**
 * Created by mattr on 11/13/2017.
 */

public class GearAdapter extends RecyclerView.Adapter<GearViewHolder>{

    private ArrayList<ClosetHanger> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    RecyclerView listView;

    public GearAdapter(Context context, ArrayList<ClosetHanger> input,RecyclerView listView) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.listView = listView;
    }
    @Override
    public GearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GearViewHolder viewHolder = new GearViewHolder(inflater,parent,context);
        viewHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = listView.getChildAdapterPosition(v);
                Intent intent = new Intent(context, ClosetDetail.class);
                Bundle bundle = new Bundle();
                ClosetHanger hanger = input.get(itemPosition);

                hanger.calcStats(context);

                bundle.putParcelable("stats",hanger);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GearViewHolder holder, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        ClosetHanger closetHanger = input.get(position);

        holder.manageHolder(closetHanger);

    }

    @Override
    public int getItemCount() {
        return input.size();
    }

}

package com.mattrubacky.monet2.adapter.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.CampaignStageInfo;
import com.mattrubacky.monet2.deserialized.CampaignWeapon;
import com.mattrubacky.monet2.helper.ImageHandler;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mattr on 12/10/2017.
 */

public class CampaignWorldAdapter extends RecyclerView.Adapter<CampaignWorldAdapter.ViewHolder> {

    private ArrayList<ArrayList<CampaignStageInfo>> input;
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;
    private Map<Integer, CampaignWeapon> weaponMap;

    public CampaignWorldAdapter(Activity activity, ArrayList<ArrayList<CampaignStageInfo>> input, Map<Integer, CampaignWeapon> weaponMap ) {
        this.inflater = LayoutInflater.from(activity);
        this.input = input;
        this.activity = activity;
        this.context = activity;
        this.weaponMap = weaponMap;

    }

    @Override
    public CampaignWorldAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_world, parent, false);
        CampaignWorldAdapter.ViewHolder viewHolder = new CampaignWorldAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CampaignWorldAdapter.ViewHolder holder, final int position) {
        final ArrayList<CampaignStageInfo> infos = input.get(position);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");
        Typeface fontTitle=Typeface.createFromAsset(context.getAssets(),"Paintball.otf");
        ImageHandler imageHandler = new ImageHandler();

        holder.world.setTypeface(fontTitle);

        holder.world.setText("World "+(position+1));

        CampaignStageAdapter campaignStageAdapter = new CampaignStageAdapter(activity,infos,weaponMap,holder.list);
        holder.list.setAdapter(campaignStageAdapter);
        holder.list.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return input.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView world;
        RecyclerView list;


        public ViewHolder(View itemView) {
            super(itemView);
            world = (TextView) itemView.findViewById(R.id.World);
            list = (RecyclerView) itemView.findViewById(R.id.List);

        }

    }

}
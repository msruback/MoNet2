package com.mattrubacky.monet2.ui.adapter.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mattrubacky.monet2.ui.activities.ClosetDetail;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.ViewHolders.MerchViewHolder;
import com.mattrubacky.monet2.data.deserialized.splatoon.Product;
import com.mattrubacky.monet2.data.stats.GearStats;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/13/2017.
 */

public class MerchAdapter extends RecyclerView.Adapter<MerchViewHolder>{

    private ArrayList<Product> input = new ArrayList<Product>();
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;
    private View.OnClickListener onClickListener;

    public MerchAdapter(Activity activity, ArrayList<Product> input, View.OnClickListener onClickListener) {
        this.inflater = LayoutInflater.from(activity);
        this.input = input;
        this.context = activity;
        this.activity = activity;
        this.onClickListener = onClickListener;

    }
    @Override
    public MerchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MerchViewHolder viewHolder = new MerchViewHolder(inflater,parent,context);
        viewHolder.itemView.setOnClickListener(onClickListener);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                RecyclerView currentMerch = activity.findViewById(R.id.CurrentMerch);
                int itemPosition = currentMerch.indexOfChild(v);
                Intent intent = new Intent(activity, ClosetDetail.class);
                SplatnetSQLManager database = new SplatnetSQLManager(activity);
                Product gear = input.get(itemPosition);
                GearStats hanger = database.selectCloset(gear.gear.id,gear.gear.kind);

                if(hanger.gear!=null) {

                    hanger.calcStats(context);

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("stats", hanger);
                    intent.putExtras(bundle);

                    activity.startActivity(intent);
                }else{
                    Toast.makeText(context,"No Data Found",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MerchViewHolder holder, final int position) {
        Product product  = input.get(position);
        holder.manageHolder(product);
    }

    @Override
    public int getItemCount() {
        return input.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout item,infoBar,infoPatch;
        ImageView brand,gear,mainAbility,sub2,sub3;
        TextView name,cost,time;


        public ViewHolder(View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.Item);
            infoBar = itemView.findViewById(R.id.InfoBar);
            infoPatch = itemView.findViewById(R.id.infoPatch);
            item.setClipToOutline(true);

            brand = itemView.findViewById(R.id.Brand);
            gear = itemView.findViewById(R.id.Image);
            mainAbility = itemView.findViewById(R.id.MainAbility);
            sub2 = itemView.findViewById(R.id.Sub2);
            sub3 = itemView.findViewById(R.id.Sub3);

            name = itemView.findViewById(R.id.Name);
            cost = itemView.findViewById(R.id.Cost);

            time = itemView.findViewById(R.id.Time);
        }

    }

}

package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.ClosetHanger;
import com.mattrubacky.monet2.fragment.ClosetFragment;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 11/13/2017.
 */

public class GearAdapter extends RecyclerView.Adapter<GearAdapter.ViewHolder>{

    private ArrayList<ClosetHanger> input = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener onClickListener;

    public GearAdapter(Context context, ArrayList<ClosetHanger> input,View.OnClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.input = input;
        this.context = context;
        this.onClickListener = onClickListener;
    }
    @Override
    public GearAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gear, parent, false);
        GearAdapter.ViewHolder viewHolder = new GearAdapter.ViewHolder(view);
        view.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GearAdapter.ViewHolder holder, final int position) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
        ImageHandler imageHandler = new ImageHandler();

        ClosetHanger closetHanger = input.get(position);

        String url = "https://app.splatoon2.nintendo.net"+closetHanger.gear.url;
        String location = closetHanger.gear.name.toLowerCase().replace(" ","_");
        if(imageHandler.imageExists("gear",location,context)){
            holder.gear.setImageBitmap(imageHandler.loadImage("weapon",location));
        }else{
            Picasso.with(context).load(url).into(holder.gear);
            imageHandler.downloadImage("gear",location,url,context);
        }

        holder.name.setText(closetHanger.gear.name);
        holder.name.setTypeface(font);

        switch(closetHanger.gear.kind){
            case "head":
                holder.hook.setBackgroundTintList(context.getResources().getColorStateList(R.color.head));
                holder.card.setBackgroundTintList(context.getResources().getColorStateList(R.color.head));
                break;
            case "clothes":
                holder.hook.setBackgroundTintList(context.getResources().getColorStateList(R.color.clothes));
                holder.card.setBackgroundTintList(context.getResources().getColorStateList(R.color.clothes));
                break;
            case "shoes":
                holder.hook.setBackgroundTintList(context.getResources().getColorStateList(R.color.shoes));
                holder.card.setBackgroundTintList(context.getResources().getColorStateList(R.color.shoes));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return input.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout hook,card;
        ImageView gear;
        TextView name;


        public ViewHolder(View itemView) {
            super(itemView);

            hook = (RelativeLayout) itemView.findViewById(R.id.hook);
            card = (RelativeLayout) itemView.findViewById(R.id.card);
            gear = (ImageView) itemView.findViewById(R.id.GearImage);
            name = (TextView) itemView.findViewById(R.id.Name);
        }

    }

}

package com.mattrubacky.monet2.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mattrubacky.monet2.ClosetDetail;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.WeaponLockerDetail;
import com.mattrubacky.monet2.deserialized.ClosetHanger;
import com.mattrubacky.monet2.deserialized.Record;
import com.mattrubacky.monet2.deserialized.WeaponStats;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.StatCalc;
import com.mattrubacky.monet2.splatnet_interface.Splatnet;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by mattr on 11/6/2017.
 */

public class ClosetFragment extends Fragment {

    ViewGroup rootView;
    SharedPreferences settings;
    ArrayList<ClosetHanger> gearList;
    RecyclerView gearListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_closet, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        SplatnetSQLManager database = new SplatnetSQLManager(getContext());
        gearList = database.getCloset();
        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    private void updateUi(){
        gearListView = (RecyclerView) rootView.findViewById(R.id.GearList);
        GearAdapter gearAdapter = new GearAdapter(getContext(),gearList);
        gearListView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        gearListView.setAdapter(gearAdapter);
    }

    class GearAdapter extends RecyclerView.Adapter<GearAdapter.ViewHolder>{

        private ArrayList<ClosetHanger> input = new ArrayList<>();
        private LayoutInflater inflater;
        private Context context;

        public GearAdapter(Context context, ArrayList<ClosetHanger> input) {
            this.inflater = LayoutInflater.from(context);
            this.input = input;
            this.context = context;

        }
        @Override
        public GearAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_gear, parent, false);
            GearAdapter.ViewHolder viewHolder = new GearAdapter.ViewHolder(view);
            view.setOnClickListener(new GearClickListener());
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final GearAdapter.ViewHolder holder, final int position) {
            Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
            ImageHandler imageHandler = new ImageHandler();

            ClosetHanger closetHanger = gearList.get(position);

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
                    holder.hook.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.head));
                    holder.card.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.head));
                    break;
                case "clothes":
                    holder.hook.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.clothes));
                    holder.card.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.clothes));
                    break;
                case "shoes":
                    holder.hook.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.shoes));
                    holder.card.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.shoes));
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

    class GearClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = gearListView.getChildAdapterPosition(v);
            Intent intent = new Intent(getActivity(), ClosetDetail.class);
            Bundle bundle = new Bundle();
            ClosetHanger hanger = gearList.get(itemPosition);

            StatCalc statCalc = new StatCalc(getContext(),hanger.gear);
            hanger.inkStats = statCalc.getInkStats();
            hanger.killStats = statCalc.getKillStats();
            hanger.deathStats = statCalc.getDeathStats();
            hanger.specialStats = statCalc.getSpecialStats();
            hanger.numGames = statCalc.getNum();

            bundle.putParcelable("stats",hanger);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

}

package com.mattrubacky.monet2.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.WeaponLockerDetail;
import com.mattrubacky.monet2.deserialized.Battle;
import com.mattrubacky.monet2.deserialized.Product;
import com.mattrubacky.monet2.deserialized.Records;
import com.mattrubacky.monet2.deserialized.ResultList;
import com.mattrubacky.monet2.deserialized.WeaponStats;
import com.mattrubacky.monet2.dialog.BuyDialog;
import com.mattrubacky.monet2.dialog.CookieDialog;
import com.mattrubacky.monet2.dialog.GearNotificationPickerDialog;
import com.mattrubacky.monet2.dialog.StageNotificationPickerDialog;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.mattrubacky.monet2.helper.StatCalc;
import com.mattrubacky.monet2.reciever.BootReciever;
import com.mattrubacky.monet2.reciever.DataUpdateAlarm;
import com.mattrubacky.monet2.reciever.SalmonAlarm;
import com.mattrubacky.monet2.splatnet_interface.Splatnet;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 11/1/2017.
 */

public class WeaponLockerFragment extends Fragment {

    ViewGroup rootView;
    SharedPreferences settings;
    ArrayList<WeaponStats> weaponStatsList;
    UpdateBattleData updateBattleData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup)  inflater.inflate(R.layout.fragment_settings, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        updateBattleData = new UpdateBattleData();
        updateBattleData.execute();

        return rootView;

    }

    private void updateUi(){
        RecyclerView weaponList = (RecyclerView) rootView.findViewById(R.id.WeaponList);
    }

    private class UpdateBattleData extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {}
        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
            String cookie = settings.getString("cookie","");

            try {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://app.splatoon2.nintendo.net").addConverterFactory(GsonConverterFactory.create()).build();
                Splatnet splatnet = retrofit.create(Splatnet.class);
                Response response;
                ArrayList<Battle> list = new ArrayList<>();
                response = splatnet.getRecords(cookie).execute();
                if(response.isSuccessful()){
                    Records records = (Records) response.body();
                    Integer[] keys = (Integer[]) records.weaponStats.keySet().toArray();
                    for(int i=0;i<keys.length;i++){
                        weaponStatsList.add(records.weaponStats.get(keys[i]));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            updateUi();
        }

    }

    class WeaponAdapter extends RecyclerView.Adapter<WeaponLockerFragment.WeaponAdapter.ViewHolder>{

        private ArrayList<Product> input = new ArrayList<Product>();
        private LayoutInflater inflater;
        private Context context;

        public WeaponAdapter(Context context, ArrayList<Product> input) {
            this.inflater = LayoutInflater.from(context);
            this.input = input;
            this.context = context;

        }
        @Override
        public WeaponLockerFragment.WeaponAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_merch, parent, false);
            WeaponLockerFragment.WeaponAdapter.ViewHolder viewHolder = new WeaponLockerFragment.WeaponAdapter.ViewHolder(view);
            view.setOnClickListener(new WeaponLockerFragment.WeaponClickListener());
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final WeaponLockerFragment.WeaponAdapter.ViewHolder holder, final int position) {
            Typeface font = Typeface.createFromAsset(context.getAssets(),"Splatfont2.ttf");
            ImageHandler imageHandler = new ImageHandler();

            WeaponStats weaponStats = weaponStatsList.get(position);

            String url = "https://app.splatoon2.nintendo.net"+weaponStats.weapon.url;
            String location = weaponStats.weapon.name.toLowerCase().replace(" ","_");
            if(imageHandler.imageExists("brand",location,context)){
                holder.weapon.setImageBitmap(imageHandler.loadImage("weapon",location));
            }else{
                Picasso.with(context).load(url).into(holder.weapon);
                imageHandler.downloadImage("brand",location,url,context);
            }

            holder.name.setText(weaponStats.weapon.name);
            holder.name.setTypeface(font);

        }

        @Override
        public int getItemCount() {
            return input.size();
        }



        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView weapon;
            TextView name;


            public ViewHolder(View itemView) {
                super(itemView);

                weapon = (ImageView) itemView.findViewById(R.id.Weapon);
                name = (TextView) itemView.findViewById(R.id.Name);
            }

        }

    }

    class WeaponClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            RecyclerView currentMerch = (RecyclerView) rootView.findViewById(R.id.CurrentMerch);
            int itemPosition = currentMerch.indexOfChild(v);
            Intent intent = new Intent(getActivity(), WeaponLockerDetail.class);
            Bundle bundle = new Bundle();
            WeaponStats weaponStats = weaponStatsList.get(itemPosition);

            StatCalc statCalc = new StatCalc(getContext(),weaponStats.weapon);
            weaponStats.inkStats = statCalc.getInkStats();
            weaponStats.killStats = statCalc.getKillStats();
            weaponStats.deathStats = statCalc.getDeathStats();
            weaponStats.specialStats = statCalc.getSpecialStats();

            bundle.putParcelable("stats",weaponStats);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
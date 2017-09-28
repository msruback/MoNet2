package com.mattrubacky.monet2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mattr on 9/27/2017.
 */

public class BattleListFragment extends Fragment {
        ViewGroup rootView;
        android.os.Handler customHandler;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_battle_list, container, false);

            SplatnetSQL database = new SplatnetSQL(getContext());
            TextView textView = (TextView) rootView.findViewById(R.id.count);

            textView.setText(String.valueOf(database.battleCount()));

            return rootView;
        }

        @Override
        public void onPause() {
            super.onPause();

        }

        @Override
        public void onResume() {
            super.onResume();

        }

    private class BattleAdapter extends ArrayAdapter<Integer> {
        public BattleAdapter(Context context, ArrayList<Integer> input) {
            super(context, 0, input);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_nav, parent, false);
            }
            Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");


            TextView title = (TextView) convertView.findViewById(R.id.Title);
            title.setText(getItem(position));
            title.setTypeface(font);
            return convertView;
        }
    }

}

package com.mattrubacky.monet2;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LeagueRotation extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_league_rotation, container, false);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        TextView time = (TextView) rootView.findViewById(R.id.leagueTime);
        TextView mode = (TextView) rootView.findViewById(R.id.leagueMode);
        TextView title1 = (TextView) rootView.findViewById(R.id.leagueStageName1);
        TextView title2 = (TextView) rootView.findViewById(R.id.leagueStageName2);

        time.setTypeface(font);
        mode.setTypeface(font);
        title1.setTypeface(font);
        title2.setTypeface(font);

        return rootView;
    }
}
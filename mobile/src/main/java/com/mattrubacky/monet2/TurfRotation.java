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

import org.json.JSONException;
import org.json.JSONObject;


public class TurfRotation extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_turf_rotation, container, false);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Splatfont2.ttf");

        TextView time = (TextView) rootView.findViewById(R.id.turfTime);
        TextView title1 = (TextView) rootView.findViewById(R.id.turfStageName1);
        TextView title2 = (TextView) rootView.findViewById(R.id.turfStageName2);

        time.setTypeface(font);
        title1.setTypeface(font);
        title2.setTypeface(font);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        TextView time = (TextView) getActivity().findViewById(R.id.turfTime);
        TextView title1 = (TextView) getActivity().findViewById(R.id.turfStageName1);
        TextView title2 = (TextView) getActivity().findViewById(R.id.turfStageName2);
        Bundle bundle = this.getArguments();
        try {
            JSONObject json = new JSONObject(bundle.getString("json"));

            title1.setText(json.getJSONObject("stage_a").getString("name"));

            title2.setText(json.getJSONObject("stage_b").getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}


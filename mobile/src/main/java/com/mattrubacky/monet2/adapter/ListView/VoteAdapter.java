package com.mattrubacky.monet2.adapter.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.deserialized.NicknameIcon;
import com.mattrubacky.monet2.deserialized.SplatfestVote;
import com.mattrubacky.monet2.deserialized.SplatfestVotes;
import com.mattrubacky.monet2.helper.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mattr on 11/16/2017.
 */

public class VoteAdapter extends ArrayAdapter<NicknameIcon> {
    public VoteAdapter(Context context, ArrayList<NicknameIcon> input) {
        super(context, 0, input);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_vote, parent, false);
        }
        NicknameIcon player = getItem(position);

        RelativeLayout imageBackground = (RelativeLayout) convertView.findViewById(R.id.imageCircle);
        imageBackground.setClipToOutline(true);

        ImageView image = (ImageView) convertView.findViewById(R.id.UserImage);
        TextView playerName = (TextView) convertView.findViewById(R.id.Name);

        playerName.setText(player.nickname);

        Picasso.with(getContext()).load(player.url).into(image);

        return convertView;
    }
}
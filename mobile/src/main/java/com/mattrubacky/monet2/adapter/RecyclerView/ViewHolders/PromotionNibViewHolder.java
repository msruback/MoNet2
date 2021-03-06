package com.mattrubacky.monet2.adapter.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mattr on 11/11/2018.
 */

public class PromotionNibViewHolder extends RecyclerView.ViewHolder{
    private Context context;
    TextView title;

    public PromotionNibViewHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.nib_salmon_promotion, parent, false));

        title = itemView.findViewById(R.id.Title);
        this.context = context;
    }

    public void manageHolder(String grade) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Splatfont2.ttf");

        title.setTypeface(font);
        title.setText("Promoted to "+grade+"!");
    }
}

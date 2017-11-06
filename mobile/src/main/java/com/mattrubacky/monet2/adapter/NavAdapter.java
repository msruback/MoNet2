package com.mattrubacky.monet2.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.mattrubacky.monet2.R;

import java.util.ArrayList;

/**
 * Created by mattr on 10/30/2017.
 */

public class NavAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<String> groups,children;
    public NavAdapter(Context context, ArrayList<String> groups,ArrayList<String> children) {
        this.context = context;
        this.groups = groups;
        this.children = children;
    }
    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(groupPosition!=2){
            return 0;
        }else{
            return children.size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if(groupPosition==2){
            return children.get(childPosition);
        }else{
            return null;
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nav, parent, false);
        }
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Paintball.otf");


        TextView title = (TextView) convertView.findViewById(R.id.Title);
        title.setText((String) getGroup(groupPosition));
        title.setTypeface(font);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_nav_child, parent, false);
        }
        Typeface font = Typeface.createFromAsset(context.getAssets(),"Paintball.otf");

        TextView title = (TextView) convertView.findViewById(R.id.Title);
        title.setText((String) getChild(groupPosition,childPosition));
        title.setTypeface(font);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

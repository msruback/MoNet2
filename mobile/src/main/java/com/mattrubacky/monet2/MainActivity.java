package com.mattrubacky.monet2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ArrayList<String> titles;
    Fragment rotation,shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        //Add titles
        titles = new ArrayList<String>();
        titles.add("Rotation");
        titles.add("Shop");
        //Add fragments
        rotation = new RotationFragment();



        Typeface font = Typeface.createFromAsset(getAssets(), "Splatfont2.ttf");
        Typeface fontTitle = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(fontTitle);
        setSupportActionBar(toolbar);
        title.setText(toolbar.getTitle());

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerList.setAdapter(new NavAdapter(getApplicationContext(),titles));


        drawerLayout.setScrimColor(ContextCompat.getColor(getApplicationContext(), R.color.transparentgrey2));

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, rotation)
                .commit();


    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position){
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, rotation)
                        .commit();
        }

        // Insert the fragment by replacing any existing fragment

        // Highlight the selected item, update the title, and close the drawer
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);
    }
    private class NavAdapter extends ArrayAdapter<String> {
        public NavAdapter(Context context, ArrayList<String> input) {
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

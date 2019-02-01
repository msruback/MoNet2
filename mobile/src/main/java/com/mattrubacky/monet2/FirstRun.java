package com.mattrubacky.monet2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.mattrubacky.monet2.fragment.WelcomeFragments.AutoUpdateFragment;
import com.mattrubacky.monet2.fragment.WelcomeFragments.LoginFragment;
import com.mattrubacky.monet2.fragment.WelcomeFragments.WelcomeFragment;
import com.mattrubacky.monet2.helper.GenericCallback;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Created by mattr on 11/11/2018.
 */

public class FirstRun extends AppCompatActivity implements GenericCallback {
    ArrayList<Fragment> fragments;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);

        fragments = new ArrayList<>();


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int lastVerison = settings.getInt("lastVerison",0);

        switch (lastVerison){
            case 0:
                WelcomeFragment welcomeFragment = new WelcomeFragment();
                welcomeFragment.setGenericCallback(this);

                AutoUpdateFragment autoUpdateFragment = new AutoUpdateFragment();
                autoUpdateFragment.setGenericCallback(this);

                LoginFragment loginFragment = new LoginFragment();
                loginFragment.setGenericCallback(this);

                fragments.add(welcomeFragment);
                fragments.add(autoUpdateFragment);
                fragments.add(loginFragment);
        }

        fragmentManager = getSupportFragmentManager();
        callback();
    }

    @Override
    public void callback() {
        if(fragments.size()==0){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = settings.edit();
            try {
                edit.putInt("lastVerison", getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            edit.commit();
            startActivity(intent);
        }else {
            Fragment fragment = fragments.remove(0);
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
        }
    }
}

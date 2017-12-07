package com.mattrubacky.monet2.splatnet;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.dialog.AlertDialog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by mattr on 12/6/2017.
 */

public class SplatnetConnector extends AsyncTask<Void,Void,Void> {
    private SplatnetConnected caller;
    private Activity activity;
    private ArrayList<SplatnetRequest> requests;
    private ImageView loading;
    private boolean isUnconn,isUnauth,isReciever;
    private Context context;

    public SplatnetConnector(SplatnetConnected caller, Activity activity,Context context){
        this.caller = caller;
        this.activity = activity;
        requests = new ArrayList<>();
        isReciever = false;
    }
    public SplatnetConnector(SplatnetConnected caller, Context context){
        this.caller = caller;
        requests = new ArrayList<>();
        isReciever = true;
    }

    public void addRequest(SplatnetRequest request){
        requests.add(request);
    }

    @Override
    protected void onPreExecute() {
        if(!isReciever) {
            loading = (ImageView) activity.findViewById(R.id.loading_indicator);

            RotateAnimation animation = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setInterpolator(new LinearInterpolator());
            animation.setRepeatCount(Animation.INFINITE);
            animation.setDuration(1000);

            loading.startAnimation(animation);
            loading.setVisibility(View.VISIBLE);
        }
        isUnconn = false;
        isUnauth = false;
    }
    @Override
    protected Void doInBackground(Void... params) {
        try {
            for(int i=0;i<requests.size();i++){
                requests.get(i).run();
            }
        }catch(SplatnetUnauthorizedException e){
            e.printStackTrace();
            isUnauth = true;
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            isUnconn = true;
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(!isReciever) {
            if (isUnconn) {
                AlertDialog alertDialog = new AlertDialog(activity, "Error: Could not reach Splatnet");
                alertDialog.show();
            } else if (isUnauth) {
                AlertDialog alertDialog = new AlertDialog(activity, "Error: Cookie is invalid, please obtain a new cookie");
                alertDialog.show();
            }else{
                caller.update();
            }
            loading.setAnimation(null);
            loading.setVisibility(View.GONE);
        }else{
            if(!isUnconn&&!isUnauth){
                caller.update();
            }
        }
    }
}

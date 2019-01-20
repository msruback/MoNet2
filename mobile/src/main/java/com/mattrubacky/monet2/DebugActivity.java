package com.mattrubacky.monet2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mattrubacky.monet2.deserialized.splatoon.Gear;
import com.mattrubacky.monet2.deserialized.splatoon.RewardGear;
import com.mattrubacky.monet2.sqlite.SplatnetContract;
import com.mattrubacky.monet2.sqlite.SplatnetSQLManager;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_player);

        Typeface titleFont = Typeface.createFromAsset(getAssets(), "Paintball.otf");

        SplatnetSQLManager database = new SplatnetSQLManager(this);

        database.updateSkills();

        Intent intent = new Intent(DebugActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private class Update extends AsyncTask<Void, Void, Void>

    {
        private Context context;

        public Update(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent intent = new Intent(DebugActivity.this,MainActivity.class);
            startActivity(intent);
        }

    }

}

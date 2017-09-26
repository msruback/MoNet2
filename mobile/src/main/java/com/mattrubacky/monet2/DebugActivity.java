package com.mattrubacky.monet2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_buy_item_choose);
        SplatnetSQL database = new SplatnetSQL(getApplicationContext());
        if(database.existsIn(SplatnetContract.Battle.TABLE_NAME, SplatnetContract.Battle._ID,843)){
            System.out.println("It worked");
        }else{
            System.out.println("Fuck");
        }
    }
}

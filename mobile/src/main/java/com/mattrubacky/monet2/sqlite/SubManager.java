package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.Player;
import com.mattrubacky.monet2.deserialized.Sub;
import com.mattrubacky.monet2.sqlite.SplatnetContract;
import com.mattrubacky.monet2.sqlite.SplatnetSQLHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/18/2017.
 */

class SubManager {
    Context context;
    HashMap<Integer,Sub> toInsert;
    ArrayList<Integer> toSelect;

    public SubManager(Context context){
        this.context = context;
        toInsert = new HashMap<>(); //using a hashmap to prevent duplicate entries
        toSelect = new ArrayList<>();
    }

    //Add a sub to be inserted
    public void addToInsert(Sub sub){
        toInsert.put(sub.id, sub);
    }

    //Add a sub to be selected
    public void addToSelect(int id){
        if(!toSelect.contains(id)){
            toSelect.add(id);
        }
    }

    //Method to execute the insert
    public void insert(){
        if(toInsert.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
            ContentValues values;

            Integer[] keys = (Integer[]) toInsert.keySet().toArray();

            String whereClause = SplatnetContract.Sub._ID +" = ?";
            String[] args;
            Cursor cursor = null;

            Sub sub;
            for (int i = 0; i < keys.length; i++) {
                sub = toInsert.get(keys[i]);

                args = new String[] {String.valueOf(sub.id)};
                cursor = database.query(SplatnetContract.Sub.TABLE_NAME,null,whereClause,args,null,null,null);
                if(cursor.getCount()==0) {
                    values = new ContentValues();

                    values.put(SplatnetContract.Sub._ID, sub.id);
                    values.put(SplatnetContract.Sub.COLUMN_NAME, sub.name);
                    values.put(SplatnetContract.Sub.COLUMN_URL, sub.url);

                    database.insert(SplatnetContract.Sub.TABLE_NAME, null, values);
                }
            }
            if(cursor!=null){
                cursor.close();
            }
            database.close();
            toInsert = new HashMap<>();
        }
    }

    //Method to execute the select
    public HashMap<Integer,Sub> select(){
        HashMap<Integer,Sub> selected = new HashMap<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String[] args = new String[toSelect.size()];
        args[0] = String.valueOf(toSelect.get(0));

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Sub._ID+" = ?");

        //build the select statement
        for(int i=1;i<toSelect.size();i++){
            builder.append(" OR "+SplatnetContract.Sub._ID+" = ?");
            args[i] = String.valueOf(toSelect.get(i));
        }

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Sub.TABLE_NAME,null,whereClause,args,null,null,null);

        Sub sub = new Sub();

        if(cursor.moveToFirst()){
            do {
                sub = new Sub();

                sub.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Sub._ID));
                sub.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Sub.COLUMN_NAME));
                sub.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Sub.COLUMN_URL));

                selected.put(sub.id, sub);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();

        toSelect = new ArrayList<>();
        return selected;
    }

}

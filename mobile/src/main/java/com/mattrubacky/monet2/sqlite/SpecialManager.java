package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.GridLayout;

import com.mattrubacky.monet2.deserialized.Special;
import com.mattrubacky.monet2.deserialized.Sub;
import com.mattrubacky.monet2.dialog.LoadingDialog;
import com.mattrubacky.monet2.sqlite.SplatnetContract;
import com.mattrubacky.monet2.sqlite.SplatnetSQLHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/18/2017.
 */

class SpecialManager {

    Context context;
    HashMap<Integer,Special> toInsert;
    ArrayList<Integer> toSelect;
    LoadingDialog dialog;

    public SpecialManager(Context context){
        this.context = context;
        toInsert = new HashMap<>();
        toSelect = new ArrayList<>();
    }
    public boolean exists(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();

        String whereClause = SplatnetContract.Special._ID +" = ?";
        String[] args = new String[] {String.valueOf(id)};
        Cursor cursor = database.query(SplatnetContract.Special.TABLE_NAME,null,whereClause,args,null,null,null);

        if(cursor.getCount()==0){
            cursor.close();
            database.close();
            return false;
        }else{
            cursor.close();
            database.close();
            return true;
        }
    }

    public void addToInsert(Special special){
        toInsert.put(special.id, special);
    }
    public void addToSelect(int id){
        if(!toSelect.contains(id)){
            toSelect.add(id);
        }
    }

    public void insert(){
        if(toInsert.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
            ContentValues values;

            Object[] keys = toInsert.keySet().toArray();

            String whereClause = SplatnetContract.Special._ID +" = ?";
            String[] args;
            Cursor cursor = null;

            Special special;
            for (int i = 0; i < keys.length; i++) {
                values = new ContentValues();
                special = new Special();

                args = new String[] {String.valueOf(special.id)};
                cursor = database.query(SplatnetContract.Special.TABLE_NAME,null,whereClause,args,null,null,null);
                if(cursor.getCount()==0) {

                    values.put(SplatnetContract.Special._ID, special.id);
                    values.put(SplatnetContract.Special.COLUMN_NAME, special.name);
                    values.put(SplatnetContract.Special.COLUMN_URL, special.url);

                    database.insert(SplatnetContract.Special.TABLE_NAME, null, values);
                }
            }
            database.close();
            if(cursor!=null){
                cursor.close();
            }
        }
        toInsert = new HashMap<>();
    }

    public HashMap<Integer,Special> select(){
        HashMap<Integer,Special> selected = new HashMap<>();

        if(toSelect.size()>0) {

            SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

            String[] args = new String[toSelect.size()];
            args[0] = String.valueOf(toSelect.get(0));

            StringBuilder builder = new StringBuilder();
            builder.append(SplatnetContract.Special._ID + " = ?");

            for (int i = 1; i < toSelect.size(); i++) {
                builder.append(" OR " + SplatnetContract.Special._ID + " = ?");
                args[i] = String.valueOf(toSelect.get(i));
            }

            String whereClause = builder.toString();

            Cursor cursor = database.query(SplatnetContract.Special.TABLE_NAME, null, whereClause, args, null, null, null);

            Special special;

            if (cursor.moveToFirst()) {
                do {
                    special = new Special();
                    special.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Special._ID));
                    special.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Special.COLUMN_NAME));
                    special.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Special.COLUMN_URL));
                    selected.put(special.id, special);
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();

            toSelect = new ArrayList<>();
        }

        return selected;
    }
}

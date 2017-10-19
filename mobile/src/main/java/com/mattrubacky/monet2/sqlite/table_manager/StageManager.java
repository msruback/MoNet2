package com.mattrubacky.monet2.sqlite.table_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.Splatnet;
import com.mattrubacky.monet2.deserialized.Stage;
import com.mattrubacky.monet2.sqlite.SplatnetContract;
import com.mattrubacky.monet2.sqlite.SplatnetSQLHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by mattr on 10/17/2017.
 */

public class StageManager {

    Context context;
    HashMap<Integer,Stage> toInsert;
    ArrayList<Integer> toSelect;

    public StageManager(Context context){
        this.context = context;
        toInsert = new HashMap<>();
        toSelect = new ArrayList<>();
    }

    private boolean exists(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();

        String whereClause = SplatnetContract.Stage._ID +" = ?";
        String[] args = new String[] {String.valueOf(id)};
        Cursor cursor = database.query(SplatnetContract.Stage.TABLE_NAME,null,whereClause,args,null,null,null);

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

    public void addToInsert(Stage stage){
        if(!exists(stage.id)){
            toInsert.put(stage.id,stage);
        }
    }
    public void addToSelect(int id){
        if(!toSelect.contains(id)){
            toSelect.add(id);
        }
    }

    public void insert() {
        if (toInsert.size() > 0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();

            ContentValues values;

            Integer[] keys = (Integer[]) toInsert.keySet().toArray();

            Stage stage;
            for (int i = 0; i < keys.length; i++) {
                values = new ContentValues();

                stage = toInsert.get(keys[i]);

                values.put(SplatnetContract.Stage._ID, stage.id);
                values.put(SplatnetContract.Stage.COLUMN_NAME, stage.name);
                values.put(SplatnetContract.Stage.COLUMN_URL, stage.url);

                database.insert(SplatnetContract.Stage.TABLE_NAME, null, values);
            }
            database.close();
        }
        toInsert = new HashMap<>();
    }

    public Stage select(int id){
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String query = "SELECT * FROM "+ SplatnetContract.Stage.TABLE_NAME+" WHERE "+ SplatnetContract.Stage._ID+" = "+id;
        Cursor cursor = database.rawQuery(query,null);

        Stage stage = new Stage();

        if(cursor.moveToFirst()){
            stage.id = id;
            stage.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Stage.COLUMN_NAME));
            stage.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Stage.COLUMN_URL));
        }
        cursor.close();
        database.close();
        return stage;
    }

    public HashMap<Integer,Stage> select(){
        HashMap<Integer,Stage> selected = new HashMap<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String[] args = new String[toSelect.size()];
        args[0] = String.valueOf(toSelect.get(0));

        StringBuilder builder = new StringBuilder();
        builder.append(SplatnetContract.Stage._ID+" = ?");

        for(int i=1;i<toSelect.size();i++){
            builder.append(" OR "+SplatnetContract.Stage._ID+" = ?");
            args[i] = String.valueOf(toSelect.get(i));
        }

        String whereClause = builder.toString();

        Cursor cursor = database.query(SplatnetContract.Stage.TABLE_NAME,null,whereClause,args,null,null,null);

        Stage stage;

        if(cursor.moveToFirst()){
            do {
                stage = new Stage();
                stage.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Stage._ID));
                stage.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Stage.COLUMN_NAME));
                stage.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Stage.COLUMN_URL));
                selected.put(stage.id,stage);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();

        toSelect = new ArrayList<>();
        return selected;
    }

    public ArrayList<Stage> selectAll(){
        ArrayList<Stage> selected = new ArrayList<>();
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        Cursor cursor = database.query(SplatnetContract.Stage.TABLE_NAME,null,null,null,null,null,null);

        Stage stage;

        if(cursor.moveToFirst()){
            do {
                stage = new Stage();
                stage.id = cursor.getInt(cursor.getColumnIndex(SplatnetContract.Stage._ID));
                stage.name = cursor.getString(cursor.getColumnIndex(SplatnetContract.Stage.COLUMN_NAME));
                stage.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Stage.COLUMN_URL));
                selected.add(stage);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return selected;
    }
}

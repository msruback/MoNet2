package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.splatoon.DatabaseObjects.DatabaseObject;
import com.mattrubacky.monet2.sqlite.Factory.DatabaseObjectFactory;
import com.mattrubacky.monet2.sqlite.Factory.TableName;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/16/2018.
 */

public class TableManager<T extends DatabaseObject>{
    private Context context;
    private HashMap<Integer,T> toInsert;
    private ArrayList<Integer> toSelect;
    private Class<T> type;
    private String tableName;

    public TableManager(Context context,Class<T> type){
        this.context = context;
        toInsert = new HashMap<>();
        toSelect = new ArrayList<>();
        this.type = type;
        tableName = (type.getAnnotation(TableName.class)).value();
    }

    public TableManager(Context context,Class<T> type, String tableName){
        this.context = context;
        toInsert = new HashMap<>();
        toSelect = new ArrayList<>();
        this.type = type;
        this.tableName = tableName;
    }

    public boolean exists(SQLiteDatabase database, T object){
        String whereClause = "_ID = ?";
        String[] args = new String[]{String.valueOf(object.getId())};

        Cursor cursor = database.query(tableName, null, whereClause, args, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    public void addToInsert(T object){toInsert.put(object.getId(), object);
    }

    //Add a sub to be selected
    public void addToSelect(int id){
        if(!toSelect.contains(id)){
            toSelect.add(id);
        }
    }

    public String getWhere(){

        StringBuilder builder = new StringBuilder();
        builder.append("_ID = ?");

        //build the select statement
        for (int i = 1; i < toSelect.size(); i++) {
            builder.append(" OR _ID = ?");
        }

        return builder.toString();
    }

    public T select(int id) throws IllegalAccessException, InstantiationException {
        T selected = type.newInstance();
        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

        String[] args = new String[toSelect.size()];
        args[0] = String.valueOf(toSelect.get(0));

        //build the select statement
        for (int i = 1; i < toSelect.size(); i++) {
            args[i] = String.valueOf(toSelect.get(i));
        }

        String whereClause = getWhere();
        Cursor cursor = database.query(tableName, null, whereClause, args, null, null, null);

        if (cursor.moveToFirst()) {
            selected = buildObject(type, cursor);
        }
        cursor.close();
        database.close();

        toSelect = new ArrayList<>();
        return selected;
    }

    public HashMap<Integer,T> select(){
        HashMap<Integer,T> selected = new HashMap<>();
        if (toSelect.size() > 0) {

            SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

            String[] args = new String[toSelect.size()];
            args[0] = String.valueOf(toSelect.get(0));

            //build the select statement
            for (int i = 1; i < toSelect.size(); i++) {
                args[i] = String.valueOf(toSelect.get(i));
            }

            String whereClause = getWhere();
            try {

                Cursor cursor = database.query(tableName, null, whereClause, args, null, null, null);

                if (cursor.moveToFirst()) {
                    do {
                        T object = buildObject(type, cursor);
                        selected.put(object.getId(), object);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                database.close();

                toSelect = new ArrayList<>();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return selected;
    }

    public ArrayList<T> selectAll(){
        ArrayList<T> selected = new ArrayList<>();

        SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();
        try {

            Cursor cursor = database.query(tableName, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    T object = buildObject(type, cursor);
                    selected.add(object);
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();

            toSelect = new ArrayList<>();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return selected;
    }

    public void insert(){
        if(toInsert.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();



            for (Integer key : toInsert.keySet()) {
                T object = toInsert.get(key);
                if (exists(database, object)) {
                    ContentValues values = getValues(object, new ContentValues());

                    database.insert(tableName, null, values);
                }
                database.close();
            }
            toInsert = new HashMap<>();
        }
    }

    protected T buildObject(Class<T> type,Cursor cursor) throws IllegalAccessException, InstantiationException {
        return DatabaseObjectFactory.parseObject(type.newInstance(), cursor);
    }

    protected ContentValues getValues(T toInsert,ContentValues values){
        return DatabaseObjectFactory.storeObject(toInsert,new ContentValues());
    }

}

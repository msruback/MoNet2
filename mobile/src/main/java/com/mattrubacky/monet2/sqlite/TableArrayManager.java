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
 * Created by mattr on 10/18/2018.
 */

public class TableArrayManager<T extends DatabaseObject>{

    private Context context;
    private ArrayList<T> toInsert;
    private ArrayList<Integer> toSelect;
    private Class<T> type;

    public TableArrayManager(Context context, Class<T> type) {
        this.context = context;
        toInsert = new ArrayList<>();
        toSelect = new ArrayList<>();
        this.type = type;
    }

    public void addToInsert(int id,T object){toInsert.add(id, object);
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

    public HashMap<Integer,ArrayList<T>> select(){
        HashMap<Integer,ArrayList<T>> selected = new HashMap<>();
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

                Cursor cursor = database.query((type.getAnnotation(TableName.class)).value(), null, whereClause, args, null, null, null);

                if (cursor.moveToFirst()) {
                    do {
                        T object = buildObject(type, cursor);
                        ArrayList<T> objects;
                        if(selected.containsKey(object.getId())){
                            objects = selected.get(object.getId());
                        }else{
                            objects = new ArrayList<>();
                        }
                        if(objects.size()<=4){
                            objects.add(object);
                        }
                        selected.put(object.getId(),objects);
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

    public void insert(){
        if(toInsert.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();



            for (T object : toInsert) {
                ContentValues values = getValues(object, new ContentValues());

                database.insert(type.getAnnotation(TableName.class).value(), null, values);
                database.close();
            }
            toInsert = new ArrayList<>();
        }
    }

    protected T buildObject(Class<T> type,Cursor cursor) throws IllegalAccessException, InstantiationException {
        return DatabaseObjectFactory.parseObject(type.newInstance(), cursor);
    }

    protected ContentValues getValues(T toInsert,ContentValues values){
        return DatabaseObjectFactory.storeObject(toInsert,new ContentValues());
    }

}

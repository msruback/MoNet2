package com.mattrubacky.monet2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.deserialized.Brand;
import com.mattrubacky.monet2.deserialized.Gear;
import com.mattrubacky.monet2.deserialized.NicknameIcon;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mattr on 10/19/2017.
 */

class FriendManager {
    Context context;
    HashMap<String,NicknameIcon> toInsert;
    ArrayList<String> toSelect;

    public FriendManager(Context context){
        this.context = context;
        toInsert = new HashMap<>();
        toSelect = new ArrayList<>();
    }

    public void addToInsert(NicknameIcon friend){
        toInsert.put(friend.id,friend);
    }

    public void addToSelect(String id){
        toSelect.add(id);
    }

    public void insert(){
        if(toInsert.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();
            ContentValues values;

            Object[] keys = toInsert.keySet().toArray();

            String whereClause = SplatnetContract.Friends._ID + " = ?";
            String[] args;
            Cursor cursor = null;

            NicknameIcon friend;

            for(int i=0;i<keys.length;i++){
                friend = toInsert.get(keys[i]);

                args = new String[] {friend.id};
                cursor = database.query(SplatnetContract.Friends.TABLE_NAME,null,whereClause,args,null,null,null);

                values = new ContentValues();

                values.put(SplatnetContract.Friends._ID,friend.id);
                values.put(SplatnetContract.Friends.COLUMN_NAME,friend.nickname);
                values.put(SplatnetContract.Friends.COLUMN_URL,friend.url);
                if(cursor.getCount()==0){
                    database.insert(SplatnetContract.Friends.TABLE_NAME, null, values);
                }else{
                    database.update(SplatnetContract.Closet.TABLE_NAME, values,whereClause,args);
                }
            }
            if(cursor!=null) {
                cursor.close();
            }
            database.close();
            toInsert = new HashMap<>();
        }
    }

    public HashMap<String,NicknameIcon> select(){
        HashMap<String,NicknameIcon> selected = new HashMap<>();
        if(toSelect.size()>0) {
            SQLiteDatabase database = new SplatnetSQLHelper(context).getReadableDatabase();

            String[] args = new String[toSelect.size()];
            args[0] = toSelect.get(0);


            StringBuilder builder = new StringBuilder();
            builder.append(SplatnetContract.Friends._ID + " = ?");

            for (int i = 1; i < toSelect.size(); i++) {
                builder.append(" OR " + SplatnetContract.Friends._ID + " = ?");
                args[i] = toSelect.get(i);
            }

            String whereClause = builder.toString();

            Cursor cursor = database.query(SplatnetContract.Friends.TABLE_NAME, null, whereClause, args, null, null, null);

            NicknameIcon friend;

            if (cursor.moveToFirst()) {
                do {
                    friend = new NicknameIcon();

                    friend.id = cursor.getString(cursor.getColumnIndex(SplatnetContract.Friends._ID));
                    friend.nickname = cursor.getString(cursor.getColumnIndex(SplatnetContract.Friends.COLUMN_NAME));
                    friend.url = cursor.getString(cursor.getColumnIndex(SplatnetContract.Friends.COLUMN_URL));

                    selected.put(friend.id,friend);

                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();

        }
        return selected;
    }
}

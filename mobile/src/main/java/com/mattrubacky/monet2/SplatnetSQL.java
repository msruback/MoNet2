package com.mattrubacky.monet2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mattr on 9/22/2017.
 */

public class SplatnetSQL {

    private SQLiteDatabase database;

    public SplatnetSQL(Context context){
        database = new SplatnetSQLHelper(context).getWritableDatabase();
    }
    private void insertSkill(int id, String name, Boolean chunkable) {
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Skill._ID,id);
        values.put(SplatnetContract.Skill.COLUMN_NAME,name);
        values.put(SplatnetContract.Skill.COLUMN_CHUNKABLE,chunkable);

        database.insert(SplatnetContract.Skill.TABLE_NAME, null, values);
    }

    private void insertSub(int id, String name){
        ContentValues values = new ContentValues();

        values.put(SplatnetContract.Sub._ID,id);
        values.put(SplatnetContract.Sub.COLUMN_NAME,name);

        database.insert(SplatnetContract.Sub.TABLE_NAME, null, values);
    }
    
}
class SplatnetSQLHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "sample_database";

    public SplatnetSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Tables without Foriegn Keys (Level 0 )
        sqLiteDatabase.execSQL(SplatnetContract.Skill.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Sub.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Special.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Stage.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Splatfest.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Friends.CREATE_TABLE);

        //Tables with Foriegn Keys to Level 0 (Level 1)
        sqLiteDatabase.execSQL(SplatnetContract.Battle.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Brand.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Weapon.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.SplatfestVotes.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.StagePostcards.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.ChunkBag.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Rotation.CREATE_TABLE);

        //Tables with at least one Foriegn Key to Level 1 (Level 2)
        sqLiteDatabase.execSQL(SplatnetContract.Gear.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.WeaponLocker.CREATE_TABLE);

        //Tables with at least one Foriegn Key to Level 2 (Level 3)
        sqLiteDatabase.execSQL(SplatnetContract.Player.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Closet.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Shop.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Level 3
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Player.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Closet.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Shop.TABLE_NAME);
        //Level 2
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Gear.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.WeaponLocker.TABLE_NAME);
        //Level 1
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Battle.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Brand.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Weapon.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.SplatfestVotes.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.StagePostcards.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.ChunkBag.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Rotation.TABLE_NAME);
        //Level 0
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Skill.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Sub.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Special.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Stage.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Splatfest.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SplatnetContract.Friends.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
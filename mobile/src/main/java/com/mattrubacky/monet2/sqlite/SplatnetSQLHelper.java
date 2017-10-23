package com.mattrubacky.monet2.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mattr on 10/17/2017.
 */
public class SplatnetSQLHelper extends SQLiteOpenHelper {


    private Context context;
    private static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "splatnet";

    public SplatnetSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

        //Tables with at least one Foriegn Key to Level 1 (Level 2)
        sqLiteDatabase.execSQL(SplatnetContract.Gear.CREATE_TABLE);

        //Tables with at least one Foriegn Key to Level 2 (Level 3)
        sqLiteDatabase.execSQL(SplatnetContract.Player.CREATE_TABLE);
        sqLiteDatabase.execSQL(SplatnetContract.Closet.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {


        for(int i=oldVer+1;i<=newVer;i++) {
            switch (i) {

                case 3:
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS weapon_locker");
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS stage_postcards");
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS chunk_bag");
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS rotation");
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS shop");

                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Battle.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Battle.COLUMN_MY_TEAM_COLOR + " TEXT");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Battle.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Battle.COLUMN_MY_TEAM_KEY + " TEXT");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Battle.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Battle.COLUMN_MY_TEAM_NAME + " TEXT");

                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Battle.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Battle.COLUMN_OTHER_TEAM_COLOR + " TEXT");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Battle.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Battle.COLUMN_OTHER_TEAM_KEY + " TEXT");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Battle.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Battle.COLUMN_OTHER_TEAM_NAME + " TEXT");

                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Splatfest.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Splatfest.COLUMN_STAGE + " INTEGER REFERENCES stage(_id)");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Splatfest.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Splatfest.COLUMN_IMAGE_PANEL + " INTEGER REFERENCES stage(_id)");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Splatfest.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Splatfest.COLUMN_IMAGE_ALPHA + " INTEGER REFERENCES stage(_id)");
                    sqLiteDatabase.execSQL("ALTER TABLE " + SplatnetContract.Splatfest.TABLE_NAME + " ADD COLUMN " + SplatnetContract.Splatfest.COLUMN_IMAGE_BRAVO + " INTEGER REFERENCES stage(_id)");

                    break;
                case 4:
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS closet");
                    sqLiteDatabase.execSQL(SplatnetContract.Closet.CREATE_TABLE);

                    sqLiteDatabase.execSQL(SplatnetContract.Head.CREATE_TABLE);
                    sqLiteDatabase.execSQL(SplatnetContract.Clothes.CREATE_TABLE);
                    sqLiteDatabase.execSQL(SplatnetContract.Shoe.CREATE_TABLE);

                    new GearManager(context).updateTo4();
                    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS gear");
                    break;
            }
        }
    }
}

package com.mattrubacky.monet2;

import android.provider.BaseColumns;

/**
 * Created by mattr on 9/21/2017.
 */

public final class SplatnetContract {
    private SplatnetContract(){}

    public static class Battle implements BaseColumns{
        public static final String TABLE_NAME = "battle";
        public static final String COLUMN_STAGE = "stage";
        public static final String COLUMN_RESULT = "result";
        public static final String COLUMN_ALLY_SCORE = "ally_score";
        public static final String COLUMN_FOE_SCORE = "foe_score";
        public static final String COLUMN_RULE = "rule";
        public static final String COLUMN_MODE = "mode";
        public static final String COLUMN_POWER ="power";
        public static final String COLUMN_WIN_METER ="win_meter";
        public static final String COLUMN_FES = "fes";
        public static final String COLUMN_ELAPSED_TIME = "elapsed_time";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXIST "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_STAGE + " INTEGER, FORIEGN KEY("+COLUMN_STAGE+") REFERENCES stage(id),"+
                COLUMN_RESULT + " TEXT,"+
                COLUMN_ALLY_SCORE + " INTEGER,"+
                COLUMN_FOE_SCORE + " INTEGER,"+
                COLUMN_RULE + " TEXT,"+
                COLUMN_MODE + " TEXT,"+
                COLUMN_POWER + " INTEGER,"+
                COLUMN_WIN_METER + " REAL,"+
                COLUMN_FES + " INTEGER, FORIEGN KEY("+COLUMN_FES+") REFERENCES splatfest(id),"+
                COLUMN_ELAPSED_TIME +" INTEGER";
    }

    public static class Player implements BaseColumns{
        public static final String TABLE_NAME = "player";
        public static final String COLUMN_BATTLE = "battle";
        public static final String COLUMN_MODE = "mode";
        public static final String COLUMN_POINT = "point";
        public static final String COLUMN_KILL = "kill";
        public static final String COLUMN_ASSIST = "assist";
        public static final String COLUMN_SPECIAL = "special";
        public static final String COLUMN_WEAPON = "weapon";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_RANK = "rank";
        public static final String COLUMN_S_NUM = "s_num";
        public static final String COLUMN_FES_GRADE = "fes_grade";
        public static final String COLUMN_HEAD = "head";
        public static final String COLUMN_HEAD_MAIN = "head_main";
        public static final String COLUMN_HEAD_SUB_1 = "head_sub_1";
        public static final String COLUMN_HEAD_SUB_2 = "head_sub_2";
        public static final String COLUMN_HEAD_SUB_3 = "head_sub_3";
        public static final String COLUMN_CLOTHES = "clothes";
        public static final String COLUMN_CLOTHES_MAIN = "clothes_main";
        public static final String COLUMN_CLOTHES_SUB_1 = "clothes_sub_1";
        public static final String COLUMN_CLOTHES_SUB_2 = "clothes_sub_2";
        public static final String COLUMN_CLOTHES_SUB_3 = "clothes_sub_3";
        public static final String COLUMN_SHOES = "shoes";
        public static final String COLUMN_SHOES_MAIN = "shoes_main";
        public static final String COLUMN_SHOES_SUB_1 = "shoes_sub_1";
        public static final String COLUMN_SHOES_SUB_2 = "shoes_sub_2";
        public static final String COLUMN_SHOES_SUB_3 = "shoes_sub_3";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXIST "+TABLE_NAME+" ("+
                COLUMN_BATTLE +" INTEGER, FORIEGN KEY("+COLUMN_BATTLE+") REFERENCES battle(id)";
    }

    public static class Gear{
        public static final String TABLE_NAME = "gear";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_KIND = "kind";
        public static final String COLUMN_RARITY = "rarity";
        public static final String COLUMN_BRAND = "brand";
    }

    public static class Brand{
        public static final String TABLE_NAME = "brand";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SKILL = "skill";
    }

    public static class Skill{
        public static final String TABLE_NAME = "skill";
        public static final String COLUMN_NAME = "name";
    }

    public static class Weapon{
        public static final String TABLE_NAME = "weapon";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SUB = "sub";
        public static final String COLUMN_SPECIAL = "special";
    }

    public static class Sub{
        public static final String TABLE_NAME = "sub";
        public static final String COLUMN_NAME = "name";
    }

    public static class Special{
        public static final String TABLE_NAME = "special";
        public static final String COLUMN_NAME = "name";
    }

    public static class Stage{
        public static final String TABLE_NAME = "stage";
        public static final String COLUMN_NAME = "name";
    }

    public static class Splatfest{
        public static final String TABLE_NAME = "splatfest";
        public static final String COLUMN_ALPHA = "alpha";
        public static final String COLUMN_BRAVO = "bravo";
        public static final String COLUMN_ALPHA_LONG = "alpha_long";
        public static final String COLUMN_BRAVO_LONG = "bravo_long";
        public static final String COLUMN_ALPHA_COLOR = "alpha_color";
        public static final String COLUMN_BRAVO_COLOR = "bravo_color";
        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_END_TIME = "end_time";
        public static final String COLUMN_ANNOUNCE_TIME = "announce_time";
        public static final String COLUMN_RESULT_TIME = "result_time";
        public static final String COLUMN_ALPHA_PLAYERS = "alpha_players";
        public static final String COLUMN_BRAVO_PLAYERS = "bravo_players";
        public static final String COLUMN_ALPHA_SOLO_WINS = "alpha_solo_wins";
        public static final String COLUMN_BRAVO_SOLO_WINS = "bravo_solo_wins";
        public static final String COLUMN_ALPHA_TEAM_WINS = "alpha_team_wins";
        public static final String COLUMN_BRAVO_TEAM_WINS = "bravo_team_wins";
        public static final String COLUMN_VOTE = "vote";
        public static final String COLUMN_SOLO = "solo";
        public static final String COLUMN_TEAM = "team";
        public static final String COLUMN_WINNER = "winner";
    }

    public static class SplatfestVotes{
        public static final String TABLE_NAME = "splatfest_votes";
        public static final String COLUMN_PLAYER = "player";
        public static final String COLUMN_FES = "fes";
        public static final String COLUMN_VOTE = "vote";
    }

    public static class Friends{
        public static final String TABLE_NAME = "friends";
        public static final String COLUMN_NAME = "name";
    }

    public static class WeaponLocker{
        public static final String TABLE_NAME = "weapon_locker";
        public static final String COLUMN_WEAPON = "weapon";
        public static final String COLUMN_WIN_METER = "win_meter";
        public static final String COLUMN_WIN_COUNT = "win_count";
        public static final String COLUMN_LOSE_COUNT = "lose_count";
        public static final String COLUMN_TOTAL_PAINT_POINT = "total_paint_point";
        public static final String COLUMN_MAX_WIN_METER = "max_win_meter";
        public static final String COLUMN_LAST_USE_TIME = "last_use_time";
    }

    public static class Closet{
        public static final String TABLE_NAME = "closet";
        public static final String COLUMN_GEAR = "gear";
        public static final String COLUMN_MAIN = "main";
        public static final String COLUMN_SUB_1 = "sub_1";
        public static final String COLUMN_SUB_2 = "sub_2";
        public static final String COLUMN_SUB_3 = "sub_3";
        public static final String COLUMN_WIN_COUNT = "win_count";
        public static final String COLUMN_LOSE_COUNT = "lose_count";
        public static final String COLUMN_LAST_USE_TIME = "last_use_time";
    }

    public static class StagePostcards{
        public static final String TABLE_NAME = "stage_postcards";
        public static final String COLUMN_STAGE = "stage";
        public static final String COLUMN_SPLATZONE_WIN = "splatzone_win";
        public static final String COLUMN_TOWER_WIN = "tower_win";//yagura
        public static final String COLUMN_RAINMAKER_WIN = "rainmaker_win";//hoko
        public static final String COLUMN_TURFWAR_WIN = "turfwar_win";
        public static final String COLUMN_SPLATFEST_WIN = "splatfet_win";
        public static final String COLUMN_SPLATZONE_LOSE = "splatzone_lose";
        public static final String COLUMN_TOWER_LOSE = "tower_lose";
        public static final String COLUMN_RAINMAKER_LOSE = "rainmaker_lose";
        public static final String COLUMN_TURFWAR_LOSE = "turfwar_lose";
        public static final String COLUMN_SPLATFEST_LOSE = "splatfest_lose";
    }

    public static class ChunkBag{
        public static final String TABLE_NAME = "chunk_bag";
        public static final String COLUMN_SKILL = "skill";
        public static final String COLUMN_COUNT = "count";
    }

    public static class Rotation{
        public static final String TABLE_NAME = "rotation";
        public static final String COLUMN_STAGE_A = "stage_a";
        public static final String COLUMN_STAGE_B = "stage_b";
        public static final String COLUMN_MODE = "mode";
        public static final String COLUMN_RULE = "rule";
        public static final String COLUMN_START = "start";
        public static final String COLUMN_END = "end";
    }

    public static class Shop{
        public static final String TABLE_NAME = "shop";
        public static final String COLUMN_GEAR = "gear";
        public static final String COLUMN_MAIN = "main";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_END = "end";
    }

}

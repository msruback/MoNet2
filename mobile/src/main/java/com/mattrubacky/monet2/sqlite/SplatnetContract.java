package com.mattrubacky.monet2.sqlite;

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
        public static final String COLUMN_POWER = "power";
        public static final String COLUMN_WIN_METER = "win_meter";
        public static final String COLUMN_FES = "fes";
        public static final String COLUMN_ELAPSED_TIME = "elapsed_time";
        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_MY_TEAM_COLOR = "my_team_color";
        public static final String COLUMN_MY_TEAM_KEY = "my_team_key";
        public static final String COLUMN_MY_TEAM_NAME = "my_team_name";
        public static final String COLUMN_MY_TEAM_OTHER_NAME = "my_team_other_name";
        public static final String COLUMN_MY_TEAM_CONSECUTIVE_WINS = "my_team_consecutive_wins";
        public static final String COLUMN_OTHER_TEAM_COLOR = "other_team_color";
        public static final String COLUMN_OTHER_TEAM_KEY = "other_team_key";
        public static final String COLUMN_OTHER_TEAM_NAME = "other_team_name";
        public static final String COLUMN_OTHER_TEAM_OTHER_NAME = "other_team_other_name";
        public static final String COLUMN_OTHER_TEAM_CONSECUTIVE_WINS = "other_team_consecutive_wins";
        public static final String COLUMN_OTHER_TEAM_FES_POWER = "other_team_fes_power";
        public static final String COLUMN_FES_POINT = "fes_point";
        public static final String COLUMN_FES_GRADE = "fes_grade";
        public static final String COLUMN_CONTRIBUTION_POINTS = "contribution_points";
        public static final String COLUMN_UNIFORM_BONUS = "uniform_bonus";
        public static final String COLUMN_FES_MODE = "fes_mode";
        public static final String COLUMN_EVENT_TYPE = "event_type";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_STAGE + " INTEGER REFERENCES stage(_id), "+
                COLUMN_RESULT + " TEXT, "+
                COLUMN_ALLY_SCORE + " INTEGER, "+
                COLUMN_FOE_SCORE + " INTEGER, "+
                COLUMN_RULE + " TEXT, "+
                COLUMN_MODE + " TEXT, "+
                COLUMN_POWER + " INTEGER, "+
                COLUMN_WIN_METER + " REAL, "+
                COLUMN_FES + " INTEGER REFERENCES splatfest(_id), "+
                COLUMN_START_TIME + " INTEGER, "+
                COLUMN_ELAPSED_TIME +" INTEGER, "+
                COLUMN_MY_TEAM_COLOR + " TEXT, "+
                COLUMN_MY_TEAM_KEY + " TEXT, "+
                COLUMN_MY_TEAM_NAME + " TEXT, "+
                COLUMN_MY_TEAM_OTHER_NAME + " TEXT, "+
                COLUMN_MY_TEAM_CONSECUTIVE_WINS + " INTEGER, "+
                COLUMN_OTHER_TEAM_COLOR + " TEXT, "+
                COLUMN_OTHER_TEAM_KEY + " TEXT, "+
                COLUMN_OTHER_TEAM_NAME + " TEXT, "+
                COLUMN_OTHER_TEAM_OTHER_NAME + " TEXT, "+
                COLUMN_OTHER_TEAM_CONSECUTIVE_WINS + " INTEGER, "+
                COLUMN_OTHER_TEAM_FES_POWER + " INTEGER, "+
                COLUMN_FES_POINT + " INTEGER, "+
                COLUMN_FES_GRADE + " TEXT, "+
                COLUMN_CONTRIBUTION_POINTS + " INTEGER, "+
                COLUMN_UNIFORM_BONUS + " REAL, "+
                COLUMN_FES_MODE + " TEXT, "+
                COLUMN_EVENT_TYPE + " TEXT)";
    }

    public static class Player implements BaseColumns{
        public static final String TABLE_NAME = "player";
        public static final String COLUMN_BATTLE = "battle";
        public static final String COLUMN_MODE = "mode";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_POINT = "point";
        public static final String COLUMN_KILL = "kill";
        public static final String COLUMN_ASSIST = "assist";
        public static final String COLUMN_DEATH = "death";
        public static final String COLUMN_SPECIAL = "special";
        public static final String COLUMN_WEAPON = "weapon";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_RANK = "rank";
        public static final String COLUMN_STAR_RANK = "star_rank";
        public static final String COLUMN_S_NUM = "s_num";
        public static final String COLUMN_FES_GRADE = "fes_grade";
        public static final String COLUMN_SPECIES = "species";
        public static final String COLUMN_STYLE = "style";
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

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                COLUMN_BATTLE +" INTEGER REFERENCES battle(_id), "+
                COLUMN_MODE +" TEXT, "+
                COLUMN_POINT + " INTEGER, "+
                COLUMN_KILL + " INTEGER, "+
                COLUMN_ASSIST + " INTEGER, "+
                COLUMN_DEATH + " INTEGER, "+
                COLUMN_SPECIAL +" INTEGER, "+
                COLUMN_WEAPON + " INTEGER REFERENCES weapon(_id), "+
                COLUMN_NAME +" TEXT, "+
                COLUMN_ID + " TEXT, "+
                COLUMN_TYPE + " INTEGER, "+ //0: user, 1: ally, 2: foe
                COLUMN_LEVEL + " INTEGER, "+
                COLUMN_RANK + " TEXT, "+
                COLUMN_STAR_RANK + " INTEGER, "+
                COLUMN_S_NUM + " TEXT, "+
                COLUMN_FES_GRADE + " TEXT, "+
                COLUMN_SPECIES + " TEXT, "+
                COLUMN_STYLE + " TEXT, "+
                COLUMN_HEAD + " INTEGER REFERENCES head(_id), "+
                COLUMN_HEAD_MAIN + " INTEGER REFERENCES skill(_id), "+
                COLUMN_HEAD_SUB_1 + " INTEGER REFERENCES skill(_id), "+
                COLUMN_HEAD_SUB_2 + " INTEGER REFERENCES skill(_id), "+
                COLUMN_HEAD_SUB_3 + " INTEGER REFERENCES skill(_id), "+
                COLUMN_CLOTHES + " INTEGER REFERENCES clothes(_id), "+
                COLUMN_CLOTHES_MAIN + " INTEGER REFERENCES skill(_id), "+
                COLUMN_CLOTHES_SUB_1 + " INTEGER REFERENCES skill(_id), "+
                COLUMN_CLOTHES_SUB_2 + " INTEGER REFERENCES skill(_id), "+
                COLUMN_CLOTHES_SUB_3 + " INTEGER REFERENCES skill(_id), "+
                COLUMN_SHOES + " INTEGER REFERENCES shoe(_id), "+
                COLUMN_SHOES_MAIN + " INTEGER REFERENCES skill(_id), "+
                COLUMN_SHOES_SUB_1 + " INTEGER REFERENCES skill(_id), "+
                COLUMN_SHOES_SUB_2 + " INTEGER REFERENCES skill(_id), "+
                COLUMN_SHOES_SUB_3 + " INTEGER REFERENCES skill(_id))";

    }

    public static class Gear implements BaseColumns{
        public static final String TABLE_NAME = "gear";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_KIND = "kind";
        public static final String COLUMN_RARITY = "rarity";
        public static final String COLUMN_BRAND = "brand";
        public static final String COLUMN_URL = "url";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_NAME + " TEXT, "+
                COLUMN_BRAND +" INTEGER REFERENCES brand(_id), "+
                COLUMN_KIND + " TEXT, "+
                COLUMN_RARITY + " INTEGER, "+
                COLUMN_URL + " TEXT)";
    }

    public static class Head implements BaseColumns{
        public static final String TABLE_NAME = "head";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_KIND = "kind";
        public static final String COLUMN_RARITY = "rarity";
        public static final String COLUMN_BRAND = "brand";
        public static final String COLUMN_URL = "url";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_NAME + " TEXT, "+
                COLUMN_BRAND +" INTEGER REFERENCES brand(_id), "+
                COLUMN_KIND + " TEXT, "+
                COLUMN_RARITY + " INTEGER, "+
                COLUMN_URL + " TEXT)";
    }

    public static class Clothes implements BaseColumns{
        public static final String TABLE_NAME = "clothes";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_KIND = "kind";
        public static final String COLUMN_RARITY = "rarity";
        public static final String COLUMN_BRAND = "brand";
        public static final String COLUMN_URL = "url";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_NAME + " TEXT, "+
                COLUMN_BRAND +" INTEGER REFERENCES brand(_id), "+
                COLUMN_KIND + " TEXT, "+
                COLUMN_RARITY + " INTEGER, "+
                COLUMN_URL + " TEXT)";
    }

    public static class Shoe implements BaseColumns{
        public static final String TABLE_NAME = "shoe";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_KIND = "kind";
        public static final String COLUMN_RARITY = "rarity";
        public static final String COLUMN_BRAND = "brand";
        public static final String COLUMN_URL = "url";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_NAME + " TEXT, "+
                COLUMN_BRAND +" INTEGER REFERENCES brand(_id), "+
                COLUMN_KIND + " TEXT, "+
                COLUMN_RARITY + " INTEGER, "+
                COLUMN_URL + " TEXT)";
    }

    public static class Brand implements BaseColumns{
        public static final String TABLE_NAME = "brand";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SKILL = "skill";
        public static final String COLUMN_URL = "url";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_SKILL + " INTEGER REFERENCES skill(_id), "+
                COLUMN_NAME + " TEXT, " +
                COLUMN_URL + " TEXT)";
    }

    public static class Skill implements BaseColumns{
        public static final String TABLE_NAME = "skill";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CHUNKABLE = "chunkable";
        public static final String COLUMN_URL = "url";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_NAME + " TEXT, "+
                COLUMN_CHUNKABLE + " INTEGER, " +
                COLUMN_URL + " TEXT)";
    }

    public static class Weapon implements BaseColumns{
        public static final String TABLE_NAME = "weapon";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_SUB = "sub";
        public static final String COLUMN_SPECIAL = "special";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_NAME + " TEXT, "+
                COLUMN_URL + " TEXT, "+
                COLUMN_SUB + " INTEGER REFERENCES sub(_id), "+
                COLUMN_SPECIAL + " INTEGER REFERENCES special(_id))";
    }

    public static class Sub implements BaseColumns{
        public static final String TABLE_NAME = "sub";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_URL = "url";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_NAME + " TEXT, " +
                COLUMN_URL + " TEXT)";
    }

    public static class Special implements BaseColumns{
        public static final String TABLE_NAME = "special";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_URL = "url";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_URL + " TEXT, "+
                COLUMN_NAME + " TEXT)";
    }

    public static class Stage implements BaseColumns{
        public static final String TABLE_NAME = "stage";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_URL = "url";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_NAME + " TEXT, " +
                COLUMN_URL + " TEXT)";
    }

    public static class Splatfest implements BaseColumns{
        public static final String TABLE_NAME = "splatfest";
        public static final String COLUMN_VERSION = "verison";
        public static final String COLUMN_ALPHA = "alpha";
        public static final String COLUMN_BRAVO = "bravo";
        public static final String COLUMN_ALPHA_LONG = "alpha_long";
        public static final String COLUMN_BRAVO_LONG = "bravo_long";
        public static final String COLUMN_ALPHA_COLOR = "alpha_color";
        public static final String COLUMN_BRAVO_COLOR = "bravo_color";
        public static final String COLUMN_ALPHA_URL = "alpha_url";
        public static final String COLUMN_BRAVO_URL = "bravo_url";
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
        public static final String COLUMN_ALPHA_SOLO_AVERAGE = "alpha_solo_average";
        public static final String COLUMN_BRAVO_SOLO_AVERAGE = "bravo_solo_average";
        public static final String COLUMN_ALPHA_TEAM_AVERAGE = "alpha_team_average";
        public static final String COLUMN_BRAVO_TEAM_AVERAGE = "bravo_team_average";
        public static final String COLUMN_VOTE = "vote";
        public static final String COLUMN_SOLO = "solo";
        public static final String COLUMN_TEAM = "team";
        public static final String COLUMN_WINNER = "winner";
        public static final String COLUMN_STAGE = "stage";
        public static final String COLUMN_IMAGE_PANEL = "image_panel";
        public static final String COLUMN_IMAGE_ALPHA = "image_alpha";
        public static final String COLUMN_IMAGE_BRAVO = "image_bravo";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_VERSION + " INTEGER, "+
                COLUMN_ALPHA + " TEXT, "+
                COLUMN_BRAVO + " TEXT, "+
                COLUMN_ALPHA_LONG + " TEXT, "+
                COLUMN_BRAVO_LONG + " TEXT, "+
                COLUMN_ALPHA_COLOR + " TEXT, "+
                COLUMN_BRAVO_COLOR + " TEXT, "+
                COLUMN_ALPHA_URL + " TEXT, "+
                COLUMN_BRAVO_URL + " TEXT, "+
                COLUMN_START_TIME + " INTEGER, "+
                COLUMN_END_TIME + " INTEGER, "+
                COLUMN_ANNOUNCE_TIME + " INTEGER, "+
                COLUMN_RESULT_TIME + " INTEGER, "+
                COLUMN_ALPHA_PLAYERS + " INTEGER, "+
                COLUMN_BRAVO_PLAYERS + " INTEGER, "+
                COLUMN_ALPHA_SOLO_WINS + " INTEGER, "+
                COLUMN_BRAVO_SOLO_WINS + " INTEGER, "+
                COLUMN_ALPHA_TEAM_WINS + " INTEGER, "+
                COLUMN_BRAVO_TEAM_WINS + " INTEGER, "+
                COLUMN_ALPHA_SOLO_AVERAGE + " REAL, "+
                COLUMN_BRAVO_SOLO_AVERAGE + " REAL, "+
                COLUMN_ALPHA_TEAM_AVERAGE + " REAL, "+
                COLUMN_BRAVO_TEAM_AVERAGE + " REAL, "+
                COLUMN_VOTE + " INTEGER, "+//0 alpha 1 bravo
                COLUMN_SOLO + " INTEGER, "+//0 alpha 1 bravo
                COLUMN_TEAM + " INTEGER, "+//0 alpha 1 bravo
                COLUMN_WINNER + " INTEGER, "+//0 alpha 1 bravo
                COLUMN_STAGE + " INTEGER REFERENCES stage(_id), " +
                COLUMN_IMAGE_PANEL+ " TEXT, "+
                COLUMN_IMAGE_ALPHA + " TEXT, "+
                COLUMN_IMAGE_BRAVO + " TEXT)";
    }


    public static class Closet implements BaseColumns{
        public static final String TABLE_NAME = "closet";
        public static final String COLUMN_GEAR = "gear";
        public static final String COLUMN_KIND = "kind";
        public static final String COLUMN_MAIN = "main";
        public static final String COLUMN_SUB_1 = "sub_1";
        public static final String COLUMN_SUB_2 = "sub_2";
        public static final String COLUMN_SUB_3 = "sub_3";
        public static final String COLUMN_LAST_USE_TIME = "last_use_time";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_GEAR + " INTEGER, "+
                COLUMN_KIND + " TEXT, "+
                COLUMN_MAIN + " INTEGER REFERENCES skill(_id), "+
                COLUMN_SUB_1 + " INTEGER REFERENCES skill(_id), "+
                COLUMN_SUB_2 + " INTEGER REFERENCES skill(_id), "+
                COLUMN_SUB_3 + " INTEGER REFERENCES skill(_id), "+
                COLUMN_LAST_USE_TIME + " INTEGER)";
    }

    public static class Shift implements BaseColumns{
        public static final String TABLE_NAME = "shift";
        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_END_TIME = "end_time";
        public static final String COLUMN_STAGE = "stage";
        public static final String COLUMN_STAGE_IMAGE = "stage_image";
        public static final String COLUMN_WEAPON_1 = "weapon_1";
        public static final String COLUMN_WEAPON_2 = "weapon_2";
        public static final String COLUMN_WEAPON_3 = "weapon_3";
        public static final String COLUMN_WEAPON_4 = "weapon_4";

        public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME+" ("+
                COLUMN_START_TIME + " INTEGER PRIMARY KEY, "+
                COLUMN_END_TIME + " INTEGER, " +
                COLUMN_STAGE + " TEXT, "+
                COLUMN_STAGE_IMAGE + " TEXT, "+
                COLUMN_WEAPON_1 + " INTEGER REFERENCES weapon(_id), "+
                COLUMN_WEAPON_2 + " INTEGER REFERENCES weapon(_id), "+
                COLUMN_WEAPON_3 + " INTEGER REFERENCES weapon(_id), "+
                COLUMN_WEAPON_4 + " INTEGER REFERENCES weapon(_id))";
    }

    public static class Job implements BaseColumns{
        public static final String TABLE_NAME = "job";
        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_PLAY_TIME = "play_time";
        public static final String COLUMN_PAY_GRADE = "pay_grade";
        public static final String COLUMN_JOB_SCORE = "job_score";
        public static final String COLUMN_KUMA_POINT = "kuma_point";
        public static final String COLUMN_GRADE = "grade";
        public static final String COLUMN_GRADE_POINT = "grade_point";
        public static final String COLUMN_GRADE_POINT_DELTA = "grade_point_delta";
        public static final String COLUMN_DANGER_RATE = "danger_rate";
        public static final String COLUMN_IS_CLEAR = "is_clear";
        public static final String COLUMN_FAILURE_REASON = "failure_reason";
        public static final String COLUMN_FAILURE_WAVE = "failure_wave";
        public static final String COLUMN_GOLDIE = "goldie";
        public static final String COLUMN_STEELHEAD = "steelhead";
        public static final String COLUMN_FLYFISH = "flyfish";
        public static final String COLUMN_SCRAPPER = "scrapper";
        public static final String COLUMN_STEEL_EEL = "steel_eel";
        public static final String COLUMN_STINGER = "stinger";
        public static final String COLUMN_MAWS = "maws";
        public static final String COLUMN_GRILLER = "griller";
        public static final String COLUMN_DRIZZLER = "drizzler";

        public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME+" ("+
                _ID +" INTEGER PRIMARY KEY, "+
                COLUMN_START_TIME + " INTEGER REFERENCES shift(start_time), "+
                COLUMN_PLAY_TIME + " INTEGER, "+
                COLUMN_PAY_GRADE + " REAL, "+
                COLUMN_JOB_SCORE + " INTEGER, "+
                COLUMN_KUMA_POINT + " INTEGER, "+
                COLUMN_GRADE + " TEXT, "+
                COLUMN_GRADE_POINT + " INTEGER, "+
                COLUMN_GRADE_POINT_DELTA + " INTEGER, "+
                COLUMN_DANGER_RATE + " REAL, "+
                COLUMN_IS_CLEAR + " INTEGER, "+
                COLUMN_FAILURE_REASON + " TEXT, "+
                COLUMN_FAILURE_WAVE + " INTEGER, "+
                COLUMN_GOLDIE + " INTEGER, "+
                COLUMN_STEELHEAD + " INTEGER, "+
                COLUMN_FLYFISH + " INTEGER, "+
                COLUMN_SCRAPPER + " INTEGER, "+
                COLUMN_STEEL_EEL + " INTEGER, "+
                COLUMN_STINGER + " INTEGER, "+
                COLUMN_MAWS + " INTEGER, "+
                COLUMN_GRILLER + " INTEGER, "+
                COLUMN_DRIZZLER + " INTEGER)";

    }

    public static class Wave implements BaseColumns{
        public static final String TABLE_NAME = "wave";
        public static final String COLUMN_QUOTA = "quota";
        public static final String COLUMN_WATER_LEVEL = "water_level";
        public static final String COLUMN_GOLDEN_IKURA_NUM = "golden_ikura_num";
        public static final String COLUMN_GOLDEN_IKURA_POP = "golden_ikura_pop";
        public static final String COLUMN_IKURA_NUM = "ikura_num";
        public static final String COLUMN_EVENT_TYPE = "event_type";
        public static final String COLUMN_JOB = "job";
        public static final String COLUMN_NUM = "num";

        public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME+" ("+
                _ID + " INTEGER PRIMARY KEY, "+
                COLUMN_QUOTA + " INTEGER, "+
                COLUMN_WATER_LEVEL + " TEXT, "+
                COLUMN_GOLDEN_IKURA_NUM + " INTEGER, "+
                COLUMN_GOLDEN_IKURA_POP + " INTEGER, "+
                COLUMN_IKURA_NUM + " INTEGER, "+
                COLUMN_EVENT_TYPE + " TEXT, "+
                COLUMN_JOB + " INTEGER, "+
                COLUMN_NUM + " INTEGER)";
    }

    public static class Coworker implements BaseColumns{
        public static final String TABLE_NAME = "coworker";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_JOB = "job";
        public static final String COLUMN_PID = "pid";
        public static final String COLUMN_IKURA_NUM = "ikura_num";
        public static final String COLUMN_GOLDEN_IKURA_NUM = "golden_ikura_num";
        public static final String COLUMN_DEAD_COUNT = "dead_count";
        public static final String COLUMN_HELP_COUNT = "help_count";
        public static final String COLUMN_SPECIAL = "special";
        public static final String COLUMN_SPECIAL_1 = "special_1";
        public static final String COLUMN_SPECIAL_2 = "special_2";
        public static final String COLUMN_SPECIAL_3 = "special_3";
        public static final String COLUMN_WEAPON_1 = "weapon_1";
        public static final String COLUMN_WEAPON_2 = "weapon_2";
        public static final String COLUMN_WEAPON_3 = "weapon_3";
        public static final String COLUMN_GOLDIE = "goldie";
        public static final String COLUMN_STEELHEAD = "steelhead";
        public static final String COLUMN_FLYFISH = "flyfish";
        public static final String COLUMN_SCRAPPER = "scrapper";
        public static final String COLUMN_STEEL_EEL = "steel_eel";
        public static final String COLUMN_STINGER = "stinger";
        public static final String COLUMN_MAWS = "maws";
        public static final String COLUMN_GRILLER = "griller";
        public static final String COLUMN_DRIZZLER = "drizzler";

        public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME+" ("+
                COLUMN_NAME + " TEXT, "+
                COLUMN_TYPE + " INTEGER, "+
                COLUMN_JOB + " INTEGER, "+
                COLUMN_PID + " TEXT, "+
                COLUMN_IKURA_NUM + " INTEGER, "+
                COLUMN_GOLDEN_IKURA_NUM + " INTEGER, "+
                COLUMN_DEAD_COUNT + " INTEGER, "+
                COLUMN_HELP_COUNT + " INTEGER, "+
                COLUMN_SPECIAL + " INTEGER REFERENCES special(_id), "+
                COLUMN_SPECIAL_1 + " INTEGER, "+
                COLUMN_SPECIAL_2 + " INTEGER, "+
                COLUMN_SPECIAL_3 + " INTEGER, "+
                COLUMN_WEAPON_1 + " INTEGER REFERENCES weapon(_id), "+
                COLUMN_WEAPON_2 + " INTEGER REFERENCES weapon(_id), "+
                COLUMN_WEAPON_3 + " INTEGER REFERENCES weapon(_id), "+
                COLUMN_GOLDIE + " INTEGER, "+
                COLUMN_STEELHEAD + " INTEGER, "+
                COLUMN_FLYFISH + " INTEGER, "+
                COLUMN_SCRAPPER + " INTEGER, "+
                COLUMN_STEEL_EEL + " INTEGER, "+
                COLUMN_STINGER + " INTEGER, "+
                COLUMN_MAWS + " INTEGER, "+
                COLUMN_GRILLER + " INTEGER, "+
                COLUMN_DRIZZLER + " INTEGER)";
    }
}

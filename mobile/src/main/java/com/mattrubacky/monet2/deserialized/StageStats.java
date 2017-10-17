package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 * This class represents the User's stats on a certain stage
 */
public class StageStats{
    public StageStats(){}

    //The Stage in question
    @SerializedName("stage")
    public Stage stage;

    //The wins on this Stage under the Rainmaker rule
    @SerializedName("yagura_win")
    public int rainmakerWin;

    //The losses on this Stage under the Rainmaker rule
    @SerializedName("yagura_lose")
    public int rainmakerLose;

    //The wins on this Stage under the Splatzone rule
    @SerializedName("area_win")
    public int splatzonesWin;

    //The losses on this Stage under the Splatzone rule
    @SerializedName("area_lose")
    public int splatzonesLose;

    //The wins on this Stage under the Tower Control rule
    @SerializedName("hoko_win")
    public int towerWin;

    //The losses on this stage under the Tower Control rule
    @SerializedName("hoko_lose")
    public int towerLose;

    //The last time the user played on this stage
    //IMPORTANT: This is in seconds from epoch, Jave takes milliseconds from epoch, don't forget to multiply by 1000
    @SerializedName("last_play_time")
    public Long lastPlayed;
}

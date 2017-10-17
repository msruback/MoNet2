package com.mattrubacky.monet2.deserialized;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mattr on 10/17/2017.
 */
class StageStats{
    public StageStats(){}

    @SerializedName("stage")
    Stage stage;
    @SerializedName("yagura_win")
    int rainmakerWin;
    @SerializedName("yagura_lose")
    int rainmakerLose;
    @SerializedName("area_win")
    int splatzonesWin;
    @SerializedName("area_lose")
    int splatzonesLose;
    @SerializedName("hoko_win")
    int towerWin;
    @SerializedName("hoko_lose")
    int towerLose;
    @SerializedName("last_play_time")
    Long lastPlayed;
}

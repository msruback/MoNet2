package com.mattrubacky.monet2.rooms.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "splatfest")
public class SplatfestRoom {
    @PrimaryKey
    public int id;

    public int version;
    public int stage;
    @ColumnInfo(name = "announce_time")
    public long announced;
    @ColumnInfo(name = "start_time")
    public long start;
    @ColumnInfo(name = "end_time")
    public long end;
    @ColumnInfo(name = "result_time")
    public long results;

    @ColumnInfo(name = "alpha_name")
    public String alphaName;
    @ColumnInfo(name = "alpha_long_name")
    public String alphaLongName;
    @ColumnInfo(name = "alpha_color")
    public String alphaColor;
    @ColumnInfo(name = "alpha_url")
    public String alphaUrl;
    @ColumnInfo(name = "alpha_players")
    public float alphaPlayers;
    @ColumnInfo(name = "alpha_solo")
    public float alphaSolo;
    @ColumnInfo(name = "alpha_team")
    public float alphaTeam;
    @ColumnInfo(name = "alpha_solo_average")
    public float alphaSoloAverage;
    @ColumnInfo(name = "alpha_team_average")
    public float alphaTeamAverage;

    @ColumnInfo(name = "bravo_name")
    public String bravoName;
    @ColumnInfo(name = "bravo_long_name")
    public String bravoLongName;
    @ColumnInfo(name = "bravo_color")
    public String bravoColor;
    @ColumnInfo(name = "bravo_url")
    public String bravoUrl;
    @ColumnInfo(name = "bravo_players")
    public float bravoPlayers;
    @ColumnInfo(name = "bravo_solo")
    public float bravoSolo;
    @ColumnInfo(name = "bravo_team")
    public float bravoTeam;
    @ColumnInfo(name = "bravo_solo_average")
    public float bravoSoloAverage;
    @ColumnInfo(name = "bravo_team_average")
    public float bravoTeamAverage;

    public SplatfestRoom(int id, int version, int stage, long announced, long start, long end, long results,
                         String alphaName, String alphaLongName, String alphaColor, String alphaUrl, float alphaPlayers, float alphaSolo, float alphaTeam, float alphaSoloAverage, float alphaTeamAverage,
                         String bravoName, String bravoLongName, String bravoColor, String bravoUrl, float bravoPlayers, float bravoSolo, float bravoTeam, float bravoSoloAverage, float bravoTeamAverage){
        this.id = id;
        this.version = version;
        this.stage = stage;
        this.announced = announced;
        this.start = start;
        this.end = end;
        this.results = results;

        this.alphaName = alphaName;
        this.alphaLongName = alphaLongName;
        this.alphaColor = alphaColor;
        this.alphaUrl = alphaUrl;
        this.alphaPlayers = alphaPlayers;
        this.alphaSolo = alphaSolo;
        this.alphaTeam = alphaTeam;
        this.alphaSoloAverage = alphaSoloAverage;
        this.alphaTeamAverage = alphaTeamAverage;

        this.bravoName = bravoName;
        this.bravoLongName = bravoLongName;
        this.bravoColor = bravoColor;
        this.bravoUrl = bravoUrl;
        this.bravoPlayers = bravoPlayers;
        this.bravoSolo = bravoSolo;
        this.bravoTeam = bravoTeam;
        this.bravoSoloAverage = bravoSoloAverage;
        this.bravoTeamAverage = bravoTeamAverage;
    }
}

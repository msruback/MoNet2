package com.mattrubacky.monet2.data.rooms.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Rates;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestContribution;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestRates;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestResult;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestSummary;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "splatfest_result")
public class SplatfestResultRoom {

    @PrimaryKey
    public int id;

    public int version;

    @ColumnInfo(name = "vote")
    public int vote;
    @ColumnInfo(name = "solo")
    public int solo;
    @ColumnInfo(name = "team")
    public int team;
    @ColumnInfo(name = "total")
    public int total;

    @ColumnInfo(name = "alpha_players")
    public int alphaPlayers;
    @ColumnInfo(name = "alpha_solo")
    public int alphaSolo;
    @ColumnInfo(name = "alpha_team")
    public int alphaTeam;
    @ColumnInfo(name = "alpha_solo_average")
    public float alphaSoloAverage;
    @ColumnInfo(name = "alpha_team_average")
    public float alphaTeamAverage;

    @ColumnInfo(name = "bravo_players")
    public int bravoPlayers;
    @ColumnInfo(name = "bravo_solo")
    public int bravoSolo;
    @ColumnInfo(name = "bravo_team")
    public int bravoTeam;
    @ColumnInfo(name = "bravo_solo_average")
    public float bravoSoloAverage;
    @ColumnInfo(name = "bravo_team_average")
    public float bravoTeamAverage;

    public SplatfestResultRoom(int id, int version, int vote, int solo, int team, int total,
                         int alphaPlayers, int alphaSolo, int alphaTeam, float alphaSoloAverage, float alphaTeamAverage,
                         int bravoPlayers, int bravoSolo, int bravoTeam, float bravoSoloAverage, float bravoTeamAverage){
        this.id = id;
        this.version = version;

        this.vote = vote;
        this.solo = solo;
        this.team = team;
        this.total = total;

        this.alphaPlayers = alphaPlayers;
        this.alphaSolo = alphaSolo;
        this.alphaTeam = alphaTeam;
        this.alphaSoloAverage = alphaSoloAverage;
        this.alphaTeamAverage = alphaTeamAverage;

        this.bravoPlayers = bravoPlayers;
        this.bravoSolo = bravoSolo;
        this.bravoTeam = bravoTeam;
        this.bravoSoloAverage = bravoSoloAverage;
        this.bravoTeamAverage = bravoTeamAverage;
    }

    @Ignore
    public SplatfestResultRoom(SplatfestResult splatfestResult){
        this.id = splatfestResult.id;
        this.version = splatfestResult.version;

        if(version==1){
            alphaSolo = splatfestResult.rates.solo.alpha;
            bravoSolo = splatfestResult.rates.solo.bravo;

            alphaTeam = splatfestResult.rates.team.alpha;
            bravoTeam = splatfestResult.rates.team.bravo;
        }else{
            alphaSolo = splatfestResult.rates.challenge.alpha;
            bravoSolo = splatfestResult.rates.challenge.bravo;

            alphaTeam = splatfestResult.rates.regular.alpha;
            bravoTeam = splatfestResult.rates.regular.bravo;

            alphaSoloAverage = splatfestResult.alphaAverages.challenge;
            alphaTeamAverage = splatfestResult.alphaAverages.regular;

            bravoSoloAverage = splatfestResult.bravoAverages.challenge;
            bravoTeamAverage = splatfestResult.bravoAverages.regular;
        }
        alphaPlayers = splatfestResult.rates.vote.alpha;
        bravoPlayers = splatfestResult.rates.vote.bravo;

        vote = splatfestResult.summary.vote;
        solo = splatfestResult.summary.solo;
        team = splatfestResult.summary.team;
        total = splatfestResult.summary.total;
    }

    public SplatfestResult toDeserialized(){
        SplatfestResult splatfestResult = new SplatfestResult();
        splatfestResult.id = id;
        splatfestResult.version = version;

        splatfestResult.rates = new SplatfestRates();
        if(version==1){
            splatfestResult.rates.solo = new Rates();
            splatfestResult.rates.solo.alpha = alphaSolo;
            splatfestResult.rates.solo.bravo = bravoSolo;

            splatfestResult.rates.team = new Rates();
            splatfestResult.rates.team.alpha = alphaTeam;
            splatfestResult.rates.team.bravo = bravoTeam;
        }else{
            splatfestResult.rates.challenge = new Rates();
            splatfestResult.rates.challenge.alpha = alphaSolo;
            splatfestResult.rates.challenge.bravo = bravoSolo;

            splatfestResult.rates.regular = new Rates();
            splatfestResult.rates.regular.alpha = alphaTeam;
            splatfestResult.rates.regular.bravo = bravoTeam;


            splatfestResult.alphaAverages = new SplatfestContribution();
            splatfestResult.alphaAverages.regular = alphaTeamAverage;
            splatfestResult.alphaAverages.challenge = alphaSoloAverage;

            splatfestResult.bravoAverages = new SplatfestContribution();
            splatfestResult.bravoAverages.regular = bravoTeamAverage;
            splatfestResult.bravoAverages.challenge = bravoSoloAverage;
        }
        splatfestResult.rates.vote = new Rates();
        splatfestResult.rates.vote.alpha = alphaPlayers;
        splatfestResult.rates.vote.bravo = bravoPlayers;

        splatfestResult.summary = new SplatfestSummary();
        splatfestResult.summary.vote = vote;
        splatfestResult.summary.solo = solo;
        splatfestResult.summary.team = team;
        splatfestResult.summary.total = total;
        return splatfestResult;
    }
}

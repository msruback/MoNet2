package com.mattrubacky.monet2.data.entity;

import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestColor;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestColors;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestImages;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestNames;
import com.mattrubacky.monet2.data.deserialized.splatoon.SplatfestTimes;
import com.mattrubacky.monet2.data.deserialized_entities.Stage;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "splatfest",
        foreignKeys = {
                @ForeignKey(entity = Stage.class,
                        parentColumns = "stage_id",
                        childColumns = "splatfest_stage")
        },
        indices = {
                @Index(name="splatfest_stage",
                        value = "splatfest_stage")
        })
public class SplatfestRoom {
    @PrimaryKey
    @ColumnInfo(name="splatfest_id")
    public int id;

    @ColumnInfo(name = "splatfest_stage")
    public Stage stage;
    @ColumnInfo(name = "announce_time")
    public long announced;
    @ColumnInfo(name = "start_time")
    public long start;
    @ColumnInfo(name = "end_time")
    public long end;
    @ColumnInfo(name = "result_time")
    public long results;
    @ColumnInfo(name = "panel_url")
    public String panelUrl;

    @ColumnInfo(name = "alpha_name")
    public String alphaName;
    @ColumnInfo(name = "alpha_long_name")
    public String alphaLongName;
    @ColumnInfo(name = "alpha_color")
    public String alphaColor;
    @ColumnInfo(name = "alpha_url")
    public String alphaUrl;

    @ColumnInfo(name = "bravo_name")
    public String bravoName;
    @ColumnInfo(name = "bravo_long_name")
    public String bravoLongName;
    @ColumnInfo(name = "bravo_color")
    public String bravoColor;
    @ColumnInfo(name = "bravo_url")
    public String bravoUrl;

    public SplatfestRoom(int id, Stage stage, long announced, long start, long end, long results, String panelUrl,
                         String alphaName, String alphaLongName, String alphaColor, String alphaUrl,
                         String bravoName, String bravoLongName, String bravoColor, String bravoUrl){
        this.id = id;
        this.stage = stage;
        this.announced = announced;
        this.start = start;
        this.end = end;
        this.results = results;
        this.panelUrl = panelUrl;

        this.alphaName = alphaName;
        this.alphaLongName = alphaLongName;
        this.alphaColor = alphaColor;
        this.alphaUrl = alphaUrl;

        this.bravoName = bravoName;
        this.bravoLongName = bravoLongName;
        this.bravoColor = bravoColor;
        this.bravoUrl = bravoUrl;
    }

    @Ignore
    public SplatfestRoom(Splatfest splatfest){
        this.id = splatfest.id;
        this.stage = splatfest.stage;

        this.announced = splatfest.times.announce;
        this.start = splatfest.times.start;
        this.end = splatfest.times.end;
        this.results = splatfest.times.result;

        this.panelUrl = splatfest.images.panel;
        this.alphaUrl = splatfest.images.alpha;
        this.bravoUrl = splatfest.images.bravo;

        this.alphaName = splatfest.names.alpha;
        this.alphaLongName = splatfest.names.alphaDesc;
        this.bravoName = splatfest.names.bravo;
        this.bravoLongName = splatfest.names.bravoDesc;

        this.alphaColor = splatfest.colors.alpha.color;
        this.bravoColor = splatfest.colors.bravo.color;
    }

    public Splatfest toDeserialized(List<Stage> stages){
        Splatfest splatfest = new Splatfest();
        splatfest.id = id;
        for(Stage stage:stages) {
            if(this.stage.id==stage.id) {
                splatfest.stage = stage;
            }
        }

        splatfest.times = new SplatfestTimes();
        splatfest.times.announce = announced;
        splatfest.times.start = start;
        splatfest.times.end = end;
        splatfest.times.result = results;

        splatfest.names = new SplatfestNames();
        splatfest.names.alpha = alphaName;
        splatfest.names.alphaDesc = alphaLongName;
        splatfest.names.bravo = bravoName;
        splatfest.names.bravoDesc = bravoLongName;

        splatfest.colors = new SplatfestColors();
        splatfest.colors.alpha = new SplatfestColor();
        splatfest.colors.alpha.color = alphaColor;
        splatfest.colors.bravo = new SplatfestColor();
        splatfest.colors.bravo.color = bravoColor;

        splatfest.images = new SplatfestImages();
        splatfest.images.alpha = alphaUrl;
        splatfest.images.bravo = bravoUrl;
        splatfest.images.panel = panelUrl;

        return splatfest;
    }

}

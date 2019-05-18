package com.mattrubacky.monet2.data.combo;

import androidx.room.Embedded;

import com.mattrubacky.monet2.data.deserialized.splatoon.Splatfest;
import com.mattrubacky.monet2.data.deserialized_entities.Stage;
import com.mattrubacky.monet2.data.entity.SplatfestRoom;

public class SplatfestStageCombo {
    @Embedded
    public SplatfestRoom splatestRoom;
    @Embedded
    public Stage stage;

    public Splatfest toDeserialized(){
        Splatfest splatfest = splatestRoom.toDeserialized();
        splatfest.stage = stage;
        return splatfest;
    }
}

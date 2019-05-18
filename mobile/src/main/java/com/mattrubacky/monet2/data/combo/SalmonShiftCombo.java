package com.mattrubacky.monet2.data.combo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mattrubacky.monet2.data.deserialized.splatoon.SalmonRunDetail;
import com.mattrubacky.monet2.data.deserialized_entities.SalmonRunWeapon;
import com.mattrubacky.monet2.data.deserialized_entities.SalmonStage;
import com.mattrubacky.monet2.data.entity.SalmonShiftRoom;

import java.util.ArrayList;
import java.util.List;

public class SalmonShiftCombo {
    @Embedded
    public SalmonShiftRoom salmonShiftRoom;

    @Embedded
    public SalmonStage salmonStage;

    @Relation(parentColumn = "shift_id",entityColumn="weapon_shift_id")
    public List<SalmonRunWeapon> weapons;

    public SalmonRunDetail toDeserialised(){
        SalmonRunDetail salmonRunDetail = salmonShiftRoom.toDeserialisedDetail();
        salmonRunDetail.stage = salmonStage;
        salmonRunDetail.weapons = new ArrayList<>(weapons);
        return salmonRunDetail;
    }
}

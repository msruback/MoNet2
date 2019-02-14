package com.mattrubacky.monet2.modeledobjects;

import com.google.gson.Gson;
import com.mattrubacky.monet2.deserialized.splatoon.Stage;
import com.mattrubacky.monet2.rooms.entity.StageRoom;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class StageTests extends ModeledObjectTest {

    @Test
    public void toDeserialized(){
        try {
            String[] stageCsv = getCSV("stage_1.csv");

            StageRoom stageRoom = new StageRoom(Integer.valueOf(stageCsv[2]),stageCsv[1], stageCsv[0]);

            Stage stage = stageRoom.toDeserialized();

            assertEquals((int) Integer.valueOf(stageCsv[2]),stage.id);
            assertEquals(stageCsv[1],stage.url);
            assertEquals(stageCsv[0],stage.name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toRoom(){
        try{
            Gson gson = new Gson();
            Stage stage = gson.fromJson(getJSON("stage_1.json"),Stage.class);

            StageRoom stageRoom = stage.toRoom();

            assertEquals(stage.id,stageRoom.id);
            assertEquals(stage.url,stageRoom.url);
            assertEquals(stage.name,stageRoom.name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

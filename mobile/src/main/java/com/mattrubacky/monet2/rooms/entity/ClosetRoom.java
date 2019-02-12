package com.mattrubacky.monet2.rooms.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "closet",
        foreignKeys = {
                @ForeignKey(entity = GearRoom.class,
                        parentColumns = "id",
                        childColumns = "gear"
                ),
                @ForeignKey(entity = SkillRoom.class,
                        parentColumns = "id",
                        childColumns = "main"
                ),
                @ForeignKey(entity = SkillRoom.class,
                        parentColumns = "id",
                        childColumns = "fr_sub"
                ),
                @ForeignKey(entity = SkillRoom.class,
                        parentColumns = "id",
                        childColumns = "sc_sub"
                ),
                @ForeignKey(entity = SkillRoom.class,
                        parentColumns = "id",
                        childColumns = "tr_sub"
                )
        })
public class ClosetRoom {
    public int gear;
    public int main;
    @ColumnInfo(name = "fr_sub")
    public int sub1;
    @ColumnInfo(name = "sc_sub")
    public int sub2;
    @ColumnInfo(name = "tr_sub")
    public int sub3;

    public ClosetRoom(int gear, int main, int sub1, int sub2, int sub3){
        this.gear = gear;
        this.main = main;
        this.sub1 = sub1;
        this.sub2 = sub2;
        this.sub3 = sub3;
    }
}

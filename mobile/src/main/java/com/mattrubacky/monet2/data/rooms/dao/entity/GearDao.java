package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.combo.GearBrand;
import com.mattrubacky.monet2.data.deserialized_entities.Gear;
import com.mattrubacky.monet2.ui.adapter.RecyclerView.GearAdapter;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class GearDao {
    void insertGear(Gear gear,boolean isDefault,BrandDao brandDao,SkillDao skillDao){
        brandDao.insertBrand(gear.brand,skillDao);
        gear.generatedId = Gear.generateId(gear.kind,gear.id);
        try{
            insert(gear);
        }catch(SQLiteConstraintException e){
            e.printStackTrace();
            if(isDefault){
                update(gear);
            }
        }
    }

    @Insert
    protected abstract void insert(Gear... gear);

    @Update
    protected abstract void update(Gear... gear);

    @Delete
    protected abstract void delete(Gear... gear);

    @Query("SELECT * FROM gear JOIN brand ON gear_brand = brand_id LEFT JOIN skill ON brand_skill = skill_id WHERE gear_id=:id")
    public abstract LiveData<GearBrand> select(int id);

    @Query("SELECT * FROM gear JOIN brand ON gear_brand = brand_id LEFT JOIN skill ON brand_skill = skill_id WHERE splatnet_id=:id AND kind=:kind")
    public abstract LiveData<GearBrand> select(int id, String kind);

    @Query("SELECT * FROM gear JOIN brand ON gear_brand = brand_id LEFT JOIN skill ON brand_skill = skill_id WHERE kind='head'")
    public abstract LiveData<List<GearBrand>> selectHead();

    @Query("SELECT * FROM gear JOIN brand ON gear_brand = brand_id LEFT JOIN skill ON brand_skill = skill_id WHERE kind='clothes'")
    public abstract LiveData<List<GearBrand>> selectClothes();

    @Query("SELECT * FROM gear JOIN brand ON gear_brand = brand_id LEFT JOIN skill ON brand_skill = skill_id WHERE kind='shoe'")
    public abstract LiveData<List<GearBrand>> selectShoes();

    @Query("SELECT * FROM gear JOIN brand ON gear_brand = brand_id JOIN skill ON brand_skill = skill_id WHERE gear_id IN(:ids)")
    public abstract LiveData<List<GearBrand>> selectList(int[] ids);
}

package com.mattrubacky.monet2.data.rooms.dao.entity;

import android.database.sqlite.SQLiteConstraintException;

import com.mattrubacky.monet2.data.deserialized.splatoon.Ordered;
import com.mattrubacky.monet2.data.deserialized.splatoon.Product;
import com.mattrubacky.monet2.data.entity.ProductRoom;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class ProductDao {

    void insertProduct(Product product, GearDao gearDao, BrandDao brandDao, SkillDao skillDao){
        skillDao.insertSkill(product.skill);
        gearDao.insertGear(product.gear,true,brandDao,skillDao);
        try{
            insert(new ProductRoom(product));
        } catch(SQLiteConstraintException e){
        }
    }

    void insertProduct(Ordered ordered, GearDao gearDao, BrandDao brandDao,SkillDao skillDao){
        skillDao.insertSkill(ordered.skill);
        gearDao.insertGear(ordered.gear,true,brandDao,skillDao);
        try{
            insert(new ProductRoom(ordered));
        } catch(SQLiteConstraintException e){
            update(new ProductRoom(ordered));
        }
    }

    @Insert
    abstract void insert(ProductRoom ...productRooms);
    @Update
    abstract void update(ProductRoom ...productRooms);
    @Delete
    abstract void delete(ProductRoom ...productRooms);

    @Query("SELECT * FROM products WHERE id!='-1'")
    abstract List<ProductRoom> selectMerch();

    @Query("SELECT * FROM products WHERE id!='-1' AND endTime<:now")
    abstract List<ProductRoom> selectOld(long now);

    @Query("SELECT * FROM products WHERE id=='-1'")
    abstract ProductRoom selectOrdered();
}

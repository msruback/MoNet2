package com.mattrubacky.monet2.sqlite.Factory;

import android.content.ContentValues;
import android.database.Cursor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mattr on 10/16/2018.
 */

public class DatabaseObjectFactory {
    public DatabaseObjectFactory(){}
    public static <T> T parseObject(T toReturn,Cursor cursor){
        try {
            for (Field field : toReturn.getClass().getFields()) {
                if (field.isAnnotationPresent(ColumnName.class)) {
                    ColumnName columnName = field.getAnnotation(ColumnName.class);
                    if(columnName.field().equals("")) {
                        if (field.getType().equals(int.class)) {
                            field.set(toReturn, cursor.getInt(cursor.getColumnIndex(columnName.value())));
                        } else if (field.getType().equals(Long.class)) {
                            field.set(toReturn, cursor.getLong(cursor.getColumnIndex(columnName.value())));
                        } else if (field.getType().equals(Float.class)) {
                            field.set(toReturn, cursor.getFloat(cursor.getColumnIndex(columnName.value())));
                        } else if (field.getType().equals(String.class)) {
                            field.set(toReturn, cursor.getString(cursor.getColumnIndex(columnName.value())));
                        }
                    }else{
                        Object objectToInsert = field.getType().newInstance();
                        Field subField = field.getType().getField(columnName.field());
                        if (subField.getType().equals(int.class)) {
                            subField.set(objectToInsert, cursor.getInt(cursor.getColumnIndex(columnName.value())));
                        } else if (subField.getType().equals(Long.class)) {
                            subField.set(objectToInsert, cursor.getLong(cursor.getColumnIndex(columnName.value())));
                        } else if (subField.getType().equals(Float.class)) {
                            subField.set(toReturn, cursor.getFloat(cursor.getColumnIndex(columnName.value())));
                        } else if (subField.getType().equals(String.class)) {
                            subField.set(objectToInsert, cursor.getString(cursor.getColumnIndex(columnName.value())));
                        }
                        field.set(toReturn,objectToInsert);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    public static ContentValues storeObject(Object object,ContentValues values){
        try {
            ArrayList<Field> fields = new ArrayList<>();
            fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
            for (Field field : fields) {
                if (field.isAnnotationPresent(ColumnName.class)) {
                    Object data;
                    String key;
                    ColumnName columnName = field.getAnnotation(ColumnName.class);
                    key = columnName.value();
                    if (columnName.field().equals("")) {
                        data = field.get(object);
                    } else {
                        data = field.getClass().getField(columnName.field()).get(field.get(object));
                        if(data == null){
                            data = -1;
                        }
                    }
                    values = writeValue(values, key, data);
                }
            }
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        }
        return values;
    }
    static private ContentValues writeValue(ContentValues values, String key, Object data){
        if (data.getClass() == int.class){
            values.put(key,int.class.cast(data));
        } else if(data.getClass() == String.class){
            values.put(key,String.class.cast(data));
        } else if(data.getClass() == long.class){
            values.put(key,long.class.cast(data));
        } else if(data.getClass() == float.class){
            values.put(key,float.class.cast(data));
        }
        return values;
    }
}

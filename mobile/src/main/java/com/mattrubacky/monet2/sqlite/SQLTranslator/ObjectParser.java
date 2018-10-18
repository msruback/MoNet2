package com.mattrubacky.monet2.sqlite.SQLTranslator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mattrubacky.monet2.sqlite.Factory.ColumnName;
import com.mattrubacky.monet2.sqlite.SplatnetSQLHelper;
import com.mattrubacky.monet2.sqlite.Factory.TableName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by mattr on 10/10/2018.
 */

public class ObjectParser {
    private Context context;
    public ObjectParser(Context context){
        this.context = context;
    }

    public void writeToDatabase(Object object) throws IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        ArrayList<Field> fields = new ArrayList<>();
        ArrayList<Method> methods = new ArrayList<>();
        SQLiteDatabase database = new SplatnetSQLHelper(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        if(object.getClass().isAnnotationPresent(TableName.class)) {
            TableName annotation = object.getClass().getAnnotation(TableName.class);
            String tableName = annotation.value();
            fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
            for(Field field : fields){
                if(field.isAnnotationPresent(ColumnName.class)) {
                    if (field.isAnnotationPresent(ReferenceField.class)) {
                        writeToDatabase(field.get(object));
                    } else if (field.isAnnotationPresent(ForiegnColumn.class)) {
                        Object data;
                        String key;
                        ColumnName columnName = field.getAnnotation(ColumnName.class);
                        Object foriegnObject = field.get(object);

                        data = foriegnObject.getClass().getDeclaredField(columnName.field()).get(foriegnObject);
                        key = columnName.value();

                        writeToDatabase(foriegnObject);

                        values = writeValue(values, key, data);
                    } else {
                        Object data;
                        String key;
                        ColumnName columnName = field.getAnnotation(ColumnName.class);
                        key = columnName.value();
                        if (columnName.field().equals("")) {
                            data = field.getClass().getDeclaredField(columnName.field()).get(field);
                        } else {
                            data = field.get(object);
                        }
                        values = writeValue(values, key, data);
                    }
                }
            }
            methods.addAll(Arrays.asList(object.getClass().getDeclaredMethods()));
            for(Method method : methods){
                if(method.isAnnotationPresent(ColumnInput.class)){
                    String key = method.getAnnotation(ColumnInput.class).value();
                    Object data = method.invoke(object);
                    values = writeValue(values, key, data);
                }
            }
            database.insert(tableName,null,values);
        }

    }

//    public <T> T readFromDatabase(Class<T> toMake,int reference) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
//        HashMap<String,SQLStatement> sqlStatements = buildSql(toMake,new HashMap<String, SQLStatement>());
//        for(String key : sqlStatements.keySet()){
//            SQLStatement sqlStatement = sqlStatements.get(key);
//            String statement = sqlStatement.getStatement() +" WHERE "+sqlStatement.getReference()+ " = "+String.valueOf(reference);
//            sqlStatements.put(key,new SQLStatement(statement));
//        }
//
//    }

    private HashMap<String,SQLStatement> buildSql(Class toMake,HashMap<String,SQLStatement> toReturn) throws NoSuchFieldException, IllegalAccessException {
        StringBuilder builder = new StringBuilder();
        if(toMake.isAnnotationPresent(TableName.class)) {
            String tableName = ((TableName)toMake.getAnnotation(TableName.class)).value();
            builder.append("SELECT * FROM ");
            builder.append(tableName);
            builder.append(" ");
            if (toMake.isAnnotationPresent(TableName.class)) {
                ArrayList<Field> joins = new ArrayList<>();
                ArrayList<Field> fieldAdds = new ArrayList<>();
                ArrayList<Method> adds = new ArrayList<>();
                for (Field field : toMake.getFields()) {
                    if(field.isAnnotationPresent(ColumnName.class)&&!field.isAnnotationPresent(ColumnInput.class)) {
                        if (field.isAnnotationPresent(ReferenceField.class)) {
                            fieldAdds.add(field);
                        } else if (field.isAnnotationPresent(ForiegnColumn.class)) {
                            joins.add(field);
                        }
                    }
                }
                for(Method method : toMake.getMethods()){
                    if(method.isAnnotationPresent(ColumnName.class)){
                        if(method.isAnnotationPresent(ReferenceField.class)&&method.isAnnotationPresent(ColumnInput.class)){
                            adds.add(method);
                        }
                    }
                }
                for(Field field : joins){
                    if(field.getClass().isAnnotationPresent(TableName.class)) {
                        ColumnName columnName = field.getAnnotation(ColumnName.class);
                        String joinName = ((TableName)field.getClass().getAnnotation(TableName.class)).value();
                        builder.append("LEFT JOIN ");
                        builder.append(joinName);
                        builder.append(" ON ");
                        builder.append(tableName);
                        builder.append(".");
                        builder.append(columnName.value());
                        builder.append(" = ");
                        builder.append(joinName);
                        builder.append(".");
                        builder.append(((ColumnName)field.getClass().getDeclaredField(columnName.field()).getAnnotation(ColumnName.class)).value());
                        builder.append(" ");
                    }
                }
                for(Method method : adds){
                    Class[] types = method.getParameterTypes();
                    ColumnName columnName = method.getAnnotation(ColumnName.class);
                    if(types.length>0){
                        Class parameter = types[0];
                        if(parameter.equals(ArrayList.class)){
                            parameter = (Class) ((ParameterizedType) parameter.getGenericSuperclass()).getActualTypeArguments()[0];
                        }
                        String addName = ((TableName)parameter.getAnnotation(TableName.class)).value();
                        if(parameter.isAnnotationPresent(TableName.class)){
                            toReturn = buildSql(parameter,toReturn);
                            SQLStatement statement = toReturn.get(addName);
                            statement.setReference(columnName.field());
                        }
                    }
                }
            }
            if(!toReturn.containsKey(tableName)){
                toReturn.put(tableName,new SQLStatement(builder.toString()));
            }
        }
        return toReturn;
    }
    private <T> T parseObject(Class<T> toMake, Cursor cursor) throws IllegalAccessException, InstantiationException {
        T toReturn = toMake.newInstance();
        for(Field field : toMake.getFields()){
            if(field.isAnnotationPresent(ColumnName.class)){
                ColumnName columnName = field.getAnnotation(ColumnName.class);
                if(field.getType().equals(int.class)){
                    field.set(toReturn,cursor.getInt(cursor.getColumnIndex(columnName.value())));
                }else if(field.getClass().equals(Long.class)){
                    field.set(toReturn,cursor.getLong(cursor.getColumnIndex(columnName.value())));
                }else if(field.getClass().equals(Float.class)){
                    field.set(toReturn,cursor.getFloat(cursor.getColumnIndex(columnName.value())));
                }else if(field.getClass().equals(String.class)){
                    field.set(toReturn,cursor.getString(cursor.getColumnIndex(columnName.value())));
                }
            }
        }
        return toReturn;
    }

    private ContentValues writeValue(ContentValues values, String key, Object data){
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

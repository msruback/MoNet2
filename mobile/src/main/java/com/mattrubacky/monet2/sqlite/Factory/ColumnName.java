package com.mattrubacky.monet2.sqlite.Factory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mattr on 10/9/2018.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnName {
    String value();
    //For nonstandard objects, the field in the object
    String field() default "";
}

package com.vasax.clothes.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by oper4 on 07.11.2014.
 */
public abstract class AbstractGeneric<T> {

    private Class<Type> type;

    public AbstractGeneric() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType)t;
        type = (Class)pt.getActualTypeArguments()[0];
    }
}

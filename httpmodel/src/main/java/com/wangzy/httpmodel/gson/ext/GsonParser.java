package com.wangzy.httpmodel.gson.ext;

import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import ikidou.reflect.TypeBuilder;

/**
 * Created by wangzy on 2018/3/27.
 */

public class GsonParser {


    public static <T> Result<T> fromJsonObject(Reader reader, Class<T> clazz) {
        Type type = TypeBuilder
                .newInstance(Result.class)
                .addTypeParam(clazz)
                .build();

        Gson gson=new Gson();
        return gson.fromJson(reader, type);

    }



    public static <T> Result<List<T>> fromJsonArray(Reader reader, Class<T> clazz) {
        Type type = TypeBuilder
                .newInstance(Result.class)
                .beginSubType(List.class)
                .addTypeParam(clazz)
                .endSubType()
                .build();
        Gson gson=new Gson();
        return gson.fromJson(reader, type);

    }
}

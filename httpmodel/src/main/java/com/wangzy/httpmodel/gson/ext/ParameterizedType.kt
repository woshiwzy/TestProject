package com.wangzy.httpmodel.gson.ext

import java.lang.reflect.Type

interface ParameterizedType : Type {
    // 返回Map<String,User>里的String和User，所以这里返回[String.class,User.clas]
    val actualTypeArguments: Array<Type>
    // Map<String,User>里的Map,所以返回值是Map.class
    val rawType: Type
    // 用于这个泛型上中包含了内部类的情况,一般返回null
    val ownerType: Type
}
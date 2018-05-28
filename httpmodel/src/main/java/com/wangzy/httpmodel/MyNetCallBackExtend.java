package com.wangzy.httpmodel;

import com.wangzy.httpmodel.gson.ext.GsonParser;
import com.wangzy.httpmodel.gson.ext.Result;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by wangzy on 2018/3/28.
 */

public class  MyNetCallBackExtend<T> extends MyNetCallBack{

    private Class claz;
    private boolean isArray;

    private Type type;

    public MyNetCallBackExtend(Class claz,boolean isArray){
        this.claz=claz;
        this.isArray=isArray;
    }

    public MyNetCallBackExtend(Type type){
        this.type=type;
    }



    @Override
    public void  onSuccessResponseText(String responseText, Call call) {

        if(isArray){
            Result<List<T>> object = GsonParser.fromJsonArray(new StringReader(responseText), claz);
            onResponseResultList(object);
        }else {
            Result<T> object = GsonParser.fromJsonObject(new StringReader(responseText), claz);
            onResponseReslt(object);
        }
    }

    @Override
    public void onSucessResponsePureText(String responseText) {

    }

    @Override
    public void onSuccessFinish(Call call, String response) {

    }

    public void onResponseResultList(Result<List<T>> result){

    }

    public void onResponseReslt(Result<T> result){


    }
}

package com.wangzy.httpmodel;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.internal.util.LogUtil;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.wangzy.httpmodel.domain.Server;
import com.wangzy.httpmodel.gson.ext.GsonParser;
import com.wangzy.httpmodel.gson.ext.Result;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    public static String readAssetsTxt(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer, "utf-8");
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }

    @Test
    public void testParseData() throws Exception {


        final String tag = "test";

//        final String ret = readAssetsTxt(InstrumentationRegistry.getTargetContext(), "demo.json");
//        Log.i(tag, ret);

//        Result<List<Server>> rets = GsonParser.fromJsonArray(new StringReader(ret),Server.class);
//        Result<Server> server = GsonParser.fromJsonObject(new StringReader(ret), Server.class);
//        Log.i(tag, "  " + rets.message);


        NetCallTest.login("sand","123",new MyNetCallBackExtend<Server>(Server.class,true){


//            @Override
//            public void onSuccessResponseText(String responseText, Response response, Call call) {
//                Log.i(tag,"123---"+"onSuccessResponseText==========>>>>>>>>>");
//            }
//
//            @Override
//            public void onStart(Call call) {
//                Log.i(tag,"123---"+"start==========>>>>>>>>>");
//            }
//
//            @Override
//            public void onFailureFinish(Call call, Exception e) {
//                Log.i(tag,"123---"+"fail==========>>>>>>>>>");
//            }

            @Override
            public void onResponseReslt(Result<Server> result) {
                Log.i(tag,"123---"+result.message);
            }
            @Override
            public void onResponseResultList(Result<List<Server>> result) {
                Log.i(tag,"123---"+result.message);
            }
        });





    }


    @Test
    public void useAppContext() throws Exception {


        String json = "{\"a\":{\"b\":\"vvvv\"}}";
        String ret = JsonSelector.getJsonObject(json, "a->b");
        Log.i("t", ret);


        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.wangzy.httpmodel", appContext.getPackageName());
    }
}

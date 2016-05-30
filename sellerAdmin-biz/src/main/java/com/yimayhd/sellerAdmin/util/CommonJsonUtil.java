package com.yimayhd.sellerAdmin.util;

import com.google.gson.Gson;
import java.lang.reflect.Type;
/**
 * Created by wangdi on 16/5/26.
 */
public class CommonJsonUtil {

    public static String objectToJson(Object model,Class<?> classType){

        Gson gson=new Gson();

        return gson.toJson(model,classType);

    }
    public static Object  jsonToListObject(String json, Type classType){

        Gson gson=new Gson();

        return gson.fromJson(json,classType);

    }

    public static Object  jsonToObject(String json,Class<?> classType){

        Gson gson=new Gson();

        return gson.fromJson(json,classType);

    }

}

package com.jo.cch.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class JsonUtils {


    public Map<String, String> getJsonData (Context context, String fileName, String key){
        Map<String, String> rs = null;
        BufferedReader br = null;
        InputStreamReader is = null;
        try {
            AssetManager assetManager = context.getAssets(); //获得assets资源管理器（assets中的文件无法直接访问，可以使用AssetManager访问）
            is = new InputStreamReader(assetManager.open("data/"+fileName),"UTF-8"); //使用IO流读取json文件内容
            br = new BufferedReader(is);//使用字符高效流
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine())!=null){
                builder.append(line);
            }
            br.close();
            is.close();
            List<Map<String, String>> jsonList = new Gson().fromJson(builder.toString(), new TypeToken<List<Map<String, String>>>(){}.getType());//把JSON格式
            for (Map<String, String> json : jsonList){
                if(json.get("word").equals(key)){
                    rs = json;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                br = null;
            }
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                is = null;
            }
        }
        return rs;
    }
}

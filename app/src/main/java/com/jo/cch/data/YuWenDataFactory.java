package com.jo.cch.data;

import java.util.HashMap;
import java.util.Map;

public class YuWenDataFactory {

    private static Map<String, YuWenData> yuWenMap = new HashMap<>();

    static {
        yuWenMap.put("一年级-上册", new OneUBooKData());
        yuWenMap.put("一年级-下册", new OneDBooKData());

        yuWenMap.put("二年级-上册", new TowUBooKData());
        yuWenMap.put("二年级-下册", new TowDBooKData());

        yuWenMap.put("三年级-上册", new ThreeUBooKData());
        yuWenMap.put("三年级-下册", new ThreeDBooKData());

        yuWenMap.put("四年级-上册", new FourUBooKData());
        yuWenMap.put("四年级-下册", new FourDBooKData());

        yuWenMap.put("五年级-上册", new FiveUBooKData());
        yuWenMap.put("五年级-下册", new FiveDBooKData());

        yuWenMap.put("六年级-上册", new SixUBooKData());
        yuWenMap.put("六年级-下册", new SixDBooKData());

    }

    public static YuWenData getYuWenData(String grade, String book){
        return yuWenMap.get(grade+"-"+book);
    }
}

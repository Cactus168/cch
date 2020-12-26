package com.jo.cch.data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class FiveDBooKData extends YuWenData {

    @Override
    public TreeMap<Integer, String> getCourses() {
        //初始化课程
        TreeMap<Integer, String> courseMap = new TreeMap<>();

        return courseMap;
    }

    @Override
    public Map<Integer, String> getXzNWords() {
        //课程对应的初始化写字表
        Map<Integer, String> xzNWordMap = new HashMap<>();

        return xzNWordMap;
    }

    @Override
    public Map<Integer, String> getSzNWords() {
        //课程对应的初始化识字表
        Map<Integer, String> szNWordMap = new HashMap<>();

        return szNWordMap;
    }

    @Override
    public int getHighLightWordNum() {
        return 4;
    }
}

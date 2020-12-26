package com.jo.cch.data;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.utils.DataUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class YuWenData {
    //课程
    public abstract TreeMap<Integer, String> getCourses();
    //key：课程序号， value：写字表
    public abstract Map<Integer, String> getXzNWords();
    //key：课程序号， value：识字表
    public abstract Map<Integer, String> getSzNWords();
    //识字表中高亮字个数
    public abstract int getHighLightWordNum();

    public int getAWordNum(){
        return getXWordNum()+getSWordNum();
    }

    public int getXWordNum(){
        return getXWords().size();
    }

    public int getSWordNum(){
        return getSWords().size()-getHighLightWordNum();
    }

    public List<String> getCourseList(){
        List<String> courseList = new ArrayList<>();
        for(Map.Entry<Integer, String> c : getCourses().entrySet()){
            courseList.add(c.getValue());
        }
        return courseList;
    }

    public List<WordInfo> getWords(){
        List<WordInfo> rsList = new ArrayList<>();
        Set<String> wordSet = new HashSet<>();
        wordSet.addAll(getXWords());
        wordSet.addAll(getSWords());
        for(String s : wordSet){
            rsList.add(new WordInfo(s));
        }
        return rsList;
    }

    private List<String> getXWords(){
        List<String> xWordList = new ArrayList<>();
        for(Map.Entry<Integer, String> x : getXzNWords().entrySet()){
            xWordList.addAll(Arrays.asList(DataUtils.toStringArray(x.getValue())));
        }
        return xWordList;
    }

    private List<String> getSWords(){
        List<String> sList = new ArrayList<>();
        for(Map.Entry<Integer, String> s : getSzNWords().entrySet()){
            sList.addAll(Arrays.asList(DataUtils.toStringArray(s.getValue())));
        }
        return sList;
    }
}

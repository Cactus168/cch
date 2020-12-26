package com.jo.cch.helper;

import android.content.Context;
import android.text.TextUtils;

import com.jo.cch.R;
import com.jo.cch.bean.LetterInfo;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.db.SpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LetterDataHelper {

    public static String key = "letterError";

    public static Map<String, String> letterWordMap = new HashMap<>();

    public static Map<String,Map<String, List<LetterInfo>>> datas = new HashMap<String,Map<String, List<LetterInfo>>>();

    public static String[] dym = {"a","o","e","i","u","ü"};//6

    public static String[] sm = {"b","p","m","f","d","t","n","l","g","k","h","j","q","x","zh","ch","sh","r","z","c","s","y","w"};//23

    public static String[] fym = {"ai","ei","ui","ao","ou","iu","ie","üe","er"};

    public static String[] qbym = {"an","en","in","un","ün"};

    public static String[] hbym = {"ang","eng","ing","ong"};

    public static String[] zqrdyj = {"zhi","chi","shi","ri","zi","ci","si","yi","wu","yu","ye","yue","yuan","yin","yun","ying"};

    static {
        //声母
        Map<String, List<LetterInfo>> smMap = new HashMap<>();
        smMap.put("声母", createLetterList(sm));
        datas.put("声母", smMap);
        //韵母
        Map<String, List<LetterInfo>> ymMap = new HashMap<>();
        ymMap.put("单韵母", createLetterList(dym));
        ymMap.put("复韵母", createLetterList(fym));
        ymMap.put("前鼻韵母", createLetterList(qbym));
        ymMap.put("后鼻韵母", createLetterList(hbym));
        datas.put("韵母", ymMap);
        //整体认读音节
        Map<String, List<LetterInfo>> zqrdyjMap = new HashMap<>();
        zqrdyjMap.put("整体认读音节", createLetterList(zqrdyj));
        datas.put("整体认读音节", zqrdyjMap);
        //易错
        Map<String, List<LetterInfo>> ycMap = new HashMap<>();
        zqrdyjMap.put("易错", createLetterList(zqrdyj));
        datas.put("易错", zqrdyjMap);

        letterWordMap.put("a", "a");
        letterWordMap.put("o", "wo");
        letterWordMap.put("e", "e");
        letterWordMap.put("i", "yi");
        letterWordMap.put("u", "wu");
        letterWordMap.put("ü", "yu");

        letterWordMap.put("b", "bo");
        letterWordMap.put("p", "po");
        letterWordMap.put("m", "mo");
        letterWordMap.put("f", "fo");
        letterWordMap.put("d", "de");
        letterWordMap.put("t", "te");
        letterWordMap.put("n", "ne");
        letterWordMap.put("l", "le");
        letterWordMap.put("g", "ge");
        letterWordMap.put("k", "ke");
        letterWordMap.put("h", "he");
        letterWordMap.put("j", "ji");
        letterWordMap.put("q", "qi");
        letterWordMap.put("x", "xi");
        letterWordMap.put("zh", "zhi");
        letterWordMap.put("ch", "chi");
        letterWordMap.put("sh", "shi");
        letterWordMap.put("r", "ri");
        letterWordMap.put("z", "zi");
        letterWordMap.put("c", "ci");
        letterWordMap.put("s", "si");
        letterWordMap.put("y", "yi");
        letterWordMap.put("w", "wu");

        letterWordMap.put("ai", "ai");
        letterWordMap.put("ei", "ei");
        letterWordMap.put("ui", "wei");
        letterWordMap.put("ao", "ao");
        letterWordMap.put("ou", "ou");
        letterWordMap.put("iu", "you");
        letterWordMap.put("ie", "ye");
        letterWordMap.put("üe", "yue");
        letterWordMap.put("er", "er");

        letterWordMap.put("an", "an");
        letterWordMap.put("en", "en");
        letterWordMap.put("in", "yin");
        letterWordMap.put("un", "wen");
        letterWordMap.put("ün", "yun");

        letterWordMap.put("ang", "ang");
        letterWordMap.put("eng", "en");
        letterWordMap.put("ing", "ying");
        letterWordMap.put("ong", "weng");

        letterWordMap.put("zhi", "zhi");
        letterWordMap.put("chi", "chi");
        letterWordMap.put("shi", "shi");
        letterWordMap.put("ri", "ri");
        letterWordMap.put("zi", "zi");
        letterWordMap.put("ci", "ci");
        letterWordMap.put("si", "si");
        letterWordMap.put("yi", "yi");
        letterWordMap.put("wu", "wu");
        letterWordMap.put("yu", "yu");
        letterWordMap.put("ye", "ye");
        letterWordMap.put("yue", "yue");
        letterWordMap.put("yuan", "yuan");
        letterWordMap.put("yin", "yin");
        letterWordMap.put("yun", "yun");
        letterWordMap.put("ying", "ying");


    }

    public static List<LetterInfo> getAllDatas(){
        List<LetterInfo> datas = createLetterList(dym);
        datas.addAll(createLetterList(sm));
        datas.addAll(createLetterList(fym));
        datas.addAll(createLetterList(qbym));
        datas.addAll(createLetterList(hbym));
        datas.addAll(createLetterList(zqrdyj));
        return datas;
    }

    public static List<LetterInfo> getDatas(Context context, String groupKey, String childKey){
        if("易错".equals(childKey)){
            return SpUtil.getInstance(context).getList(key, LetterInfo.class);
        }else{
            return datas.get(groupKey).get(childKey);
        }
    }

    private static List<LetterInfo> createLetterList(String[] letters){
        List<LetterInfo> rs = new ArrayList<>();
        for(String l : letters){
            rs.add(new LetterInfo(l, R.drawable.yesmark, false));
        }
        return rs;
    }

    public static void addErrorWord(Context context, String errorLetter){
        boolean flag = true;
        List<LetterInfo> list = SpUtil.getInstance(context).getList(key,LetterInfo.class);
        for(LetterInfo l : list){
            if(errorLetter.equals(l.getLetter())){
                flag = false;
                break;
            }
        }
        if(flag){
            list.add(new LetterInfo(errorLetter, R.drawable.yesmark,false));
        }
        SpUtil.getInstance(context).saveList(key,list);
    }

    public static String getWordByLetter(String letter){
        return letterWordMap.get(letter);
    }
}

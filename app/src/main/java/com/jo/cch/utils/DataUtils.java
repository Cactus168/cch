package com.jo.cch.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataUtils {

    public static String[] arrayDeduplication(String[] originArray) {
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < originArray.length; i++) {
            set.add(originArray[i]);
        }
        return (String[])set.toArray();
    }

    public static String ListToString(List<String> list){
        StringBuilder sb = new StringBuilder();
        for(String s : list){
            sb.append(s);
            sb.append(",");
        }
        return sb.length() > 0 ? sb.delete(sb.length()-1,sb.length()).toString() : "";
    }

    /**
     * 获取两个数之间的随机数
     * @param i
     * @param j
     * @return
     */
    public static int getRandom(int i, int j){
        int max = i>j?i:j;
        int min = i<j?i:j;
        int mid = max - min;
        int random = (int)(Math.random()*(mid + 1) + min);
        return random;
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source,int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n; //(先计算出余数)
        int number=source.size()/n; //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }

    public static String[] toStringArray(String text){
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        String[] result = new String[text.length()];
        for (int i = 0; i < text.length(); i++) {
            result[i] = String.valueOf(text.charAt(i));
        }
        return result;
    }

}

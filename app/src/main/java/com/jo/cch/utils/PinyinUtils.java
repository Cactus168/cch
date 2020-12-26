package com.jo.cch.utils;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jo.cch.R;
import com.jo.cch.activity.LetterActivity;
import com.jo.cch.activity.PyvoiceActivity;
import com.jo.cch.activity.ShuxuelxActivity;
import com.jo.cch.bean.LetterInfo;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.view.TagGroup;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author: xiaolijuan
 * @description:
 * @projectName: PinyinDome
 * @date: 2016-02-18
 * @time: 10:13
 */
public class PinyinUtils {



    /**
     * 汉字转换位汉语拼音首字母，英文字符不变，特殊字符丢失 支持多音字，生成方式如（长沙市长:cssc,zssz,zssc,cssz）
     *
     * @param chines
     *            汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines) {
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat);
                    if (strs != null) {
                        for (int j = 0; j < strs.length; j++) {
                            // 取首字母
                            pinyinName.append(strs[j].charAt(0));
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                    // else {
                    // pinyinName.append(nameChar[i]);
                    // }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(nameChar[i]);
            }
            pinyinName.append(" ");
        }
        // return pinyinName.toString();
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }

    /**
     * 汉字转换位汉语全拼，英文字符不变，特殊字符丢失
     * 支持多音字，生成方式如（重当参:zhongdangcen,zhongdangcan,chongdangcen
     * ,chongdangshen,zhongdangshen,chongdangcan）
     *
     * @param chines
     *            汉字
     * @return 拼音
     */
    public static String converterToSpell(String chines) {
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // UPPERCASE：大写  (ZHONG)
        // LOWERCASE：小写  (zhong)
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);//输出大写
        // WITHOUT_TONE：无音标  (zhong)
        // WITH_TONE_NUMBER：1-4数字表示音标  (zhong4)
        // WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常）  (zhòng)
        defaultFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        // WITH_V：用v表示ü  (nv)
        // WITH_U_AND_COLON：用"u:"表示ü  (nu:)
        // WITH_U_UNICODE：直接用ü (nü)
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat);
                    if (strs != null) {
                        for (int j = 0; j < strs.length; j++) {
                            pinyinName.append(strs[j]);
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(nameChar[i]);
            }
            pinyinName.append(" ");
        }
        // return pinyinName.toString();
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }

    /**
     * 汉字转换位汉语全拼，英文字符不变，特殊字符丢失, 无音标
     * 支持多音字，生成方式如（重当参:zhongdangcen,zhongdangcan,chongdangcen
     * ,chongdangshen,zhongdangshen,chongdangcan）
     *
     * @param chines
     *            汉字
     * @return 拼音
     */
    public static String converterToSpellx(String chines) {
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // UPPERCASE：大写  (ZHONG)
        // LOWERCASE：小写  (zhong)
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);//输出大写
        // WITHOUT_TONE：无音标  (zhong)
        // WITH_TONE_NUMBER：1-4数字表示音标  (zhong4)
        // WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常）  (zhòng)
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // WITH_V：用v表示ü  (nv)
        // WITH_U_AND_COLON：用"u:"表示ü  (nu:)
        // WITH_U_UNICODE：直接用ü (nü)
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(
                            nameChar[i], defaultFormat);
                    if (strs != null) {
                        for (int j = 0; j < strs.length; j++) {
                            pinyinName.append(strs[j]);
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(nameChar[i]);
            }
            pinyinName.append(" ");
        }
        // return pinyinName.toString();
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }
    /**
     * 去除多音字重复数据
     *
     * @param theStr
     * @return
     */
    private static List<Map<String, Integer>> discountTheChinese(String theStr) {
        // 去除重复拼音后的拼音列表
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
        // 用于处理每个字的多音字，去掉重复
        Map<String, Integer> onlyOne = null;
        String[] firsts = theStr.split(" ");
        // 读出每个汉字的拼音
        for (String str : firsts) {
            onlyOne = new Hashtable<String, Integer>();
            String[] china = str.split(",");
            // 多音字处理
            for (String s : china) {
                Integer count = onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, new Integer(1));
                } else {
                    onlyOne.remove(s);
                    count++;
                    onlyOne.put(s, count);
                }
            }
            mapList.add(onlyOne);
        }
        return mapList;
    }

    /**
     * 解析并组合拼音，对象合并方案(推荐使用)
     *
     * @return
     */
    private static String parseTheChineseByObject(List<Map<String, Integer>> list) {
        Map<String, Integer> first = null; // 用于统计每一次,集合组合数据
        // 遍历每一组集合
        for (int i = 0; i < list.size(); i++) {
            // 每一组集合与上一次组合的Map
            Map<String, Integer> temp = new Hashtable<String, Integer>();
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                for (String s : first.keySet()) {
                    for (String s1 : list.get(i).keySet()) {
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
                // 清理上一次组合数据
                if (temp != null && temp.size() > 0) {
                    first.clear();
                }
            } else {
                for (String s : list.get(i).keySet()) {
                    String str = s;
                    temp.put(str, 1);
                }
            }
            // 保存组合数据以便下次循环使用
            if (temp != null && temp.size() > 0) {
                first = temp;
            }
        }
        String returnStr = "";
        if (first != null) {
            // 遍历取出组合字符串
            for (String str : first.keySet()) {
                returnStr += (str + ",");
            }
        }
        if (returnStr.length() > 0) {
            returnStr = returnStr.substring(0, returnStr.length() - 1);
        }
        return returnStr;
    }

    public static void showDialog(final Context context, final OnCancelClickListener onCancelClickListener, final List<String> errors, final int total, final int yes, final int no, final String selectGroup, final String selectChild){
        final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(context);
        View view1 = View.inflate(context, R.layout.dialog_pinyin_finish, null);
        final RatingBar ratingBar = (RatingBar) view1.findViewById(R.id.ratingBar);
        final TextView tv_total_msg = (TextView) view1.findViewById(R.id.tv_total_msg);
        final TextView tv_yes_msg = (TextView) view1.findViewById(R.id.tv_yes_msg);
        final TextView tv_no_msg = (TextView) view1.findViewById(R.id.tv_no_msg);
        final TagGroup mTagGroup = (TagGroup) view1.findViewById(R.id.tag_group);
        Button but_xx = (Button) view1.findViewById(R.id.but_xx);
        Button but_yy = (Button) view1.findViewById(R.id.but_yy);
        Button but_qx = (Button) view1.findViewById(R.id.but_qx);
        tv_total_msg.setText(""+total);
        tv_yes_msg.setText(""+yes);
        tv_no_msg.setText(""+no);
        if(yes == 0){
            ratingBar.setRating((float) 0.0);
        }else{
            BigDecimal a = BigDecimal.valueOf(total);
            BigDecimal b = BigDecimal.valueOf(yes);
            BigDecimal c = a.divide(b,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal d = BigDecimal.valueOf(5f);
            float val = d.divide(c,2,BigDecimal.ROUND_HALF_UP).floatValue();
            ratingBar.setRating(val);
        }
        if(no == 0){
            but_xx.setVisibility(View.GONE);
            mTagGroup.setVisibility(View.GONE);
        }else{
            mTagGroup.setTags(errors);
        }
        alertDialog7.setCancelable(false);
        alertDialog7.setView(view1).create();
        final AlertDialog dialog = alertDialog7.show();
        but_xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onCancelClickListener.onCancelClickListener();
                goActivity(context, LetterActivity.class, errors, selectGroup, selectChild);
            }
        });
        but_yy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onCancelClickListener.onCancelClickListener();
                goActivity(context, PyvoiceActivity.class, errors, selectGroup, selectChild);
            }
        });
        but_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onCancelClickListener.onCancelClickListener();
            }
        });
    }

    public interface OnCancelClickListener{
        void onCancelClickListener();
    }

    private static void goActivity(Context context, Class clazz, final List<String> errors, String selectGroup, String selectChild){
        Bundle bundle = new Bundle();
        List<LetterInfo> letters = new ArrayList<>();
        for(String l : errors){
            letters.add(new LetterInfo(l, R.drawable.yesmark, false));
        }
        bundle.putSerializable("letters", (Serializable) letters);
        Intent intent = new Intent(context, clazz);
        intent.putExtra("selectGroup", selectGroup);
        intent.putExtra("selectChild", selectChild);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


}

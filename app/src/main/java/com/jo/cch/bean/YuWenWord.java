package com.jo.cch.bean;

import java.util.List;

/**
 * 语文生字表，
 * 识字表，
 * 写字表
 */
public class YuWenWord {
    //对应的课程
    private String course;
    //是否显示复选框
    private boolean isShow = false;
    //是否选中
    private boolean isChecked = false;
    //生字表归类，是写字表，还是识字表
    private String yuWenType;
    //生字
    private List<WordInfo> words;

    public YuWenWord() {}

    public YuWenWord(String course, String yuWenType, List<WordInfo> words) {
        this.course = course;
        this.yuWenType = yuWenType;
        this.words = words;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getYuWenType() {
        return yuWenType;
    }

    public void setYuWenType(String yuWenType) {
        this.yuWenType = yuWenType;
    }

    public List<WordInfo> getWords() {
        return words;
    }

    public void setWords(List<WordInfo> words) {
        this.words = words;
    }
}

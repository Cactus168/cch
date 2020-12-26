package com.jo.cch.bean;

import java.util.List;

/**
 * 语文课程
 */
public class YuWenCourse {
    //课程
    private String course;
    //是否显示复选框
    private boolean isShow = false;
    //是否选中
    private boolean isAChecked = false;
    //是否选中
    private boolean isXChecked = false;
    //是否选中
    private boolean isSChecked = false;
    //写字表对象
    private YuWenWord xWord;
    //识字表对象
    private YuWenWord sWord;

    public YuWenCourse() {
    }

    public YuWenCourse(String course) {
        this.course = course;
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

    public boolean isAChecked() {
        return isAChecked;
    }

    public void setAChecked(boolean AChecked) {
        isAChecked = AChecked;
    }

    public boolean isXChecked() {
        return isXChecked;
    }

    public void setXChecked(boolean XChecked) {
        isXChecked = XChecked;
    }

    public boolean isSChecked() {
        return isSChecked;
    }

    public void setSChecked(boolean SChecked) {
        isSChecked = SChecked;
    }

    public YuWenWord getxWord() {
        return xWord;
    }

    public void setxWord(YuWenWord xWord) {
        this.xWord = xWord;
    }

    public YuWenWord getsWord() {
        return sWord;
    }

    public void setsWord(YuWenWord sWord) {
        this.sWord = sWord;
    }
}

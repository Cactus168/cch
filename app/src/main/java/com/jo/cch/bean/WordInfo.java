package com.jo.cch.bean;

import java.io.Serializable;

public class WordInfo implements Serializable {

    private String word;

    private boolean checked = false;

    public WordInfo (){};

    public WordInfo (String word){
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WordInfo)) {  //判断obj对象是否是WordInfo的一个实例，若不是返回false
            return false;
        }
        WordInfo w= (WordInfo) obj;  //子类转化成父类
        return word.equals(w.word);
    }

    @Override
    public int hashCode() {
        return word.hashCode();

    }
}

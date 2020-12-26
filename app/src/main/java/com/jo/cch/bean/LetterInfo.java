package com.jo.cch.bean;

import java.io.Serializable;

public class LetterInfo implements Serializable {

    private String letter;

    private int icon;

    private boolean checked;

    public LetterInfo(){};

    public LetterInfo(String letter, int icon, boolean checked){
        this.letter = letter;
        this.icon = icon;
        this.checked = checked;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

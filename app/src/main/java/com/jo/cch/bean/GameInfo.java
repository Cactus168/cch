package com.jo.cch.bean;

import java.io.Serializable;

public class GameInfo implements Serializable {

    private int num;

    private int state;

    public GameInfo(){};

    public GameInfo(int num, int state){
        this.num = num;
        this.state = state;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

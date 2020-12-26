package com.jo.cch.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jo.cch.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShuxueActivity extends Activity {

    private AppCompatImageView backIv;

    private LinearLayout shuxue_layout;

    private RadioButton rb_jiafa, rb_jianfa, rb_chengfa, rb_chufa;

    private Button but_jia1, but_jia2, but_jia3, but_jia4;

    private Button but_jian1, but_jian2;

    private Button but_cheng1, but_cheng2, but_cheng3, but_cheng4, but_cheng5, but_cheng6, but_cheng7, but_cheng8, but_cheng9;

    private Button but_chu1, but_chu2, but_chu3, but_chu4, but_chu5, but_chu6, but_chu7, but_chu8, but_chu9;

    private View jiaFaView, jianFaView, chengFaView, chuFaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuxue);
        shuxue_layout = (LinearLayout) findViewById(R.id.shuxue_layout);

        jiaFaView = FrameLayout.inflate(this, R.layout.shuxue_jiafa, null);//加法布局加载
        jianFaView = FrameLayout.inflate(this, R.layout.shuxue_jianfa, null);//减法布局加载
        chengFaView = FrameLayout.inflate(this, R.layout.shuxue_chengfa, null);//乘法布局加载
        chuFaView = FrameLayout.inflate(this, R.layout.shuxue_chufa, null);//除法布局加载

        initJiaFa();
        initJianFa();
        initChengFa();
        initChuFa();

        shuxue_layout.removeAllViews();//移除其他页面
        shuxue_layout.addView(jiaFaView);///加载加法页面

        //加法事件
        rb_jiafa = (RadioButton) findViewById(R.id.rb_jiafa);
        rb_jiafa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_jiafa.setChecked(true);
                rb_jianfa.setChecked(false);
                rb_chengfa.setChecked(false);
                rb_chufa.setChecked(false);

                shuxue_layout.removeAllViews();//移除其他页面
                shuxue_layout.addView(jiaFaView);///加载加法页面
            }
        });
        //减法事件
        rb_jianfa = (RadioButton) findViewById(R.id.rb_jianfa);
        rb_jianfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_jiafa.setChecked(false);
                rb_jianfa.setChecked(true);
                rb_chengfa.setChecked(false);
                rb_chufa.setChecked(false);

                shuxue_layout.removeAllViews();//移除其他页面
                shuxue_layout.addView(jianFaView);///加载减法页面
            }
        });
        //乘法事件
        rb_chengfa = (RadioButton) findViewById(R.id.rb_chengfa);
        rb_chengfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_jiafa.setChecked(false);
                rb_jianfa.setChecked(false);
                rb_chengfa.setChecked(true);
                rb_chufa.setChecked(false);

                shuxue_layout.removeAllViews();//移除其他页面
                shuxue_layout.addView(chengFaView);///加载乘法页面
            }
        });
        //除法事件
        rb_chufa = (RadioButton) findViewById(R.id.rb_chufa);
        rb_chufa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_jiafa.setChecked(false);
                rb_jianfa.setChecked(false);
                rb_chengfa.setChecked(false);
                rb_chufa.setChecked(true);

                shuxue_layout.removeAllViews();//移除其他页面
                shuxue_layout.addView(chuFaView);///加载除法页面
            }
        });

        //返回
        backIv = (AppCompatImageView) findViewById(R.id.backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化加法事件
     */
    public void initJiaFa(){
        //5以内
        but_jia1 = (Button) jiaFaView.findViewById(R.id.but_jia1);
        but_jia1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(5,"jia");
            }
        });
        //10以内
        but_jia2 = (Button) jiaFaView.findViewById(R.id.but_jia2);
        but_jia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(9,"jia");
            }
        });
        //等于10
        but_jia3 = (Button) jiaFaView.findViewById(R.id.but_jia3);
        but_jia3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(10,"jia");
            }
        });
        //20以内
        but_jia4 = (Button) jiaFaView.findViewById(R.id.but_jia4);
        but_jia4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(20,"jia");
            }
        });

    }

    /**
     * 初始化减法事件
     */
    public void initJianFa(){
        //5以内
        but_jian1 = (Button) jianFaView.findViewById(R.id.but_jian1);
        but_jian1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(5,"jian");
            }
        });
        //10以内
        but_jian2 = (Button) jianFaView.findViewById(R.id.but_jian2);
        but_jian2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(10,"jian");
            }
        });

    }

    /**
     * 初始化乘法事件
     */
    public void initChengFa(){
        //1
        but_cheng1 = (Button) chengFaView.findViewById(R.id.but_cheng1);
        but_cheng1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(1,"cheng");
            }
        });
        //2
        but_cheng2 = (Button) chengFaView.findViewById(R.id.but_cheng2);
        but_cheng2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(2,"cheng");
            }
        });
        //3
        but_cheng3 = (Button) chengFaView.findViewById(R.id.but_cheng3);
        but_cheng3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(3,"cheng");
            }
        });
        //4
        but_cheng4 = (Button) chengFaView.findViewById(R.id.but_cheng4);
        but_cheng4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(4,"cheng");
            }
        });
        //5
        but_cheng5 = (Button) chengFaView.findViewById(R.id.but_cheng5);
        but_cheng5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(5,"cheng");
            }
        });
        //6
        but_cheng6 = (Button) chengFaView.findViewById(R.id.but_cheng6);
        but_cheng6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(6,"cheng");
            }
        });
        //7
        but_cheng7 = (Button) chengFaView.findViewById(R.id.but_cheng7);
        but_cheng7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(7,"cheng");
            }
        });
        //8
        but_cheng8 = (Button) chengFaView.findViewById(R.id.but_cheng8);
        but_cheng8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(8,"cheng");
            }
        });
        //9
        but_cheng9 = (Button) chengFaView.findViewById(R.id.but_cheng9);
        but_cheng9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(9,"cheng");
            }
        });
    }

    /**
     * 初始化除法事件
     */
    public void initChuFa(){
        //1
        but_chu1 = (Button) chuFaView.findViewById(R.id.but_chu1);
        but_chu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(1,"chu");
            }
        });
        //2
        but_chu2 = (Button) chuFaView.findViewById(R.id.but_chu2);
        but_chu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(2,"chu");
            }
        });
        //3
        but_chu3 = (Button) chuFaView.findViewById(R.id.but_chu3);
        but_chu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(3,"chu");
            }
        });
        //4
        but_chu4 = (Button) chuFaView.findViewById(R.id.but_chu4);
        but_chu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(4,"chu");
            }
        });
        //5
        but_chu5 = (Button) chuFaView.findViewById(R.id.but_chu5);
        but_chu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(5,"chu");
            }
        });
        //6
        but_chu6 = (Button) chuFaView.findViewById(R.id.but_chu6);
        but_chu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(6,"chu");
            }
        });
        //7
        but_chu7 = (Button) chuFaView.findViewById(R.id.but_chu7);
        but_chu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(7,"chu");
            }
        });
        //8
        but_chu8 = (Button) chuFaView.findViewById(R.id.but_chu8);
        but_chu8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(8,"chu");
            }
        });
        //9
        but_chu9 = (Button) chuFaView.findViewById(R.id.but_chu9);
        but_chu9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShuxuelx(9,"chu");
            }
        });
    }

    private void goShuxuelx(int num, String type){
        List<String> datas = getDatas(num,type);
        Bundle bundle = new Bundle();
        Intent intent = new Intent(ShuxueActivity.this, ShuxuelxActivity.class);
        bundle.putSerializable("formulas", (Serializable) datas);
        if("jia".equals(type)){
            intent.putExtra("subject", "数学/加法");
            if(num == 9){
                intent.putExtra("title", "10以内的减法");
            }else if(num == 10){
                intent.putExtra("title", "整10加法");
            }else{
                intent.putExtra("title", num+"以内的减法");
            }
        }else if("jian".equals(type)){
            intent.putExtra("subject", "数学/减法");
            intent.putExtra("title", num+"以内的减法");
        }else if("cheng".equals(type)){
            intent.putExtra("subject", "数学/乘法");
            intent.putExtra("title", num+"的乘法");
        }else if("chu".equals(type)){
            intent.putExtra("subject", "数学/除法");
            intent.putExtra("title", num+"的除法");
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private List<String> getDatas(int num, String type){
        List<String> datas = new ArrayList<String>();
        if("jia".equals(type)){
            for(int n = 1; n <= 9; n++) {
                for(int m = 1; m <= 9; m++) {
                    int val = n + m;
                    if(num == 5 || num == 9){
                        if(val <= num) {
                            String ss = n+"+"+m;
                            if(!datas.contains(ss)){
                                datas.add(ss);
                            }
                        }
                    }else if(num == 10){
                        if(val == num) {
                            String ss = n+"+"+m;
                            if(!datas.contains(ss)){
                                datas.add(ss);
                            }
                        }
                    }else if(num == 20){
                        if(val < num) {
                            String ss = n+"+"+m;
                            if(!datas.contains(ss)){
                                datas.add(ss);
                            }
                        }
                    }
                }
            }
        }else if("jian".equals(type)){
            for(int n = num; n >= 1; n--) {
                for (int m = n; m >= 1; m--) {
                    String ss = n+"-"+m;
                    if(!datas.contains(ss)){
                        datas.add(ss);
                    }
                }
            }
        }else if("cheng".equals(type)){
            for(int m = num; m <= 9; m++) {
                String ss = num+"×"+m;
                if(!datas.contains(ss)){
                    datas.add(ss);
                }
            }
        }else if("chu".equals(type)){
            for(int n = num; n <= num; n++) {
                for(int m = num; m <= 9; m++) {
                    int val = n * m;
                    String ss = val+"÷"+n;
                    if(!datas.contains(ss)){
                        datas.add(ss);
                    }
                }
            }
        }
        return datas;
    }
}

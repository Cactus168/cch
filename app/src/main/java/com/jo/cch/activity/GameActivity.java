package com.jo.cch.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;

import com.jo.cch.R;
import com.jo.cch.adapter.GameGridAdapter;
import com.jo.cch.bean.GameInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends Activity {

    private AppCompatImageView backIv;

    private Chronometer chTime;

    private GridView gview;

    private Button startTime, endTime;

    private GameGridAdapter gameAdapter;

    private TextView tips;

    private int currNum = 1;

    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        chTime = (Chronometer) findViewById(R.id.chTime);
        gview = (GridView) findViewById(R.id.gview);
        tips = (TextView) findViewById(R.id.tips);
        final List<GameInfo> datas = new ArrayList<GameInfo>();
        for(int i = 1; i <= 25; i++){
            datas.add(new GameInfo(i,2));
        }
        Collections.shuffle(datas);
        //新建List
        gameAdapter = new GameGridAdapter(this, datas);
        //配置适配器
        gview.setAdapter(gameAdapter);
        tips.setText("下一个数字："+currNum);
        gview.setOnItemClickListener(new GridView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                if(flag){
                    GameInfo g = datas.get(position);
                    for (GameInfo gi : datas){
                        gi.setState(2);
                    }
                    if(currNum == g.getNum()){
                        g.setState(1);
                        if(currNum == 25){
                            chTime.stop();//自动停止
                            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                            builder.setIcon(R.mipmap.logo);
                            builder.setTitle("游戏结束");
                            builder.setMessage("用时"+chTime.getText());
                            builder.setCancelable(false);
                            builder.setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    flag = false;
                                    for (GameInfo gi : datas){
                                        gi.setState(2);
                                    }
                                    gameAdapter.notifyDataSetChanged();
                                    currNum = 1;
                                    chTime.setBase(SystemClock.elapsedRealtime());
                                    startTime.setEnabled(true);
                                    tips.setText("下一个数字："+currNum);
                                }
                            });
                            builder.create().show();
                        }else{
                            currNum++;
                            tips.setText("下一个数字："+currNum);
                        }
                    }else{
                        g.setState(0);
                    }
                    gameAdapter.notifyDataSetChanged();
                }
            }
        });

        startTime = (Button) findViewById(R.id.startTime);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置开始计时时间
                chTime.setBase(SystemClock.elapsedRealtime() );
                //启动计时器
                chTime.start();
                startTime.setEnabled(false);
                flag = true;

            }
        });

        endTime = (Button) findViewById(R.id.endTime);
        endTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                chTime.stop();//自动停止
                startTime.setEnabled(true);
                flag = false;
                for (GameInfo gi : datas){
                    gi.setState(2);
                }
                gameAdapter.notifyDataSetChanged();
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

        //为Chronomter绑定事件监听器
        chTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //如果计时到现在超过了一小时秒
                if (SystemClock.elapsedRealtime() - chTime.getBase() > 3600 * 1000) {
                    chTime.stop();//自动停止
                    flag = false;
                    startTime.setEnabled(true);
                    for (GameInfo gi : datas){
                        gi.setState(2);
                    }
                    gameAdapter.notifyDataSetChanged();
                }
            }
        });

    }
}

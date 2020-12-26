package com.jo.cch.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jo.cch.R;
import com.jo.cch.db.SPConstant;
import com.jo.cch.db.SpUtil;
import com.jo.cch.view.HomeGridLayout;

public class MainActivity extends Activity{

    private long currTime = 0;

    private AppCompatImageView backIv, sttingIv;

    private HomeGridLayout grid;

    int[] srcs = {R.drawable.yw, R.drawable.ks, R.drawable.py, R.drawable.table, R.drawable.yl, R.drawable.log};

    String titles[] = {"语文", "数学", "拼音", "笔记", "娱乐", "日志"};

    Class contexts[] = {YuwenActivity.class, ShuxueActivity.class, PinyinActivity.class, NotesActivity.class, GameActivity.class, LearnlogActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = (HomeGridLayout) findViewById(R.id.list);
        grid.setGridAdapter(new HomeGridLayout.GridAdatper() {

            @Override
            public View getView(int index) {
                View view = getLayoutInflater().inflate(R.layout.actions_item,null);
                ImageView iv = (ImageView) view.findViewById(R.id.iv);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                iv.setImageResource(srcs[index]);
                tv.setText(titles[index]);
                return view;
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });

        grid.setOnItemClickListener(new HomeGridLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                startActivity(new Intent(MainActivity.this,contexts[index]));
            }
        });

        //设置
        sttingIv = (AppCompatImageView) findViewById(R.id.sttingIv);
        sttingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(MainActivity.this);
                View view1 = View.inflate(MainActivity.this, R.layout.dialog_setting, null);
                final EditText et_speed = (EditText)view1.findViewById(R.id.et_speed);
                final EditText et_pinxie = (EditText)view1.findViewById(R.id.et_pinxie);
                final EditText et_pindu = (EditText)view1.findViewById(R.id.et_pindu);
                final EditText et_voice = (EditText)view1.findViewById(R.id.et_voice);
                final EditText et_shuxue = (EditText)view1.findViewById(R.id.et_shuxue);
                final EditText et_pinyin = (EditText)view1.findViewById(R.id.et_pinyin);
                final EditText et_learnnum = (EditText)view1.findViewById(R.id.et_learnnum);
                et_speed.setText(SpUtil.getInstance(MainActivity.this).getParam(SPConstant.SPEED_FLAG, 5).toString());
                et_pindu.setText(SpUtil.getInstance(MainActivity.this).getParam(SPConstant.PINDU_FLAG, 5).toString());
                et_pinxie.setText(SpUtil.getInstance(MainActivity.this).getParam(SPConstant.PINXIE_FLAG, 10).toString());
                et_voice.setText(SpUtil.getInstance(MainActivity.this).getParam(SPConstant.VOICE_FLAG, 5).toString());
                et_shuxue.setText(SpUtil.getInstance(MainActivity.this).getParam(SPConstant.SHUXUE_FLAG, 5).toString());
                et_pinyin.setText(SpUtil.getInstance(MainActivity.this).getParam(SPConstant.PINYIN_FLAG, 5).toString());
                et_learnnum.setText(SpUtil.getInstance(MainActivity.this).getParam(SPConstant.LEARN_NUM_FLAG, 3).toString());
                Button but_qd = (Button) view1.findViewById(R.id.but_qd);
                Button but_qx = (Button) view1.findViewById(R.id.but_qx);
                alertDialog7.setCancelable(false);
                alertDialog7.setView(view1).create();
                final AlertDialog dialog = alertDialog7.show();
                but_qd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int speed = Integer.parseInt(et_speed.getText().toString());
                        int pindu = Integer.parseInt(et_pindu.getText().toString());
                        int pinxie = Integer.parseInt(et_pinxie.getText().toString());
                        int voice = Integer.parseInt(et_voice.getText().toString());
                        int shuxue = Integer.parseInt(et_shuxue.getText().toString());
                        int pinyin = Integer.parseInt(et_pinyin.getText().toString());
                        int learnnum = Integer.parseInt(et_learnnum.getText().toString());
                        if(speed < 3 || pindu < 3 || pinxie < 3 || voice < 3 || shuxue < 3 || pinyin < 3){
                            Toast.makeText(MainActivity.this,"设置的计时数必须大于等于3！", Toast.LENGTH_LONG).show();
                        }else{
                            SpUtil.getInstance(MainActivity.this).saveParam(SPConstant.SPEED_FLAG, speed);
                            SpUtil.getInstance(MainActivity.this).saveParam(SPConstant.PINDU_FLAG, pindu);
                            SpUtil.getInstance(MainActivity.this).saveParam(SPConstant.PINXIE_FLAG, pinxie);
                            SpUtil.getInstance(MainActivity.this).saveParam(SPConstant.VOICE_FLAG, voice);
                            SpUtil.getInstance(MainActivity.this).saveParam(SPConstant.SHUXUE_FLAG, shuxue);
                            SpUtil.getInstance(MainActivity.this).saveParam(SPConstant.PINYIN_FLAG, pinyin);
                            SpUtil.getInstance(MainActivity.this).saveParam(SPConstant.LEARN_NUM_FLAG, learnnum);
                            dialog.dismiss();
                        }
                    }
                });
                but_qx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        //返回
        backIv = (AppCompatImageView) findViewById(R.id.backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if((System.currentTimeMillis() - currTime) > 2000){
                Toast.makeText(MainActivity.this,"连续双击退出应用",Toast.LENGTH_SHORT).show();
                currTime = System.currentTimeMillis();
                return false;
            }else{
                finish();
                System.exit(0);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

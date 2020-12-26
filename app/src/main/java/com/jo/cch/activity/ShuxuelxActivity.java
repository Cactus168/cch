package com.jo.cch.activity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.promeg.pinyinhelper.Pinyin;
import com.jo.cch.R;
import com.jo.cch.dao.LearnLogDao;
import com.jo.cch.db.SPConstant;
import com.jo.cch.db.SpUtil;
import com.jo.cch.helper.PinyinDataHelper;
import com.jo.cch.helper.WordDataHelper;
import com.jo.cch.sql.LearnLog;
import com.jo.cch.utils.DataUtils;
import com.jo.cch.utils.DateUtils;
import com.jo.cch.utils.PublicUtils;
import com.jo.cch.utils.ShuxueUtils;
import com.jo.cch.utils.YuwenUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class ShuxuelxActivity extends Activity {

    private AppCompatImageView backIv;

    private TextView tv_formula;

    private TextView tips;

    private int index = 0;

    private int yesIndex = 0;

    private int noIndex = 0;

    private int val;

    private String subject, title;

    private Button but_val1, but_val2, but_val3;

    private List<String> datas;

    private List<String> errorSs = new ArrayList<String>();

    private LearnLogDao learnLogDao;

    private String logId;

    private String startDate;

    private String type = "数学";

    private Toast mtoast;

    private TextView tv_time;

    private Timer timer;

    private final static int START = 1;

    private final static int RE_START = 2;

    private int TOTAL_TIME = -1;

    private SoundPool soundPool; // 成功声音加载池

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuxuelx);

        //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.yes, 1);
        soundPool.load(this, R.raw.no, 1);

        tv_formula = (TextView) findViewById(R.id.tv_formula);
        tips = (TextView) findViewById(R.id.tips);
        but_val1 = (Button) findViewById(R.id.but_val1);
        but_val2 = (Button) findViewById(R.id.but_val2);
        but_val3 = (Button) findViewById(R.id.but_val3);

        timer = new Timer();
        tv_time = (TextView) findViewById(R.id.tv_time);
        TOTAL_TIME = (Integer) SpUtil.getInstance(this).getParam(SPConstant.SHUXUE_FLAG,5);

        logId = UUID.randomUUID().toString();
        startDate = DateUtils.getDate(new Date(), null);
        datas = (List<String>) getIntent().getExtras().getSerializable("formulas");
        subject = getIntent().getStringExtra("subject");
        title = getIntent().getStringExtra("title");

        learnLogDao = new LearnLogDao(this);
        tips.setText("共"+datas.size()+"道算式，已读0道，对0道，错0道");
        Collections.shuffle(datas);
        String ss = datas.get(0);
        tv_formula.setText(ss+"=?");
        val = initVals(ss);

        but_val1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sval = Integer.parseInt(but_val1.getText().toString());
                if(datas.size() > index) {
                    count(sval);
                }
            }
        });

        but_val2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sval = Integer.parseInt(but_val2.getText().toString());
                if(datas.size() > index) {
                    count(sval);
                }
            }
        });

        but_val3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sval = Integer.parseInt(but_val3.getText().toString());
                if(datas.size() > index) {
                    count(sval);
                }
            }
        });
        //返回
        backIv = (AppCompatImageView) findViewById(R.id.backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();//0秒结束
                finish();
            }
        });

        /**
         * 每一秒发送一次消息给handler更新UI
         * schedule(TimerTask task, long delay, long period)
         */
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(START);
            }
        }, 0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        soundPool.release();
    }

    public void count(int sval){
        String ss = datas.get(index);
        index++;
        if(datas.size() > index){
            String sss = datas.get(index);
            isSuccess(sval, ss);
            setAnimatorTextView(sss+"=?", tv_formula);
            val = initVals(sss);
            handler.sendEmptyMessage(RE_START);
        }else{
            timer.cancel();//0秒结束
            isSuccess(sval, ss);
            LearnLog log = learnLogDao.queryById(logId);
            String titlex = title+"-"+log.getNo()+ "-1";
            ShuxueUtils.showDialog(ShuxuelxActivity.this, new ShuxueUtils.OnCancelClickListener() {
                @Override
                public void onCancelClickListener() {
                    setResult(2, getIntent());
                    timer.cancel();//0秒结束
                    finish();
                }
            }, errorSs, datas.size(), yesIndex, noIndex, subject, titlex);
        }
        String startMgs = "共"+datas.size()+"道题，已读"+index+"道，对"+yesIndex+"道，错"+noIndex+"道";
        tips.setText(startMgs);
    }

    private void isSuccess(int sval, String ss){
        if(val == sval){
            yesIndex++;
            if(mtoast!=null){
                mtoast.cancel();//注销之前显示的那条信息
                mtoast=null;//这里要注意上一步相当于隐藏了信息，mtoast并没有为空，我们强制是他为空
            }
            if(mtoast==null){
                mtoast=Toast.makeText(getApplicationContext(),"你真棒",Toast.LENGTH_SHORT);
                mtoast.setGravity(Gravity.CENTER,0,0);
                LinearLayout linearLayout= (LinearLayout)mtoast.getView();
                ImageView imageView=new ImageView(ShuxuelxActivity.this);
                imageView.setImageResource(R.drawable.laugh);
                linearLayout.addView(imageView,0);
                mtoast.show();
            }
            PublicUtils.addLog(ShuxuelxActivity.this, learnLogDao, logId, subject, title, type, datas.size() + "/" + index + "/" + yesIndex + "/" + noIndex, startDate, errorSs);
            soundPool.play(1, 1, 1, 0, 0, 1);
        }else{
            noIndex++;
            errorSs.add(ss);
            if(mtoast!=null){
                mtoast.cancel();//注销之前显示的那条信息
                mtoast=null;//这里要注意上一步相当于隐藏了信息，mtoast并没有为空，我们强制是他为空
            }
            if(mtoast==null) {
                mtoast = Toast.makeText(getApplicationContext(), "错了哦", Toast.LENGTH_SHORT);
                mtoast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout linearLayout = (LinearLayout) mtoast.getView();
                ImageView imageView = new ImageView(ShuxuelxActivity.this);
                imageView.setImageResource(R.drawable.cry);
                linearLayout.addView(imageView, 0);
                mtoast.show();
            }
            PublicUtils.addLog(ShuxuelxActivity.this, learnLogDao, logId, subject, title, type, datas.size() + "/" + index + "/" + yesIndex + "/" + noIndex, startDate, errorSs);
            soundPool.play(2, 1, 1, 0, 0, 1);
        }
    }

    private int initVals(String ss){
        List<Integer> vals = new ArrayList<>();
        if(ss.indexOf("+") > -1){
            String[] sss = ss.split("\\+");
            vals.add(Integer.parseInt(sss[0])+Integer.parseInt(sss[1]));
        }else if(ss.indexOf("-") > -1){
            String[] sss = ss.split("-");
            vals.add(Integer.parseInt(sss[0])-Integer.parseInt(sss[1]));
        }else if(ss.indexOf("×") > -1){
            String[] sss = ss.split("×");
            vals.add(Integer.parseInt(sss[0])*Integer.parseInt(sss[1]));
        }else if(ss.indexOf("÷") > -1){
            String[] sss = ss.split("÷");
            vals.add(Integer.parseInt(sss[0])/Integer.parseInt(sss[1]));
        }
        int val = vals.get(0);
        if(val > 5){
            vals.add(val+DataUtils.getRandom(1,2));
            vals.add(val-DataUtils.getRandom(3,4));
        }else if(val > 3){
            int tval = DataUtils.getRandom(1,2);
            vals.add(val+tval);
            vals.add(val-tval);
        }else if(val >= 1){
            vals.add(val+1);
            vals.add(val-1);
        }else{
            vals.add(val+1);
            vals.add(val+DataUtils.getRandom(1,2));
        }
        Collections.shuffle(vals);
        but_val1.setText(vals.get(0)+"");
        but_val2.setText(vals.get(1)+"");
        but_val3.setText(vals.get(2)+"");
        return val;
    }

    private void setAnimatorTextView(String val, TextView tv){
        ValueAnimator animator = ValueAnimator.ofObject(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                TextView tv = (TextView) endValue;
                tv.setText(startValue.toString());
                return startValue;
            }
        }, val, tv);
        //动画时间
        animator.setDuration(800);
        animator.start();
    }

    private Handler handler = new Handler() {
        int num = TOTAL_TIME;
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case START:
                    if (num == 0){
                        count(-1);
                        num = TOTAL_TIME;
                    }else if (num < 0){
                        num = TOTAL_TIME;
                    }else{
                        num--;
                    }
                    tv_time.setText(String.valueOf(num));
                    break;
                case RE_START:
                    num = TOTAL_TIME;
                    tv_time.setText(String.valueOf(num));
                    break;
                default:
                    break;
            }
        }
    };
}

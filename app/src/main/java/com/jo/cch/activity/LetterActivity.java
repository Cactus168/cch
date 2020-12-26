package com.jo.cch.activity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jo.cch.R;
import com.jo.cch.bean.LetterInfo;
import com.jo.cch.dao.LearnLogDao;
import com.jo.cch.db.SPConstant;
import com.jo.cch.db.SpUtil;
import com.jo.cch.sql.LearnLog;
import com.jo.cch.utils.DataUtils;
import com.jo.cch.utils.DateUtils;
import com.jo.cch.utils.PinyinUtils;
import com.jo.cch.utils.PublicUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static com.jo.cch.utils.PinyinUtils.*;

public class LetterActivity extends Activity {

    private AppCompatImageView backIv;

    private TextView letter;

    private ImageButton yes, no;

    private List<LetterInfo> datas;

    private TextView tips;

    private int index = 0;

    private int yesIndex = 0;

    private int noIndex = 0;

    private String logId;

    private String startDate;

    private String type = "速认";

    private String selectGroup, selectChild;

    private List<String> errorSs = new ArrayList<String>();

    private LearnLogDao learnLogDao;

    private TextView tv_time;

    private Timer timer;

    private final static int START = 1;

    private final static int RE_START = 2;

    private int TOTAL_TIME = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);
        letter = (TextView) findViewById(R.id.letter);
        tips = (TextView) findViewById(R.id.tips);

        timer = new Timer();
        tv_time = (TextView) findViewById(R.id.tv_time);
        TOTAL_TIME = (Integer) SpUtil.getInstance(this).getParam(SPConstant.PINYIN_FLAG,5);

        logId = UUID.randomUUID().toString();
        startDate = DateUtils.getDate(new Date(), null);
        learnLogDao = new LearnLogDao(this);
        datas = (List<LetterInfo>) getIntent().getExtras().getSerializable("letters");
        selectGroup = getIntent().getStringExtra("selectGroup");
        selectChild = getIntent().getStringExtra("selectChild");
        Collections.shuffle(datas);
        letter.setText(datas.get(index).getLetter());
        tips.setText("共"+datas.size()+"个字母，已读0个，对0个，错0个");
        yes = (ImageButton) findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datas.size() > 0){
                    index++;
                    yesIndex++;
                    PublicUtils.addLog(LetterActivity.this, learnLogDao, logId, selectGroup, selectChild, type, datas.size() + "/" + index + "/" + yesIndex + "/" + noIndex, startDate, errorSs);
                    handler.sendEmptyMessage(RE_START);
                    if(datas.size() > index){
                        String letterStr = datas.get(index).getLetter();
                        ValueAnimator animator = ValueAnimator.ofObject(new TypeEvaluator() {
                            @Override
                            public Object evaluate(float fraction, Object startValue, Object endValue) {
                                TextView tv = (TextView) endValue;
                                tv.setText(startValue.toString());
                                return startValue;
                            }
                        }, letterStr, letter);
                        //动画时间
                        animator.setDuration(800);
                        animator.start();
                    }else{
                        LearnLog log = learnLogDao.queryById(logId);
                        String title = selectChild+"-"+log.getNo()+ "-1";
                        PinyinUtils.showDialog(LetterActivity.this, new OnCancelClickListener() {
                            @Override
                            public void onCancelClickListener() {
                                setResult(2, getIntent());
                                timer.cancel();//0秒结束
                                finish();
                            }
                        }, errorSs, datas.size(), yesIndex, noIndex, selectGroup, title);
                    }
                    tips.setText("共" + datas.size() + "个字母，已读" + index + "个，对" + yesIndex + "个，错" + noIndex + "个");
                }
            }
        });
        no = (ImageButton) findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datas.size() > 0) {
                    String noLetter = datas.get(index).getLetter();
                    index++;
                    noIndex++;
                    errorSs.add(noLetter);
                    PublicUtils.addLog(LetterActivity.this, learnLogDao, logId, selectGroup, selectChild, type, datas.size() + "/" + index + "/" + yesIndex + "/" + noIndex, startDate, errorSs);
                    if(datas.size() > index) {
                        String letterStr = datas.get(index).getLetter();
                        ValueAnimator animator = ValueAnimator.ofObject(new TypeEvaluator() {
                            @Override
                            public Object evaluate(float fraction, Object startValue, Object endValue) {
                                TextView tv = (TextView) endValue;
                                tv.setText(startValue.toString());
                                return startValue;
                            }
                        }, letterStr, letter);
                        //动画时间
                        animator.setDuration(800);
                        animator.start();
                        handler.sendEmptyMessage(RE_START);
                    }else{
                        timer.cancel();//0秒结束
                        LearnLog log = learnLogDao.queryById(logId);
                        String title = selectChild+"-"+log.getNo()+ "-1";
                        PinyinUtils.showDialog(LetterActivity.this, new OnCancelClickListener() {
                            @Override
                            public void onCancelClickListener() {
                                setResult(2, getIntent());
                                timer.cancel();//0秒结束
                                finish();
                            }
                        }, errorSs, datas.size(), yesIndex, noIndex, selectGroup, title);
                    }
                    tips.setText("共" + datas.size() + "个字母，已读" + index + "个，对" + yesIndex + "个，错" + noIndex + "个");
                    //LetterDataHelper.addErrorWord(LetterActivity.this, noLetter);
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
    }

    private Handler handler = new Handler() {
        int num = TOTAL_TIME;
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case START:
                    if (num == 0){
                        no.callOnClick();
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

package com.jo.cch.activity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.pinyinhelper.PinyinMapDict;
import com.jo.cch.R;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.dao.LearnLogDao;
import com.jo.cch.db.SPConstant;
import com.jo.cch.db.SpUtil;
import com.jo.cch.helper.PinyinDataHelper;
import com.jo.cch.helper.WordDataHelper;
import com.jo.cch.sql.LearnLog;
import com.jo.cch.utils.DataUtils;
import com.jo.cch.utils.DateUtils;
import com.jo.cch.utils.PinyinUtils;
import com.jo.cch.utils.PublicUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinyinknowActivity extends Activity {

    private AppCompatImageView backIv;

    private TextView word;

    private List<WordInfo> datas;

    private TextView tips, tv_jc;

    private int index = 0;

    private int yesIndex = 0;

    private int noIndex = 0;

    private String selectGroup, selectChild;

    private Button pinYin1, pinYin2, pinYin3;

    private List<String> errorSs = new ArrayList<String>();

    private LearnLogDao learnLogDao;

    private String logId;

    private String startDate;

    private String type = "拼读";

    private Toast mtoast;

    private TextView tv_time;

    private Timer mTimer = null;

    private TimerTask mTimerTask = null;

    private Handler mHandler = null;

    private final static int START = 1;

    private final static int RE_START = 2;

    private int TOTAL_TIME = -1;

    private SoundPool soundPool; // 成功声音加载池

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinyinknow);

        //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.yes, 1);
        soundPool.load(this, R.raw.no, 1);

        word = (TextView) findViewById(R.id.word);
        tips = (TextView) findViewById(R.id.tips);
        tv_jc = (TextView) findViewById(R.id.tv_jc);
        pinYin1 = (Button) findViewById(R.id.pinYin1);
        pinYin2 = (Button) findViewById(R.id.pinYin2);
        pinYin3 = (Button) findViewById(R.id.pinYin3);

        tv_time = (TextView) findViewById(R.id.tv_time);
        TOTAL_TIME = (Integer) SpUtil.getInstance(this).getParam(SPConstant.PINDU_FLAG,10);

        logId = UUID.randomUUID().toString();
        startDate = DateUtils.getDate(new Date(), null);
        learnLogDao = new LearnLogDao(this);

        datas = (List<WordInfo>) getIntent().getExtras().getSerializable("words");
        selectGroup = getIntent().getStringExtra("selectGroup");
        selectChild = getIntent().getStringExtra("selectChild");

        Collections.shuffle(datas);
        String noWord = datas.get(index).getWord();
        word.setText(noWord);
        tips.setText("共"+datas.size()+"个字，已读0个，对0个，错0个");
        setPinyin(noWord);

        Pinyin.init(Pinyin.newConfig()
                .with(new PinyinMapDict() {
                    @Override
                    public Map<String, String[]> mapping() {
                        List<Map> list = SpUtil.getInstance(PinyinknowActivity.this).getList(SPConstant.AMEND_FLAG, Map.class);
                        Map<String, String[]> map = new HashMap<>();
                        for(Map m : list){
                            for (Object key : m.keySet()) {
                                map.put(key.toString(), new String[]{m.get(key).toString()});
                            }
                        }
                        return map;
                    }
                }));
        pinYin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datas.size() > 0) {
                    String spy = pinYin1.getText().toString();
                    know(spy);
                }
            }
        });

        pinYin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datas.size() > 0) {
                    String spy = pinYin2.getText().toString();
                    know(spy);
                }
            }
        });

        pinYin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spy = pinYin3.getText().toString();
                know(spy);
            }
        });

        tv_jc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(PinyinknowActivity.this);
                View view1 = View.inflate(PinyinknowActivity.this, R.layout.dialog_amend, null);
                final EditText et_amend = (EditText) view1.findViewById(R.id.et_amend);
                Button but_qd = (Button) view1.findViewById(R.id.but_qd);
                Button but_qx = (Button) view1.findViewById(R.id.but_qx);
                alertDialog7.setCancelable(false);
                alertDialog7.setView(view1).create();
                final AlertDialog show = alertDialog7.show();
                but_qd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show.dismiss();
                        startTimer();
                        String amend = et_amend.getText().toString();
                        String regex = "^[a-z]+$";
                        Pattern p = Pattern.compile(regex);
                        Matcher m = p.matcher(amend);
                        boolean isValid = m.matches();
                        if(isValid){
                            String noWord = datas.get(index).getWord();
                            String opy = PinyinUtils.converterToSpellx(noWord);
                            boolean flag = opy.indexOf(amend) > -1;
                            if(flag){
                                Map map = new HashMap();
                                List<Map> list = SpUtil.getInstance(PinyinknowActivity.this).getList(SPConstant.AMEND_FLAG, Map.class);
                                map.put(noWord, amend);
                                list.add(map);
                                SpUtil.getInstance(PinyinknowActivity.this).saveList(SPConstant.AMEND_FLAG, list);
                            }
                            know(amend);
                        }else{
                            know("-");
                        }
                    }
                });
                but_qx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show.dismiss();
                        startTimer();
                    }
                });
            }
        });

        //返回
        backIv = (AppCompatImageView) findViewById(R.id.backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                finish();
            }
        });

        mHandler = new Handler() {
            int num = TOTAL_TIME;
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case START:
                        if (num == 0){
                            know("-");
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

        startTimer();
    }

    private void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    sendMessage(START);
                }
            };
        }
        if (mTimer != null && mTimerTask != null){
            mTimer.schedule(mTimerTask, 0, 1000);
        }
    }

    private void stopTimer(){
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    public void sendMessage(int id){
        if (mHandler != null) {
            Message message = Message.obtain(mHandler, id);
            mHandler.sendMessage(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
        soundPool.release();
    }

    public void know(String spy){
        index++;
        PublicUtils.addLog(PinyinknowActivity.this, learnLogDao, logId, selectGroup, selectChild, type, datas.size() + "/" + index + "/" + yesIndex + "/" + noIndex, startDate, errorSs);
        if(datas.size() > index){
            String noWord = datas.get(index-1).getWord();
            String pinyin = Pinyin.toPinyin(noWord,"").toLowerCase();
            String wordStr = datas.get(index).getWord();
            setPinyin(wordStr);
            isSuccess(spy, pinyin, noWord);
            setAnimatorTextView(wordStr, word);
            sendMessage(RE_START);
        }else{
            stopTimer();
            String noWord = datas.get(index-1).getWord();
            String pinyin = Pinyin.toPinyin(noWord,"").toLowerCase();
            isSuccess(spy, pinyin, noWord);
            LearnLog log = learnLogDao.queryById(logId);
            String title = selectChild+"-"+log.getNo()+ "-1";
            YuwenUtils.showDialog(PinyinknowActivity.this, new YuwenUtils.OnCancelClickListener() {
                @Override
                public void onCancelClickListener() {
                    setResult(2, getIntent());
                    stopTimer();
                    finish();
                }
            }, errorSs, datas.size(), yesIndex, noIndex, selectGroup, title);
        }
        tips.setText("共"+datas.size()+"个字，已读"+index+"个，对"+yesIndex+"个，错"+noIndex+"个");
    }

    private void isSuccess(String spy, String pinyin, String noWord){
        if(spy.equals(PinyinDataHelper.getSpecialDatasByKey(pinyin))){
            yesIndex++;
            if(mtoast!=null){
                mtoast.cancel();//注销之前显示的那条信息
                mtoast=null;//这里要注意上一步相当于隐藏了信息，mtoast并没有为空，我们强制是他为空
            }
            if(mtoast==null) {
                mtoast = Toast.makeText(getApplicationContext(), "你真棒", Toast.LENGTH_SHORT);
                mtoast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout linearLayout = (LinearLayout) mtoast.getView();
                ImageView imageView = new ImageView(PinyinknowActivity.this);
                imageView.setImageResource(R.drawable.laugh);
                linearLayout.addView(imageView, 0);
                mtoast.show();
            }
            soundPool.play(1, 1, 1, 0, 0, 1);
        }else{
            noIndex++;
            errorSs.add(noWord);
            //WordDataHelper.addErrorWord(PinyinknowActivity.this, selectGroup, noWord);
            if(mtoast!=null){
                mtoast.cancel();//注销之前显示的那条信息
                mtoast=null;//这里要注意上一步相当于隐藏了信息，mtoast并没有为空，我们强制是他为空
            }
            if(mtoast==null) {
                mtoast = Toast.makeText(getApplicationContext(), "错了哦", Toast.LENGTH_SHORT);
                mtoast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout linearLayout = (LinearLayout) mtoast.getView();
                ImageView imageView = new ImageView(PinyinknowActivity.this);
                imageView.setImageResource(R.drawable.cry);
                linearLayout.addView(imageView, 0);
                mtoast.show();
            }
            soundPool.play(2, 1, 1, 0, 0, 1);
        }
    }
    private void setPinyin(String noWord){
        List<String> pys = new ArrayList<>(3);
        /*System.out.println("匹>>>>"+Pinyin.toPinyin("匹","").toLowerCase());
        System.out.println("和>>>>"+Pinyin.toPinyin("和","").toLowerCase());
        System.out.println("厂>>>>"+Pinyin.toPinyin("厂","").toLowerCase());
        System.out.println("长>>>>"+Pinyin.toPinyin("长","").toLowerCase());*/
        String pinyin1 = Pinyin.toPinyin(noWord,"").toLowerCase();
        pys.add(PinyinDataHelper.getSpecialDatasByKey(pinyin1));
        String key = pinyin1.substring(0,1);
        String[] vals1 = PinyinDataHelper.getDataByKey(key);
        String[] vals2 = removeArray(vals1, pinyin1);
        int index2 = DataUtils.getRandom(0, vals2.length-1);
        String pinyin2 = vals2[index2];
        pys.add(pinyin2);

        String[] vals3 = removeArray(vals2, pinyin2);
        int index3 = DataUtils.getRandom(0, vals3.length-1);
        String pinyin3 = vals3[index3];
        pys.add(pinyin3);

        Collections.shuffle(pys);
        pinYin1.setText(pys.get(0));
        pinYin2.setText(pys.get(1));
        pinYin3.setText(pys.get(2));
    }

    public static String[] removeArray(String[] vals, String val) {
        int index = 0;
        String tempVal = PinyinDataHelper.getSpecialDatasByKey(val);
        String[] rsVals = new String[vals.length-1];
        for(String v : vals) {
            if(!v.equals(tempVal)) {
                rsVals[index] = v;
                index++;
            }
        }
        return rsVals;
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

}

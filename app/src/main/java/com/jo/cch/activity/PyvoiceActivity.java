package com.jo.cch.activity;

import android.Manifest;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.github.promeg.pinyinhelper.Pinyin;
import com.jo.cch.R;
import com.jo.cch.bean.LetterInfo;
import com.jo.cch.dao.LearnLogDao;
import com.jo.cch.db.SPConstant;
import com.jo.cch.db.SpUtil;
import com.jo.cch.helper.LetterDataHelper;
import com.jo.cch.sql.LearnLog;
import com.jo.cch.utils.DateUtils;
import com.jo.cch.utils.PinyinUtils;
import com.jo.cch.utils.PublicUtils;
import com.jo.cch.utils.SpeechRecognizerTool;
import com.jo.cch.view.WaveLineView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PyvoiceActivity extends Activity implements SpeechRecognizerTool.ResultsCallback {

    private ImageButton mStartSpeechButton;

    private SpeechRecognizerTool mSpeechRecognizerTool = new SpeechRecognizerTool(this);

    private WaveLineView waveLineView;

    private AppCompatImageView backIv;

    private TextView letter, tv_jc, tv_total_msg, tv_redtotal_msg, tv_yes_msg, tv_no_msg;

    private List<LetterInfo> datas;

    private int index = 0;

    private int yesIndex = 0;

    private int noIndex = 0;

    private String selectGroup, selectChild;

    private List<String> errorSs = new ArrayList<String>();

    private LearnLogDao learnLogDao;

    private String logId;

    private String startDate;

    private String type = "语音";

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
        setContentView(R.layout.activity_pyvoice);
        initPermission();
        waveLineView = (WaveLineView) findViewById(R.id.waveLineView);

        //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.yes, 1);
        soundPool.load(this, R.raw.no, 1);

        letter = (TextView) findViewById(R.id.letter);
        tv_jc = (TextView) findViewById(R.id.tv_jc);
        tv_total_msg = (TextView) findViewById(R.id.tv_total_msg);
        tv_redtotal_msg = (TextView) findViewById(R.id.tv_redtotal_msg);
        tv_yes_msg = (TextView) findViewById(R.id.tv_yes_msg);
        tv_no_msg = (TextView) findViewById(R.id.tv_no_msg);

        tv_time = (TextView) findViewById(R.id.tv_time);
        TOTAL_TIME = (Integer) SpUtil.getInstance(this).getParam(SPConstant.VOICE_FLAG,10);

        logId = UUID.randomUUID().toString();
        startDate = DateUtils.getDate(new Date(), null);
        learnLogDao = new LearnLogDao(this);

        datas = (List<LetterInfo>) getIntent().getExtras().getSerializable("letters");
        selectGroup = getIntent().getStringExtra("selectGroup");
        selectChild = getIntent().getStringExtra("selectChild");

        Collections.shuffle(datas);
        letter.setText(datas.get(index).getLetter());

        setTips();
        mStartSpeechButton = (ImageButton) findViewById(R.id.startSpeechButton);
        mStartSpeechButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizerTool.startASR(PyvoiceActivity.this);
                        waveLineView.startAnim();
                        mStartSpeechButton.setImageResource(R.drawable.voice);
                        break;
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizerTool.stopASR();
                        waveLineView.stopAnim();
                        mStartSpeechButton.setImageResource(R.drawable.voice_no);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        tv_jc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
                final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(PyvoiceActivity.this);
                View view1 = View.inflate(PyvoiceActivity.this, R.layout.dialog_amend, null);
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
                            String letter = datas.get(index).getLetter();
                            String letterWord = LetterDataHelper.getWordByLetter(letter);
                            boolean flag = amend.equals(letterWord);
                            check(flag);
                        }else{
                            check(false);
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
                            check(false);
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
    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

    public void check(boolean flag){
        String noLetter = datas.get(index).getLetter();
        index++;
        if(datas.size() > index){
            String letterStr = datas.get(index).getLetter();
            isSuccess(flag, noLetter);
            setAnimatorTextView(letterStr, letter);
            sendMessage(RE_START);
        }else{
            stopTimer();
            isSuccess(flag, noLetter);
            LearnLog log = learnLogDao.queryById(logId);
            String title = selectChild+"-"+log.getNo()+ "-1";
            PinyinUtils.showDialog(PyvoiceActivity.this, new PinyinUtils.OnCancelClickListener() {
                @Override
                public void onCancelClickListener() {
                    setResult(2, getIntent());
                    stopTimer();
                    finish();
                }
            }, errorSs, datas.size(), yesIndex, noIndex, selectGroup, title);
        }
        PublicUtils.addLog(PyvoiceActivity.this, learnLogDao, logId, selectGroup, selectChild, type, datas.size() + "/" + index + "/" + yesIndex + "/" + noIndex, startDate, errorSs);
        setTips();
    }

    public void isSuccess(boolean flag, String noLetter){
        if(flag){
            yesIndex++;
            if(mtoast!=null){
                mtoast.cancel();//注销之前显示的那条信息
                mtoast=null;//这里要注意上一步相当于隐藏了信息，mtoast并没有为空，我们强制是他为空
            }
            if(mtoast==null) {
                mtoast = Toast.makeText(getApplicationContext(), "你真棒", Toast.LENGTH_SHORT);
                mtoast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout linearLayout = (LinearLayout) mtoast.getView();
                ImageView imageView = new ImageView(PyvoiceActivity.this);
                imageView.setImageResource(R.drawable.laugh);
                linearLayout.addView(imageView, 0);
                mtoast.show();
            }
            soundPool.play(1, 1, 1, 0, 0, 1);
        }else{
            noIndex++;
            errorSs.add(noLetter);
            if(mtoast!=null){
                mtoast.cancel();//注销之前显示的那条信息
                mtoast=null;//这里要注意上一步相当于隐藏了信息，mtoast并没有为空，我们强制是他为空
            }
            if(mtoast==null) {
                mtoast = Toast.makeText(getApplicationContext(), "错了哦", Toast.LENGTH_SHORT);
                mtoast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout linearLayout = (LinearLayout) mtoast.getView();
                ImageView imageView = new ImageView(PyvoiceActivity.this);
                imageView.setImageResource(R.drawable.cry);
                linearLayout.addView(imageView, 0);
                mtoast.show();
            }
            soundPool.play(2, 1, 1, 0, 0, 1);
        }
    }

    public void setTips(){
        tv_total_msg.setText(datas.size()+"");
        tv_redtotal_msg.setText((yesIndex+noIndex)+"");
        tv_yes_msg.setText(yesIndex+"");
        tv_no_msg.setText(noIndex+"");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSpeechRecognizerTool.createTool();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mSpeechRecognizerTool.destroyTool();
    }
    @Override
    public void onResults(String rsLetter) {
        final String finalLetter = rsLetter;
        PyvoiceActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String letter = datas.get(index).getLetter();
                String letterWord = LetterDataHelper.getWordByLetter(letter);
                String rspy = Pinyin.toPinyin(finalLetter, "").toLowerCase();
                boolean flag = letterWord.equals(rspy);
                if(flag){
                    check(flag);
                }else{
                    soundPool.play(2, 1, 1, 0, 0, 1);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        waveLineView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        waveLineView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
        soundPool.release();
        waveLineView.release();
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


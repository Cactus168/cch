package com.jo.cch.activity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.promeg.pinyinhelper.Pinyin;
import com.jo.cch.R;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.dao.LearnLogDao;
import com.jo.cch.db.SPConstant;
import com.jo.cch.db.SpUtil;
import com.jo.cch.utils.PinyinUtils;
import com.jo.cch.utils.PublicUtils;
import com.jo.cch.utils.YuwenUtils;
import com.jo.cch.view.FieldWordTextView;
import com.jo.cch.view.FourLineTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordpinActivity extends Activity {

    private AppCompatImageView backIv;

    private FourLineTextView wordPy;

    private FieldWordTextView word;

    private Button b_but, p_but, m_but, f_but, d_but, t_but, n_but, l_but, g_but, k_but, h_but, j_but, q_but, x_but, r_but, z_but, c_but, s_but, y_but, w_but;

    private Button a_but, o_but, e_but, i_but, u_but, v_but;

    private Button b_one, b_two, b_three, b_four;

    private Button yes, del;

    private TextView tv_wordTs, tv_num;

    private List<WordInfo> datas;

    private String currWord;

    private int totalNum = 0;

    private int learnNum = 0;

    private int learnNumx;

    private String logId;

    private String selectGroup, selectChild;

    private String startDate;

    private Class jumpClazz;

    private SoundPool soundPool; // 成功声音加载池

    private String[] as = new String[]{"ā","á","ă","à"};

    private String[] os = new String[]{"ō","ó","ŏ","ò"};

    private String[] es = new String[]{"ē","é","ĕ","è"};

    private String[] is = new String[]{"ī","í","ĭ","ì"};

    private String[] us = new String[]{"ū","ú","ŭ","ù"};

    private String[] vs = new String[]{"ǖ","ǘ","ǚ","ǜ"};

    private LearnLogDao learnLogDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordpin);

        //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.yes, 1);
        soundPool.load(this, R.raw.no, 1);

        learnLogDao = new LearnLogDao(this);

        word = (FieldWordTextView) findViewById(R.id.word);
        wordPy = (FourLineTextView) findViewById(R.id.wordPy);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_wordTs = (TextView) findViewById(R.id.tv_wordTs);

        datas = (List<WordInfo>) getIntent().getExtras().getSerializable("words");
        jumpClazz = (Class)getIntent().getSerializableExtra("jumpClazz");
        logId = getIntent().getStringExtra("logId");
        startDate = getIntent().getStringExtra("startDate");
        currWord = getIntent().getStringExtra("currWord");
        totalNum = Integer.parseInt(getIntent().getStringExtra("totalNum"));
        learnNum = Integer.parseInt(getIntent().getStringExtra("learnNum"));
        selectGroup = getIntent().getStringExtra("selectGroup");
        selectChild = getIntent().getStringExtra("selectChild");

        learnNumx = (Integer) SpUtil.getInstance(this).getParam(SPConstant.LEARN_NUM_FLAG,1);
        tv_num.setText(learnNumx+"");
        word.setText(currWord);
        wordPy.setText("");
        tv_wordTs.setText("共:"+totalNum+"个  已学"+learnNum+"个");

        initSmBut();
        initDymBut();
        initSdBut();

        yes = findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipy = wordPy.getText().toString().trim();
                if (ipy == null || ipy.equals("")) {
                    Toast.makeText(WordpinActivity.this,"请输入拼音！", Toast.LENGTH_LONG).show();
                }else {
                    String opy = PinyinUtils.converterToSpell(currWord);
                    boolean flag = false;
                    String[] opys = opy.split(",");
                    for(String op : opys){
                        if(ipy.equals(op)){
                            flag = true;
                            break;
                        }
                    }
                    if(flag){
                        soundPool.play(1, 1, 1, 0, 0, 1);
                        learnNumx = learnNumx - 1;
                        if(learnNumx == 0) {
                            PublicUtils.addLog(WordpinActivity.this, learnLogDao, logId, selectGroup, selectChild, "学习", totalNum + "/" + learnNum, startDate, new ArrayList<String>());
                            if(datas.size() > 0){
                                WordInfo w =  datas.get(0);
                                Bundle bundle = new Bundle();
                                List<WordInfo> aa = YuwenUtils.resetWords(datas, w);
                                bundle.putSerializable("words", (Serializable) YuwenUtils.resetWords(datas, w));
                                bundle.putSerializable("jumpClazz", jumpClazz);
                                Intent intent = new Intent(WordpinActivity.this, WordduActivity.class);
                                intent.putExtra("currWord", w.getWord());
                                intent.putExtra("totalNum", totalNum+"");
                                intent.putExtra("learnNum", (learnNum+1)+"");
                                intent.putExtra("logId", logId);
                                intent.putExtra("startDate", startDate);
                                intent.putExtra("selectGroup", selectGroup);
                                intent.putExtra("selectChild", selectChild);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }else{
                                Intent intent = new Intent(WordpinActivity.this, jumpClazz);
                                startActivity(intent);//启动Activity
                            }
                        }else{
                            tv_num.setText(learnNumx+"");
                        }
                        setAnimatorTextView("", wordPy);
                    }else{
                        soundPool.play(2, 1, 1, 0, 0, 1);
                    }
                }
            }
        });

        del = (Button) findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordPy.setText("");
            }
        });
        //返回
        backIv = (AppCompatImageView) findViewById(R.id.backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordpinActivity.this, jumpClazz);
                startActivity(intent);//启动Activity
            }
        });
    }

    private void initSmBut(){
        b_but = (Button) findViewById(R.id.b_but);
        p_but = (Button) findViewById(R.id.p_but);
        m_but = (Button) findViewById(R.id.m_but);
        f_but = (Button) findViewById(R.id.f_but);
        d_but = (Button) findViewById(R.id.d_but);
        t_but = (Button) findViewById(R.id.t_but);
        n_but = (Button) findViewById(R.id.n_but);
        l_but = (Button) findViewById(R.id.l_but);
        g_but = (Button) findViewById(R.id.g_but);
        k_but = (Button) findViewById(R.id.k_but);
        h_but = (Button) findViewById(R.id.h_but);
        j_but = (Button) findViewById(R.id.j_but);
        q_but = (Button) findViewById(R.id.q_but);
        x_but = (Button) findViewById(R.id.x_but);
        r_but = (Button) findViewById(R.id.r_but);
        z_but = (Button) findViewById(R.id.z_but);
        c_but = (Button) findViewById(R.id.c_but);
        s_but = (Button) findViewById(R.id.s_but);
        y_but = (Button) findViewById(R.id.y_but);
        w_but = (Button) findViewById(R.id.w_but);

        b_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"b", wordPy);
            }
        });
        p_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"p", wordPy);
            }
        });
        m_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"m", wordPy);
            }
        });
        f_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"f", wordPy);
            }
        });
        d_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"d", wordPy);
            }
        });
        t_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"t", wordPy);
            }
        });
        n_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"n", wordPy);
            }
        });
        l_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"l", wordPy);
            }
        });
        g_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"g", wordPy);
            }
        });
        k_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"k", wordPy);
            }
        });
        h_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"h", wordPy);
            }
        });
        j_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"j", wordPy);
            }
        });
        q_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"q", wordPy);
            }
        });
        x_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"x", wordPy);
            }
        });
        r_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"r", wordPy);
            }
        });
        z_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"z", wordPy);
            }
        });
        c_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"c", wordPy);
            }
        });
        s_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"s", wordPy);
            }
        });
        y_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"y", wordPy);
            }
        });
        w_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"w", wordPy);
            }
        });

    }

    private void initDymBut(){
        a_but = (Button) findViewById(R.id.a_but);
        o_but = (Button) findViewById(R.id.o_but);
        e_but = (Button) findViewById(R.id.e_but);
        i_but = (Button) findViewById(R.id.i_but);
        u_but = (Button) findViewById(R.id.u_but);
        v_but = (Button) findViewById(R.id.v_but);
        a_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"a", wordPy);
            }
        });
        o_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"o", wordPy);
            }
        });
        e_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"e", wordPy);
            }
        });
        i_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"i", wordPy);
            }
        });
        u_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"u", wordPy);
            }
        });
        v_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimatorTextView(wordPy.getText().toString()+"ü", wordPy);
            }
        });
    }

    private void initSdBut(){
        b_one = (Button) findViewById(R.id.b_one);
        b_two = (Button) findViewById(R.id.b_two);
        b_three = (Button) findViewById(R.id.b_three);
        b_four = (Button) findViewById(R.id.b_four);
        b_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSd(0);
            }
        });
        b_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSd(1);
            }
        });
        b_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSd(2);
            }
        });
        b_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSd(3);
            }
        });
    }

    private void setSd(int sdIndex){
        String py = wordPy.getText().toString();
        if(py.indexOf("a") > -1){
            String npy = py.replace("a",as[sdIndex]);
            setAnimatorTextView(npy, wordPy);
        }else if(py.indexOf("o") > -1){
            String npy = py.replace("o",os[sdIndex]);
            setAnimatorTextView(npy, wordPy);
        }else if(py.indexOf("e") > -1){
            String npy = py.replace("e",es[sdIndex]);
            setAnimatorTextView(npy, wordPy);
        }else if(py.indexOf("i") > -1){
            int iIndex = py.indexOf("i");
            int uIndex = py.indexOf("u");
            if(uIndex > -1){
                if(iIndex > uIndex){
                    String npy = py.replace("i",is[sdIndex]);
                    setAnimatorTextView(npy, wordPy);
                }else{
                    String npy = py.replace("u",us[sdIndex]);
                    setAnimatorTextView(npy, wordPy);
                }
            }else{
                String npy = py.replace("i",is[sdIndex]);
                setAnimatorTextView(npy, wordPy);
            }
        }else if (py.indexOf("u") > -1){
            String npy = py.replace("u",us[sdIndex]);
            setAnimatorTextView(npy, wordPy);
        }else if (py.indexOf("ü") > -1){
            String npy = py.replace("ü",vs[sdIndex]);
            setAnimatorTextView(npy, wordPy);
        }else{
            Pattern pa = Pattern.compile("[āáăà]");
            Matcher ma = pa.matcher(py);
            Pattern po = Pattern.compile("[ōóŏò]");
            Matcher mo = po.matcher(py);
            Pattern pe = Pattern.compile("[ēéĕè]");
            Matcher me = pe.matcher(py);
            Pattern pi = Pattern.compile("[īíĭì]");
            Matcher mi = pi.matcher(py);
            Pattern pu = Pattern.compile("[ūúŭù]");
            Matcher mu = pu.matcher(py);
            Pattern pv = Pattern.compile("[ǖǘǚǜ]");
            Matcher mv = pv.matcher(py);
            if(ma.find()){
                String npy = py.replace(ma.group(),as[sdIndex]);
                setAnimatorTextView(npy, wordPy);
            }else if(mo.find()){
                String npy = py.replace(mo.group(),os[sdIndex]);
                setAnimatorTextView(npy, wordPy);
            }else if(me.find()){
                String npy = py.replace(me.group(),es[sdIndex]);
                setAnimatorTextView(npy, wordPy);
            }else if(mi.find()){
                if(mu.find()){
                    if(mi.start() > mu.start()){
                        String npy = py.replace("i",is[sdIndex]);
                        setAnimatorTextView(npy, wordPy);
                    }else{
                        String npy = py.replace("u",us[sdIndex]);
                        setAnimatorTextView(npy, wordPy);
                    }
                }else{
                    String npy = py.replace(mi.group(),is[sdIndex]);
                    setAnimatorTextView(npy, wordPy);
                }
            }else if(mu.find()){
                String npy = py.replace(mu.group(),us[sdIndex]);
                setAnimatorTextView(npy, wordPy);
            }else if(mv.find()){
                String npy = py.replace(mv.group(),vs[sdIndex]);
                setAnimatorTextView(npy, wordPy);
            }
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
    }
}

package com.jo.cch.activity;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.jo.cch.R;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.db.SPConstant;
import com.jo.cch.db.SpUtil;
import com.jo.cch.view.FieldWordTextView;
import com.jo.cch.view.SignatureView;

import java.io.Serializable;
import java.util.List;

public class WordxieActivity extends Activity {

    private AppCompatImageView backIv;

    private FieldWordTextView word;

    private SignatureView mMSignature;

    private TextView tv_wordTs, tv_num;

    private List<WordInfo> datas;

    private String currWord;

    private int totalNum = 0;

    private int learnNum = 0;

    private int learnNumx;

    private String logId;

    private String startDate;

    private String selectGroup, selectChild;

    private Class jumpClazz;

    private SoundPool soundPool; // 成功声音加载池

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordxie);

        mMSignature = (SignatureView)findViewById(R.id.gsv_signature);

        //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.yes, 1);
        soundPool.load(this, R.raw.no, 1);

        word = (FieldWordTextView) findViewById(R.id.word);
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
        word.setText("");

        tv_wordTs.setText("共:"+totalNum+"个  已学"+learnNum+"个");

        //清空
        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMSignature.clear();
                word.setText("");
            }
        });

        //保存
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMSignature.getTouched()){
                    learnNumx = learnNumx - 1;
                    word.setText(currWord);
                    if(learnNumx == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("words", (Serializable) datas);
                        bundle.putSerializable("jumpClazz", jumpClazz);
                        Intent intent = new Intent(WordxieActivity.this, WordpinActivity.class);
                        intent.putExtra("currWord", currWord);
                        intent.putExtra("totalNum", totalNum+"");
                        intent.putExtra("learnNum", learnNum+"");
                        intent.putExtra("logId", logId);
                        intent.putExtra("startDate", startDate);
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", selectChild);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }else{
                        tv_num.setText(learnNumx+"");
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "请写字！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //返回
        backIv = (AppCompatImageView) findViewById(R.id.backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordxieActivity.this, jumpClazz);
                startActivity(intent);//启动Activity
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
    }


}

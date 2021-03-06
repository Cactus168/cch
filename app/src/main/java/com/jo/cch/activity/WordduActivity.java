package com.jo.cch.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.jo.cch.R;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.db.SPConstant;
import com.jo.cch.db.SpUtil;
import com.jo.cch.utils.DateUtils;
import com.jo.cch.utils.PinyinUtils;
import com.jo.cch.utils.PublicUtils;
import com.jo.cch.utils.SpeechRecognizerTool;
import com.jo.cch.utils.SqliteUtils;
import com.jo.cch.view.FieldWordTextView;
import com.jo.cch.view.FourLineTextView;
import com.jo.cch.view.WaveLineView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class WordduActivity extends Activity implements SpeechRecognizerTool.ResultsCallback {

    private ImageButton mStartSpeechButton;

    private SpeechRecognizerTool mSpeechRecognizerTool = new SpeechRecognizerTool(this);

    private WaveLineView waveLineView;

    private AppCompatImageView backIv;

    private FourLineTextView wordPy;

    private FieldWordTextView word;

    private TextView tv_wordTs, tv_wordBs, tv_wordBh, tv_wordYs, tv_num;

    private List<WordInfo> datas;

    private String currWord;

    private int totalNum = 0;

    private int learnNum = 0;

    private int learnNumx;

    private String logId;

    private String startDate;

    private String selectGroup, selectChild;

    private Class jumpClazz;

    private SoundPool soundPool; // ?????????????????????

    /**
     * TessBaseAPI???????????????????????????????????????????????????
     */
    private static final String DATAPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    /**
     * ???DATAPATH????????????????????????
     */
    private static final String cchdata = DATAPATH + File.separator + "cchdata";
    /**
     * TessBaseAPI????????????????????????????????????????????????????????????????????????
     */
    private static final String DEFAULT_LANGUAGE = "word";
    /**
     * assets???????????????
     */
    private static final String DEFAULT_LANGUAGE_NAME = DEFAULT_LANGUAGE + ".mbtiles";
    /**
     * ?????????SD????????????????????????
     */
    private static final String LANGUAGE_PATH = cchdata + File.separator + DEFAULT_LANGUAGE_NAME;

    /**
     * ???????????????
     */
    private static final int PERMISSION_REQUEST_CODE=0;

    private String[] as = new String[]{"??","??","??","??"};

    private String[] os = new String[]{"??","??","??","??"};

    private String[] es = new String[]{"??","??","??","??"};

    private String[] is = new String[]{"??","??","??","??"};

    private String[] us = new String[]{"??","??","??","??"};

    private String[] vs = new String[]{"??","??","??","??"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worddu);

        PublicUtils.initPermission(this,this);
        waveLineView = (WaveLineView) findViewById(R.id.waveLineView);

        //???????????????????????????????????????????????????????????????????????????????????????????????????
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.yes, 1);
        soundPool.load(this, R.raw.no, 1);

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
        String pinyins = PinyinUtils.converterToSpell(currWord);
        if(pinyins.indexOf(",") > -1){
            String py = Pinyin.toPinyin(currWord,"").toLowerCase();
            for(String p : pinyins.split(",")){
                String tpy = p;
                for(String a : as){
                    tpy = tpy.replace(a,"a");
                }
                for(String o : os){
                    tpy = tpy.replace(o,"o");
                }
                for(String e : es){
                    tpy = tpy.replace(e,"e");
                }
                for(String i : is){
                    tpy = tpy.replace(i,"i");
                }
                for(String u : us){
                    tpy = tpy.replace(u,"u");
                }
                for(String v : vs){
                    tpy = tpy.replace(v,"??");
                }
                if(tpy.equals(py)){
                    wordPy.setText(p);
                    break;
                }
            }
        }else{
            wordPy.setText(pinyins);
        }
        tv_wordTs.setText("???:"+totalNum+"???  ??????"+learnNum+"???");

        /*if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }*/

        /*//Android6.0??????????????????????????????6.0?????????????????????????????????6.0??????????????????????????????
        copyToSD(LANGUAGE_PATH, DEFAULT_LANGUAGE_NAME);

        initData();*/

        mStartSpeechButton = (ImageButton) findViewById(R.id.startSpeechButton);
        mStartSpeechButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizerTool.startASR(WordduActivity.this);
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

        //??????
        backIv = (AppCompatImageView) findViewById(R.id.backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordduActivity.this, jumpClazz);
                startActivity(intent);//??????Activity
            }
        });

    }

    public void initData(){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = SqliteUtils.getConnection();
            //??????sql
            String sql = "SELECT * FROM t_word WHERE word = \""+currWord+"\"";
            //????????????sql?????????
            st = conn.createStatement();
            //??????sql
            rs = st.executeQuery(sql);
            while (rs.next()) {
                tv_wordBs.setText("?????????"+rs.getString("radicals"));
                tv_wordBh.setText("?????????"+rs.getString("strokes")+"???");
                tv_wordYs.setText("?????????"+rs.getString("explanation").replace("\\n","  "));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            SqliteUtils.close(rs,st,conn);
        }

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
    public void onResults(String rsWord) {
        final String finalWord = rsWord;
        WordduActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String opy = Pinyin.toPinyin(currWord, "").toLowerCase();
                String rspy = PinyinUtils.converterToSpellx(finalWord);
                boolean flag = (rspy.indexOf(opy) > -1);
                if(flag){
                    soundPool.play(1, 1, 1, 0, 0, 1);
                    learnNumx = learnNumx - 1;
                    if(learnNumx == 0){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("words", (Serializable) datas);
                        bundle.putSerializable("jumpClazz", jumpClazz);
                        Intent intent = new Intent(WordduActivity.this, WordxieActivity.class);
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
        soundPool.release();
        waveLineView.release();
    }

    /**
     * ???assets????????????????????????SD??????
     * @param path  ????????????SD????????? ??????????????????????????????"/storage/emulated/0/cchdata/word.mbtiles"
     * @param name  assets??????????????? ????????? "word.mbtiles"
     */
    public void copyToSD(String path, String name) {

        //?????????????????????
        File f = new File(path);
        if (f.exists()){
            f.delete();
        }
        if (!f.exists()){
            File p = new File(f.getParent());
            if (!p.exists()){
                p.mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        InputStream is=null;
        OutputStream os=null;
        try {
            is = this.getAssets().open(name);
            File file = new File(path);
            os = new FileOutputStream(file);
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * ??????????????????????????????????????????
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    copyToSD(LANGUAGE_PATH, DEFAULT_LANGUAGE_NAME);
                }
                break;
            default:
                break;
        }
    }
}

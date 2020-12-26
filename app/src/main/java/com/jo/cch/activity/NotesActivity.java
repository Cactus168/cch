package com.jo.cch.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jo.cch.R;
import com.jo.cch.adapter.LiteracyAdapter;
import com.jo.cch.adapter.NotesAdapter;
import com.jo.cch.adapter.WordGridAdapter;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.dao.LearnLogDao;
import com.jo.cch.db.SPConstant;
import com.jo.cch.db.SpUtil;
import com.jo.cch.helper.WordDataHelper;
import com.jo.cch.sql.LearnLog;
import com.jo.cch.utils.DateUtils;
import com.jo.cch.utils.PublicUtils;
import com.jo.cch.utils.PublicUtils.OnConfirmClickListener;
import com.jo.cch.utils.YuwenUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotesActivity extends Activity {

    private AppCompatImageView backIv;

    private GridView gview;

    private RelativeLayout toolBar;

    private ExpandableListView expandableListView;

    private WordGridAdapter wordAdapter;

    private List<WordInfo> datas = new ArrayList<>();

    private Button addWord, selectMode, speedMode, pinMode, pinYinMode, yuYinMode, goLearnMode;

    private TextView tips;

    private String selectGroup = "识字笔记", selectChild = " 笔记";

    private boolean iSSelectModel = false;

    private LearnLogDao learnLogDao;

    private List<WordInfo> selectDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        tips = (TextView) findViewById(R.id.tips);
        gview = (GridView) findViewById(R.id.gview);
        toolBar = (RelativeLayout) findViewById(R.id.toolBar);
        addWord = (Button) findViewById(R.id.addWord);

        learnLogDao = new LearnLogDao(this);

        expandableListView = (ExpandableListView) findViewById(R.id.expend_list);

        List<LearnLog> list = SpUtil.getInstance(this).getList(SPConstant.LEARN_LOG_FLAG, LearnLog.class);
        LearnLog[] logs = new LearnLog[list.size()];
        for(int i = 0; i < list.size(); i++){
            LearnLog log = list.get(i);
            logs[i] = log;
        }
        LearnLog log = new LearnLog();
        log.setSubject("生字表");
        LearnLog[][] childString = {new LearnLog[]{log}, logs};
        final NotesAdapter ccAdapter = new NotesAdapter(childString);

        expandableListView.setAdapter(ccAdapter);
        //设置子项布局监听
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                v.setSelected(true);
                if(iSSelectModel){
                    iSSelectModel = false;
                    selectDatas.clear();
                    tips.setText("共"+datas.size()+"个字");
                    for (WordInfo w : datas){
                        w.setChecked(false);
                    }
                    wordAdapter.notifyDataSetChanged();
                    selectMode.setText("选取模式");
                }
                datas.clear();
                String group = ccAdapter.groupString[groupPosition];
                LearnLog log = ccAdapter.childString[groupPosition][childPosition];
                String child = log.getSubject().replace("语文/","");
                if(log.getNo() != null){
                    child = child+"-"+log.getNo();
                }
                selectGroup = group;
                selectChild = child;
                if(child.equals("生字表")){
                    datas.addAll(SpUtil.getInstance(NotesActivity.this).getList(selectGroup, WordInfo.class));
                    tips.setText("共"+datas.size()+"个字");
                    toolBar.setVisibility(View.VISIBLE);
                    addWord.setVisibility(View.VISIBLE);
                }else{
                    String[] ws = log.getErrorMgs().split(",");
                    List<WordInfo> words = new ArrayList<>();
                    for(String w : ws){
                        words.add(new WordInfo(w));
                    }
                    datas.addAll(words);
                    tips.setText("共"+datas.size()+"个字");
                    toolBar.setVisibility(View.VISIBLE);
                    addWord.setVisibility(View.GONE);
                }
                wordAdapter.notifyDataSetChanged();
                return true;
            }
        });
        //新建List
        wordAdapter = new WordGridAdapter(this,R.layout.word_item, datas);
        //配置适配器
        gview.setAdapter(wordAdapter);

        gview.setOnItemClickListener(new GridView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                if(iSSelectModel){
                    WordInfo w = datas.get(position);
                    if(w.isChecked()){
                        w.setChecked(false);
                        selectDatas.remove(w);
                    }else{
                        w.setChecked(true);
                        selectDatas.add(w);
                    }
                    tips.setText("共"+datas.size()+"个字,已选择"+selectDatas.size()+"个");
                    wordAdapter.notifyDataSetChanged();
                }
            }
        });

        gview.setOnItemLongClickListener(new GridView.OnItemLongClickListener(){//设置事件监听(长按)
            public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
                if(!iSSelectModel){
                    final WordInfo w = datas.get(position);
                    PublicUtils.showDelDialog(NotesActivity.this, new OnConfirmClickListener() {
                        @Override
                        public void onConfirmClickListener() {
                            datas.remove(w);
                            tips.setText("共"+datas.size()+"个字");
                            SpUtil.getInstance(NotesActivity.this).remove(selectGroup);
                            SpUtil.getInstance(NotesActivity.this).saveList(selectGroup,datas);
                            wordAdapter.notifyDataSetChanged();
                        }
                    }, new PublicUtils.OnCancelClickListener() {
                        @Override
                        public void onCancelClickListener() {

                        }
                    });
                }
                return true;
            }
        });

        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initOtherPopupMenu();
            }
        });

        selectMode = (Button) findViewById(R.id.selectMode);
        selectMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("选取模式".equals(selectMode.getText())){
                    initPopupMenu();
                }else{
                    iSSelectModel = false;
                    selectDatas.clear();
                    tips.setText("共"+datas.size()+"个字");
                    for (WordInfo w : datas){
                        w.setChecked(false);
                    }
                    wordAdapter.notifyDataSetChanged();
                    selectMode.setText("选取模式");
                }

            }
        });
        speedMode = (Button) findViewById(R.id.speedMode);
        speedMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(iSSelectModel){
                    if(selectDatas.size() > 0){
                        bundle.putSerializable("words", (Serializable) selectDatas);
                        Intent intent = new Intent(NotesActivity.this, SpeedActivity.class);
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", "快速认字");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(NotesActivity.this,"请选择要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(datas.size() > 0){
                        bundle.putSerializable("words", (Serializable) datas);
                        Intent intent = new Intent(NotesActivity.this, SpeedActivity.class);
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", "快速认字");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(NotesActivity.this,"请添加要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        pinMode = (Button) findViewById(R.id.pinMode);
        pinMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(iSSelectModel){
                    if(selectDatas.size() > 0){
                        bundle.putSerializable("words", (Serializable) selectDatas);
                        Intent intent = new Intent(NotesActivity.this, LearnActivity.class);
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", "拼写认字");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(NotesActivity.this,"请选择要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(datas.size() > 0){
                        bundle.putSerializable("words", (Serializable) datas);
                        Intent intent = new Intent(NotesActivity.this, LearnActivity.class);
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", "拼写认字");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(NotesActivity.this,"请添加要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        pinYinMode = (Button) findViewById(R.id.pinYinMode);
        pinYinMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(iSSelectModel){
                    if(selectDatas.size() > 0){
                        bundle.putSerializable("words", (Serializable) selectDatas);
                        Intent intent = new Intent(NotesActivity.this, PinyinknowActivity.class);
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", "拼音认字");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(NotesActivity.this,"请选择要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(datas.size()>0){
                        bundle.putSerializable("words", (Serializable) datas);
                        Intent intent = new Intent(NotesActivity.this, PinyinknowActivity.class);
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", "拼音认字");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(NotesActivity.this,"请添加要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        yuYinMode = (Button) findViewById(R.id.yuYinMode);
        yuYinMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(iSSelectModel){
                    if(selectDatas.size() > 0){
                        bundle.putSerializable("words", (Serializable) selectDatas);
                        Intent intent = new Intent(NotesActivity.this, VoiceActivity.class);
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", "语音认字");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(NotesActivity.this,"请选择要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    bundle.putSerializable("words", (Serializable) datas);
                    Intent intent = new Intent(NotesActivity.this, VoiceActivity.class);
                    intent.putExtra("selectGroup", selectGroup);
                    intent.putExtra("selectChild", "语音认字");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        goLearnMode = (Button) findViewById(R.id.goLearnMode);
        goLearnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(iSSelectModel){
                    if(selectDatas.size() > 0){
                        WordInfo w = datas.get(0);
                        bundle.putSerializable("words", (Serializable) YuwenUtils.resetWords(datas, w));
                        bundle.putSerializable("jumpClazz", (Serializable) NotesActivity.class);
                        Intent intent = new Intent(NotesActivity.this, WordduActivity.class);
                        intent.putExtra("currWord", w.getWord());
                        intent.putExtra("totalNum", datas.size()+"");
                        intent.putExtra("learnNum", 0+"");
                        intent.putExtra("logId", UUID.randomUUID().toString());
                        intent.putExtra("startDate", DateUtils.getDate(new Date(), null));
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", "生字学习");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(NotesActivity.this,"请选择要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    WordInfo w = datas.get(0);
                    bundle.putSerializable("words", (Serializable) YuwenUtils.resetWords(datas, w));
                    bundle.putSerializable("jumpClazz", (Serializable) NotesActivity.class);
                    Intent intent = new Intent(NotesActivity.this, WordduActivity.class);
                    intent.putExtra("currWord", w.getWord());
                    intent.putExtra("totalNum", datas.size()+"");
                    intent.putExtra("learnNum", 0+"");
                    intent.putExtra("logId", UUID.randomUUID().toString());
                    intent.putExtra("startDate", DateUtils.getDate(new Date(), null));
                    intent.putExtra("selectGroup", selectGroup);
                    intent.putExtra("selectChild", "生字学习");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
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

    private PopupWindow popupMenu;

    //点击我的菜单
    private void initPopupMenu(){
        if (popupMenu== null ){
            LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = lay.inflate(R.layout.popup_menu,null);
            //点击快速选择
            ((Button)v.findViewById(R.id.btn_my_favorites)).setOnClickListener( new  View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    popupMenu.dismiss();
                    final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(NotesActivity.this);
                    View view1 = View.inflate(NotesActivity.this, R.layout.dialog_item, null);
                    final EditText et_start = (EditText) view1.findViewById(R.id.et_start);
                    final EditText et_end = (EditText) view1.findViewById(R.id.et_end);
                    et_end.setText(datas.size()+"");
                    Button but_qd = (Button) view1.findViewById(R.id.but_qd);
                    Button but_qx = (Button) view1.findViewById(R.id.but_qx);
                    alertDialog7.setView(view1).create();
                    final AlertDialog show = alertDialog7.show();
                    but_qd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int start = Integer.parseInt(et_start.getText().toString());
                            int end = Integer.parseInt(et_end.getText().toString());
                            if(end <= datas.size()){
                                if(start <= end){
                                    for (int i = start; i <= end; i++){
                                        WordInfo w = datas.get(i-1);
                                        w.setChecked(true);
                                        selectDatas.add(w);
                                    }
                                    tips.setText("共"+datas.size()+"个字,已选择"+selectDatas.size()+"个");
                                    selectMode.setText("取消选择");
                                    wordAdapter.notifyDataSetChanged();
                                    iSSelectModel = true;
                                    show.dismiss();
                                }else{
                                    Toast.makeText(NotesActivity.this,"开始位置不能大于结束位置！", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(NotesActivity.this,"总共"+datas.size()+"个字已超出范围！", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    but_qx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            show.dismiss();
                        }
                    });
                }
            });
            //点击自由选择
            ((Button)v.findViewById(R.id.btn_my_correction)).setOnClickListener( new  View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    popupMenu.dismiss();
                    tips.setText("共"+datas.size()+"个字,已选择"+selectDatas.size()+"个");
                    selectMode.setText("取消选择");
                    iSSelectModel = true;
                }
            });

            popupMenu = new PopupWindow(v,selectMode.getWidth(),getApplicationContext().getResources().getDisplayMetrics().heightPixels/7,true);
        }
        //设置整个popupwindow的样式。
        popupMenu.setBackgroundDrawable( new BitmapDrawable());
        //使窗口里面的空间显示其相应的效果，比较点击button时背景颜色改变。
        //如果为false点击相关的空间表面上没有反应，但事件是可以监听到的。
        //listview的话就没有了作用。
        popupMenu.setFocusable( true );
        popupMenu.setOutsideTouchable( true );
        popupMenu.update();

        popupMenu.showAsDropDown(selectMode);
    }

    private  PopupWindow otherPopupMenu;

    //点击我的菜单
    private void initOtherPopupMenu(){
        if (otherPopupMenu== null ){
            LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = lay.inflate(R.layout.otherpopup_menu,null);
            //点击快速删除
            ((Button)v.findViewById(R.id.btn_del)).setOnClickListener( new  View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    otherPopupMenu.dismiss();
                    final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(NotesActivity.this);
                    View view1 = View.inflate(NotesActivity.this, R.layout.dialog_delitem, null);
                    final EditText et_start = (EditText) view1.findViewById(R.id.et_start);
                    final EditText et_end = (EditText) view1.findViewById(R.id.et_end);
                    et_end.setText(datas.size()+"");
                    Button but_qd = (Button) view1.findViewById(R.id.but_qd);
                    Button but_qx = (Button) view1.findViewById(R.id.but_qx);
                    alertDialog7.setCancelable(false);
                    alertDialog7.setView(view1).create();
                    final AlertDialog show = alertDialog7.show();
                    but_qd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int start = Integer.parseInt(et_start.getText().toString());
                            int end = Integer.parseInt(et_end.getText().toString());
                            if(end <= datas.size()){
                                if(start <= end){
                                    List<WordInfo> ws = new ArrayList<>();
                                    for (int i = start; i <= end; i++){
                                        ws.add(datas.get(i-1));
                                    }
                                    for(WordInfo w : ws){
                                        datas.remove(w);
                                    }
                                    tips.setText("共"+datas.size()+"个字");
                                    SpUtil.getInstance(NotesActivity.this).remove(selectGroup);
                                    SpUtil.getInstance(NotesActivity.this).saveList(selectGroup,datas);
                                    wordAdapter.notifyDataSetChanged();
                                    show.dismiss();
                                }else{
                                    Toast.makeText(NotesActivity.this,"开始位置不能大于结束位置！", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(NotesActivity.this,"总共"+datas.size()+"个字已超出范围！", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    but_qx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            show.dismiss();
                        }
                    });
                }
            });
            //点击添加汉字
            ((Button)v.findViewById(R.id.btn_add)).setOnClickListener( new  View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    otherPopupMenu.dismiss();
                    final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(NotesActivity.this);
                    View view1 = View.inflate(NotesActivity.this, R.layout.dialog_additem, null);
                    final EditText et_word = (EditText) view1.findViewById(R.id.et_word);
                    Button but_qd = (Button) view1.findViewById(R.id.but_qd);
                    Button but_qx = (Button) view1.findViewById(R.id.but_qx);
                    alertDialog7.setCancelable(false);
                    alertDialog7.setView(view1).create();
                    final AlertDialog show = alertDialog7.show();
                    but_qd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String word = et_word.getText().toString();
                            String regex = "[\u4e00-\u9fa5]";
                            ArrayList<String> list = new ArrayList<String>();// 定义一个列表用于存放找到的中文
                            Pattern pattern = Pattern.compile(regex);// 定义模式，（模具）
                            Matcher matcher = pattern.matcher(word); // 匹配结果
                            while (matcher.find()) { // 匹配结果读找到第一个
                                list.add(matcher.group());
                            }
                            if(list.size() > 0){
                                WordDataHelper.addErrorWord(NotesActivity.this, selectGroup, list.get(0));
                                datas.add(new WordInfo(word));
                                tips.setText("共"+datas.size()+"个字");
                                wordAdapter.notifyDataSetChanged();
                            }
                            show.dismiss();
                        }
                    });
                    but_qx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            show.dismiss();
                        }
                    });
                }
            });

            otherPopupMenu = new PopupWindow(v,addWord.getWidth(),getApplicationContext().getResources().getDisplayMetrics().heightPixels/7,true);
        }
        //设置整个popupwindow的样式。
        otherPopupMenu.setBackgroundDrawable( new BitmapDrawable());
        //使窗口里面的空间显示其相应的效果，比较点击button时背景颜色改变。
        //如果为false点击相关的空间表面上没有反应，但事件是可以监听到的。
        //listview的话就没有了作用。
        otherPopupMenu.setFocusable( true );
        otherPopupMenu.setOutsideTouchable( true );
        otherPopupMenu.update();

        otherPopupMenu.showAsDropDown(addWord);
    }
}

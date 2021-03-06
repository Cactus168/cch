package com.jo.cch.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.jo.cch.adapter.WordGridAdapter;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.db.SpUtil;
import com.jo.cch.helper.WordDataHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiteracyActivity extends Activity {

    private AppCompatImageView backIv;

    private GridView gview;

    private RelativeLayout toolBar;

    private WordGridAdapter wordAdapter;

    private List<WordInfo> datas = new ArrayList<>();

    private ExpandableListView expandableListView;

    final LiteracyAdapter ccAdapter = new LiteracyAdapter();

    private Button addWord, selectMode, speedMode, pinMode, pinYinMode, yuYinMode;

    private TextView tips;

    private String selectGroup, selectChild;

    private boolean iSSelectModel = false;

    private List<WordInfo> selectDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_literacy);
        tips = (TextView) findViewById(R.id.tips);
        gview = (GridView) findViewById(R.id.gview);
        toolBar = (RelativeLayout) findViewById(R.id.toolBar);
        addWord = (Button) findViewById(R.id.addWord);
        //??????List
        wordAdapter = new WordGridAdapter(this, R.layout.word_item, datas);
        //???????????????
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
                    tips.setText("???"+datas.size()+"??????,?????????"+selectDatas.size()+"???");
                    wordAdapter.notifyDataSetChanged();
                }
            }
        });

        gview.setOnItemLongClickListener(new GridView.OnItemLongClickListener(){//??????????????????(??????)
            public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
                if(!iSSelectModel && selectChild.equals("?????????")){
                    final WordInfo w = datas.get(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(LiteracyActivity.this);
                    builder.setIcon(R.mipmap.logo);
                    builder.setTitle("????????????");
                    builder.setMessage("??????????????????????????????");
                    builder.setCancelable(false);
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            datas.remove(w);
                            tips.setText("???"+datas.size()+"??????");
                            SpUtil.getInstance(LiteracyActivity.this).remove(selectGroup);
                            SpUtil.getInstance(LiteracyActivity.this).saveList(selectGroup,datas);
                            wordAdapter.notifyDataSetChanged();
                            if(datas.size() == 0){
                                toolBar.setVisibility(View.GONE);
                            }
                        }
                    });
                    builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    builder.create().show();
                }
                return true;
            }
        });

        expandableListView = (ExpandableListView) findViewById(R.id.expend_list);

        expandableListView.setAdapter(ccAdapter);
        //????????????????????????
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                v.setSelected(true);
                if(iSSelectModel){
                    iSSelectModel = false;
                    selectDatas.clear();
                    tips.setText("???"+datas.size()+"??????");
                    for (WordInfo w : datas){
                        w.setChecked(false);
                    }
                    wordAdapter.notifyDataSetChanged();
                    selectMode.setText("????????????");
                }
                datas.clear();
                String group = ccAdapter.groupString[groupPosition];
                String child = ccAdapter.childString[groupPosition][childPosition];
                Map<String, List<WordInfo>> groupData = WordDataHelper.datas.get(group);
                if(groupData != null){
                    selectGroup = group;
                    selectChild = child;
                    List<WordInfo> words = WordDataHelper.getDatas(LiteracyActivity.this, group, child);
                    if(words != null){
                        datas.addAll(words);
                        tips.setText("???"+datas.size()+"??????");
                        if(datas.size() > 0){
                            toolBar.setVisibility(View.VISIBLE);
                        }else{
                            toolBar.setVisibility(View.GONE);
                        }
                    }else{
                        toolBar.setVisibility(View.GONE);
                    }
                    if(child.equals("?????????")){
                        addWord.setVisibility(View.VISIBLE);
                    }else{
                        addWord.setVisibility(View.GONE);
                    }
                }else{
                    toolBar.setVisibility(View.GONE);
                }
                wordAdapter.notifyDataSetChanged();
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
            if("????????????".equals(selectMode.getText())){
                initPopupMenu();
            }else{
                iSSelectModel = false;
                selectDatas.clear();
                tips.setText("???"+datas.size()+"??????");
                for (WordInfo w : datas){
                    w.setChecked(false);
                }
                wordAdapter.notifyDataSetChanged();
                selectMode.setText("????????????");
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
                        Intent intent = new Intent(LiteracyActivity.this, SpeedActivity.class);
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", selectChild);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LiteracyActivity.this,"???????????????????????????", Toast.LENGTH_LONG).show();
                    }
                }else{
                    bundle.putSerializable("words", (Serializable) datas);
                    Intent intent = new Intent(LiteracyActivity.this, SpeedActivity.class);
                    intent.putExtra("selectGroup", selectGroup);
                    intent.putExtra("selectChild", selectChild);
                    intent.putExtras(bundle);
                    startActivity(intent);
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
                        Intent intent = new Intent(LiteracyActivity.this, LearnActivity.class);
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", selectChild);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LiteracyActivity.this,"???????????????????????????", Toast.LENGTH_LONG).show();
                    }
                }else{
                    bundle.putSerializable("words", (Serializable) datas);
                    Intent intent = new Intent(LiteracyActivity.this, LearnActivity.class);
                    intent.putExtra("selectGroup", selectGroup);
                    intent.putExtra("selectChild", selectChild);
                    intent.putExtras(bundle);
                    startActivity(intent);
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
                        Intent intent = new Intent(LiteracyActivity.this, PinyinknowActivity.class);
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", selectChild);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LiteracyActivity.this,"???????????????????????????", Toast.LENGTH_LONG).show();
                    }
                }else{
                    bundle.putSerializable("words", (Serializable) datas);
                    Intent intent = new Intent(LiteracyActivity.this, PinyinknowActivity.class);
                    intent.putExtra("selectGroup", selectGroup);
                    intent.putExtra("selectChild", selectChild);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        yuYinMode = (Button) findViewById(R.id.pinYinMode);
        yuYinMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if(iSSelectModel){
                    if(selectDatas.size() > 0){
                        bundle.putSerializable("words", (Serializable) selectDatas);
                        Intent intent = new Intent(LiteracyActivity.this, VoiceActivity.class);
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", selectChild);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LiteracyActivity.this,"???????????????????????????", Toast.LENGTH_LONG).show();
                    }
                }else{
                    bundle.putSerializable("words", (Serializable) datas);
                    Intent intent = new Intent(LiteracyActivity.this, VoiceActivity.class);
                    intent.putExtra("selectGroup", selectGroup);
                    intent.putExtra("selectChild", selectChild);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        //??????
        backIv = (AppCompatImageView) findViewById(R.id.backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private  PopupWindow popupMenu;

    //??????????????????
    private void initPopupMenu(){
        if (popupMenu== null ){
            LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = lay.inflate(R.layout.popup_menu,null);
            //??????????????????
            ((Button)v.findViewById(R.id.btn_my_favorites)).setOnClickListener( new  View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    popupMenu.dismiss();
                    final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(LiteracyActivity.this);
                    View view1 = View.inflate(LiteracyActivity.this, R.layout.dialog_item, null);
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
                                    tips.setText("???"+datas.size()+"??????,?????????"+selectDatas.size()+"???");
                                    selectMode.setText("????????????");
                                    wordAdapter.notifyDataSetChanged();
                                    iSSelectModel = true;
                                    show.dismiss();
                                }else{
                                    Toast.makeText(LiteracyActivity.this,"???????????????????????????????????????", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(LiteracyActivity.this,"??????"+datas.size()+"????????????????????????", Toast.LENGTH_LONG).show();
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
            //??????????????????
            ((Button)v.findViewById(R.id.btn_my_correction)).setOnClickListener( new  View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    popupMenu.dismiss();
                    tips.setText("???"+datas.size()+"??????,?????????"+selectDatas.size()+"???");
                    selectMode.setText("????????????");
                    iSSelectModel = true;
                }
            });

            popupMenu = new PopupWindow(v,selectMode.getWidth(),getApplicationContext().getResources().getDisplayMetrics().heightPixels/7,true);
        }
        //????????????popupwindow????????????
        popupMenu.setBackgroundDrawable( new BitmapDrawable());
        //???????????????????????????????????????????????????????????????button????????????????????????
        //?????????false??????????????????????????????????????????????????????????????????????????????
        //listview???????????????????????????
        popupMenu.setFocusable( true );
        popupMenu.setOutsideTouchable( true );
        popupMenu.update();

        popupMenu.showAsDropDown(selectMode);
    }

    private  PopupWindow otherPopupMenu;

    //??????????????????
    private void initOtherPopupMenu(){
        if (otherPopupMenu== null ){
            LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = lay.inflate(R.layout.otherpopup_menu,null);
            //??????????????????
            ((Button)v.findViewById(R.id.btn_del)).setOnClickListener( new  View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    otherPopupMenu.dismiss();
                    final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(LiteracyActivity.this);
                    View view1 = View.inflate(LiteracyActivity.this, R.layout.dialog_delitem, null);
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
                                    List<WordInfo> ws = new ArrayList<>();
                                    for (int i = start; i <= end; i++){
                                        ws.add(datas.get(i-1));
                                    }
                                    for(WordInfo w : ws){
                                        datas.remove(w);
                                    }
                                    tips.setText("???"+datas.size()+"??????");
                                    SpUtil.getInstance(LiteracyActivity.this).remove(selectGroup);
                                    SpUtil.getInstance(LiteracyActivity.this).saveList(selectGroup,datas);
                                    wordAdapter.notifyDataSetChanged();
                                    if(datas.size() == 0){
                                        toolBar.setVisibility(View.GONE);
                                    }
                                    show.dismiss();
                                }else{
                                    Toast.makeText(LiteracyActivity.this,"???????????????????????????????????????", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(LiteracyActivity.this,"??????"+datas.size()+"????????????????????????", Toast.LENGTH_LONG).show();
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
            //??????????????????
            ((Button)v.findViewById(R.id.btn_add)).setOnClickListener( new  View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    otherPopupMenu.dismiss();
                    final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(LiteracyActivity.this);
                    View view1 = View.inflate(LiteracyActivity.this, R.layout.dialog_additem, null);
                    final EditText et_word = (EditText) view1.findViewById(R.id.et_word);
                    Button but_qd = (Button) view1.findViewById(R.id.but_qd);
                    Button but_qx = (Button) view1.findViewById(R.id.but_qx);
                    alertDialog7.setView(view1).create();
                    final AlertDialog show = alertDialog7.show();
                    but_qd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String word = et_word.getText().toString();
                            String regex = "[\u4e00-\u9fa5]";
                            ArrayList<String> list = new ArrayList<String>();// ?????????????????????????????????????????????
                            Pattern pattern = Pattern.compile(regex);// ???????????????????????????
                            Matcher matcher = pattern.matcher(word); // ????????????
                            while (matcher.find()) { // ??????????????????????????????
                                list.add(matcher.group());
                            }
                            if(list.size() > 0){
                                WordDataHelper.addErrorWord(LiteracyActivity.this, selectGroup, list.get(0));
                                datas.add(new WordInfo(word));
                                tips.setText("???"+datas.size()+"??????");
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
        //????????????popupwindow????????????
        otherPopupMenu.setBackgroundDrawable( new BitmapDrawable());
        //???????????????????????????????????????????????????????????????button????????????????????????
        //?????????false??????????????????????????????????????????????????????????????????????????????
        //listview???????????????????????????
        otherPopupMenu.setFocusable( true );
        otherPopupMenu.setOutsideTouchable( true );
        otherPopupMenu.update();

        otherPopupMenu.showAsDropDown(addWord);
    }

}

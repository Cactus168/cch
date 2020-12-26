package com.jo.cch.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;

import com.jo.cch.R;
import com.jo.cch.adapter.LetterGridAdapter;
import com.jo.cch.adapter.PinyinAdapter;
import com.jo.cch.adapter.WordGridAdapter;
import com.jo.cch.bean.LetterInfo;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.db.SpUtil;
import com.jo.cch.helper.LetterDataHelper;
import com.jo.cch.helper.WordDataHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PinyinActivity extends Activity {

    private AppCompatImageView backIv;

    private GridView gview;

    private LetterGridAdapter letterAdapter;

    private List<LetterInfo> datas = new ArrayList<>();

    private ExpandableListView expandableListView;

    final PinyinAdapter pyAdapter = new PinyinAdapter();

    private Button startStudy, voiceStudy;

    private String selectGroup = "全部", selectChild = "全部";

    private TextView tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinyin);
        tips = (TextView) findViewById(R.id.tips);
        gview = (GridView) findViewById(R.id.gview);
        datas = LetterDataHelper.getAllDatas();
        //新建List
        letterAdapter = new LetterGridAdapter(this, datas);
        //配置适配器
        gview.setAdapter(letterAdapter);

        tips.setText("共"+datas.size()+"个字母");

        expandableListView = (ExpandableListView) findViewById(R.id.expend_list);

        expandableListView.setAdapter(pyAdapter);
        //设置子项布局监听
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                v.setSelected(true);
                datas.clear();
                String group = pyAdapter.groupString[groupPosition];
                String child = pyAdapter.childString[groupPosition][childPosition];
                Map<String, List<LetterInfo>> groupData = LetterDataHelper.datas.get(group);
                if(groupData != null){
                    selectGroup = group;
                    selectChild = child;
                    datas.addAll(LetterDataHelper.getDatas(PinyinActivity.this, group, child));
                    tips.setText("共"+datas.size()+"个字母");
                }
                letterAdapter.notifyDataSetChanged();
                return true;
            }
        });

        startStudy = (Button) findViewById(R.id.startStudy);
        startStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("letters", (Serializable) datas);
                Intent intent = new Intent(PinyinActivity.this, LetterActivity.class);
                intent.putExtra("selectGroup", "拼音");
                intent.putExtra("selectChild", selectGroup.equals(selectChild) ? selectGroup : selectGroup+"/"+selectChild);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        voiceStudy = (Button) findViewById(R.id.voiceStudy);
        voiceStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("letters", (Serializable) datas);
                Intent intent = new Intent(PinyinActivity.this, PyvoiceActivity.class);
                intent.putExtra("selectGroup", "拼音");
                intent.putExtra("selectChild", selectGroup.equals(selectChild) ? selectGroup : selectGroup+"/"+selectChild);
                intent.putExtras(bundle);
                startActivity(intent);
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
}

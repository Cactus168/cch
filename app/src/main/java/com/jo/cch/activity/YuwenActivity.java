package com.jo.cch.activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jo.cch.R;
import com.jo.cch.adapter.YuWenAdapter;
import com.jo.cch.adapter.YuWenExListAdapter;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.bean.YuWenCourse;
import com.jo.cch.bean.YuWenWord;
import com.jo.cch.data.YuWenData;
import com.jo.cch.data.YuWenDataFactory;
import com.jo.cch.helper.WordDataHelper;
import com.jo.cch.utils.DateUtils;
import com.jo.cch.utils.YuwenUtils;
import com.jo.cch.view.TagGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class YuwenActivity extends Activity {

    private AppCompatImageView backIv;

    private RecyclerView recyclerView;

    private YuWenAdapter yuWenAdapter;

    private RelativeLayout toolBar;

    private LinkedList<Object> mDatas = new LinkedList<>();

    private ExpandableListView expandableListView;

    private YuWenExListAdapter ccAdapter = new YuWenExListAdapter();

    private Button selectMode, speedMode, pinMode, pinYinMode, yuYinMode, goLearnMode;

    private TextView tips;

    private String selectGroup, selectChild;

    private boolean iSSelectModel = false;

    private YuWenData ywData;

    private List<WordInfo> selectDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuwen);

        tips = (TextView) findViewById(R.id.tips);
        toolBar = (RelativeLayout) findViewById(R.id.toolBar);
        recyclerView = (RecyclerView) findViewById(R.id.tv_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        //设置recyclerView布局管理器
        recyclerView.setLayoutManager(manager);//这里用线性显示 类似于listview
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于gridview

        //初始化recyclerView的适配器
        yuWenAdapter = new YuWenAdapter(this, mDatas);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(yuWenAdapter);

        yuWenAdapter.setOnXWordCheckedChangeListener(new YuWenAdapter.OnXWordCheckedChangeListener() {
            @Override
            public void onXWordChecked(View v, final YuWenCourse course, final boolean isChecked) {
                //各种处理数据的代码
                //更新数据的时候使用handler
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        YuWenWord xWord = course.getxWord();
                        if(xWord != null){
                            xWord.setChecked(isChecked);
                            for(WordInfo w : xWord.getWords()){
                                w.setChecked(isChecked);
                                if(isChecked){
                                    if(!selectDatas.contains(w)){
                                        selectDatas.add(w);
                                    }
                                }else{
                                    selectDatas.remove(w);
                                }
                            }
                            refreshTips();
                            yuWenAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

        yuWenAdapter.setOnSWordCheckedChangeListener(new YuWenAdapter.OnSWordCheckedChangeListener() {
            @Override
            public void onSWordChecked(View v, final YuWenCourse course, final boolean isChecked) {
                //各种处理数据的代码
                //更新数据的时候使用handler
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        YuWenWord sWord = course.getsWord();
                        if(sWord != null){
                            sWord.setChecked(isChecked);
                            for(WordInfo w : sWord.getWords()){
                                w.setChecked(isChecked);
                                if(isChecked){
                                    if(!selectDatas.contains(w)){
                                        selectDatas.add(w);
                                    }
                                }else{
                                    selectDatas.remove(w);
                                }
                            }
                            refreshTips();
                            yuWenAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

        yuWenAdapter.setOnGridItemClickListener(new YuWenAdapter.OnGridItemClickListener() {
            @Override
            public void onItemClick(View v, WordInfo w) {
                if(iSSelectModel){
                    if(w.isChecked()){
                        w.setChecked(false);
                        selectDatas.remove(w);
                    }else{
                        w.setChecked(true);
                        if(!selectDatas.contains(w)){
                            selectDatas.add(w);
                        }
                    }
                    refreshTips();
                }
            }
        });

        expandableListView = (ExpandableListView) findViewById(R.id.expend_list);

        expandableListView.setAdapter(ccAdapter);
        //设置子项布局监听
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                v.setSelected(true);
                if(iSSelectModel){
                    iSSelectModel = false;
                    selectDatas.clear();
                    selectMode.setText("选取模式");
                }
                mDatas.clear();
                selectGroup = ccAdapter.groupString[groupPosition];
                selectChild = ccAdapter.childString[groupPosition][childPosition];
                getDatas(selectGroup,selectChild);
                if(mDatas.size() > 0){
                    toolBar.setVisibility(View.VISIBLE);
                    refreshTips();
                }else{
                    toolBar.setVisibility(View.GONE);
                }
                yuWenAdapter.notifyDataSetChanged();
                return true;
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
                    for(Object o : mDatas){
                        if(o instanceof YuWenCourse){
                            YuWenCourse c = (YuWenCourse)o;
                            c.setShow(false);
                            c.setAChecked(false);
                            c.setXChecked(false);
                            c.setSChecked(false);
                        }else{
                            YuWenWord w = (YuWenWord)o;
                            w.setShow(false);
                            w.setChecked(false);
                        }
                    }
                    yuWenAdapter.notifyDataSetChanged();
                    refreshTips();
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
                        bundle.putSerializable("words", (Serializable) getWords());
                        Intent intent = new Intent(YuwenActivity.this, SpeedActivity.class);
                        intent.putExtra("selectGroup", "语文/"+selectGroup+"/"+selectChild);
                        intent.putExtra("selectChild", "快速认字");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(YuwenActivity.this,"请选择要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    bundle.putSerializable("words", (Serializable) ywData.getWords());
                    Intent intent = new Intent(YuwenActivity.this, SpeedActivity.class);
                    intent.putExtra("selectGroup", "语文/"+selectGroup+"/"+selectChild);
                    intent.putExtra("selectChild", "快速认字");
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
                        bundle.putSerializable("words", (Serializable) getWords());
                        Intent intent = new Intent(YuwenActivity.this, LearnActivity.class);
                        intent.putExtra("selectGroup", "语文/"+selectGroup+"/"+selectChild);
                        intent.putExtra("selectChild", "拼写认字");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(YuwenActivity.this,"请选择要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    bundle.putSerializable("words", (Serializable) ywData.getWords());
                    Intent intent = new Intent(YuwenActivity.this, LearnActivity.class);
                    intent.putExtra("selectGroup", "语文/"+selectGroup+"/"+selectChild);
                    intent.putExtra("selectChild", "拼写认字");
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
                        bundle.putSerializable("words", (Serializable) getWords());
                        Intent intent = new Intent(YuwenActivity.this, PinyinknowActivity.class);
                        intent.putExtra("selectGroup", "语文/"+selectGroup+"/"+selectChild);
                        intent.putExtra("selectChild", "拼音认字");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(YuwenActivity.this,"请选择要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    bundle.putSerializable("words", (Serializable) ywData.getWords());
                    Intent intent = new Intent(YuwenActivity.this, PinyinknowActivity.class);
                    intent.putExtra("selectGroup", "语文/"+selectGroup+"/"+selectChild);
                    intent.putExtra("selectChild", "拼音认字");
                    intent.putExtras(bundle);
                    startActivity(intent);
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
                        bundle.putSerializable("words", (Serializable) getWords());
                        Intent intent = new Intent(YuwenActivity.this, VoiceActivity.class);
                        intent.putExtra("selectGroup", "语文/"+selectGroup+"/"+selectChild);
                        intent.putExtra("selectChild", "语音认字");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(YuwenActivity.this,"请选择要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    bundle.putSerializable("words", (Serializable) ywData.getWords());
                    Intent intent = new Intent(YuwenActivity.this, VoiceActivity.class);
                    intent.putExtra("selectGroup", "语文/"+selectGroup+"/"+selectChild);
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
                        WordInfo w = ywData.getWords().get(0);
                        bundle.putSerializable("words", (Serializable) YuwenUtils.resetWords(ywData.getWords(), w));
                        bundle.putSerializable("jumpClazz", (Serializable) YuwenActivity.class);
                        Intent intent = new Intent(YuwenActivity.this, WordduActivity.class);
                        intent.putExtra("currWord", w.getWord());
                        intent.putExtra("totalNum", ywData.getWords().size()+"");
                        intent.putExtra("learnNum", 0+"");
                        intent.putExtra("logId", UUID.randomUUID().toString());
                        intent.putExtra("startDate", DateUtils.getDate(new Date(), null));
                        intent.putExtra("selectGroup", selectGroup);
                        intent.putExtra("selectChild", "生字学习");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(YuwenActivity.this,"请选择要学习的字！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    WordInfo w = ywData.getWords().get(0);
                    bundle.putSerializable("words", (Serializable) YuwenUtils.resetWords(ywData.getWords(), w));
                    bundle.putSerializable("jumpClazz", (Serializable) YuwenActivity.class);
                    Intent intent = new Intent(YuwenActivity.this, WordduActivity.class);
                    intent.putExtra("currWord", w.getWord());
                    intent.putExtra("totalNum", ywData.getWords().size()+"");
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
                    final List<String> selectCourse = new ArrayList<>();
                    final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(YuwenActivity.this);
                    View view1 = View.inflate(YuwenActivity.this, R.layout.dialog_yuwen_select, null);
                    final TextView tv_title = (TextView) view1.findViewById(R.id.tv_title);
                    final TagGroup mTagGroup = (TagGroup) view1.findViewById(R.id.tag_group);
                    final CheckBox cb_x = (CheckBox) view1.findViewById(R.id.cb_x);
                    final CheckBox cb_s = (CheckBox) view1.findViewById(R.id.cb_s);
                    mTagGroup.setTags(ywData.getCourseList());
                    mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                        @Override
                        public void onTagClick(TagGroup.TagView tag, String tagText) {
                            if(selectCourse.contains(tagText)){
                                tag.unTagHighlight();
                                selectCourse.remove(tagText);
                            }else{
                                tag.setTagHighlight();
                                selectCourse.add(tagText);
                            }
                        }
                    });
                    tv_title.setText(selectGroup+"/"+selectChild);
                    Button but_sr = (Button) view1.findViewById(R.id.but_sr);
                    Button but_px = (Button) view1.findViewById(R.id.but_px);
                    Button but_pd = (Button) view1.findViewById(R.id.but_pd);
                    Button but_yy = (Button) view1.findViewById(R.id.but_yy);
                    Button but_qx = (Button) view1.findViewById(R.id.but_qx);
                    alertDialog7.setCancelable(false);
                    alertDialog7.setView(view1).create();
                    final AlertDialog show = alertDialog7.show();
                    but_sr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(selectCourse.size() > 0){
                                List<WordInfo> words = getSelectWords(selectCourse, cb_x.isChecked(),cb_s.isChecked());
                                if(words.size() > 0){
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("words", (Serializable) words);
                                    Intent intent = new Intent(YuwenActivity.this, SpeedActivity.class);
                                    intent.putExtra("selectGroup", "语文/"+selectGroup+"/"+selectChild);
                                    intent.putExtra("selectChild", "快速认字");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    show.dismiss();
                                }else{
                                    Toast.makeText(YuwenActivity.this,"您选择的课程中没有对应类型的字！", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(YuwenActivity.this,"请选择要学习的课程！", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    but_px.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(selectCourse.size() > 0){
                                List<WordInfo> words = getSelectWords(selectCourse, cb_x.isChecked(),cb_s.isChecked());
                                if(words.size() > 0){
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("words", (Serializable) words);
                                Intent intent = new Intent(YuwenActivity.this, LearnActivity.class);
                                intent.putExtra("selectGroup", "语文/"+selectGroup+"/"+selectChild);
                                intent.putExtra("selectChild", "拼写认字");
                                intent.putExtras(bundle);
                                startActivity(intent);
                                show.dismiss();
                                }else{
                                    Toast.makeText(YuwenActivity.this,"您选择的课程中没有对应类型的字！", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(YuwenActivity.this,"请选择要学习的课程！", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    but_pd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(selectCourse.size() > 0){
                                List<WordInfo> words = getSelectWords(selectCourse, cb_x.isChecked(),cb_s.isChecked());
                                if(words.size() > 0){
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("words", (Serializable) words);
                                    Intent intent = new Intent(YuwenActivity.this, PinyinknowActivity.class);
                                    intent.putExtra("selectGroup", "语文/"+selectGroup+"/"+selectChild);
                                    intent.putExtra("selectChild", "拼读认字");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    show.dismiss();
                                }else{
                                    Toast.makeText(YuwenActivity.this,"您选择的课程中没有对应类型的字！", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(YuwenActivity.this,"请选择要学习的课程！", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    but_yy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(selectCourse.size() > 0){
                                List<WordInfo> words = getSelectWords(selectCourse, cb_x.isChecked(),cb_s.isChecked());
                                if(words.size() > 0){
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("words", (Serializable) words);
                                    Intent intent = new Intent(YuwenActivity.this, VoiceActivity.class);
                                    intent.putExtra("selectGroup", "语文/"+selectGroup+"/"+selectChild);
                                    intent.putExtra("selectChild", "语音认字");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    show.dismiss();
                                }else{
                                    Toast.makeText(YuwenActivity.this,"您选择的课程中没有对应类型的字！", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(YuwenActivity.this,"请选择要学习的课程！", Toast.LENGTH_LONG).show();
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
                    for(Object o : mDatas){
                        if(o instanceof YuWenCourse){
                            YuWenCourse c = (YuWenCourse)o;
                            c.setShow(true);
                            c.setAChecked(false);
                            c.setXChecked(false);
                            c.setSChecked(false);
                        }else{
                            YuWenWord w = (YuWenWord)o;
                            w.setShow(true);
                            w.setChecked(false);
                        }
                    }
                    yuWenAdapter.notifyDataSetChanged();
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

    private List<WordInfo> getSelectWords(final List<String> selectCourse, final boolean x, final boolean s){
        Set<WordInfo> words = new HashSet<>();
        for(Object o : mDatas){
            if(o instanceof YuWenCourse){
                YuWenCourse c = (YuWenCourse)o;
                if(selectCourse.contains(c.getCourse())){
                    if(x){
                        YuWenWord xnw = c.getxWord();
                        if(xnw != null){
                            words.addAll(xnw.getWords());
                        }
                    }
                    if(s){
                        YuWenWord snw = c.getsWord();
                        if(snw != null){
                            words.addAll(snw.getWords());
                        }
                    }
                }else{
                    continue;
                }
            }
        }
        return new ArrayList<>(words);
    }

    private void getDatas(String selectGroup, String selectChild) {
        ywData = YuWenDataFactory.getYuWenData(selectGroup,selectChild);
        if(ywData.getCourses() != null){
            for(Map.Entry<Integer, String> e : ywData.getCourses().entrySet()){
                YuWenCourse course = new YuWenCourse(e.getValue());
                mDatas.add(course);
                String xzs = ywData.getXzNWords().get(e.getKey());
                if(xzs != null){
                    List<WordInfo> words = new ArrayList<>();
                    for(String xz : WordDataHelper.toStringArray(xzs)){
                        words.add(new WordInfo(xz));
                    }
                    YuWenWord xWord = new YuWenWord(e.getValue(), "写",words);
                    course.setxWord(xWord);
                    mDatas.add(xWord);
                }
                String szs = ywData.getSzNWords().get(e.getKey());
                if(szs != null){
                    String[] szArray = WordDataHelper.toStringArray(szs);
                    List<WordInfo> words = new ArrayList<>();
                    for(String sz : szArray){
                        words.add(new WordInfo(sz));
                    }
                    YuWenWord sWord = new YuWenWord(e.getValue(), "识",words);
                    course.setsWord(sWord);
                    mDatas.add(sWord);
                }
            }
        }
    }

    private void refreshTips(){
        StringBuilder sb = new StringBuilder("共:"+ywData.getAWordNum()+"  写:"+ywData.getXWordNum()+"  识:"+ywData.getSWordNum());
        if(this.iSSelectModel){
            sb.append("  选:"+this.selectDatas.size());
        }
        this.tips.setText(sb.toString());
    }

    public List<WordInfo> getWords(){
        return new ArrayList<WordInfo>(new HashSet<WordInfo>(this.selectDatas));
    }

}

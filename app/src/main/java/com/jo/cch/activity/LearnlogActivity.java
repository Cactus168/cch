package com.jo.cch.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jo.cch.R;
import com.jo.cch.bean.LetterInfo;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.dao.LearnLogDao;
import com.jo.cch.db.SPConstant;
import com.jo.cch.db.SpUtil;
import com.jo.cch.helper.WordDataHelper;
import com.jo.cch.sql.LearnLog;
import com.jo.cch.utils.DateUtils;
import com.jo.cch.utils.PublicUtils;
import com.jo.cch.utils.YuwenUtils;
import com.jo.cch.view.TagGroup;
import com.rmondjone.locktableview.LockTableView;
import com.rmondjone.xrecyclerview.ProgressStyle;
import com.rmondjone.xrecyclerview.XRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class LearnlogActivity extends Activity {

    private AppCompatImageView backIv;

    private LinearLayout contentView;

    private LearnLogDao learnLogDao;

    private LockTableView mLockTableView;

    private  List<LearnLog> datas;

    private final static int REQUESTCODE = 1; // 返回的结果码

    private  ArrayList<ArrayList<String>> tableDatas = new ArrayList<ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnlog);
        contentView = (LinearLayout) findViewById(R.id.contentView);
        learnLogDao = new LearnLogDao(this);

        initTable();
        //返回
        backIv = (AppCompatImageView) findViewById(R.id.backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initTable(){
        loadTableDatas();
        mLockTableView = new LockTableView(this, contentView, tableDatas);
        mLockTableView.setLockFristColumn(true) //是否锁定第一列
                .setLockFristRow(true) //是否锁定第一行
//              .setMaxColumnWidth(80) //列最大宽度
                .setMinColumnWidth(100) //列最小宽度
                .setColumnWidth(0,50) //设置指定列文本宽度
                .setColumnWidth(1,150) //设置指定列文本宽度
                .setColumnWidth(2,200)
                .setColumnWidth(3,50)
                .setColumnWidth(4,100)
                .setColumnWidth(5,150)
                .setColumnWidth(6,150)
                .setColumnWidth(7,80)
                .setMinRowHeight(30)//行最小高度
                .setMaxRowHeight(35)//行最大高度
                .setTextViewSize(16) //单元格字体大小
                .setFristRowBackGroudColor(R.color.light_gray)//表头背景色
                .setTableHeadTextColor(R.color.forget_pwd)//表头字体颜色
                .setTableContentTextColor(R.color.border_color)//单元格字体颜色
                .setCellPadding(8)//设置单元格内边距(dp)
                .setNullableString("N/A") //空值替换值
                .setOnLoadingListener(new LockTableView.OnLoadingListener() {
                    //下拉刷新、上拉加载监听
                    @Override
                    public void onRefresh(final XRecyclerView mXRecyclerView, final ArrayList<ArrayList<String>> mTableDatas) {
                        //如需更新表格数据调用,部分刷新不会全部重绘
                        loadTableDatas();
                        mLockTableView.setTableDatas(tableDatas);
                        //停止刷新
                        mXRecyclerView.refreshComplete();
                    }

                    @Override
                    public void onLoadMore(final XRecyclerView mXRecyclerView, final ArrayList<ArrayList<String>> mTableDatas) {
                        //如需更新表格数据调用,部分刷新不会全部重绘
                        loadTableDatas();
                        mLockTableView.setTableDatas(tableDatas);
                        //停止刷新
                        mXRecyclerView.loadMoreComplete();
                        //如果没有更多数据调用
                        mXRecyclerView.setNoMore(true);
                    }
                })
                .setOnItemClickListenter(new LockTableView.OnItemClickListenter() {
                    @Override
                    public void onItemClick(View item, int position) {}
                })
                .setOnItemLongClickListenter(new LockTableView.OnItemLongClickListenter() {
                    @Override
                    public void onItemLongClick(View item, int position) {
                        initDialog(position);
                    }
                })
                .setOnItemSeletor(R.color.paleturquoise)//设置Item被选中颜色
                .show(); //显示表格,此方法必须调用
                mLockTableView.getTableScrollView().setPullRefreshEnabled(true);
                mLockTableView.getTableScrollView().setLoadingMoreEnabled(true);
                mLockTableView.getTableScrollView().setRefreshProgressStyle(ProgressStyle.SquareSpin);
    }

    private void initDialog(int position){
        final LearnLog log = datas.get(position-1);
        final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(LearnlogActivity.this);
        View view1 = View.inflate(LearnlogActivity.this, R.layout.dialog_log, null);
        final TagGroup mTagGroup = (TagGroup) view1.findViewById(R.id.tag_group);
        final TextView tv_no = (TextView) view1.findViewById(R.id.tv_no);
        final TextView tv_km = (TextView) view1.findViewById(R.id.tv_km);
        final TextView tv_bt = (TextView) view1.findViewById(R.id.tv_bt);
        final LinearLayout ll_jichu = (LinearLayout) view1.findViewById(R.id.ll_jichu);;
        final LinearLayout ll_shuxue = (LinearLayout) view1.findViewById(R.id.ll_shuxue);
        final LinearLayout ll_yuwen = (LinearLayout) view1.findViewById(R.id.ll_yuwen);
        final LinearLayout ll_pinyin = (LinearLayout) view1.findViewById(R.id.ll_pinyin);
        final Button but_xx = (Button) view1.findViewById(R.id.but_xx);
        final Button but_sx_sc = (Button) view1.findViewById(R.id.but_sx_sc);
        final Button but_sx_qx = (Button) view1.findViewById(R.id.but_sx_qx);

        final Button but_py_sr = (Button) view1.findViewById(R.id.but_py_sr);
        final Button but_py_yy = (Button) view1.findViewById(R.id.but_py_yy);
        final Button but_py_sc = (Button) view1.findViewById(R.id.but_py_sc);
        final Button but_py_qx = (Button) view1.findViewById(R.id.but_py_qx);

        final Button but_sr = (Button) view1.findViewById(R.id.but_sr);
        final Button but_px = (Button) view1.findViewById(R.id.but_px);
        final Button but_pd = (Button) view1.findViewById(R.id.but_pd);
        final Button but_yy = (Button) view1.findViewById(R.id.but_yy);
        final Button but_xz = (Button) view1.findViewById(R.id.but_xz);
        final Button but_dr = (Button) view1.findViewById(R.id.but_dr);
        final Button but_yw_sc = (Button) view1.findViewById(R.id.but_yw_sc);
        final Button but_yw_qx = (Button) view1.findViewById(R.id.but_yw_qx);

        final Button but_sc = (Button) view1.findViewById(R.id.but_sc);
        final Button but_qx = (Button) view1.findViewById(R.id.but_qx);
        if(log.getErrorMgs().length() > 0){
            mTagGroup.setTags(log.getErrorMgs().split(","));
            if(log.getSubject().indexOf("数学") > -1){
                ll_jichu.setVisibility(View.GONE);
                ll_yuwen.setVisibility(View.GONE);
                ll_pinyin.setVisibility(View.GONE);
                ll_shuxue.setVisibility(View.VISIBLE);
            }else if(log.getSubject().indexOf("拼音") > -1){
                ll_jichu.setVisibility(View.GONE);
                ll_yuwen.setVisibility(View.GONE);
                ll_shuxue.setVisibility(View.GONE);
                ll_pinyin.setVisibility(View.VISIBLE);
            }else{
                ll_yuwen.setVisibility(View.VISIBLE);
                ll_shuxue.setVisibility(View.GONE);
                ll_pinyin.setVisibility(View.GONE);
                ll_jichu.setVisibility(View.GONE);
            }
        }else{
            mTagGroup.setVisibility(View.GONE);
            ll_shuxue.setVisibility(View.GONE);
            ll_yuwen.setVisibility(View.GONE);
            ll_pinyin.setVisibility(View.GONE);
            ll_jichu.setVisibility(View.VISIBLE);
        }
        tv_no.setText(log.getNo()+"");
        tv_km.setText(log.getSubject());
        tv_bt.setText(log.getTitle());
        alertDialog7.setCancelable(false);
        alertDialog7.setView(view1).create();
        final AlertDialog dialog = alertDialog7.show();
        //数学
        but_xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LearnlogActivity.this, ShuxuelxActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("formulas", (Serializable) Arrays.asList(log.getErrorMgs().split(",")));
                intent.putExtra("subject", log.getSubject());
                intent.putExtra("title", log.getTitle()+"-"+log.getNo());
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUESTCODE); //REQUESTCODE--->1
                dialog.dismiss();
            }
        });

        but_py_sr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                List<LetterInfo> rs = new ArrayList<>();
                String[] letters = log.getErrorMgs().split(",");
                for(String l : letters){
                    rs.add(new LetterInfo(l, R.drawable.yesmark, false));
                }
                bundle.putSerializable("letters", (Serializable) rs);
                Intent intent = new Intent(LearnlogActivity.this, LetterActivity.class);
                intent.putExtra("selectGroup", log.getSubject());
                intent.putExtra("selectChild", getTitleName(log));
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUESTCODE); //REQUESTCODE--->1
                dialog.dismiss();
            }
        });

        but_py_yy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                List<LetterInfo> rs = new ArrayList<>();
                String[] letters = log.getErrorMgs().split(",");
                for(String l : letters){
                    rs.add(new LetterInfo(l, R.drawable.yesmark, false));
                }
                bundle.putSerializable("letters", (Serializable) rs);
                Intent intent = new Intent(LearnlogActivity.this, PyvoiceActivity.class);
                intent.putExtra("selectGroup", log.getSubject());
                intent.putExtra("selectChild", getTitleName(log));
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUESTCODE); //REQUESTCODE--->1
                dialog.dismiss();
            }
        });

        but_sr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                List<WordInfo> rs = new ArrayList<>();
                String[] words = log.getErrorMgs().split(",");
                for(String w : words){
                    rs.add(new WordInfo(w));
                }
                bundle.putSerializable("words", (Serializable) rs);
                Intent intent = new Intent(LearnlogActivity.this, SpeedActivity.class);
                intent.putExtra("selectGroup", log.getSubject());
                intent.putExtra("selectChild", getTitleName(log));
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUESTCODE); //REQUESTCODE--->1
                dialog.dismiss();
            }
        });
        but_px.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                List<WordInfo> rs = new ArrayList<>();
                String[] words = log.getErrorMgs().split(",");
                for(String w : words){
                    rs.add(new WordInfo(w));
                }
                bundle.putSerializable("words", (Serializable) rs);
                Intent intent = new Intent(LearnlogActivity.this, LearnActivity.class);
                intent.putExtra("selectGroup", log.getSubject());
                intent.putExtra("selectChild", getTitleName(log));
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUESTCODE); //REQUESTCODE--->1
                dialog.dismiss();
            }
        });
        but_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                List<WordInfo> rs = new ArrayList<>();
                String[] words = log.getErrorMgs().split(",");
                for(String w : words){
                    rs.add(new WordInfo(w));
                }
                bundle.putSerializable("words", (Serializable) rs);
                Intent intent = new Intent(LearnlogActivity.this, PinyinknowActivity.class);
                intent.putExtra("selectGroup", log.getSubject());
                intent.putExtra("selectChild", getTitleName(log));
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUESTCODE); //REQUESTCODE--->1
                dialog.dismiss();
            }
        });
        but_yy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                List<WordInfo> rs = new ArrayList<>();
                String[] words = log.getErrorMgs().split(",");
                for(String w : words){
                    rs.add(new WordInfo(w));
                }
                bundle.putSerializable("words", (Serializable) rs);
                Intent intent = new Intent(LearnlogActivity.this, VoiceActivity.class);
                intent.putExtra("selectGroup", log.getSubject());
                intent.putExtra("selectChild", getTitleName(log));
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUESTCODE); //REQUESTCODE--->1
                dialog.dismiss();
            }
        });

        but_xz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                List<WordInfo> rs = new ArrayList<>();
                String[] words = log.getErrorMgs().split(",");
                for(String w : words){
                    rs.add(new WordInfo(w));
                }
                WordInfo w = rs.get(0);
                bundle.putSerializable("words", (Serializable) YuwenUtils.resetWords(rs, w));
                bundle.putSerializable("jumpClazz", (Serializable) LearnlogActivity.class);
                Intent intent = new Intent(LearnlogActivity.this, WordduActivity.class);
                intent.putExtra("currWord", w.getWord());
                intent.putExtra("totalNum", rs.size()+"");
                intent.putExtra("learnNum", 0+"");
                intent.putExtra("logId", UUID.randomUUID().toString());
                intent.putExtra("startDate", DateUtils.getDate(new Date(), null));
                intent.putExtra("selectGroup", getTitleName(log));
                intent.putExtra("selectChild", "生字学习");
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUESTCODE); //REQUESTCODE--->1
                dialog.dismiss();
            }
        });

        but_dr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordDataHelper.addItemLog(LearnlogActivity.this, SPConstant.LEARN_LOG_FLAG, log);
                dialog.dismiss();
            }
        });

        but_sx_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                del(log);
            }
        });
        but_sx_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        but_py_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                del(log);
            }
        });
        but_py_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        but_yw_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                del(log);
            }
        });
        but_yw_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        but_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                del(log);
            }
        });
        but_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private void loadTableDatas(){
        tableDatas.clear();
        ArrayList<String> titles = new ArrayList<String>();
        titles.addAll(Arrays.asList(new String[] {"编号","课目", "标题", "方式", "总/做/对/错", "开始时间", "结束时间", "错误数"}));
        tableDatas.add(titles);
        datas = learnLogDao.queryAll();
        if(datas != null){
            for (LearnLog log : datas) {
                ArrayList<String> mRowDatas = new ArrayList<String>();
                //数据填充
                mRowDatas.add(log.getNo()+"");
                mRowDatas.add(log.getSubject());
                mRowDatas.add(log.getTitle());
                mRowDatas.add(log.getType());
                mRowDatas.add(log.getStateMgs());
                mRowDatas.add(log.getStartDate());
                mRowDatas.add(log.getEndDate());
                mRowDatas.add(log.getErrorMgs().length() > 0 ? log.getErrorMgs().split(",").length+"" : "0");
                tableDatas.add(mRowDatas);
            }
        }
    }

    public void del(final LearnLog log){
        final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(LearnlogActivity.this);
        View view1 = View.inflate(LearnlogActivity.this, R.layout.dialog_password, null);
        final EditText et_password = (EditText) view1.findViewById(R.id.et_password);
        Button but_qd = (Button) view1.findViewById(R.id.but_qd);
        Button but_qx = (Button) view1.findViewById(R.id.but_qx);
        alertDialog7.setCancelable(false);
        alertDialog7.setView(view1).create();
        final AlertDialog dialog = alertDialog7.show();
        but_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pw = et_password.getText().toString();
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);//日
                int hour = calendar.get(Calendar.HOUR_OF_DAY);//小时
                String tpw = day+""+hour;
                if(pw.equals(tpw)){
                    dialog.dismiss();
                    List<LearnLog> logs = new ArrayList<>();
                    List<LearnLog> list = SpUtil.getInstance(LearnlogActivity.this).getList(SPConstant.LEARN_LOG_FLAG, LearnLog.class);
                    for(LearnLog log : list){
                        if(!log.getId().equals(log.getId())){
                            logs.add(log);
                        }
                    }
                    SpUtil.getInstance(LearnlogActivity.this).saveList(SPConstant.LEARN_LOG_FLAG, logs);
                    learnLogDao.delete(log.getId());
                    loadTableDatas();
                    mLockTableView.setTableDatas(tableDatas);
                }else{
                    Toast.makeText(LearnlogActivity.this,"密码不正确！", Toast.LENGTH_LONG).show();
                }
            }
        });
        but_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public String getTitleName(LearnLog log){
        int index = 1;
        loadTableDatas();
        String title = log.getTitle()+"-"+log.getNo();
        for (LearnLog logx : datas) {
            if(logx.getTitle().indexOf(title) > -1){
                index++;
            }
        }
        return title + "-" + index;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == REQUESTCODE) {
                loadTableDatas();
                mLockTableView.setTableDatas(tableDatas);
            }
        }
    }

}

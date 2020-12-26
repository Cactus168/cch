package com.jo.cch.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jo.cch.R;
import com.jo.cch.activity.LearnActivity;
import com.jo.cch.activity.PinyinknowActivity;
import com.jo.cch.activity.SpeedActivity;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.dao.LearnLogDao;
import com.jo.cch.db.SPConstant;
import com.jo.cch.db.SpUtil;
import com.jo.cch.sql.LearnLog;
import com.jo.cch.view.TagGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublicUtils {

    public static void showDelDialog(final Context context, final OnConfirmClickListener onConfirmClickListener, final OnCancelClickListener onCancelClickListener){
        final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(context);
        View view1 = View.inflate(context, R.layout.dialog_del, null);
        Button but_qd = (Button) view1.findViewById(R.id.but_qd);
        Button but_qx = (Button) view1.findViewById(R.id.but_qx);
        alertDialog7.setCancelable(false);
        alertDialog7.setView(view1).create();
        final AlertDialog dialog = alertDialog7.show();
        but_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onConfirmClickListener.onConfirmClickListener();
            }
        });
        but_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onCancelClickListener.onCancelClickListener();
            }
        });
    }
    public interface OnConfirmClickListener{
        void onConfirmClickListener();
    }

    public interface OnCancelClickListener{
        void onCancelClickListener();
    }

    /**
     * 适合播放声音短，文件小
     * 可以同时播放多种音频
     * 消耗资源较小
     */
    public static void playSound1(Context context, int rawId) {
        SoundPool soundPool;
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频的数量
            builder.setMaxStreams(1);
            //AudioAttributes是一个封装音频各种属性的类
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        } else {
            //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
            soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        }
        //第一个参数Context,第二个参数资源Id，第三个参数优先级
        soundPool.load(context, rawId, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(1, 1, 1, 0, 0, 1);
            }
        });
        //第一个参数id，即传入池中的顺序，第二个和第三个参数为左右声道，第四个参数为优先级，第五个是否循环播放，0不循环，-1循环
        //最后一个参数播放比率，范围0.5到2，通常为1表示正常播放
//        soundPool.play(1, 1, 1, 0, 0, 1);
        //回收Pool中的资源
        soundPool.release();
    }

    public static void addLog(Context context, LearnLogDao learnLogDao, String logId, String subject, String title, String type, String stateMgs, String startDate, List<String> errorSs) {
        learnLogDao.delete(logId);
        LearnLog log = new LearnLog();
        log.setId(logId);
        if(subject.indexOf("拼音") > -1){
            int pinyinNo = (int) SpUtil.getInstance(context).getParam(SPConstant.PINYIN_NO_FLAG,1);
            log.setNo(pinyinNo);
            SpUtil.getInstance(context).saveParam(SPConstant.PINYIN_NO_FLAG,(pinyinNo+1));
        }else if(subject.indexOf("数学") > -1){
            int shuxueNo = (int) SpUtil.getInstance(context).getParam(SPConstant.SHUXUE_NO_FLAG,1);
            log.setNo(shuxueNo);
            SpUtil.getInstance(context).saveParam(SPConstant.SHUXUE_NO_FLAG,(shuxueNo+1));
        }else{
            int yuwenNo = (int) SpUtil.getInstance(context).getParam(SPConstant.YUWEN_NO_FLAG,1);
            log.setNo(yuwenNo);
            SpUtil.getInstance(context).saveParam(SPConstant.YUWEN_NO_FLAG,(yuwenNo+1));
        }
        log.setSubject(subject);
        log.setTitle(title);
        log.setType(type);
        log.setStateMgs(stateMgs);
        log.setStartDate(startDate);
        log.setEndDate(DateUtils.getDate(new Date(), null));
        log.setErrorMgs(DataUtils.ListToString(errorSs));
        learnLogDao.insert(log);
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    public static void initPermission(Context context, Activity activity) {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(context, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, toApplyList.toArray(tmpList), 123);
        }

    }
}

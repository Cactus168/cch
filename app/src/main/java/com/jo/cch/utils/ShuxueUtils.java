package com.jo.cch.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jo.cch.R;
import com.jo.cch.activity.ShuxuelxActivity;
import com.jo.cch.view.TagGroup;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ShuxueUtils {

    public static void showDialog(final Context context, final OnCancelClickListener onCancelClickListener, final List<String> errors, final int total, final int yes, final int no, final String selectGroup, final String selectChild){
        final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(context);
        View view1 = View.inflate(context, R.layout.dialog_shuxue_finish, null);
        final RatingBar ratingBar = (RatingBar) view1.findViewById(R.id.ratingBar);
        final TextView tv_total_msg = (TextView) view1.findViewById(R.id.tv_total_msg);
        final TextView tv_yes_msg = (TextView) view1.findViewById(R.id.tv_yes_msg);
        final TextView tv_no_msg = (TextView) view1.findViewById(R.id.tv_no_msg);
        final TagGroup mTagGroup = (TagGroup) view1.findViewById(R.id.tag_group);
        Button but_xx = (Button) view1.findViewById(R.id.but_xx);
        Button but_qx = (Button) view1.findViewById(R.id.but_qx);
        tv_total_msg.setText(""+total);
        tv_yes_msg.setText(""+yes);
        tv_no_msg.setText(""+no);
        if(yes == 0){
            ratingBar.setRating((float) 0.0);
        }else{
            BigDecimal a = BigDecimal.valueOf(total);
            BigDecimal b = BigDecimal.valueOf(yes);
            BigDecimal c = a.divide(b,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal d = BigDecimal.valueOf(5f);
            float val = d.divide(c,2,BigDecimal.ROUND_HALF_UP).floatValue();
            ratingBar.setRating(val);
        }
        if(no == 0){
            but_xx.setVisibility(View.GONE);
            mTagGroup.setVisibility(View.GONE);
        }else{
            mTagGroup.setTags(errors);
        }
        alertDialog7.setCancelable(false);
        alertDialog7.setView(view1).create();
        final AlertDialog dialog = alertDialog7.show();
        but_xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onCancelClickListener.onCancelClickListener();
                goActivity(context, ShuxuelxActivity.class, errors, selectGroup, selectChild);
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

    public interface OnCancelClickListener{
        void onCancelClickListener();
    }

    private static void goActivity(Context context, Class clazz, final List<String> errors, String selectGroup, String selectChild){
        Bundle bundle = new Bundle();
        bundle.putSerializable("formulas", (Serializable) errors);
        Intent intent = new Intent(context, clazz);
        intent.putExtra("subject", selectGroup);
        intent.putExtra("title", selectChild);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}

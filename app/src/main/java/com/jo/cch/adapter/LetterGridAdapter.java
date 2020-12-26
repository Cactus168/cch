package com.jo.cch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jo.cch.R;
import com.jo.cch.bean.LetterInfo;

import java.util.ArrayList;
import java.util.List;

public class LetterGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<LetterInfo> mData = new ArrayList<>();

    public LetterGridAdapter(Context context) {
        this.mContext = context;
    }

    public LetterGridAdapter(Context context, List<LetterInfo> mDatas) {
        this.mContext = context;
        this.mData = mDatas;
    }

    @Override
    public int getCount () {
        return mData.size();
    }

    @Override
    public Object getItem ( int position ) {
        return mData.get(position);
    }

    @Override
    public long getItemId ( int position ) {
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.letter_item, parent, false);
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_letter);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.im_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.mTextView.setText(mData.get(position).getLetter());
        holder.mImageView.setImageResource(mData.get(position).getIcon());
        holder.mImageView.setVisibility(mData.get(position).isChecked() ? View.VISIBLE : View.GONE);//选上了则显示小勾图片
        return convertView;
    }

    class ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;

    }

}
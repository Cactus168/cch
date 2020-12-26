package com.jo.cch.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jo.cch.R;
import com.jo.cch.bean.WordInfo;

import java.util.ArrayList;
import java.util.List;

public class WordGridAdapter extends BaseAdapter {

    private Context mContext;

    private int mResource;

    private List<WordInfo> mData = new ArrayList<>();

    public WordGridAdapter ( Context context) {
        this.mContext = context;
    }

    public WordGridAdapter (Context context, @LayoutRes int resource, List<WordInfo> mDatas) {
        this.mContext = context;
        this.mResource = resource;
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
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_word);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.im_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.mTextView.setText(mData.get(position).getWord());
        holder.mImageView.setVisibility(mData.get(position).isChecked() ? View.VISIBLE : View.GONE);//选上了则显示小勾图片
        return convertView;
    }

    public List<WordInfo> getDatas(){
        return this.mData;
    }

    class ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;

    }

}
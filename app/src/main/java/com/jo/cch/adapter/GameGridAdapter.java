package com.jo.cch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jo.cch.R;
import com.jo.cch.bean.GameInfo;
import com.jo.cch.bean.LetterInfo;

import java.util.ArrayList;
import java.util.List;

public class GameGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<GameInfo> mData = new ArrayList<>();

    public GameGridAdapter(Context context) {
        this.mContext = context;
    }

    public GameGridAdapter(Context context, List<GameInfo> mDatas) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.game_item, parent, false);
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.mTextView.setText(mData.get(position).getNum()+"");
        int stateColor = mContext.getResources().getColor(R.color.item);
        if(mData.get(position).getState() == 0){
            stateColor = mContext.getResources().getColor(R.color.red);
        }else if(mData.get(position).getState() == 1){
            stateColor = mContext.getResources().getColor(R.color.green);
        }
        holder.mTextView.setBackgroundColor(stateColor);
        return convertView;
    }

    class ViewHolder {
        private TextView mTextView;
    }

}
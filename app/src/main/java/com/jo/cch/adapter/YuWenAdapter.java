package com.jo.cch.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jo.cch.R;
import com.jo.cch.bean.WordInfo;
import com.jo.cch.bean.YuWenCourse;
import com.jo.cch.bean.YuWenWord;

import java.util.LinkedList;
import java.util.List;

public class YuWenAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private LinkedList<Object> mDatas;
    private LayoutInflater mInflater;

    private static final int TYPE_HEAD = 1;
    private static final int TYPE_CONTENT = 2;

    public YuWenAdapter(Context context, LinkedList<Object> datas) {
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            return new YuWenCourseViewHolder(mInflater.inflate(R.layout.yuwen_course, parent, false));
        } else{
            return new YuWenWordViewHolder(mInflater.inflate(R.layout.yuwen_nwords, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof YuWenCourseViewHolder) {
            YuWenCourseViewHolder courseHolder= (YuWenCourseViewHolder) holder;
            courseHolder.setCourse((YuWenCourse)mDatas.get(position));
        } else if (holder instanceof YuWenWordViewHolder) {
            YuWenWordViewHolder wordViewHolder = (YuWenWordViewHolder)holder;
            YuWenWord nWord = (YuWenWord)mDatas.get(position);
            wordViewHolder.setGridWords(nWord);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position) instanceof  YuWenCourse ? TYPE_HEAD : TYPE_CONTENT;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public interface OnXWordCheckedChangeListener{
        void onXWordChecked(View v, YuWenCourse course, boolean isChecked);
    }

    private OnXWordCheckedChangeListener onXWordCheckedChangeListener;

    public void setOnXWordCheckedChangeListener(OnXWordCheckedChangeListener onXWordCheckedChangeListener){
        this.onXWordCheckedChangeListener = onXWordCheckedChangeListener;
    }

    public interface OnSWordCheckedChangeListener{
        void onSWordChecked(View v, YuWenCourse course, boolean isChecked);
    }

    private OnSWordCheckedChangeListener onSWordCheckedChangeListener;

    public void setOnSWordCheckedChangeListener(OnSWordCheckedChangeListener onSWordCheckedChangeListener){
        this.onSWordCheckedChangeListener = onSWordCheckedChangeListener;
    }

    public class YuWenCourseViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_course;
        public LinearLayout ll_course;
        public CheckBox cb_a;
        public CheckBox cb_x;
        public CheckBox cb_s;
        YuWenCourseViewHolder(View view){
            super(view);
            tv_course = (TextView) view.findViewById(R.id.tv_course);
            ll_course = (LinearLayout) view.findViewById(R.id.ll_course);
            cb_a = (CheckBox) view.findViewById(R.id.cb_a);
            cb_a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    YuWenCourse course = (YuWenCourse)mDatas.get(getLayoutPosition());
                    course.setAChecked(isChecked);
                    course.setXChecked(isChecked);
                    course.setSChecked(isChecked);
                    cb_x.setEnabled(!isChecked);
                    cb_s.setEnabled(!isChecked);
                    buttonView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            notifyDataSetChanged();
                                        }
                                    }
                    );
                }
            });
            cb_x = (CheckBox) view.findViewById(R.id.cb_x);
            cb_x.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(onXWordCheckedChangeListener != null){
                        YuWenCourse course = (YuWenCourse)mDatas.get(getLayoutPosition());
                        course.setXChecked(isChecked);
                        onXWordCheckedChangeListener.onXWordChecked(buttonView, course, isChecked);
                    }
                }
            });
            cb_s = (CheckBox) view.findViewById(R.id.cb_s);
            cb_s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(onSWordCheckedChangeListener != null){
                        YuWenCourse course = (YuWenCourse)mDatas.get(getLayoutPosition());
                        course.setSChecked(isChecked);
                        onSWordCheckedChangeListener.onSWordChecked(buttonView, course, isChecked);
                    }
                }
            });

        }
        void setCourse(YuWenCourse course){
            tv_course.setText(course.getCourse());
            ll_course.setVisibility(course.isShow() ? View.VISIBLE : View.GONE);
            cb_a.setChecked(course.isAChecked());
            cb_x.setChecked(course.isXChecked());
            cb_s.setChecked(course.isSChecked());
        }
    }

    public interface OnGridItemClickListener{
        void onItemClick(View v, WordInfo w);
    }

    private OnGridItemClickListener onGridItemClickListener;

    public void setOnGridItemClickListener(OnGridItemClickListener onGridItemClickListener){
        this.onGridItemClickListener = onGridItemClickListener;
    }

    public class YuWenWordViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_nword;
        public GridView grid_words;
        public WordGridAdapter wordAdapter;
        YuWenWordViewHolder(final View view){
            super(view);
            grid_words = (GridView) view.findViewById(R.id.grid_words);
            tv_nword = (TextView) view.findViewById(R.id.tv_nword);
            grid_words.setOnItemClickListener(new GridView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    if(onGridItemClickListener != null){
                        onGridItemClickListener.onItemClick(v, wordAdapter.getDatas().get(position));
                        wordAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        void setGridWords(YuWenWord nWord){
            for(WordInfo w : nWord.getWords()){
                w.setChecked(nWord.isChecked());
            }
            wordAdapter = new WordGridAdapter(mContext, R.layout.yuwen_word, nWord.getWords());
            tv_nword.setText(nWord.getYuWenType());
            grid_words.setAdapter(wordAdapter);
            wordAdapter.notifyDataSetChanged();
            setGridHorizontalScroll(grid_words,nWord.getWords().size());
        }
    }

    /**
     * 设置grid横向滚动
     * @param grid
     * @param numColumns
     */
    private void setGridHorizontalScroll(GridView grid, int numColumns){
        int itemWidth = 80;
        int horizontalSpacing = 5;
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        float density = dm.density;
        int gridviewWidth = (int) (numColumns * (itemWidth + horizontalSpacing) * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        grid.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        grid.setColumnWidth((int)(itemWidth * density)); // 设置列表项宽
        grid.setHorizontalSpacing(horizontalSpacing); // 设置列表项水平间距
        grid.setStretchMode(GridView.NO_STRETCH);
        grid.setNumColumns(numColumns); // 设置列数量=列表集合数
    }

    /**
     * 动态设置ItemView高度(暂不用)
     * @param nWordsView
     * @param words
     */
    private void setNWordsViewHeigh(View nWordsView, GridView grid_words, List<WordInfo> words){
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        double width = (dm.widthPixels / dm.density+ 0.5f)-130;
        if(nWordsView.getHeight() > grid_words.getHeight()){
            if(width > (words.size() * 80)){
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)nWordsView.getLayoutParams();
                params.height = nWordsView.getHeight()/2;
                nWordsView.setLayoutParams(params);
            }
        }
        if(width < (words.size() * 80)){
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)nWordsView.getLayoutParams();
            params.height = nWordsView.getHeight()*2;
            nWordsView.setLayoutParams(params);
        }
    }
}

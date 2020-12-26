package com.jo.cch.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.jo.cch.utils.AppApplication;

@SuppressLint("AppCompatCustomView")
public class PinYinTextView extends TextView {
    //控件宽高
    private int width = 0, height = 0;

    private Paint mTextPaint;
    private float mMaxTextSize; // 获取当前所设置文字大小作为最大文字大小
    private float mMinTextSize = 8;

    private Context context;

    //这个不太清楚，重写了就重写了
    public PinYinTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        initialise();
    }
    //这个构造用于xml文件中的构造
    public PinYinTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        initialise();
    }
    //这个构造方法用于在代码中定义控件
    public PinYinTextView(Context context) {
        super(context);
        this.context = context;
        init();
        initialise();
    }
    /**
     * 初始化画笔
     */
    private void init() {

        //使用字体成仿宋体
        this.setTypeface(AppApplication.wordTypeFace);

        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private void initialise() {
        mTextPaint = new TextPaint();
        mTextPaint.set(this.getPaint());
        // max size defaults to the intially specified text size unless it is too small
        mMaxTextSize = this.getTextSize();
//        mMinTextSize = 8;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw) {
            refitText(this.getText().toString(), w);
        }
    }

    /**
     * Resize the font so the specified text fits in the text box
     * assuming the text box is the specified width.
     *
     */
    private void refitText(String text, int textWidth) {
        if (textWidth > 0) {
            int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
            float trySize = mMaxTextSize;

            mTextPaint.setTextSize(trySize);
            while (mTextPaint.measureText(text) > availableWidth) {
                trySize -= 1;
                if (trySize <= mMinTextSize) {
                    trySize = mMinTextSize;
                    break;
                }
                mTextPaint.setTextSize(trySize);
            }

            // setTextSize参数值为sp值
            setTextSize(px2sp(getContext(), trySize));
        }
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static float px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (pxValue / fontScale);
    }
}

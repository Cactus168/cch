package com.jo.cch.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.jo.cch.R;
import com.jo.cch.utils.AppApplication;

@SuppressLint("AppCompatCustomView")
public class FieldWordTextView extends TextView {
    //控件宽高
    private int width = 0, height = 0;

    private Paint mTextPaint;
    private float mMaxTextSize; // 获取当前所设置文字大小作为最大文字大小
    private float mMinTextSize = 8;

    private Paint wbkPaint, szxPaint, jcxPaint;

    private Context context;

    //这个不太清楚，重写了就重写了
    public FieldWordTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        initialise();
    }
    //这个构造用于xml文件中的构造
    public FieldWordTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        initialise();
    }
    //这个构造方法用于在代码中定义控件
    public FieldWordTextView(Context context) {
        super(context);
        this.context = context;
        init();
        initialise();
    }
    /**
     * 初始化画笔
     */
    private void init() {
        wbkPaint = new Paint();
        wbkPaint.setColor(this.getResources().getColor(R.color.fieldWordBK));
        wbkPaint.setAntiAlias(true);
        wbkPaint.setStyle(Paint.Style.STROKE);
        wbkPaint.setStrokeWidth(2);

        szxPaint = new Paint();
        szxPaint.setColor(this.getResources().getColor(R.color.fieldWordX));
        szxPaint.setAntiAlias(true);
        szxPaint.setStyle(Paint.Style.STROKE);
        szxPaint.setStrokeWidth(2);

        jcxPaint = new Paint();
        jcxPaint.setColor(this.getResources().getColor(R.color.fieldWordX));
        jcxPaint.setAntiAlias(true);
        jcxPaint.setStyle(Paint.Style.STROKE);
        jcxPaint.setStrokeWidth(2);
        jcxPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));

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
    /**
     * 重写onDraw方法 可以在绘制文字前后进行一些自己的操作
     * super.onDraw(canvas);调用父类方法绘制文字
     * 如果绘制矩形的代码写在它的后边，文字就会被覆盖
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //边框
        canvas.drawRect(new Rect(2, 2, width - 2, height - 2), wbkPaint);
        //实线
        canvas.drawLine(0, height / 2, width, height / 2, szxPaint);
        canvas.drawLine(width / 2, 0, width / 2, height, szxPaint);
        //虚线
        canvas.drawLine(0, 0, width, height, jcxPaint);
        canvas.drawLine(0, height, width, 0, jcxPaint);
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

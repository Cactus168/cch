package com.jo.cch.view;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.jo.cch.R;
import com.jo.cch.utils.AppApplication;

@SuppressLint("AppCompatCustomView")
public class FourLineTextView extends TextView {
    //控件宽高
    private int width = 0, height = 0;

    private Paint wbkPaint, szxPaint, jcxPaint;

    private Context context;
    //这个不太清楚，重写了就重写了
    public FourLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
    //这个构造用于xml文件中的构造
    public FourLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }
    //这个构造方法用于在代码中定义控件
    public FourLineTextView(Context context) {
        super(context);
        this.context = context;
        init();
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

        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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
        canvas.drawLine(0, 0, width, 0, wbkPaint);

        canvas.drawLine(0, height -150, width, height - 150, jcxPaint);

        canvas.drawLine(0, height -50, width, height -50, jcxPaint);

        canvas.drawLine(0, height, width, height, wbkPaint);

    }
}
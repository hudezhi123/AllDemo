package com.first.hdz.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.first.hdz.customview.R;


/**
 * Created by boy on 2017/8/4.
 */

public class GradientTextView extends View {

    private int mStartColor;
    private int mEndColor;
    private int mGradientStyle;
    private int mTextColor;
    private String mText;
    private int mTextSize;
    private Shader mShader;


    public static final int LINE_GRADIENT = 1;
    public static final int RADIO_GRADIENT = 2;
    public static final int SWEEP_GRADIENT = 3;


    private Paint mPaint;

    private TextPaint mTextPaint;


    public int getStartColor() {
        return mStartColor;
    }

    public int getEndColor() {
        return mEndColor;
    }

    public int getGradientStyle() {
        return mGradientStyle;
    }

    /**
     * 设置起始颜色
     *
     * @param startColor
     */
    private void setStartColor(int startColor) {
        this.mStartColor = startColor;
        invalidate();
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        invalidate();
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
        invalidate();
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        invalidate();
    }

    /**
     * 设置最终颜色
     *
     * @param endColor
     */
    private void setEndColor(int endColor) {
        this.mEndColor = endColor;
        invalidate();
    }

    /**
     * 设置Gradient的种类
     */
    private void setGradientStyle(int gradientStyle) {
        this.mGradientStyle = gradientStyle;
        createShader(gradientStyle);
        invalidate();
    }

    private void createShader(int style) {
        switch (style) {
            case LINE_GRADIENT:
                /**
                 float x0: 渐变起始点x坐标
                 float y0:渐变起始点y坐标
                 float x1:渐变结束点x坐标
                 float y1:渐变结束点y坐标
                 int color0: 起始渐变色
                 int color1: 结束渐变色
                 Shader.TileMode tile: 渲染器平铺模式
                 */
                mShader = new LinearGradient(0, 0, 50, 50, mStartColor, mEndColor, Shader.TileMode.REPEAT);
                break;
            case RADIO_GRADIENT:
                /**
                 float x:  圆心X坐标
                 float y:  圆心Y坐标
                 float radius: 半径
                 int color0: 圆心颜色
                 int color1: 圆边缘颜色
                 Shader.TileMode tile:渲染器平铺模式
                 */
                mShader = new LinearGradient(0, 0, 0, 40, mStartColor, mEndColor, Shader.TileMode.REPEAT);
                break;
            case SWEEP_GRADIENT:
                /**
                 cx	渲染中心点x 坐标
                 cy	渲染中心点y 坐标
                 color0	起始渲染颜色
                 color1	结束渲染颜色
                 */
                mShader = new LinearGradient(0, 0, 50, 0, mStartColor, mEndColor, Shader.TileMode.REPEAT);
                break;
        }
    }


    public GradientTextView(Context context) {
        this(context, null);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
        initTools();
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GradientTextView, defStyleAttr, 0);
        int arrayCount = array.getIndexCount();
        for (int i = 0; i < arrayCount; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.GradientTextView_startColor:
                    mStartColor = array.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.GradientTextView_endColor:
                    mEndColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.GradientTextView_gradientStyle:
                    mGradientStyle = array.getInteger(attr, SWEEP_GRADIENT);
                    createShader(mGradientStyle);
                    break;
                case R.styleable.GradientTextView_text:
                    mText = array.getString(attr);
                    break;
                case R.styleable.GradientTextView_textColor:
                    mTextColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.GradientTextView_textSize:
                    mTextSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
    }

    private void initTools() {
        mPaint = new Paint();
        mTextPaint = new TextPaint();
        mPaint.setAntiAlias(true);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextPaint.setShader(mShader);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        if (mShader == null) {
            mTextPaint.setColor(mTextColor);
        }
        canvas.drawText(mText, 0.5f * getWidth() - mTextPaint.measureText(mText) * 0.5f, getHeight() * 0.5f + mTextPaint.getFontMetrics().bottom * 1, mTextPaint);
    }
}

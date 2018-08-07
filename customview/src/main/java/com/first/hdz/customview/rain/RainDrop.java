package com.first.hdz.customview.rain;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.first.hdz.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hdz
 * on 2018/8/7
 */
public class RainDrop extends View {

    private Paint mPaint;

    private int mColor;

    private int mRadius;

    private int mAlpha;


    public RainDrop(Context context) {
        this(context, null);
    }

    public RainDrop(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainDrop(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void setContent(Drop drop) {
        this.mColor = drop.getColor();
        this.mAlpha = drop.getColor();
        this.mRadius = drop.getRadius();
        invalidate();
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RainDrop, defStyleAttr, 0);
        int indexCount = array.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.RainDrop_centerR:
                    mRadius = array.getDimensionPixelSize(attr, 50);
                    break;
                case R.styleable.RainDrop_color:
                    mColor = array.getColor(attr, Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
                    break;
                case R.styleable.RainDrop_alpha_r:
                    mAlpha = array.getInt(attr, 255);
                    break;
            }
        }
        array.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        mPaint.setColor(mColor);
        mPaint.setAlpha(mAlpha);
        canvas.drawCircle(cx, cy, mRadius - 10, mPaint);
    }
}

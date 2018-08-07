package com.first.hdz.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by boy on 2017/9/7.
 */

public class BatherCurve extends View {
    private Paint mPaint;
    private Path mPath;

    public BatherCurve(Context context) {
        this(context, null);
    }

    public BatherCurve(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatherCurve(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.moveTo(0,0);
        mPath.lineTo(50, 50);
        mPath.quadTo(getWidth() - 5, 0, getWidth() - 5, getHeight());
        canvas.drawPath(mPath, mPaint);
    }
}

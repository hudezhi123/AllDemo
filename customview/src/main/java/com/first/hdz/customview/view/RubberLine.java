package com.first.hdz.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by boy on 2017/8/3.
 */

public class RubberLine extends View implements GestureDetector.OnGestureListener {


    private GestureDetector gestureDetector;

    private float mScreenWidth;
    private float mScreenHeight;

    private float maxVerticalMove = 100;

    private Paint linePaint;
    private Path linePath;
    private float top = 0;

    public RubberLine(Context context) {
        this(context, null);
    }

    public RubberLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RubberLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }

    /**
     * 初始化一些状态值（如：屏幕尺寸）
     */
    private void init(Context context) {
        gestureDetector = new GestureDetector(context, this);
        mScreenHeight = getMetrics(context).heightPixels;
        mScreenWidth = getMetrics(context).widthPixels;
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setStrokeWidth(15);
        linePath = new Path();
    }

    /**
     * 初始化弹簧
     */
    private float rubberMoveVertical(float verticalDistance) {
        float realDistance = verticalDistance / (mScreenHeight / maxVerticalMove);
        return realDistance;
    }

    private float startPosition;
    private float endPosition;


    private DisplayMetrics getMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        linePath.moveTo(0, 0);
        linePath.lineTo(mScreenWidth / 2, top);
        linePath.lineTo(mScreenWidth, 0);
        canvas.drawPath(linePath, linePaint);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {

        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent1, MotionEvent motionEvent2, float v, float v1) {
        startPosition = motionEvent1.getRawY();
        Log.i("position_start", startPosition + "");
        endPosition = motionEvent2.getRawY();
        Log.i("position_end", endPosition + "");
        if (endPosition > startPosition) {
            top = rubberMoveVertical(endPosition - startPosition);
            invalidate();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float v, float v1) {

        return false;
    }

}

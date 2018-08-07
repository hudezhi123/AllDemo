package com.first.hdz.customview.rain;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import com.first.hdz.customview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * created by hdz
 * on 2018/8/7
 */
public class RainView extends ViewGroup implements GestureDetector.OnGestureListener {

    private BlockingQueue<Drop> mQueue;
    private GestureDetector mDetector;
    private Paint mPaint;
    private List<Drop> drops;
    private Context context;


    public GestureDetector getDetector() {
        return mDetector;
    }

    public RainView(Context context) {
        this(context, null);
    }

    public RainView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        mQueue = new LinkedBlockingDeque<>();
        drops = new ArrayList<>();
        mDetector = new GestureDetector(context, this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private Drop produce(int x, int y) {
        Drop drop = new Drop();
        drop.setAlpha(Drop.ALPHA);
        drop.setPointX(x);
        drop.setPointY(y);
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        int color = Color.rgb(red, green, blue);
        drop.setColor(color);
        drop.setRadius(Drop.MIN_R);
        return drop;
    }


    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        for (int i = 0; i < getChildCount(); i++) {
            RainDrop child = (RainDrop) getChildAt(i);
            Drop drop = drops.get(i);
            int cx = drop.getPointX();
            int cy = drop.getPointY();
            int radius = drop.getRadius();
            left = cx - radius;
            right = cx + radius;
            top = cy - radius;
            bottom = cy + radius;
            child.layout(left, top, right, bottom);
        }
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
        int currentX = (int) motionEvent.getX();
        int currentY = (int) motionEvent.getY();
        final Drop drop = produce(currentX, currentY);
        drops.add(drop);
        final RainDrop rainDrop = new RainDrop(context);
        rainDrop.setContent(drop);
        AnimationSet set = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.drop_anim);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RainView.this.removeView(rainDrop);
                drops.remove(drop);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rainDrop.startAnimation(set);

        addView(rainDrop);
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}

package com.first.hdz.qq.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * created by hdz
 * on 2018/9/11
 */
public class MenuLayout extends ViewGroup {

    private View mHeader;

    private View mFooter;

    private Scroller mScroller;


    public MenuLayout(Context context) {
        this(context, null);
    }

    public MenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    private void addHeaderView(View header) {
        this.mHeader = header;
        addView(mHeader);
    }

    private void setContentView() {

    }

    private void addFooterView(View footer) {
        this.mFooter = footer;
        addView(footer);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {

        }
    }

    @Override
    protected void onLayout(boolean boo, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int mCurrentHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            child.layout(0, mCurrentHeight, width, mCurrentHeight + height);
            mCurrentHeight += height;
        }
        scrollTo(0, 0);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int tempHeight = 0;
        int tempWidth = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            tempWidth = Math.max(width, widthSize);
            tempHeight += height;
        }
        tempHeight = Math.max(heightSize, tempHeight);
        setMeasuredDimension(tempWidth, tempHeight);
    }

    private float latestDown = 0;
    private static float ValidDistance = 4.0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                latestDown = event.getRawY();
                return super.onTouchEvent(event);
            case MotionEvent.ACTION_MOVE:
                float currentY = event.getRawY();
                float delta = currentY - latestDown; //移动的有向线段
                float distance = Math.abs(delta);   //移动距离
                scrollBy(0, (int) -delta);
                invalidate();
                latestDown = currentY;
                break;
            case MotionEvent.ACTION_UP:
                scrollTo(0, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }


}

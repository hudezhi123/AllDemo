package com.first.hdz.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.first.hdz.customview.R;


public class SquareScroll extends ViewGroup {

    private Scroller mScroller;
    private View footerView;
    private View bodyView;
    private View headerView;
    private LayoutInflater inflater;
    private int initHeight;
    private int initBottom;
    private int downScroll;
    private int mLastY;

    public SquareScroll(Context context) {
        this(context, null);
    }

    public SquareScroll(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        inflater = LayoutInflater.from(context);
        initView();
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            this.postInvalidate();
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:
                int currentY = (int) event.getRawY();
                Log.i("", "" + currentY);
                int deltaY = currentY - mLastY;
                if (deltaY > 0) {
                    //向下拉
                    if (downScroll >= initHeight) {
                        //上一次已经到顶部
                        downScroll = initHeight;

                    } else {
                        if (downScroll + deltaY >= initHeight) {  //当前滑动滑到了顶部
                            //滑到顶部
                            scrollTo(0, 0);
                            downScroll = initHeight;

                        } else {   //当前没有滑到底部
                            downScroll += deltaY;
                            whetherScroll(deltaY);
                        }
                    }
                } else if (deltaY < 0) {
                    //向上滑
                    if (downScroll <= -initBottom) {
                        downScroll = (-initBottom);
                    } else {
                        if (downScroll + deltaY <= (-initBottom)) {
                            int finalY = initBottom + initHeight;
                            scrollTo(0, finalY);
                            downScroll = (-initBottom);
                        } else {
                            downScroll += deltaY;
                            whetherScroll(deltaY);
                        }
                    }
                }
                mLastY = currentY;
                break;
        }
        return true;
    }

    private void whetherScroll(int offsetY) {
        scrollBy(0, -offsetY);
        this.invalidate();
    }

    private void initView() {
        initHeaderView();
        initBodyView();
        initFooterView();
    }

    private void initHeaderView() {
        headerView = inflater.inflate(R.layout.header, this, false);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(lp);
        addView(headerView);
    }

    private void initBodyView() {
        bodyView = inflater.inflate(R.layout.body, this, false);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        bodyView.setLayoutParams(lp);
        addView(bodyView);
    }

    private void initFooterView() {
        footerView = inflater.inflate(R.layout.footer, this, false);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        footerView.setLayoutParams(lp);
        addView(footerView);
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
            int childWidth = child.getMeasuredWidth();
            tempWidth = Math.max(childWidth, tempWidth);
            tempHeight += child.getMeasuredHeight();
        }
        heightSize = Math.max(tempHeight, heightSize);
        widthSize = Math.max(tempWidth, widthSize);
        setMeasuredDimension(widthSize, heightSize);
    }


    @Override
    protected void onLayout(boolean is, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = 0;
        int top = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
            top += child.getMeasuredHeight();
        }
        initHeight = headerView.getMeasuredHeight();
        initBottom = footerView.getMeasuredHeight();
        scrollTo(0, initHeight);
    }
}

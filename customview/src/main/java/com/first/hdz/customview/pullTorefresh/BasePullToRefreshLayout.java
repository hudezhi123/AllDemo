package com.first.hdz.customview.pullTorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

/**
 * created by hdz
 * on 2018/8/7
 */
public class BasePullToRefreshLayout<T extends View> extends ViewGroup implements AbsListView.OnScrollListener {


    public BasePullToRefreshLayout(Context context) {
        super(context);
    }

    public BasePullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addHeaderView(){

    }

    public void setContentView(){

    }

    public void addFooterView(){

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }
}

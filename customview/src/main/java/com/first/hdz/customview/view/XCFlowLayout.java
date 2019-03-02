package com.first.hdz.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boy on 2017/5/16.
 */

public class XCFlowLayout extends ViewGroup {
    List<List<View>> mAllChildViews = new ArrayList<>();
    List<Integer> mLineHeights = new ArrayList<>();

    public XCFlowLayout(Context context) {
        this(context, null);
    }

    public XCFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取Viewgroup
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int lineWidth = 0;
        int lineHeight = 0;

        int width = 0;
        int height = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams mp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + mp.leftMargin + mp.rightMargin;
            int childHeight = child.getMeasuredHeight() + mp.topMargin + mp.bottomMargin;
            if (childWidth + lineWidth > sizeWidth) {
                //换行
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        //决定了当前View的大小
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width, modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean change, int l, int r, int t, int b) {
        mAllChildViews.clear();
        mLineHeights.clear();
        int lineWidth = 0;
        int lineHeight = 0;
        int width = getWidth();
        List<View> lineViews = new ArrayList<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams mp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            int childWidthSpace = childWidth + mp.leftMargin + mp.rightMargin;
            if (childWidthSpace + lineWidth > width) {
                //如果需要换行
                mLineHeights.add(lineHeight);
                mAllChildViews.add(lineViews);
                lineWidth = 0;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
                lineViews = new ArrayList<>();
            }
            lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + mp.topMargin + mp.bottomMargin);
            lineViews.add(child);
        }
        mLineHeights.add(lineHeight);
        mAllChildViews.add(lineViews);
        int left = 0;
        int top = 0;
        int lineCount = mAllChildViews.size();
        for (int i = 0; i < lineCount; i++) {
            lineViews = mAllChildViews.get(i);
            lineHeight = mLineHeights.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams mp = (MarginLayoutParams) child.getLayoutParams();
                int cLeft = left + mp.leftMargin;
                int cTop = top + mp.topMargin;
                int cRight = cLeft + child.getMeasuredWidth() + mp.rightMargin;
                int cBottom = cTop + mp.bottomMargin + child.getMeasuredHeight();
                child.layout(cLeft, cTop, cRight, cBottom);
                left += child.getMeasuredWidth() + mp.leftMargin + mp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        // TODO Auto-generated method stub
        return new MarginLayoutParams(getContext(), attrs);
    }
}

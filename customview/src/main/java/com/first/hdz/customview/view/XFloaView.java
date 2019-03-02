package com.first.hdz.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boy on 2017/8/29.
 */

public class XFloaView extends ViewGroup {
    private List<Integer> lineHeightList = new ArrayList<>();
    private List<List<View>> childList = new ArrayList<>();

    public XFloaView(Context context) {
        this(context, null);
    }

    public XFloaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XFloaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        int lineWidth = 0;
        int lineHeight = 0;

        int tempWidth = 0;
        int tempHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams ml = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + ml.leftMargin + ml.rightMargin;
            int childHeight = child.getMeasuredHeight() + ml.topMargin + ml.bottomMargin;
            if (childWidth + lineWidth >= specWidthSize) {
                tempWidth = Math.max(lineWidth, tempWidth);
                tempHeight += lineHeight;
                lineWidth = childWidth;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == childCount - 1) {
                tempHeight += lineHeight;
                tempWidth = Math.max(lineWidth, tempWidth);
            }
        }

        setMeasuredDimension(Math.max(specWidthSize, tempWidth), Math.max(specHeightSize, tempHeight));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean is, int l, int r, int t, int b) {
        childList.clear();
        lineHeightList.clear();
        int lineHeight = 0;
        int lineWidth = 0;
        int mWidth = getWidth();
        int childCount = getChildCount();
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            int childWidthSpec = mlp.rightMargin + mlp.leftMargin + childWidth;
            int childHeightSpec = mlp.bottomMargin + mlp.topMargin + childHeight;
            if (childWidthSpec + lineWidth >= mWidth) {
                childList.add(viewList);
                lineHeightList.add(lineHeight);
                lineWidth = childWidth;
                lineHeight = childHeight;
                viewList = new ArrayList<>();
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(childHeight, lineHeight);
            }
            viewList.add(child);
        }
        childList.add(viewList);
        lineHeightList.add(lineHeight);

        int mLeft = 0;
        int mRight = 0;
        int mTop = 0;
        int mBottom = 0;
        int lineCount = lineHeightList.size();
        for (int i = 0; i < lineCount; i++) {
            int tempLineHeight = lineHeightList.get(i);
            List<View> lineViews = childList.get(i);
            int lineChildCount = lineViews.size();
            for (int j = 0; j < lineChildCount; j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
                mLeft += mlp.leftMargin;
                mRight = mLeft + child.getMeasuredWidth();
                mTop += mlp.topMargin;
                mBottom = mTop + child.getMeasuredHeight();
                child.layout(mLeft, mTop, mRight, mBottom);
                mRight += mlp.rightMargin;
                mLeft = mRight;
                mTop -= mlp.topMargin;
                if (j == lineChildCount - 1) {
                    mTop += mlp.bottomMargin + mlp.topMargin;
                }
            }
            mTop += tempLineHeight;
            mLeft = 0;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}

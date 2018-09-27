package com.first.hdz.event.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;


/**
 * created by hdz
 * on 2018/9/10
 */

public class ChildView extends android.support.v7.widget.AppCompatTextView {

    public static String TAG = "ChildView";

    public ChildView(Context context) {
        super(context);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("TAG_TAG", TAG + ":dispatch");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("TAG_TAG", TAG + ":touch");
        return super.onTouchEvent(event);
    }
}

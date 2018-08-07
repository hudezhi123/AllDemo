package com.first.hdz.customview.flexible;

/**
 * created by hdz
 * on 2018/8/7
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by boy on 2017/8/3.
 */

public class RubberView extends View {


    public RubberView(Context context) {
        super(context);
    }

    public RubberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RubberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}


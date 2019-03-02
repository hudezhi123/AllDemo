package com.first.hdz.animation.explore;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * created by hdz
 * on 2018/8/29
 */
public class AnimationField extends View {



    public AnimationField(Context context) {
        super(context);
    }

    public AnimationField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationField(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}

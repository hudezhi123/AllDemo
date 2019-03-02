package com.first.hdz.animation.interpolator;

import android.animation.TimeInterpolator;

/**
 * created by hdz
 * on 2018/8/24
 */
public class GravityInterpolator implements TimeInterpolator {

    @Override
    public float getInterpolation(float v) {
        return (float) Math.pow(v,2);
    }
}

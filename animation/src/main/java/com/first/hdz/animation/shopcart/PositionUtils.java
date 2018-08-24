package com.first.hdz.animation.shopcart;

import android.graphics.Point;
import android.view.View;

/**
 * created by hdz
 * on 2018/8/23
 */
public class PositionUtils {

    /**
     * 获取控件中心坐标：相对屏幕的坐标
     *
     * @param view
     * @return
     */
    public static Point getCenterPositionOnScreen(View view) {
        Point point = new Point();
        int position[] = new int[2];
        view.getLocationOnScreen(position);
        int width = view.getWidth();
        int height = view.getHeight();
        point.x = position[0] + width / 2;
        point.y = position[1] + height / 2;
        return point;
    }

    /**
     * 获取控件中心坐标：相对父控件的坐标
     *
     * @param view
     * @return
     */
    public static Point getCenterPositionOnWindow(View view) {
        Point point = new Point();
        int position[] = new int[2];
        view.getLocationInWindow(position);
        int width = view.getWidth();
        int height = view.getHeight();
        point.x = position[0] + width / 2;
        point.y = position[1] + height / 2;
        return point;
    }
}

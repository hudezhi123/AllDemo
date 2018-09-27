package com.first.hdz.qq.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * created by hdz
 * on 2018/9/17
 */
public class PopWindowManager {

    private int resLayout;
    private View anchorView;
    private Context context;
    private PopupWindow mPopupWindow;
    private View popView;
    private static PopWindowManager mInstance;

    public static final int TRANSLATE = 1;
    private static final int ROTATE = 2;
    private static final int SCALE = 3;
    private static final int ALPHA = 4;

    public interface OnPopViewInitCallBack {
        public void onCallBack(View popView);
    }

    private PopWindowManager(Context context) {
        this.context = context;
    }

    public static PopWindowManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PopWindowManager(context);
        }
        return mInstance;
    }


    public PopWindowManager setPopView(int resId, OnPopViewInitCallBack callBack) {
        popView = LayoutInflater.from(this.context).inflate(resId, null);
        int[] size = DisplayUtils.getScreenSize(context);
        mPopupWindow = new PopupWindow(popView, size[0] / 2, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        callBack.onCallBack(popView);
        return mInstance;
    }

    /**
     * 现有的动画资源，直接调用
     *
     * @param animType
     * @return
     */
    public PopWindowManager setAnim(int... animType) {
        for (int type : animType) {

        }
        return mInstance;
    }

    /**
     * 设置动画
     *
     * @param animStyle
     * @return
     */
    public PopWindowManager setAnim(int animStyle) {

        return mInstance;
    }


    public void show(View anchorView) {
        if (mPopupWindow != null) {
            Point point = calculateWindowLocation(anchorView);
            mPopupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, point.x, point.y);
        }
    }

    private static int adjust = 40;

    private Point calculateWindowLocation(View anchorView) {
        Point point = new Point();
        int[] anchorLocation = new int[2];
        anchorView.getLocationInWindow(anchorLocation);
        int anchorStartX = anchorLocation[0];
        int anchorStartY = anchorLocation[1];
        int anchorEndX = anchorStartX + anchorView.getWidth();
        int anchorEndY = anchorStartY + anchorView.getHeight();
        int popWidth = mPopupWindow.getWidth();
        int popHeight = mPopupWindow.getHeight();
        int screenSize[] = DisplayUtils.getScreenSize(context);
        if (anchorEndY + popHeight <= screenSize[1]) {  //窗口在下方
            if (anchorStartX + popWidth <= screenSize[0]) {  //从anchorView左侧起点开始开始
                point.x = anchorStartX;
                point.y = anchorEndY + adjust;
            } else {   //从anchorView右侧起点起点开始开始
                point.x = anchorEndX - popWidth;
                point.y = anchorEndY + adjust;
            }
        } else {   //窗口在上方
            if (anchorStartX + popWidth <= screenSize[0]) {  //从anchorView左侧七点起点开始开始
                point.x = anchorStartX;
                point.y = anchorStartY - popHeight - adjust;
            } else {   //从anchorView右侧起点起点开始开始
                point.x = anchorEndX - popWidth;
                point.y = anchorEndY - popHeight - adjust;
            }
        }
        return point;
    }

    public void dismiss() {
        hide();
    }

    public void onPause() {
        hide();
    }

    public void onDestroy() {
        if (mPopupWindow != null) {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
            mPopupWindow = null;
        }
    }

    private void hide() {
        if (mPopupWindow != null) {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
        }
    }

}

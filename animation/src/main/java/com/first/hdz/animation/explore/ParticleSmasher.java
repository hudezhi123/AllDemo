package com.first.hdz.animation.explore;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hdz
 * on 2018/8/29
 */
public class ParticleSmasher extends View {

    private int count = 0;
    private List<SmashAnimator> mAnimators = new ArrayList<>();
    private Canvas mCanvas;
    private Activity mActivity;

    public ParticleSmasher(Activity activity) {
        super((Context) activity);
        this.mActivity = activity;
        addView2Window(activity);
        init();
    }


    private void addView2Window(Activity activity) {
        ViewGroup rootView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(this, layoutParams);
    }


    private void init() {
        mCanvas = new Canvas();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (SmashAnimator animator : mAnimators) {
            animator.draw(canvas);
        }
    }

    public SmashAnimator with(View view) {
        SmashAnimator animator = new SmashAnimator(this, view);
        mAnimators.add(animator);
        return animator;
    }

    public Bitmap createBitmapFromView(View animatorView) {
        animatorView.clearFocus();
        Bitmap bitmap = Bitmap.createBitmap(animatorView.getWidth(), animatorView.getHeight(), Bitmap.Config.ARGB_8888);
        if (bitmap != null) {
            synchronized (mCanvas) {
                Canvas canvas = mCanvas;
                canvas.setBitmap(bitmap);
                animatorView.draw(canvas);
                canvas.setBitmap(null);
            }
        }
        return bitmap;
    }

    public Rect getViewRect(View animatorView) {
        Rect rect = new Rect();
        animatorView.getGlobalVisibleRect(rect);
        int location[] = new int[2];
        getLocationOnScreen(location);
        rect.offset(-location[0], -location[1]);
        return rect;
    }

    public void removeAnimator(SmashAnimator smashAnimator) {
        if (mAnimators.contains(smashAnimator)) {
            mAnimators.remove(smashAnimator);
        }
    }

    public void clear(){
        mAnimators.clear();
        invalidate();
    }


    public void reShowView(View view){
        view.animate().setDuration(100).setStartDelay(0).scaleX(1).scaleY(1).translationX(0).translationY(0).alpha(0);
    }

}

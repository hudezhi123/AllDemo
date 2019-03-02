package com.first.hdz.animation.explore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.Random;

/**
 * created by hdz
 * on 2018/8/29
 */
public class SmashAnimator {

    public static final int STYLE_EXPLOSION = 1,     // 爆炸
            STYLE_DROP = 2,                         //下落
            STYLE_FLOAT_LEFT = 3,                   //飘落-->自左往右，逐列飘落
            STYLE_FLOAT_RIGHT = 4,                  //飘落-->自右往左，逐列飘落
            STYLE_FLOAT_TOP = 5,                    //飘落-->自上往下，逐行飘落
            STYLE_FLOAT_BOTTOM = 6;                 //飘落-->自下往上，逐行飘落


    private int mStyle = STYLE_EXPLOSION;    //动画样式
    private ValueAnimator mValueAnimator;
    private ParticleSmasher mContainer;       //绘制动画效果的View
    private View mAnimatorView;               //要进行爆炸动画的View

    private Bitmap mBitmap;
    private Rect mRect;                        // 要进行动画的View在坐标系中的矩形

    private Paint mPaint;   // 绘制粒子的画笔
    private Particle[][] mParticles;

    private float mEndValue = 1.5f;
    private long mDuration = 1000L;
    private long mStartDelay = 150L;

    private float mHorizontalMultiple = 3;    //粒子水平变化幅度
    private float mVerticalMultiple = 4;      // 粒子垂直变化幅度
    private int mRadius = Utils.dp2Px(2);      // 粒子基础半径

    private static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateInterpolator(0.6f);


    private OnAnimatorListener mOnAnimatorListener;

    public static abstract class OnAnimatorListener {
        public abstract void onAnimatorStart();

        public abstract void onAnimatorEnd();
    }

    public SmashAnimator(ParticleSmasher view, View animatorView) {
        this.mContainer = view;
        init(animatorView);
    }

    private void init(View animatorView) {
        this.mAnimatorView = animatorView;
        mBitmap = mContainer.createBitmapFromView(animatorView);
        mRect = mContainer.getViewRect(animatorView);
        initValueAnimator();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    private void initValueAnimator() {
        mValueAnimator = new ValueAnimator();
        mValueAnimator.setFloatValues(0f, mEndValue);
        mValueAnimator.setInterpolator(DEFAULT_INTERPOLATOR);
    }

    public SmashAnimator setStyle(int style) {
        this.mStyle = style;
        return this;
    }

    public SmashAnimator setDuration(long duration) {
        this.mDuration = duration;
        return this;
    }

    public SmashAnimator setStartDelay(long startDelay) {
        mStartDelay = startDelay;
        return this;
    }

    public SmashAnimator setHorizontalMultiple(float horizontalMultiple) {
        this.mHorizontalMultiple = horizontalMultiple;
        return this;
    }

    public SmashAnimator setVerticalMultiple(float verticalMultiple) {
        this.mVerticalMultiple = verticalMultiple;
        return this;
    }

    public SmashAnimator setParticleRadius(int radius) {
        this.mRadius = radius;
        return this;
    }

    public SmashAnimator addAnimatorListener(final OnAnimatorListener listener) {
        this.mOnAnimatorListener = listener;
        return this;
    }

    public void start() {
        setValueAnimator();
        calculateParticles(mBitmap);
        hideView(mAnimatorView, mStartDelay);
        mValueAnimator.start();
        mContainer.invalidate();
    }


    private void setValueAnimator() {
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.setStartDelay(mStartDelay);
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mOnAnimatorListener != null) {
                    if (mOnAnimatorListener != null) {
                        mOnAnimatorListener.onAnimatorEnd();
                    }
                    mContainer.removeAnimator(SmashAnimator.this);
                }

            }

            @Override
            public void onAnimationStart(Animator animation) {
                if (mOnAnimatorListener != null) {
                    mOnAnimatorListener.onAnimatorStart();
                }
            }
        });
    }

    private void calculateParticles(Bitmap bitmap) {
        int col = bitmap.getWidth() / (mRadius * 2);
        int row = bitmap.getHeight() / (mRadius * 2);
        Random random = new Random(System.currentTimeMillis());
        mParticles = new Particle[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int x = j * mRadius * 2 + mRadius;
                int y = i * mRadius * 2 + mRadius;
                int color = bitmap.getPixel(x, y);
                Point point = new Point(mRect.left + x, mRect.top + y);
                switch (mStyle) {
                    case STYLE_EXPLOSION:
                        mParticles[i][j] = new ExplosionParticle(color, mRadius, mRect, mEndValue, random, mHorizontalMultiple, mVerticalMultiple);
                        break;
                    case STYLE_DROP:
                        mParticles[i][j] = new DropParticle(point, color, mRadius, mRect, mEndValue, random, mHorizontalMultiple, mVerticalMultiple);
                        break;
                    case STYLE_FLOAT_LEFT:
                        mParticles[i][j] = new FloatParticle(FloatParticle.ORIENTATION_LEFT, point, color, mRadius, mRect, mEndValue, random, mHorizontalMultiple, mVerticalMultiple);
                        break;
                    case STYLE_FLOAT_RIGHT:
                        mParticles[i][j] = new FloatParticle(FloatParticle.ORIENTATION_RIGHT, point, color, mRadius, mRect, mEndValue, random, mHorizontalMultiple, mVerticalMultiple);
                        break;
                    case STYLE_FLOAT_TOP:
                        mParticles[i][j] = new FloatParticle(FloatParticle.ORIENTATION_TOP, point, color, mRadius, mRect, mEndValue, random, mHorizontalMultiple, mVerticalMultiple);
                        break;
                    case STYLE_FLOAT_BOTTOM:
                        mParticles[i][j] = new FloatParticle(FloatParticle.ORIENTATION_BOTTOM, point, color, mRadius, mRect, mEndValue, random, mHorizontalMultiple, mVerticalMultiple);
                        break;
                }
            }
        }
        mBitmap.recycle();
        mBitmap = null;
    }

    private void hideView(final View animatorView, long startDelay) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(startDelay + 50).setFloatValues(0f, 1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            Random random = new Random();

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatorView.setTranslationX((random.nextFloat() - 0.5F) * animatorView.getWidth() * 0.05F);
                animatorView.setTranslationY((random.nextFloat() - 0.5f) * animatorView.getHeight() * 0.05f);
            }
        });
        valueAnimator.start();
        animatorView.animate().setDuration(260).setStartDelay(startDelay).scaleX(0).scaleY(0).alpha(0).start();
    }

    public boolean draw(Canvas canvas) {
        if (!mValueAnimator.isStarted()) {
            return false;
        }
        for (Particle[] particles : mParticles) {
            for (Particle p : particles) {
                mPaint.setColor(p.color);
                mPaint.setAlpha((int) (Color.alpha(p.color) * p.alpha));
                canvas.drawCircle(p.cx, p.cy, p.radius, mPaint);
            }
        }
        mContainer.invalidate();
        return true;
    }
}

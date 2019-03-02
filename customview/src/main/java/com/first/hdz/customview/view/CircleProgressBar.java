package com.first.hdz.customview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.first.hdz.customview.R;


/**
 * Created by boy on 2017/5/26.
 */

public class CircleProgressBar extends View {
    private int InCircleR;
    private int OutCircleR;
    private int InCircleColor;
    private int OutCircleCacheColor;
    private int OutCircleProgressColor;
    private int mProgress = 0;

    private int mWidth;
    private int mHeight;

    private String text = "开始";

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    private int textSize;

    public int getmProgress() {
        return mProgress;
    }

    public void setmProgress(int mProgress) {
        this.mProgress = mProgress;
        postInvalidate();
    }

    public void setText(String text) {
        this.text = text;
        postInvalidate();
    }

    public String getText() {
        return text;
    }


    private Paint paint;
    private TextPaint textPaint;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrbute(context, attrs, defStyleAttr);
    }


    private void initAttrbute(Context context, AttributeSet attrs, int defStyleAttr) {
        /**
         * @param set The base set of attribute values.  May be null.
         * @param attrs The desired attributes to be retrieved.
         * @param defStyleAttr An attribute in the current theme that contains a
         *                     reference to a style resource that supplies
         *                     defaults values for the TypedArray.  Can be
         *                     0 to not look for defaults.
         * @param defStyleRes A resource identifier of a style resource that
         *                    supplies default values for the TypedArray,
         *                    used only if defStyleAttr is 0 or can not be found
         *                    in the theme.  Can be 0 to not look for defaults.
         *
         * @return Returns a TypedArray holding an array of the attribute values.
         * Be sure to call {@link TypedArray#recycle() TypedArray.recycle()}
         * when done with it.
         */
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, defStyleAttr, 0);
        int attrCount = array.getIndexCount();
        for (int i = 0; i < attrCount; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.CircleProgressBar_InCircleR:
                    InCircleR = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CircleProgressBar_OutCircleR:
                    OutCircleR = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CircleProgressBar_InCircleColor:
                    InCircleColor = array.getColor(attr, getResources().getColor(R.color.colorPressGray));
                    break;
                case R.styleable.CircleProgressBar_OutCircleCacheColor:
                    OutCircleCacheColor = array.getColor(attr, getResources().getColor(R.color.colorPressGray));
                    break;
                case R.styleable.CircleProgressBar_OutCircleProgressColor:
                    OutCircleProgressColor = array.getColor(attr, getResources().getColor(R.color.colorGreen));
                    break;
                case R.styleable.CircleProgressBar_Text:
                    text = (String) array.getText(attr);
                    break;
                case R.styleable.CircleProgressBar_TextSize:
                    textSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 25, getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
    }


    private void paintCircle(int color, int r, boolean isFill, Canvas canvas) {
        if (isFill) {
            paint.setStyle(Paint.Style.FILL);
        } else {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
        }
        paint.setColor(color);
        canvas.drawCircle(mWidth / 2, mHeight / 2, r, paint);
    }

    private void paintText(Canvas canvas) {
        textPaint.setTextSize(textSize);
        canvas.drawText(text, mWidth * 0.5f - 0.5f * textPaint.measureText(text), mHeight * 0.5f + textPaint.getFontMetrics().bottom * 4, textPaint);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mHeight = getHeight();
        mWidth = getWidth();
        paintCircle(Color.BLACK, OutCircleR, false, canvas);
        paintCircle(OutCircleCacheColor, OutCircleR, true, canvas);

        int angle = (int) (mProgress * 1.0f * 360 / 100);
        paint.setColor(OutCircleProgressColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(mWidth / 2 - OutCircleR, mHeight / 2 - OutCircleR, mWidth / 2 + OutCircleR, mHeight / 2 + OutCircleR, -90, angle, true, paint);
        paintCircle(Color.BLACK, InCircleR, false, canvas);
        paintCircle(InCircleColor, InCircleR, true, canvas);

        paintText(canvas);
    }
}

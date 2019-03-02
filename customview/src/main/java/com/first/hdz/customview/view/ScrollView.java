package com.first.hdz.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.first.hdz.customview.R;


/**
 * Created by boy on 2017/5/19.
 */

public class ScrollView extends RelativeLayout {
    public ImageView img;

    public ScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        img = new ImageView(context);
        img.setImageResource(R.mipmap.ic_launcher);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        img.setScaleType(ImageView.ScaleType.CENTER);
        addView(img, params);
    }

    public void scrollTo() {
        img.scrollTo(10, 10);
    }

    public void scrollBy() {
        img.scrollBy(10, 10);
    }

    public void reset() {
        img.scrollTo(0, 0);
    }
}

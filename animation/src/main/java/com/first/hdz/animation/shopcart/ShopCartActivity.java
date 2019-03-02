package com.first.hdz.animation.shopcart;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.first.hdz.animation.R;
import com.first.hdz.animation.interpolator.GravityInterpolator;

import java.util.ArrayList;
import java.util.List;

public class ShopCartActivity extends AppCompatActivity implements ShopingCartAdapter.OnBuyListener {

    private List<ShopItem> mList;
    private ShopingCartAdapter mAdapter;
    private LinearLayoutManager mManager;
    private RecyclerView mListView;
    private TextView count;
    private RelativeLayout ll;
    private ImageView cart;
    private float[] mCurrentPosition = new float[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);
        init();
    }

    private void init() {
        ll = findViewById(R.id.ll);
        count = findViewById(R.id.count);
        cart = findViewById(R.id.cart);
        mList = new ArrayList<>();
        mList.add(new ShopItem(ShopItem.RMB));
        mList.add(new ShopItem(ShopItem.DOL));
        mListView = findViewById(R.id.recycler);
        mAdapter = new ShopingCartAdapter(this, mList);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mListView.setLayoutManager(mManager);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnBuyListener(this);
    }

    @Override
    public void buy(ImageView view, int position) {
        animStart(view, position);
    }

    private void animStart(final ImageView view, int position) {
        int type = mList.get(position).getType();
        final View item = LayoutInflater.from(this).inflate(R.layout.goods_item, null);
        final ImageView imgCopy = item.findViewById(R.id.goods_copy);
        if (type == ShopItem.DOL) {
            imgCopy.setImageResource(R.mipmap.icon_dollar);
        } else if (type == ShopItem.RMB) {
            imgCopy.setImageResource(R.mipmap.icon_rmb);
        }
        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(item, llp);
        Point startPoint = PositionUtils.getStartPositionOnWindow(view);
        Point endPoint = PositionUtils.getCenterPositionOnWindow(cart);
        Path movePath = new Path();
        movePath.moveTo(startPoint.x, startPoint.y);
        movePath.quadTo(endPoint.x, startPoint.y, endPoint.x, endPoint.y);
        final PathMeasure pathMeasure = new PathMeasure(movePath, false);
        float length = pathMeasure.getLength();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, length);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new GravityInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                valueAnimator.getAnimatedFraction();
                float currentLength = (float) valueAnimator.getAnimatedValue();
                pathMeasure.getPosTan(currentLength, mCurrentPosition, null);
                imgCopy.setTranslationX(mCurrentPosition[0]);
                imgCopy.setTranslationY(mCurrentPosition[1]);
            }
        });
        valueAnimator.start();
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ll.removeView(item);
                countChange();
                payChange();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private int num = 0;

    private void countChange() {
        num++;
        count.setText(num + "");
    }

    private void payChange() {

    }

}

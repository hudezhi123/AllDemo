package com.first.hdz.animation.shopcart;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.first.hdz.animation.R;

import java.util.ArrayList;
import java.util.List;

public class ShopCartActivity extends AppCompatActivity implements ShopingCartAdapter.OnBuyListener {

    private List<ShopItem> mList;
    private ShopingCartAdapter mAdapter;
    private LinearLayoutManager mManager;
    private RecyclerView mListView;
    private TextView count;
    private ImageView cart;
    private float[] mCurrentPosition = new float[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);
        init();
    }

    private void init() {
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
    public void buy(View view) {
        animStart(view);
    }

    private void animStart(View view) {
        Drawable drawable = cart.getDrawable();
        final ImageView imgCopy = findViewById(R.id.goods);
        imgCopy.setImageDrawable(drawable);
        Point startPoint = PositionUtils.getCenterPositionOnScreen(view);
        Point endPoint = PositionUtils.getCenterPositionOnScreen(cart);
        Path movePath = new Path();
        movePath.moveTo(startPoint.x, startPoint.y);
        movePath.quadTo(endPoint.x, startPoint.y, endPoint.x, endPoint.y);
        final PathMeasure pathMeasure = new PathMeasure(movePath, false);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
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

    private void countChange() {

    }

    private void payChange() {

    }

}

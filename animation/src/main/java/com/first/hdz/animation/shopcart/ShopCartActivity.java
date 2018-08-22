package com.first.hdz.animation.shopcart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.first.hdz.animation.R;

import java.util.ArrayList;
import java.util.List;

public class ShopCartActivity extends AppCompatActivity implements ShopingCartAdapter.OnBuyListener{

    private List<ShopItem> mList;
    private ShopingCartAdapter mAdapter;
    private LinearLayoutManager mManager;
    private RecyclerView mListView;
    private TextView count;
    private ImageView cart;

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
        mAdapter = new ShopingCartAdapter(this,mList);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mListView.setLayoutManager(mManager);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnBuyListener(this);
    }

    @Override
    public void buy() {
        animStart();
    }

    private void animStart(){

    }

    private void countChange(){

    }

    private void payChange(){

    }

}

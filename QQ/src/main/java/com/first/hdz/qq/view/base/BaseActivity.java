package com.first.hdz.qq.view.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.first.hdz.qq.utils.Constants;

import java.io.Serializable;

/**
 * created by hdz
 * on 2018/9/14
 */
public abstract class BaseActivity extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void turnToActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void returnToActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, Constants.NORMAL_REQUEST_CODE);
    }


}

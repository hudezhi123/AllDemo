package com.first.hdz.customview.rain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.first.hdz.customview.R;

public class RainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GestureDetector mDetector;
    private RainView rainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain);
        rainView = findViewById(R.id.rain_view);
        mDetector = rainView.getDetector();
        rainView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mDetector.onTouchEvent(motionEvent);
        return true;
    }
}

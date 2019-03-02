package com.first.hdz.customview.swipelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.first.hdz.customview.R;

public class QQSwipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqswipe);
    }


    public void onTop(View view) {
        Toast.makeText(this, "置顶", Toast.LENGTH_SHORT).show();
    }

    public void onDelete(View view) {
        Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
    }
}

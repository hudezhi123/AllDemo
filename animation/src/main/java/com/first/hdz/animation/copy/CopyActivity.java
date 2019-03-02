package com.first.hdz.animation.copy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.first.hdz.animation.R;

public class CopyActivity extends AppCompatActivity {


    private Canvas canvas;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy);
        canvas = new Canvas();
    }

    public void onClick(View view) {
        bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        view.draw(canvas);
        canvas.setBitmap(null);
    }
}

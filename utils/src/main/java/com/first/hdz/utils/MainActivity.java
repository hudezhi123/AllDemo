package com.first.hdz.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.first.hdz.utils.utils.BitmapUtils;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private Bitmap bitmap;
    private static final int SUCCESS = 1001;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    Bitmap sbitmap = (Bitmap) msg.obj;
                    int color = 0xff424242;
                    int r = sbitmap.getWidth() / 2;
                    Rect rect = new Rect(0, 0, 74, 74);
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setFilterBitmap(true);
                    paint.setColor(color);
                    Canvas canvas = new Canvas();
                    canvas.drawCircle(r, r, r, paint);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                    canvas.drawBitmap(sbitmap, rect, rect, paint);
                    img.draw(canvas);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.img);
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.qq);
                Bitmap bitmaps = BitmapUtils.drawCircle(bitmap);
                mHandler.sendMessage(mHandler.obtainMessage(SUCCESS, bitmaps));

            }
        }).start();
    }
}

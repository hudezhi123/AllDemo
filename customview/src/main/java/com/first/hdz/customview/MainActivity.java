package com.first.hdz.customview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.RelativeDateTimeFormatter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {


    /**
     ********************颜色矩阵计算方法***********************
     *           a,b,c,d,e
     *           f,g,h,i,j
     *****  M =  k,l,m,n,o    变换颜色矩阵
     *           p,q,r,s,t
     *
     *           r
     *           g
     ****  C =   b         r->red    g->green   b->blue   a->alpha   1->数字1
     *           a
     *           1
     *
     *
     *
     *      newC = M*C  即矩阵相乘所得的结果。
     *
     */

    //灰度效果
    /**
     * 0.33f,0.59f,0.11f,0,0
     * 0.33f,0.59f,0.11f,0,0
     * 0.33f,0.59f,0.11f,0,0
     * 0,    0,    0,    1,0
     */
    private static final int STYLE_GRAY = 1;

    //图像翻转
    /**
     * -1,0,0,1,1
     * 0,-1,0,1,1
     * 0,0,-1,1,1
     * 0,0,0,1,0
     */
    private static final int STYLE_NEGATIVE = 2;

    //怀旧效果
    /**
     * 0.393f,0.769f,0.189f,0,0
     * 0.349f,0.686f,0.168f,0,0
     * 0.272f,0.534f,0.131f,0,0
     * 0,     0,      0,    1,0
     */
    private static final int STYLE_OLD = 3;

    //去色效果
    /**
     * 1.5f,1.5f,1.5f,0,-1
     * 1.5f,1.5f,1.5f,0,-1
     * 1.5f,1.5f,1.5f,0,-1
     * 0,   0,   0,   1, 0
     */
    private static final int STYLE_DECLARE = 4;

    //高饱和度
    /**
     * 1.438f,-0.122f,-0.016f,0,-0.03f
     * -0.062f,1.378f,-0.016f,0,0.05f
     * -0.062f,-0.122f,1.483f,0,-0.02f
     * 0,      0,      0,     1,0
     */
    private static final int STYLE_HIGH = 5;

    private ImageView img;
    private RadioGroup group;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.img);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);
        img.setImageBitmap(bitmap);
        group = findViewById(R.id.group);
        group.setOnCheckedChangeListener(this);
    }

    private Bitmap createBitmap(Bitmap bitmap, int style) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        int[] newPixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        Bitmap bit = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = i * width + j;
                int argb = pixels[index];
                int alpha = Color.alpha(argb);
                int red = Color.red(argb);
                int green = Color.green(argb);
                int blue = Color.blue(argb);
                newPixels[index] = getPixel(red, green, blue, alpha, style);
            }
        }
        bit.setPixels(newPixels, 0, width, 0, 0, width, height);
        return bit;
    }

    private int getPixel(int red, int green, int blue, int alpha, int style) {
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int newA = 0;
        switch (style) {
            case STYLE_GRAY:
                newR = (int) (0.33f * red + 0.59f * green + 0.11f * blue);
                newG = (int) (0.33f * red + 0.59f * green + 0.11f * blue);
                newB = (int) (0.33f * red + 0.59f * green + 0.11f * blue);
                newA = alpha;
                break;
            case STYLE_OLD:
                newR = (int) (0.393f * red + 0.769f * green + 0.189f * blue);
                newG = (int) (0.394f * red + 0.686f * green + 0.168f * blue);
                newB = (int) (0.272f * red + 0.534f * green + 0.131f * blue);
                newA = alpha;
                break;
            case STYLE_DECLARE:
                newR = (int) (1.5f * red + 1.5f * green + 1.5f * blue - 1);
                newG = (int) (1.5f * red + 1.5f * green + 1.5f * blue - 1);
                newB = (int) (1.5f * red + 1.5f * green + 1.5f * blue - 1);
                newA = alpha;
                break;
            case STYLE_NEGATIVE:
                newR = -red + alpha + 1;
                newG = -green + alpha + 1;
                newB = -blue + alpha + 1;
                newA = alpha;
                break;
            case STYLE_HIGH:
                newR = (int) (1.438 * red - 0.122 * green - 0.016 * blue - 0.03);
                newG = (int) (-0.062 * red + 1.378 * green - 0.016 * blue + 0.05);
                newB = (int) (-0.062 * red - 0.122 * green + 1.483 * blue - 0.02);
                newA = alpha;
                break;
            default:
                break;
        }
        newA = adjust(newA);
        newB = adjust(newB);
        newG = adjust(newG);
        return Color.argb(newA, newR, newG, newB);
    }

    private int adjust(int a) {
        return a > 255 ? 255 : a < 0 ? 0 : a;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        int style = 0;
        switch (checkedId) {
            case R.id.radio_gray:
                style = STYLE_GRAY;
                break;
            case R.id.radio_high:
                style = STYLE_HIGH;
                break;
            case R.id.radio_negative:
                style = STYLE_NEGATIVE;
                break;
            case R.id.radio_old:
                style = STYLE_OLD;
                break;
            case R.id.radio_reduce:
                style = STYLE_DECLARE;
                break;
        }
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);
        img.setImageBitmap(createBitmap(bitmap, style));
    }
}

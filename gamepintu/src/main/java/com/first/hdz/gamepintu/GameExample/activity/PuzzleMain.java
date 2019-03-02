package com.first.hdz.gamepintu.GameExample.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.first.hdz.gamepintu.GameExample.adapter.GridItemsAdapter;
import com.first.hdz.gamepintu.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PuzzleMain extends Activity implements View.OnClickListener {

    //拼图完成时显示的最后一个图片
    public static Bitmap mLastBitmap;
    //设置为N*N显示
    public static int TYPE = 2;
    //步数显示
    public static int COUNT_INDEX = 0;
    //计时显示
    public static int TIMER_INDEX = 0;
    //选择的图片
    private Bitmap mPicSelected;

    private GridView mGvPuzzleMainDetail;
    private int mResId;
    private String mPicPath;
    private ImageView mImageView;

    private Button mBtnBack;
    private Button mBtnImage;
    private Button mBtnRestart;
    //显示步数
    private TextView mTvPuzzleMainCounts;
    //计时器
    private TextView mTvTimer;
    //切图后的图片
    private List<Bitmap> mBitmapItemLists = new ArrayList<>();

    private GridItemsAdapter mAdapter;

    //是否已经显示原图
    private boolean mIsShowImage;

    private Timer mTimer;

    private TimerTask mTimerTask;

    /**
     * UI 更新handler
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzel_main);

    }


    @Override
    public void onClick(View view) {

    }
}

package com.first.hdz.qq.view.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.first.hdz.qq.R;
import com.first.hdz.qq.utils.PopWindowManager;
import com.first.hdz.qq.view.base.BaseActivity;
import com.first.hdz.qq.view.fragment.ContactFragment;
import com.first.hdz.qq.view.fragment.DynamicFragment;
import com.first.hdz.qq.view.fragment.MessageFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgDynamic;
    private DrawerLayout mDrawerLayout;
    private RadioGroup mGroup;
    private NavigationView mNav;
    private FrameLayout mFrame;
    private View headerView;
    private ImageView imgDrawableControl;
    private TextView title;
    private ImageView imgPopMenuControl;
    private boolean IsOpen = false;
    private int mState = -1;
    private ObjectAnimator mAnim;

    private List<Fragment> fragmentList;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    private PopWindowManager mManager;

    private MessageFragment mMessage;
    private ContactFragment mContact;
    private DynamicFragment mDynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManager = PopWindowManager.getInstance(this);
        init();

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int imgHeight = imgDynamic.getHeight();
            int frameHeight = mFrame.getHeight();
            mAnim = ObjectAnimator.ofFloat(imgDynamic, "translationY", 0, frameHeight - imgHeight);
            mAnim.setDuration(3000);
            mAnim.setRepeatCount(-1);
            mAnim.setRepeatMode(ValueAnimator.REVERSE);
        }
    }

    private void initTitleBar() {
        imgPopMenuControl = findViewById(R.id.imgPopMenuControl);
        imgDrawableControl = findViewById(R.id.imgMenuControl);
        title = findViewById(R.id.textMainTitle);
        mManager.setPopView(R.layout.menu_popview_layout, new PopWindowManager.OnPopViewInitCallBack() {
            @Override
            public void onCallBack(View popView) {
                ListView listview = popView.findViewById(R.id.lvMenu);
                final List<Map<String, Object>> dataList = new ArrayList<>();
                Map<String, Object> dataMap1 = new HashMap<>();
                dataMap1.put("img", R.mipmap.icon_group_talk);
                dataMap1.put("text", getString(R.string.group_talk));

                Map<String, Object> dataMap2 = new HashMap<>();
                dataMap2.put("img", R.mipmap.icon_add_friend);
                dataMap2.put("text", getString(R.string.add_friend));

                Map<String, Object> dataMap3 = new HashMap<>();
                dataMap3.put("img", R.mipmap.icon_scan);
                dataMap3.put("text", getString(R.string.scan));

                Map<String, Object> dataMap4 = new HashMap<>();
                dataMap4.put("img", R.mipmap.icon_pay_receive);
                dataMap4.put("text", getString(R.string.receive_pay));

                Map<String, Object> dataMap5 = new HashMap<>();
                dataMap5.put("img", R.mipmap.icon_help);
                dataMap5.put("text", getString(R.string.help));
                dataList.add(dataMap1);
                dataList.add(dataMap2);
                dataList.add(dataMap3);
                dataList.add(dataMap4);
                dataList.add(dataMap5);
                SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,
                        dataList,
                        R.layout.menu_item,
                        new String[]{"img", "text"},
                        new int[]{R.id.img_menu_icon, R.id.menu_title}
                );
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(MainActivity.this, (CharSequence) dataList.get(i).get("text"), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        imgDrawableControl.setOnClickListener(this);
        imgPopMenuControl.setOnClickListener(this);
        title.setOnClickListener(this);
    }

    private void init() {
        mFragmentManager = getSupportFragmentManager();
        mTransaction = mFragmentManager.beginTransaction();
        fragmentList = new ArrayList<>();
        mMessage = new MessageFragment();
        mContact = new ContactFragment();
        mDynamic = new DynamicFragment();
        fragmentList.add(mMessage);
        fragmentList.add(mContact);
        fragmentList.add(mDynamic);
        mTransaction.add(R.id.frame_content, mMessage);
        mTransaction.commit();
        initTitleBar();
        initView();
    }

    private void showFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (!fragmentList.get(i).isAdded()) {
                transaction.add(R.id.frame_content, fragmentList.get(i));
            }
            transaction.hide(fragmentList.get(i));
        }
        transaction.show(fragmentList.get(index));
        transaction.commit();
    }

    private void initView() {
        mGroup = findViewById(R.id.radiogroup);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNav = findViewById(R.id.nav);
        headerView = mNav.getHeaderView(0);
        imgDynamic = headerView.findViewById(R.id.img_dynamic);
        mFrame = headerView.findViewById(R.id.frame);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int childId) {
                int index = 0;
                switch (childId) {
                    case R.id.radio_message:
                        index = 0;
                        break;
                    case R.id.radio_contact:
                        index = 1;
                        break;
                    case R.id.radio_dynamic:
                        index = 2;
                        break;
                }
                showFragment(index);
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                IsOpen = true;
                if (mAnim.isPaused()) {
                    mAnim.resume();
                } else if (!mAnim.isStarted()) {
                    mAnim.start();
                }

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                IsOpen = false;
                mAnim.end();
                mAnim.start();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDrawerStateChanged(int state) {
                switch (state) {
                    case DrawerLayout.STATE_DRAGGING:
                        mState = 1;
                        if (mAnim.isRunning()) {
                            mAnim.pause();
                        } else {
                            mAnim.cancel();
                        }
                        break;
                    case DrawerLayout.STATE_IDLE:
                        mState = 0;
                        if (mAnim.isPaused() && IsOpen) {
                            mAnim.resume();
                        }
                        break;
                    case DrawerLayout.STATE_SETTLING:
                        mState = 2;
                        if (mAnim.isRunning()) {
                            mAnim.pause();
                        } else {
                            mAnim.cancel();
                        }
                        break;
                    default:

                        break;
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgMenuControl:
                mDrawerLayout.openDrawer(Gravity.LEFT, true);
                break;
            case R.id.imgPopMenuControl:
                mManager.show(view);
                break;
            case R.id.textMainTitle:

                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mManager != null) {
            mManager.onPause();
            if (mAnim != null) {
                mAnim.cancel();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mManager != null) {
            mManager.onDestroy();
        }
    }
}

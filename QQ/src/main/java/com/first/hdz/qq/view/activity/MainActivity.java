package com.first.hdz.qq.view.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.first.hdz.qq.R;
import com.first.hdz.qq.bean.LoginInfo;
import com.first.hdz.qq.utils.AppUtils;
import com.first.hdz.qq.utils.Constants;
import com.first.hdz.qq.utils.DialogManager;
import com.first.hdz.qq.utils.DisplayUtils;
import com.first.hdz.qq.utils.FileAsyncTask;
import com.first.hdz.qq.utils.PopWindowManager;
import com.first.hdz.qq.utils.ProgressListener;
import com.first.hdz.qq.utils.QQApi;
import com.first.hdz.qq.utils.StringUtils;
import com.first.hdz.qq.view.base.BaseActivity;
import com.first.hdz.qq.view.fragment.ContactFragment;
import com.first.hdz.qq.view.fragment.DynamicFragment;
import com.first.hdz.qq.view.fragment.MessageFragment;

import java.io.File;
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

    private LoginInfo mInfo;

    private QQApi qqApi;

    private PopWindowManager mManager;
    private DialogManager mDialogManager;

    private MessageFragment mMessage;
    private ContactFragment mContact;
    private DynamicFragment mDynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManager = PopWindowManager.getInstance(this);
        mDialogManager = DialogManager.create(this);
        mInfo = (LoginInfo) getIntent().getSerializableExtra(Constants.DATA_KEY);
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
                        mManager.dismiss();
                    }
                });
            }
        });

        imgDrawableControl.setOnClickListener(this);
        imgPopMenuControl.setOnClickListener(this);
        title.setOnClickListener(this);
    }

    private void init() {
        if (AppUtils.getVersionCode(this) < mInfo.getVersionCode()) {
            initUpdateDialog();
        }
        initFragments();
        initTitleBar();
        initView();
    }

    private void initUpdateDialog() {
        mDialogManager.setContentView(R.layout.dialog_layout, new DialogManager.OnActionCallBack() {
            @Override
            public void callBack(View contentView) {
                TextView message = contentView.findViewById(R.id.text_message_dialog);
                TextView title = contentView.findViewById(R.id.text_title_dialog);
                TextView cancel = contentView.findViewById(R.id.text_cancel_dialog);
                TextView confirm = contentView.findViewById(R.id.text_confirm_dialog);
                TextView currentVersion = contentView.findViewById(R.id.text_current_version_dialog);
                TextView updateVersion = contentView.findViewById(R.id.text_update_version_dialog);
                TextView warn = contentView.findViewById(R.id.text_warn_dialog);
                title.setText(getString(R.string.version_update));
                message.setText(getString(R.string.update_message, mInfo.getTips()));
                cancel.setText(getString(R.string.cancel));
                confirm.setText(getString(R.string.confirm));
                currentVersion.setText(getString(R.string.current_version, AppUtils.getVersionName(MainActivity.this)));
                updateVersion.setText(getString(R.string.update_version, mInfo.getVersionName()));
                if (mInfo.isForceStatus()) {
                    warn.setText(getString(R.string.force_update_version));
                } else {
                    warn.setVisibility(View.GONE);
                }
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialogManager.hide();
                        MainActivity.this.finish();
                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialogManager.hide();
                        new FileAsyncTask(MainActivity.this, new ProgressListener() {
                            @Override
                            public void onStart() {
                                initDialog();
                            }

                            @Override
                            public void onUpdateProgress(long progress, long length) {
                                countProgress.setText(getString(R.string.progress_express, progress, length));
                                int p = (int) (progress * 100 / length);
                                mProgress.setText(getString(R.string.percentage, p));
                                mProgressBar.setProgress(p);
                            }

                            @Override
                            public void onEnd(String filePath) {
                                if (mDialog != null && mDialog.isShowing()) {
                                    mDialog.dismiss();
                                }
                                // TODO: 2018/9/30   安装apk
                                if (!StringUtils.isEmpty(filePath)) {
                                    installApk(filePath);
                                } else {
                                    Toast.makeText(MainActivity.this, "下载失败！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).execute(mInfo.getApkUrl());
                    }
                });
            }
        });
        mDialogManager.show();
    }

    private void installApk(String filePath) {
        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(this, "com.first.hdz.apk.provider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

    private Dialog mDialog;
    private ProgressBar mProgressBar;
    private TextView progressTitle;
    private TextView mProgress;
    private TextView countProgress;

    private void initDialog() {
        mDialog = new Dialog(this, R.style.dialog);
        mDialog.setCancelable(false);
        View mContentView = LayoutInflater.from(this).inflate(R.layout.dialog_progress_layout, null);
        mProgressBar = mContentView.findViewById(R.id.progress_bar_dialog);
        countProgress = mContentView.findViewById(R.id.text_byte_progress_dialog);
        mProgress = mContentView.findViewById(R.id.text_progress_dialog);
        progressTitle = mContentView.findViewById(R.id.text_title_progress_dialog);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        progressTitle.setText(R.string.download_progress);
        mProgress.setText("0%");
        countProgress.setText("计算中...");
        mDialog.setContentView(mContentView);
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        int[] size = DisplayUtils.getScreenSize(this);
        params.width = size[0] * 8 / 10;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        mDialog.show();
    }


    private void initFragments() {
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
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.vip_nav:
                        turnToActivity(VIPActivity.class);
                        break;
                    case R.id.wallet_nav:
                        turnToActivity(WalletActivity.class);
                        break;
                    case R.id.stare_nav:
                        turnToActivity(StoreActivity.class);
                        break;
                    case R.id.album_nav:
                        turnToActivity(AlbumActivity.class);
                        break;
                    case R.id.music_nav:
                        turnToActivity(AudioActivity.class);
                        break;
                    case R.id.video_nav:
                        turnToActivity(VideoActivity.class);
                        break;
                    case R.id.setting_nav:
                        turnToActivity(SettingActivity.class);
                        break;
                }
                return true;
            }
        });
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
        if (mManager != null) {
            mManager.onPause();
            if (mAnim != null) {
                mAnim.cancel();
            }
        }
        if (mDialogManager != null) {
            mDialogManager.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mManager != null) {
            mManager.onDestroy();
            mManager = null;
        }
        if (mDialogManager != null) {
            mDialogManager.onDestroy();
            mDialogManager = null;
        }
        super.onDestroy();
    }
}

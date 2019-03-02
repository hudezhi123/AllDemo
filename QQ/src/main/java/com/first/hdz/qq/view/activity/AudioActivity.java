package com.first.hdz.qq.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.first.hdz.qq.R;
import com.first.hdz.qq.view.adapter.AudioPagerAdapter;
import com.first.hdz.qq.view.base.BaseActivity;
import com.first.hdz.qq.view.customview.PagerSlidingTabStrip;
import com.first.hdz.qq.view.fragment.LocalMusicFragment;
import com.first.hdz.qq.view.fragment.NetMusicFragment;

import java.util.ArrayList;
import java.util.List;

public class AudioActivity extends BaseActivity {

    private AudioPagerAdapter adapter;
    private PagerSlidingTabStrip tabStrip;
    private ViewPager viewPager;
    private LocalMusicFragment localFragment;
    private NetMusicFragment netFragment;

    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        init();
    }

    private void init() {
        initView();
        fragmentList = new ArrayList<>();
        initFragmentList();
        adapter = new AudioPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        tabStrip.setTextSize(16);
        tabStrip.setViewPager(viewPager);
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager_audio_tab_content);
        tabStrip = findViewById(R.id.tabs_audio_guide);
    }

    private void initFragmentList() {
        localFragment = new LocalMusicFragment();
        netFragment = new NetMusicFragment();
        fragmentList.add(localFragment);
        fragmentList.add(netFragment);
    }

}

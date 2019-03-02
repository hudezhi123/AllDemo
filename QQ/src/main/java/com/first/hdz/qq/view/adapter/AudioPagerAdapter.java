package com.first.hdz.qq.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.first.hdz.qq.view.fragment.LocalMusicFragment;
import com.first.hdz.qq.view.fragment.NetMusicFragment;


import java.util.List;

/**
 * Created by boy on 2017/6/1.
 */

public class AudioPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public AudioPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public void setFragmentList() {
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String titleName = "";
        switch (position) {
            case 0:
                titleName = LocalMusicFragment.TAG;
                break;
            case 1:
                titleName = NetMusicFragment.TAG;
                break;
        }
        return titleName;
    }
}

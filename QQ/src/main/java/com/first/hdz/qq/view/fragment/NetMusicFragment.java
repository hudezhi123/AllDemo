package com.first.hdz.qq.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.first.hdz.qq.R;
import com.first.hdz.qq.view.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetMusicFragment extends BaseFragment {

    public static String TITLE = "网络歌曲";
    public static String TAG = "NetMusicFragment";


    public NetMusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_net_music, container, false);
    }

}

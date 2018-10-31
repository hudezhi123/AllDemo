package com.first.hdz.qq.view.fragment;


import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.first.hdz.qq.R;
import com.first.hdz.qq.bean.MessageBean;
import com.first.hdz.qq.view.activity.SearchActivity;
import com.first.hdz.qq.view.adapter.MessageAdapter;
import com.first.hdz.qq.view.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout searchBar;
    private RecyclerView relv;
    private LinearLayoutManager mManager;
    private MessageAdapter mAdapter;
    private List<MessageBean> messageList;
    private SmartRefreshLayout mRefresh;


    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_message, container, false);
        relv = contentView.findViewById(R.id.lvMessage);
        init(contentView);
        return contentView;
    }


    private void init(View contentView) {
        messageList = new ArrayList<>();
        getData();
        mManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new MessageAdapter(this.getContext(), messageList);
        initView(contentView);
        relv.setLayoutManager(mManager);
        relv.setAdapter(mAdapter);
    }

    private void initView(View view) {
        mRefresh = view.findViewById(R.id.regresh_message);
        relv = view.findViewById(R.id.lvMessage);
        searchBar = view.findViewById(R.id.linear_search_bar);
        searchBar.setOnClickListener(this);
        mRefresh.setEnableRefresh(true);
        mRefresh.setEnableLoadMore(false);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                try {
                    Thread.sleep(1000);
                    mRefresh.finishRefresh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getData() {
        messageList.add(new MessageBean("重邮那个屯", "实习罗：", "大胖", "17:28", R.mipmap.icon_girl1));
        messageList.add(new MessageBean("重邮电商1班", "秦培峰：", "真香", "18:20", R.mipmap.icon_girl2));
        messageList.add(new MessageBean("毕业设计", "李睿：", "你完了", "18:29", R.mipmap.icon_girl3));
        messageList.add(new MessageBean("重邮那个屯", "刘航：", "滚蛋", "20:30", R.mipmap.icon_girl4));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_search_bar:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), searchBar, getString(R.string.search)).toBundle());
                } else {
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

}

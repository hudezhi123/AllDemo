package com.first.hdz.gamepintu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.first.hdz.gamepintu.bean.BitmapBean;

import java.util.List;

/**
 * created by hdz
 * on 2018/9/7
 */
public class GameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<BitmapBean> bitList;

    public GameAdapter(Context context){
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}

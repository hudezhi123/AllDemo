package com.first.hdz.animation.shopcart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.first.hdz.animation.R;

import java.util.List;

/**
 * created by hdz
 * on 2018/8/21
 */
public class ShopingCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnBuyListener listener;

    public interface OnBuyListener {

        /**
         * 触动动画的控件
         *
         * @param view
         */
        public void buy(View view);
    }

    public void setOnBuyListener(OnBuyListener listener) {
        this.listener = listener;
    }

    private Context mContext;
    private List<ShopItem> mList;

    public ShopingCartAdapter(Context context, List<ShopItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.shopping_item_layout, parent, false);
        return new CartHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShopItem item = mList.get(position);
        if (holder instanceof CartHolder) {
            if (item.getType() == ShopItem.RMB) {
                ((CartHolder) holder).imgGoods.setImageResource(R.mipmap.icon_rmb);
            } else if (item.getType() == ShopItem.DOL) {
                ((CartHolder) holder).imgGoods.setImageResource(R.mipmap.icon_dollar);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private class CartHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgGoods;
        Button btnBuy;

        public CartHolder(View itemView) {
            super(itemView);
            imgGoods = itemView.findViewById(R.id.goods);
            btnBuy = itemView.findViewById(R.id.buy);
            btnBuy.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.buy(view);
            }
        }
    }
}

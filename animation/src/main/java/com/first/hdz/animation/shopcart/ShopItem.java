package com.first.hdz.animation.shopcart;

/**
 * created by hdz
 * on 2018/8/21
 */
public class ShopItem {

    public static final int RMB = 1;
    public static final int DOL = 2;

    public ShopItem() {
    }

    public ShopItem(int type) {
        this.type = type;
    }

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

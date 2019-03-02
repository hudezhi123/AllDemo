package com.first.hdz.gamepintu.GameExample.utils;

import com.first.hdz.gamepintu.GameExample.activity.PuzzleMain;
import com.first.hdz.gamepintu.GameExample.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hdz
 * on 2018/9/7
 */
public class GameUtil {
    public static List<ItemBean> mItemBeans = new ArrayList<>();
    public static ItemBean mBlankItemBean = new ItemBean();

    /**
     * 点击的图片是否可以移动
     *
     * @param position
     * @return
     */
    public static boolean isMovable(int position) {
        int type = PuzzleMain.TYPE;
        //获取空格item
        int blankId = GameUtil.mBlankItemBean.getItemId() - 1;
        //不同行，相差为type
        if (Math.abs(blankId - position) == type) {
            return true;
        }
        if ((blankId / type == position / type) && Math.abs(blankId - position) == 1) {
            return true;
        }
        return false;
    }

    public static void swapItems(ItemBean from, ItemBean blank) {
        ItemBean tempItemBean = new ItemBean();
        //交换BitmapId
        tempItemBean.setBitmapId(from.getBitmapId());
        from.setBitmapId(blank.getBitmapId());
        blank.setBitmapId(tempItemBean.getBitmapId());
        //交换Bitmap
        tempItemBean.setBitmap(from.getBitmap());
        from.setBitmap(blank.getBitmap());
        blank.setBitmap(tempItemBean.getBitmap());
        GameUtil.mBlankItemBean = from;
    }

    /**
     * 生成随机item
     */
    public static void getPuzzleGenerator() {
        int index = 0;
        for (int i = 0; i < mItemBeans.size(); i++) {
            index = (int) (Math.random() * PuzzleMain.TYPE * PuzzleMain.TYPE);
            swapItems(mItemBeans.get(index), GameUtil.mBlankItemBean);
        }
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < mItemBeans.size(); i++) {
            data.add(mItemBeans.get(i).getBitmapId());
        }
        if (!canSolve(data)) {
            getPuzzleGenerator();
        }
    }

    public static boolean canSolve(List<Integer> data) {
        // 获取空格Id
        int blankId = GameUtil.mBlankItemBean.getItemId();
        // 可行性原则
        if (data.size() % 2 == 1) {
            return getInversions(data) % 2 == 0;
        } else {
            // 从底往上数,空格位于奇数行
            if (((blankId - 1) / PuzzleMain.TYPE) % 2 == 1) {
                return getInversions(data) % 2 == 0;
            } else {
                // 从底往上数,空位位于偶数行
                return getInversions(data) % 2 == 1;
            }
        }
    }

    /**
     * 计算倒置和算法
     *
     * @param data 拼图数组数据
     * @return 该序列的倒置和
     */
    public static int getInversions(List<Integer> data) {
        int inversions = 0;
        int inversionCount = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                int index = data.get(i);
                if (data.get(j) != 0 && data.get(j) < index) {
                    inversionCount++;
                }
            }
            inversions += inversionCount;
            inversionCount = 0;
        }
        return inversions;
    }
}

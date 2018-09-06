package com.first.hdz.gamepintu.utils;

import java.util.List;

/**
 * created by hdz
 * on 2018/9/6
 */
public class GameUtils {

    public static final int OLD = 1;
    public static final int EVEN_OLD = 2;
    public static final int EVEN_EVEN = 3;

    public static void swampItems() {

    }

    public static boolean canSolve(List<Integer> data) {

        return false;
    }

    /**
     * @param data
     * @return
     */
    public static int getInversions(List<Integer> data) {
        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                if (data.get(i) > data.get(j) && data.get(i) != data.size() - 1) {
                    count++;
                }
            }
        }
        return count;
    }
}

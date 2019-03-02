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
        int[] result = getInversions(data);
        if (data.size() % 2 == 0) {
            if (result[1] % 2 == 1) {
                return result[0] % 2 == 0;
            } else {
                return result[0] % 2 == 1;
            }
        } else {
            return result[0] % 2 == 0;
        }
    }

    /**
     * @param data
     * @return
     */
    private static int[] getInversions(List<Integer> data) {
        int[] content = new int[2];
        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == data.size() - 1) {
                int original = (int) Math.sqrt(data.size());
                int row = data.get(i) / original + 1;   //从上往下行数
                content[1] = original + 1 - row;
            }
            for (int j = i + 1; j < data.size(); j++) {
                if (data.get(i) > data.get(j) && data.get(i) != data.size() - 1) {
                    count++;
                }
            }
        }
        return content;
    }


}

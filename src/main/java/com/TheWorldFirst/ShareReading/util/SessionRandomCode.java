package com.TheWorldFirst.ShareReading.util;

import java.util.Random;

public class SessionRandomCode {
    /**
     * 字符串池
     */
    private static String[] STR_ARR = new String[] { "a", "b", "c", "d", "e",
            "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
            "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "0", "!", "@", "#", "$", "%", "^", "&", "*", "." };

    /**
     * 生成固定长度的随机字符串
     * @param length
     * @return
     */
    public static String getSessionRandomCode(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(STR_ARR[rand.nextInt(STR_ARR.length)]);
        }
        return sb.toString();
    }
}

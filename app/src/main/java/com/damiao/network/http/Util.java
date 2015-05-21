package com.damiao.network.http;

import java.security.MessageDigest;

/**
 * Created by qiaoyao on 15/5/21.
 */
public class Util {

    public static String MD5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        byte[] btinput = s.getBytes();

        try {
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btinput);
            byte[] md = mdInst.digest();

            int j = md.length;

            char str[] = new char[j * 2];

            int k = 0;

            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}

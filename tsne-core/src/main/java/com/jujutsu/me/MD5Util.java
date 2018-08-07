package com.me;

import java.security.MessageDigest;

/**
 * 采用MD5加密解密
 *
 * @author hjl
 */
public class MD5Util {

    /***
     * MD5加密生成32位md5码
     * @param inStr the in str
     * @return the string
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch (Exception e) {
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 二维double数组转MD5
     *
     * @param doubles 二维double数组
     * @return MD5str
     */
    public static String double22MD5(double[][] doubles) {
        if (doubles == null){
            return null;
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < doubles.length; i++) {
            for (int j = 0; j < 2; j++){
                buf.append(doubles[i][j]);
            }
        }
        return string2MD5(buf.toString());
    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     *
     * @param inStr the in str
     * @return the string
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }
}

package com.java.aes;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Aesutils {

    private static String sKey = "LUCULENT19991101";
    private static String ivParameter = "LUCULENT19991101";

    public Aesutils() {
    }

    /**
     * 加密
     * @param sSrc
     * @return
     * @throws Exception
     */
    public static String encrypt(String sSrc) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(1, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return (new BASE64Encoder()).encode(encrypted);
    }

    /**
     * 解密
     * @param sSrc
     * @return
     * @throws Exception
     */
    public static String decrypt(String sSrc) throws Exception {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(2, skeySpec, iv);
            byte[] encrypted1 = (new BASE64Decoder()).decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String encData, String secretKey, String vector) throws Exception {
        if (secretKey == null) {
            return null;
        } else if (secretKey.length() != 16) {
            return null;
        } else {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = secretKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
            cipher.init(1, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
            return (new BASE64Encoder()).encode(encrypted);
        }
    }

    public String decrypt(String sSrc, String key, String ivs) throws Exception {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(2, skeySpec, iv);
            byte[] encrypted1 = (new BASE64Decoder()).decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception var11) {
            return null;
        }
    }

    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < bytes.length; ++i) {
            strBuf.append((char) ((bytes[i] >> 4 & 15) + 97));
            strBuf.append((char) ((bytes[i] & 15) + 97));
        }

        return strBuf.toString();
    }

}

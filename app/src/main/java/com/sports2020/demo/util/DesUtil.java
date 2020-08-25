package com.sports2020.demo.util;

import java.security.Key;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES算法
 * <p>
 * Created by KarenChia on 2019/12/10.
 */
public class DesUtil {

    public static void main(String[] s) {
        String encode = DesUtil.getInstance().encode("123");
        System.out.println("encode = " + encode);
        String decode = DesUtil.getInstance().decode("6946134A6FC8916C");
        System.out.println("decode = " + decode);
    }

    /**
     * 单例
     */
    private static DesUtil instance;
    /**
     * 加密字符串
     */
    private final String key = "Jiabu_desencrypt_2016";
    private static final String ALGORITHM_DES = "DES/ECB/PKCS7Padding";

    private DesUtil() {
    }

    public static DesUtil getInstance() {
        if (instance == null) {
            synchronized (DesUtil.class) {
                if (instance == null) {
                    instance = new DesUtil();
                }
            }
        }
        return instance;
    }

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    public String encode(String data) {
        if (data == null)
            return null;
        try {
            String value = MD5Util.md5Decode32(key).substring(0, 8).toUpperCase();
            DESKeySpec dks = new DESKeySpec(value.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(value.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            //cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return byte2String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 二行制转字符串
     *
     * @param b
     * @return String
     */
    private String byte2String(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase(Locale.CHINA);
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @return 解密后的字节数组
     */
    public String decode(String data) {
        if (data == null)
            return null;
        try {
            String value = MD5Util.md5Decode32(key).substring(0, 8).toUpperCase();
            DESKeySpec dks = new DESKeySpec(value.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(value.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            //cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(byte2hex(data.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 二进制转化成16进制
     *
     * @param b
     * @return byte
     */
    private byte[] byte2hex(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException();
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
}

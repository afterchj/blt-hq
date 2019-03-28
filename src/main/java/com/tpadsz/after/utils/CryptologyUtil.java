package com.tpadsz.after.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hongjian.chen on 2019/3/28.
 */
public class CryptologyUtil {

    public static final String md5(String playText, String salt) {
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        //密码
        Object source = playText;
        //加密次数
        int hashIterations = 3;
        SimpleHash result = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
        return result.toString();
    }

    public static String generateSalt(String playText) {
        String src = playText + System.currentTimeMillis();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(src.getBytes());
            //把byte数组转换为字符串
            return Hex.encodeHexString(md5Bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String salt = generateSalt("admin");
        System.out.println("salt:" + salt + ",length:" + salt.length());//CC实现转换
        String password = md5("123456", salt);
        System.out.println("password=" + password);
        //加密后的结果
        //3bcbb857c763d1429a24959cb8de2593
    }
}

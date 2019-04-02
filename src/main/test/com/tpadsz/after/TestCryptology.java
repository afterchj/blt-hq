package com.tpadsz.after;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by hongjian.chen on 2019/3/28.
 */
public class TestCryptology {

    String src = "123456";

    @Test
    public void jdkBase64() {
        try {
            //加密：
            BASE64Encoder encoder = new BASE64Encoder();
            String encode = encoder.encode(src.getBytes());
            System.out.println("encode : " + encode);
            //解密
            BASE64Decoder decoder = new BASE64Decoder();
            String decode = new String(decoder.decodeBuffer(encode));
            System.out.println("decode : " + decode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void commonsCodesBase64() {
        //加密
        byte[] encodeBytes = Base64.encodeBase64(src.getBytes());
        System.out.println("encode : " + new String(encodeBytes));
        //解密
        byte[] decodeBytes = Base64.decodeBase64(encodeBytes);
        System.out.println("decode : " + new String(decodeBytes));
    }


    @Test
    public void jdkMD5() {
        String src= String.valueOf(System.currentTimeMillis());
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(src.getBytes());
            //把byte数组转换为字符串
            String md5Str = Hex.encodeHexString(md5Bytes);
            System.out.println("md5Str:" + md5Str + ",length:" + md5Str.length());//CC实现转换
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void jdkSHA1() {
        try {
            //1.初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            System.out.println("Public Key : " + Base64.encodeBase64String(rsaPublicKey.getEncoded()));
            System.out.println("Private Key : " + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
            //2.私钥加密、公钥解密——加密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("私钥加密、公钥解密——加密 : " + Base64.encodeBase64String(result));
            //3.私钥加密、公钥解密——解密
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            result = cipher.doFinal(result);
            System.out.println("私钥加密、公钥解密——解密：" + new String(result));
            //4.公钥加密、私钥解密——加密
            x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            result= cipher.doFinal(src.getBytes());
            System.out.println("公钥加密、私钥解密——加密 : " + Base64.encodeBase64String(result));
            //5.公钥加密、私钥解密——解密
            pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            result = cipher.doFinal(result);
            System.out.println("公钥加密、私钥解密——解密：" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

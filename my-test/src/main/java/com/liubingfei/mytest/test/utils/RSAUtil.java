package com.liubingfei.mytest.test.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;


/**
 * 签名加密工具
 *
 * @author horadirm
 * @date 2023/8/25 11:42
 */
public class RSAUtil {

    /**
     * 数字签名，密钥算法
     */
    private static final String RSA_KEY_ALGORITHM = "RSA";

    /**
     * 数字签名签名/验证算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 密钥转成字符串
     *
     * @param key
     * @return
     */
    public static String encodeBase64String(byte[] key) {
        return Base64.encodeBase64String(key);
    }

    /**
     * 密钥转成byte[]
     *
     * @param key
     * @return
     */
    public static byte[] decodeBase64(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * RSA签名
     *
     * @param data   待签名数据
     * @param priKey 私钥
     * @return 签名
     * @throws Exception
     */
    public static String sign(byte[] data, byte[] priKey) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(privateKey);
        // 更新
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }


    public static void main(String[] args) throws Exception {
        Object body = new Object();
        String jsonString = JSONObject.toJSONString(body);
        System.out.println(jsonString);
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
		String prepareSignString = jsonString + timestamp;
        System.out.println(prepareSignString);
        String sign = RSAUtil.sign(prepareSignString.getBytes(), RSAUtil.decodeBase64("privateKey"));
        System.out.println(sign);
    }

}

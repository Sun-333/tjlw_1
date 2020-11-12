package com.uestc.tjlw.hbase.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import sun.security.rsa.RSASignature;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author yushansun
 * @title: SignVerifyUtil
 * @projectName tjlw
 * @description: RSA签名工具类
 * @date 2020/11/105:48 下午
 */
public class SignVerifyUtil {

    public static String dataSign(String ywData,String prikeyYlf) throws Exception {
        Sign sign = new Sign(SignAlgorithm.SHA1withRSA, prikeyYlf, null);
        byte[] encrypt = sign.sign(StrUtil.bytes(ywData, CharsetUtil.CHARSET_UTF_8));
        return Base64.encode(encrypt);
    }

    public static boolean verifySign(String ywData,String signData,String publicKey) throws Exception {
        Sign sign = new Sign(SignAlgorithm.SHA1withRSA, null, publicKey);
        boolean encrypt = sign.verify(StrUtil.bytes(ywData), Base64.decode(signData));
        return encrypt;
    }
    public static String[] generateKey(){
        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
        String privateKeyStr = Base64.encode(keyPair.getPrivate().getEncoded());
        String publicKeyStr = Base64.encode(keyPair.getPublic().getEncoded());
        return new String[]{privateKeyStr,publicKeyStr};
    }
}

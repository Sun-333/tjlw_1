package cn.mrray.raybaas.demo.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.mrray.raybaas.demo.entity.Account;

import java.security.KeyPair;
import java.util.Map;

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
    public static boolean verifySign(Object obj,String signData,String pk)  {
        Map<String,Object> map = BeanUtil.beanToMap(obj,false,true);
        map.remove("sig");
        try {
            return verifySign(map.toString(),signData,pk);
        } catch (Exception e) {
            throw new RuntimeException("验证算法报错");
        }
    }
}

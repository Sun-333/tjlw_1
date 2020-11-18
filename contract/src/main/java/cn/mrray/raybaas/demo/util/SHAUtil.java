package cn.mrray.raybaas.demo.util;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {

  /***
   * 利用Apache commons-codec的工具类实现SHA-256加密
   * 
   * @param originalStr 加密前的报文
   * @return String 加密后的报文
   */
  public static String getSHA256BasedMD(String originalStr) {
    MessageDigest messageDigest;
    String encdeStr = "";
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
      byte[] hash = messageDigest.digest(originalStr.getBytes("UTF-8"));
      encdeStr = Hex.encodeHexString(hash);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return encdeStr;
  }

  /***
   * 利用Hutool的工具类实现SHA-256加密
   * 
   * @param originalStr 加密前的报文
   * @return String 加密后的报文
   */
  public static String sha256BasedHutool(String originalStr) {
    return DigestUtil.sha256Hex(originalStr);
  }

  /**
   * 生成MD5加密计算摘要
   * @param str 字符串
   * @return  结果
   */
  public static String getMD5String(String str) {
    try {
      // 生成一个MD5加密计算摘要
      MessageDigest md = MessageDigest.getInstance("MD5");
      // 计算md5函数
      md.update(str.getBytes());
      // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
      // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
      //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
      return new BigInteger(1, md.digest()).toString(16);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}

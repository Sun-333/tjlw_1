package com.uestc.tjlw.nettyclient.util;

/**
 * @author yushansun
 * @title: ByteUtil
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/138:42 下午
 */
public class ByteUtil {
    public static int getHight(byte b) {
        return (b>>8) & 0x0f;
    }

    public static int getLow(byte b) {
        return b & 0x0f;
    }

    public static int getInt2(byte[] bytes) {
        return (0xff & bytes[1]) | (0xff00 & (bytes[0] << 8));
    }

    public static int getInt4(byte[] bytes) {
        return (0xff & bytes[3]) | (0xff00 & (bytes[2] << 8)) | (0xff0000 & (bytes[1] << 16)) | (0xff000000 & (bytes[0] << 24));
    }
    public static String deCodeIpAddr(byte[] ipArray) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ipArray[0]);
        stringBuffer.append(".");

        stringBuffer.append(ipArray[1]);
        stringBuffer.append(".");

        stringBuffer.append(ipArray[2]);
        stringBuffer.append(".");

        stringBuffer.append(ipArray[3]);
        return stringBuffer.toString();
    }

    public static String deCodeMacAddr() {
        return null;
    }

    /**
     * 十进制转化为十六进制
     */
    public static String dtoh(int a) {
        String bb = "";
        while ((a / 16) != 0) {
            int b = a % 16;
            String s = "" + b;
            if (b == 10) {
                s = "A";
            }
            if (b == 11) {
                s = "B";
            }
            if (b == 12) {
                s = "C";
            }
            if (b == 13) {
                s = "D";
            }
            if (b == 14) {
                s = "E";
            }
            if (b == 15) {
                s = "F";
            }
            a = a / 16;
            bb += s;
        }
        int k = a % 16;
        if (k >= 10) {
            if (k == 10) {
                bb += "A";
            }
            if (k == 11) {
                bb += "B";
            }
            if (k == 12) {
                bb += "C";
            }
            if (k == 13) {
                bb += "D";
            }
            if (k == 14) {
                bb += "E";
            }
            if (k == 15) {
                bb += "F";
            }
        } else {
            bb += k;
        }
        return bb;
    }
}

package com.uestc.tjlw.jpcap.process;

import com.uestc.tjlw.jpcap.util.ByteUtil;

/**
 * @author yushansun
 * @title: test
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/136:18 下午
 */
public class test {
    public static void main(String[] args) {
        System.out.println("192.168.50.141".getBytes().length);
        byte a = (byte) 0xf2;
        System.out.println(ByteUtil.getInt2(new byte[]{a, a}));
        System.out.println(ByteUtil.getHight(a));
        System.out.println(Integer.toHexString(ByteUtil.getHight(a)));
        System.out.println(Integer.toHexString(ByteUtil.getLow(a)));
    }
}

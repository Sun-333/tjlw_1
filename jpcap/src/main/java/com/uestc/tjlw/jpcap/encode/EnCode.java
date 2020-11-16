package com.uestc.tjlw.jpcap.encode;

import com.uestc.tjlw.jpcap.pojo.Info;
import com.uestc.tjlw.jpcap.pojo.P4Info;
import com.uestc.tjlw.jpcap.pojo.Switch;
import com.uestc.tjlw.jpcap.util.ByteUtil;
import net.sourceforge.jpcap.net.IPPacket;
import net.sourceforge.jpcap.net.UDPPacket;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yushansun
 * @title: EnCode
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/138:14 下午
 */
public class EnCode {
    private static int ETHERNET= 14;
    private static int IPV4=20;
    private static int IPV4_OPTION=2;
    private static int MRI =2;
    private static int SWITCH=8;

    public static void enCode(byte[] bytes){
        List<Byte> info = new LinkedList<>();
        for (int i=0;i<bytes.length;i++)
            info.add(bytes[i]);
        if (bytes.length<ETHERNET+IPV4+IPV4_OPTION+MRI+SWITCH);
            System.out.println("数据长度不满足最小条件");
        Info msg = new Info(info);
        System.out.println(("解析数据格式结果" + msg.toString()));
    }

    public static void enCode(UDPPacket udpPacket){
        try {
            P4Info p4Info = new P4Info();
            List<Switch> switches = new ArrayList<>();
            byte[] data = udpPacket.getUDPData();
            String isoData = new String(data, "ISO-8859-1");
            p4Info.setBagsize(isoData.length()+"");         //设置数据大小

            String destAddress  = udpPacket.getDestinationAddress();
            p4Info.setTargetIp(destAddress);                //设置目的IP地址

            String sourceAddress = udpPacket.getSourceAddress();
            p4Info.setSourceIp(sourceAddress);              //设置源端口号

            p4Info.setSourcePort(udpPacket.getDestinationPort()+"");
            p4Info.setTargetPort(udpPacket.getDestinationPort()+"");
            p4Info.setProtocolType("udp");                  //设置协议类型

            byte[] ipData = udpPacket.getIPData();
            byte[] headInfo=udpPacket.getIPHeader();

            List<Byte> count = new ArrayList<>();
            count.add(headInfo[22]);
            count.add(headInfo[23]);
            Info.Mir mir = new Info.Mir(count);

            for (int i=0;i<3;i++){
                List<Byte> switchesByte = new ArrayList<>();
                Switch swi = new Switch();
                byte[] a = new byte[]{headInfo[24+8*i],headInfo[24+8*i+2],headInfo[24+8*i+3],headInfo[24+8*i+4]};
                swi.setSwitchId(ByteUtil.getInt4(a)+"");
                switches.add(swi);
            }
            p4Info.setSwitchList(switches);
            System.out.println(mir.toString());
            System.out.println(p4Info.toString());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

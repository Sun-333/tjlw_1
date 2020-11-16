package com.uestc.tjlw.nettyclient.processer;

import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.pojo.Switch;
import com.uestc.tjlw.nettyclient.util.ByteUtil;
import net.sourceforge.jpcap.net.UDPPacket;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yushansun
 * @title: EncodeP4Info
 * @projectName tjlw
 * @description: 将流数据转换为p4Info
 * @date 2020/11/121:28 下午
 */
public class EncodeP4Info {

    public P4Info encode(UDPPacket udpPacket){
        P4Info p4Info = new P4Info();
        List<Switch> switches = new ArrayList<>();
        try {
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

            int count = ByteUtil.getInt2(new byte[]{headInfo[22],headInfo[23]});

            for (int i=0;i<count;i++){
                List<Byte> switchesByte = new ArrayList<>();
                Switch swi = new Switch();
                byte[] a = new byte[]{headInfo[24+8*i],headInfo[24+8*i+2],headInfo[24+8*i+3],headInfo[24+8*i+4]};
                swi.setSwitchId(ByteUtil.getInt4(a)+"");
                switches.add(swi);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return p4Info;
    }
}

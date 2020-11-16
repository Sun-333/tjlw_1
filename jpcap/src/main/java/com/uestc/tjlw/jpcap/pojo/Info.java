package com.uestc.tjlw.jpcap.pojo;

import com.uestc.tjlw.jpcap.util.ByteUtil;
import lombok.Data;

import java.util.List;

/**
 * @author yushansun
 * @title: Info
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/138:34 下午
 */
@Data
public class Info {
    private Eth eth;
    private Ipv4 ipv4;
    private Ipv4Option ipv4Option;
    private Mir mir;

    private static int ETHERNET= 14;
    private static int IPV4=20;
    private static int IPV4_OPTION=2;
    private static int MRI =2;
    private static int SWITCH=8;

    public Info(List<Byte> msg){
        if (msg.size()<ETHERNET+IPV4+IPV4_OPTION+MRI) return;
        this.eth=new Eth(msg.subList(0,ETHERNET));
        this.ipv4 = new Ipv4(msg.subList(ETHERNET,ETHERNET+IPV4));
        this.ipv4Option = new Ipv4Option(msg.subList(ETHERNET+IPV4,ETHERNET+IPV4+IPV4_OPTION));
        this.mir = new Mir(msg.subList(ETHERNET+IPV4+IPV4_OPTION,ETHERNET+IPV4+IPV4_OPTION+MRI));
    }


    @Data
    class Eth{
        //12个16进制数，如：00-16-EA-AE-3C-40
        private String  dstAddr; //6 byte
        private String  srcAddr; //6 byte
        private String  etherType; // 2byte
        public Eth(List<Byte> ethBytes){
            if (ethBytes.size()!=14) return;
            StringBuffer stringBuffer_dstAddr = new StringBuffer();
            for (int i=0;i<6;i++){
                stringBuffer_dstAddr.append(Integer.toHexString(ByteUtil.getHight(ethBytes.get(i))));
                stringBuffer_dstAddr.append(Integer.toHexString(ByteUtil.getLow(ethBytes.get(i))));
                if (i<5)
                    stringBuffer_dstAddr.append("-");
            }
            dstAddr=stringBuffer_dstAddr.toString();

            StringBuffer stringBuffer_srcAddr = new StringBuffer();
            for (int i=6;i<12;i++){
                stringBuffer_srcAddr.append(Integer.toHexString(ByteUtil.getHight(ethBytes.get(i))));
                stringBuffer_srcAddr.append(Integer.toHexString(ByteUtil.getLow(ethBytes.get(i))));
                if (i<12)
                    stringBuffer_srcAddr.append("-");
            }
            srcAddr=stringBuffer_srcAddr.toString();
        }
    }
    @Data
    class Ipv4{

        String    version;
        String    ihl;
        String    diffserv;
        String    totalLen;
        String    identification;
        String    flags;
        String    fragOffset;
        String    ttl;
        String    protocol;
        String    hdrChecksum;
        String    srcAddr;
        String    dstAddr;
        public Ipv4(List<Byte> info){
            version=ByteUtil.getHight(info.get(0))+"";
            ihl=ByteUtil.getLow(info.get(0))+"";
            diffserv=info.get(1).toString();
            totalLen=ByteUtil.getInt2(new byte[]{info.get(2),info.get(3)})+"";
            identification=ByteUtil.getInt2(new byte[]{info.get(4),info.get(5)})+"";
            flags="";
            fragOffset="";
            ttl=info.get(8).toString();
            protocol=info.get(9).toString();
            hdrChecksum=ByteUtil.getInt2(new byte[]{info.get(10),info.get(11)})+"";

            srcAddr = ByteUtil.deCodeIpAddr(new byte[]{info.get(12),info.get(13),info.get(14),info.get(15)});
            dstAddr = ByteUtil.deCodeIpAddr(new byte[]{info.get(16),info.get(17),info.get(18),info.get(19)});
        }
    }
    @Data
    class Ipv4Option{
        String copyFlag;
        String optClass;
        String option;
        String optionLength;
        public Ipv4Option(List<Byte> info){
            optionLength= info.get(1)+"";
        }
    }
    @Data
   public static class  Mir {
        String count;
        public Mir(List<Byte> info){
            count = ByteUtil.getInt2(new byte[]{info.get(0),info.get(1)})+"";
        }
    }
    @Data
    public static class Switch{
         String  id;
         String  qdepth;
         public Switch(List<Byte> info){
             int id =ByteUtil.getInt4(new byte[]{info.get(0),info.get(1),info.get(2),info.get(3)});
             this.id = id+"";
         }
    }
}

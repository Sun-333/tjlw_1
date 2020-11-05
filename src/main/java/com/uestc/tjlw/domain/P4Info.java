package com.uestc.tjlw.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yushansun
 * @title: P4Info
 * @projectName tjlw
 * @description: p4交换机器出来的信息
 * @date 2020/11/43:50 下午
 */
@Data
public class P4Info {
    public static final int baseKeySize=7;
    public static final int switchKeySize=6;


    //行键
    private String timestamp;
    //源 IP
    private String sourceIp;
    //目标IP
    private String targetIp;
    // 源端⼝号
    private String sourcePort;
    //⽬的端⼝号
    private String targetPort;
    //协议类型
    private String protocolType;
    //时间戳
    private String protocolTimestamp;
    //交换机信息
    private List<Switch> switchList;


    //baseInfo对应familyName;
    @Value("${p4.baseInfo.familyName}")
    private static  String baseFamilyName="baseInfo";

    //switches对应familyName;
    @Value("${p4.switches.familyName}")
    private static  String switchesFamilyName="switchesInfo";

    /**
     * 获取baseCase columns
     * @return 数组中为p4基本信息列表名
     */
    public static String[] getColumns(){
        return new String[]{"sourceIp","targetIp","sourcePort","targetPort","protocolType","protocolTimestamp","switcheIds"};
    }

    /**
     * 获取当前数据RowKey值
     * @return 当前数据RowKey值
     */
    public  String getRowKey(){
        return timestamp;
    }

    /**
     * 获取baseInfo Values
     * @return
     */
    public String[] getValues(){
        StringBuffer stringBuffer = new StringBuffer();
        switchList.forEach(aSwitch -> {
            stringBuffer.append(aSwitch.getSwitchId()+",");
        });
        return new String[]{sourceIp,targetIp,sourcePort,targetPort,protocolType,protocolTimestamp,stringBuffer.toString()};
    }

    /**
     * 获取basInfo 对应列镞
     */
    public static String getBaseInfoFamilyName(){
        return baseFamilyName;
    }
    /**
     * 获取swichesInfo 对应列镞
     */
    public static String getSwitchesFamilyName(){
        return switchesFamilyName;
    }

    /**
     * 获取p4存储时所用的所有familyNames
     */
    public static String[] getFamilyNames(){
        return new String[]{baseFamilyName,switchesFamilyName};
    }

    public P4Info(String timestamp, String sourceIp, String targetIp, String sourcePort, String targetPort, String protocolType, String protocolTimestamp) {
        this.timestamp = timestamp;
        this.sourceIp = sourceIp;
        this.targetIp = targetIp;
        this.sourcePort = sourcePort;
        this.targetPort = targetPort;
        this.protocolType = protocolType;
        this.protocolTimestamp = protocolTimestamp;
    }

    public P4Info() {
    }

    /**
     * 将Hbase查询p4数据行转换为P4Info类
     * @param info hbase中一行k-v数据
     * @return 映射为 P4Info对象
     */
    public static P4Info getBaseInfoInstance(Map<String,String> info){
        P4Info res = new P4Info();
        List<Switch> switches = new ArrayList<>();
        //设置baseInfo
        res.timestamp=info.get("timestamp");
        res.sourceIp=info.get("sourceIp");
        res.targetIp=info.get("targetIp");
        res.sourcePort=info.get("sourcePort");
        res.targetPort=info.get("targetPort");
        res.protocolType=info.get("protocolType");
        res.protocolTimestamp=info.get("protocolTimestamp");
        String[] ids = info.get("switcheIds").split(",");

        for (String str:ids){
            int switchId = Integer.parseInt(str);
            Switch aSwitch = new Switch();
            aSwitch.setDownIP(info.get(switchId+"_downIP"));
            aSwitch.setDownPort(info.get(switchId+"_downPort"));
            aSwitch.setSwitchId(info.get(switchId+"_switchId"));
            aSwitch.setTimestamp(info.get(switchId+"_timestamp"));
            aSwitch.setUpIp(info.get(switchId+"_upIp"));
            aSwitch.setUpPort(info.get(switchId+"_upPort"));
            switches.add(aSwitch);
        }
        res.setSwitchList(switches);
        return res;
    }
}

package com.uestc.tjlw.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author yushansun
 * @title: P4Info
 * @projectName tjlw
 * @description: p4交换机器出来的信息
 * @date 2020/11/43:50 下午
 */
@Data
public class P4Info {
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
        return new String[]{"sourceIp","targetIp","sourcePort","targetPort","protocolType","protocolTimestamp"};
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
        return new String[]{sourceIp,targetIp,sourcePort,targetPort,protocolType,protocolTimestamp};
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
}

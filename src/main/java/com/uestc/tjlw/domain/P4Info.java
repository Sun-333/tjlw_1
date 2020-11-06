package com.uestc.tjlw.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "p4数据基本信息")
public class P4Info {
    public static final int baseKeySize=7;
    public static final int switchKeySize=6;


    //行键
    @ApiModelProperty(value = "行键", required = true, example = "28918943")
    private String timestamp;

    //源 IP
    @ApiModelProperty(value = "源IP", required = true, example = "192.168.50.2")
    private String sourceIp;

    //目标IP
    @ApiModelProperty(value = "目标IP", required = true, example = "192.168.50.4")
    private String targetIp;

    // 源端⼝号
    @ApiModelProperty(value = "源端⼝号", required = true, example = "80")
    private String sourcePort;

    //⽬的端⼝号
    @ApiModelProperty(value = "⽬的端⼝号", required = true, example = "80")
    private String targetPort;

    //协议类型
    @ApiModelProperty(value = "协议类型", required = true, example = "http")
    private String protocolType;

    //时间戳
    @ApiModelProperty(value = "时间戳", required = true, example = "28918943")
    private String protocolTimestamp;

    //交换机信息
    @ApiModelProperty(value = "交换机信息", required = true)
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
    @JsonIgnore
    @JSONField(serialize = false)
    public static String[] getColumns(){
        return new String[]{"sourceIp","targetIp","sourcePort","targetPort","protocolType","protocolTimestamp","switcheIds"};
    }

    /**
     * 获取当前数据RowKey值
     * @return 当前数据RowKey值
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public  String getRowKey(){
        return timestamp;
    }

    /**
     * 获取baseInfo Values
     * @return
     */
    @JsonIgnore
    @JSONField(serialize = false)
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
    @JsonIgnore
    @JSONField(serialize = false)
    public static String getBaseInfoFamilyName(){
        return baseFamilyName;
    }
    /**
     * 获取swichesInfo 对应列镞
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public static String getSwitchesFamilyName(){
        return switchesFamilyName;
    }

    /**
     * 获取p4存储时所用的所有familyNames
     */
    @JsonIgnore
    @JSONField(serialize = false)
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
        if (info==null||info.size()==0) return null;
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

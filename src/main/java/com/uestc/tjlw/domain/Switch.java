package com.uestc.tjlw.domain;

import lombok.Data;

/**
 * @author yushansun
 * @title: Switch
 * @projectName tjlw
 * @description: 交换机信息
 * @date 2020/11/44:01 下午
 */
@Data
public class Switch {
    //交换机ID
    private String switchId;
    //时间戳
    private String timestamp;
    //上行交换机端口
    private String upPort;
    //上行交换机内网地址
    private String upIp;
    //下行交换机端口
    private String downPort;
    //下行交换机地址
    private String downIP;

    /**
     * 获取switche columns
     * @return 数组中为当前交换机列表名
     */
    public  String[] getColumns(){
        return new String[]{switchId+"_switchId",switchId+"_timestamp",switchId+"_upPort",
                switchId+"_upIp",switchId+"_downPort",switchId+"_downIP"};
    }

    /**
     * 获取交换机 value
     * @return
     */
    public String[] getValues(){
        return new String[]{switchId,timestamp,upPort,upIp,downPort,downIP};
    }

    public Switch(String switchId, String timestamp, String upPort, String upIp, String downPort, String downIP) {
        this.switchId = switchId;
        this.timestamp = timestamp;
        this.upPort = upPort;
        this.upIp = upIp;
        this.downPort = downPort;
        this.downIP = downIP;
    }

    public Switch() {
    }
}

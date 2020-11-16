package com.uestc.tjlw.jpcap.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "交换机ID", required = true,example = "1")
    private String switchId;
    //时间戳
    @ApiModelProperty(value = "时间戳", required = true,example = "231841")
    private String timestamp;
    //上行交换机端口
    @ApiModelProperty(value = "上行交换机端口", required = true,example = "80")
    private String upPort;
    //上行交换机内网地址
    @ApiModelProperty(value = "上行交换机内网地址", required = true,example = "192.168.50.240")
    private String upIp;
    //下行交换机端口
    @ApiModelProperty(value = "下行交换机端口", required = true,example = "8080")
    private String downPort;
    //下行交换机地址
    @ApiModelProperty(value = "下行交换机地址", required = true,example = "192.168.50.241")
    private String downIP;

    /**
     * 获取switche columns
     * @return 数组中为当前交换机列表名
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public  String[] getColumns(){
        return new String[]{switchId+"_switchId",switchId+"_timestamp",switchId+"_upPort",
                switchId+"_upIp",switchId+"_downPort",switchId+"_downIP"};
    }

    /**
     * 获取交换机 value
     * @return
     */
    @JsonIgnore
    @JSONField(serialize = false)
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

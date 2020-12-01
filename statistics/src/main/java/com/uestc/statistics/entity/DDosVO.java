package com.uestc.statistics.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author yushansun
 * @title: DDosVO
 * @projectName tjlw
 * @description: DDos 检测结果实体类
 * @date 2020/11/303:40 下午
 */
@Data
@ApiModel(value = "DDos检测结果")
public class DDosVO {
    private String rowKey;
    @ApiModelProperty(value = "攻击源数量", required = true)
    private String time;
    @ApiModelProperty(value = "攻击源数量", required = true)
    private String count;
    @ApiModelProperty(value = "目标IP", required = true)
    private String targetIp;
    @ApiModelProperty(value = "危险级别", required = true)
    private String level;

    /**
     * 获取baseCase columns
     * @return 数组中为p4基本信息列表名
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public static String[] getColumns(){
        return new String[]{"time","count","targetIp","level"};
    }

    /**
     * 获取列镞
     * @return
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public static String[] getFamilyName(){
        return new String[]{"info"};
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public static String getTableName(){
        return "ddosInfo";
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public String getRowKey(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(time).append("_").append(targetIp);
        return stringBuffer.toString();
    }

    /**
     * 获取 Values
     * @return
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public String[] getValues(){
        return new String[]{time,count,targetIp,level};
    }

    /**
     * 将Hbase查询ddos数据行转换为ddos对象
     * @param info hbase中一行k-v数据
     * @return 映射为 DDosVO对象
     */
    public static DDosVO getBaseInfoInstance(Map<String,String> info){
        if (info==null||info.size()==0) return null;
        DDosVO res = new DDosVO();
        //设置baseInfo
        res.time = info.get("time");
        res.count=info.get("count");
        res.targetIp=info.get("targetIp");
        res.level=info.get("level");
        return res;
    }
}

package com.uestc.tjlw.controller;

import com.uestc.tjlw.domain.P4Info;
import com.uestc.tjlw.service.P4Service;
import com.uestc.tjlw.util.GlobalRet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yushansun
 * @title: P4Controller
 * @projectName tjlw
 * @description: P4基本接口
 * @date 2020/11/69:20 上午
 */
@Api(tags = {"P4基本操作API"})
@Slf4j
@RestController
@RequestMapping("certificate")
public class P4Controller {
    @Autowired
    private P4Service p4Service;

    @ApiOperation(value = "添加p4信息到hbase")
    @PostMapping("/addP4Info")
    public GlobalRet<Boolean> addP4Info(@RequestBody P4Info p4Info){
        p4Service.add2Hbase(p4Info);
        return new GlobalRet<>(true);
    }

    @ApiOperation(value = "以RowKey查询数据")
    @PostMapping("/findByTimestamp")
    public GlobalRet<P4Info> findByTimestamp(String timestamp){
        P4Info p4Info = p4Service.findByTimestamp(timestamp);
        return new GlobalRet<>(p4Info);
    }

    @ApiOperation(value = "多列值与设定目标相等多条件查询")
    @PostMapping("/findColumnsEqualCondition")
    public GlobalRet<List<P4Info>> findColumnsEqualCondition(
            @RequestParam(value = "源地址IP",required = false) String sourceIp,
            @RequestParam(value = "目标地址IP",required = false)String targetIp,
            @RequestParam(value = "源地址端口",required = false) String sourcePort,
            @RequestParam(value = "目标地址端口",required = false)String targetPort,
            @RequestParam(value = "协议类型",required = false) String protocolType,
            @RequestParam(value = "协议发送时间戳",required = false) String protocolTimestamp
            ){
        List<P4Info> p4InfoList = p4Service.findColumnsEqualCondition(
                new String[]{"sourceIp","targetIp","sourcePort","targetPort","protocolType","protocolTimestamp"},
                new String[]{sourceIp,targetIp,sourcePort,targetPort,protocolType,protocolTimestamp});
        return new GlobalRet<>(p4InfoList);
    }
}

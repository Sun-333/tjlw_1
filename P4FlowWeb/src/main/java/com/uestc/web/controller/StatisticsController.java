package com.uestc.web.controller;

import com.uestc.tjlw.common.pojo.Statistics;
import com.uestc.tjlw.common.util.GlobalRet;
import com.uestc.web.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yushansun
 * @title: StatisticsController
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/191:55 上午
 */

@Api(tags = {"p4流统计"})
@Slf4j
@RestController
@RequestMapping("statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    @ApiOperation(value = "统计一天流量信息")
    @GetMapping("/findHours")
    public GlobalRet<List<Statistics>> findHours(){
        return new GlobalRet(statisticsService.findHours());
    }

    @ApiOperation(value = "统计一周流量信息")
    @GetMapping("/findDays")
    public GlobalRet<List<Statistics>> findDays(){
        return new GlobalRet(statisticsService.findDays());
    }

    /*public GlobalRet<Boolean> uploadDDosDetection(){

    }*/
}

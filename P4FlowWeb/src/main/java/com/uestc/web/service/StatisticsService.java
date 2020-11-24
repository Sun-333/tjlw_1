package com.uestc.web.service;

import com.uestc.web.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yushansun
 * @title: StatisticsService
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/191:58 上午
 */
@Service(value = "StatisticsService")
@Slf4j
public class StatisticsService {
    @Resource
    private RedisUtil redisUtil;

    public List<Object> findHours(){
      long size = redisUtil.lGetListSize("statistics_hour");
      return redisUtil.lGet("statistics_hour",size-7,size);
    }

    public List<Object> findDays(){
        long size = redisUtil.lGetListSize("statistics_day");
        return redisUtil.lGet("statistics_day",size-30,size);
    }
}

package com.uestc.statistics.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @author yushansun
 * @title: StatistcisVo
 * @projectName tjlw
 * @description: TODO
 * @date 2020/12/23:18 下午
 */
@Data
@AllArgsConstructor
@ApiModel(value = "DDos统计接口返回vo")
public class StatistcisVo {
    private Map<String,Long> byIPs;
    private Long allCount ;
}

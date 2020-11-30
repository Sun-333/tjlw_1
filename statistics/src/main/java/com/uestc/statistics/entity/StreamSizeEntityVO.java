package com.uestc.statistics.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author yushansun
 * @title: StreamSizeEntityVO
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/3012:22 下午
 */
@Data
@ApiModel(value = "DDos攻击检测 请求次数查询")
public class StreamSizeEntityVO {
    //截止时间
    @ApiModelProperty(value = "截止时间", required = true)
    private Date endTime;
    //查询时间窗口 秒
    @ApiModelProperty(value = "查询时间窗口", required = true)
    private Integer window;
    //目的ip地址
    @ApiModelProperty(value = "目的ip地址", required = true)
    private String targetIp;
}

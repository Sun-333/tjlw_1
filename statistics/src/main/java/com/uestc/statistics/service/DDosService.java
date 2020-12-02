package com.uestc.statistics.service;

import com.uestc.statistics.entity.DDosVO;
import com.uestc.statistics.entity.StatistcisVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yushansun
 * @title: DDosService
 * @projectName tjlw
 * @description: ddos相关业务接口
 * @date 2020/11/304:07 下午
 */
public interface DDosService {
    /**
     * 存储DDOS攻击
     * @param dDosVO ddos攻击信息
     * @return
     */
    public boolean save(DDosVO dDosVO);

    /**
     * 多条件and查询ddos攻击
     * @param dDosVO 查询条件
     * @return
     */
    public List<DDosVO> findByAndCondition(DDosVO dDosVO);

    /**
     * 创建DDos攻击相关表格
     * @return
     */
    public boolean creatDDosTable();


    /**
     * 以时间窗口统计DDos 攻击情况
     * @param beginTime 起始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    public StatistcisVo statistics(String beginTime, String endTime);

    /**
     * 以时间窗口统计DDos 攻击数据
     * @param beginTime 起始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    public List<DDosVO>  findDDosInfo(String beginTime,String endTime);

}

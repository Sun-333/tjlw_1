package com.uestc.statistics.service;

import com.uestc.statistics.entity.DDosVO;

import java.util.List;

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
}

package com.uestc.tjlw.service;

import com.uestc.tjlw.domain.P4Info;
import com.uestc.tjlw.domain.Switch;

/**
 * @author yushansun
 * @title: P4Service
 * @projectName tjlw
 * @description: p4交换机的业务处理接口
 * @date 2020/11/44:13 下午
 */
public interface P4Service {
    /**
     * 将p4信息上传到Hbase中
     * @param p4Info 上传信息
     */
    public void add2Hbase(P4Info p4Info);

    /**
     * 创建p4交换机
     */
    public boolean createP4InfoTables();

    /**
     * 以rowKey查找P4数据
     */
    public P4Info findByTimestamp(String time);
}

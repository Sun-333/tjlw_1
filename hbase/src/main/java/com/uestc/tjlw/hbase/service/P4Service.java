package com.uestc.tjlw.hbase.service;





import com.uestc.tjlw.common.pojo.P4Info;

import java.io.IOException;
import java.util.List;

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
    public boolean add2Hbase(P4Info p4Info);

    /**
     * 创建p4交换机
     */
    public boolean createP4InfoTables();

    /**
     * 以rowKey查找P4数据
     */
    public P4Info findByTimestamp(String time);

    /**
     * baseInfo 多列值与设定目标相等多条件查询
     */
    public List<P4Info> findColumnsAndEqualCondition(String[] columns,String[] cmpValues);

    /**
     * baseInfo 多列值与设定目标相等多条件查询
     */
    public List<P4Info> findColumnsOrEqualCondition(String[] columns,String[] cmpValues);
    /**
     * 以最近rowkey为关键字返回最近100条p4数据
     */
    public List<P4Info> returnClosestAHundred() throws IOException;

}

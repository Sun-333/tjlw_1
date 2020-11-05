package com.uestc.tjlw.service.imp;

import com.uestc.tjlw.domain.P4Info;
import com.uestc.tjlw.service.HBaseService;
import com.uestc.tjlw.service.P4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * @author yushansun
 * @title: P4ServiceImpl
 * @projectName tjlw
 * @description: P4业务处理实现类
 * @date 2020/11/44:18 下午
 */
@Service
@Configuration
public class P4ServiceImpl implements P4Service {

    @Autowired
    private HBaseService hBaseService;

    @Value("${P4.tableName}")
    private String  p4TableName="p4Info";    //表格名称




    @Override
    public void add2Hbase(P4Info p4Info) {
        //1.添加baseInfo
        hBaseService.putData(p4TableName,p4Info.getRowKey(),P4Info.getBaseInfoFamilyName(),P4Info.getColumns(),p4Info.getValues());
        //2.添加交换机信息
        p4Info.getSwitchList().forEach(aSwitch -> {
            hBaseService.putData(p4TableName,p4Info.getRowKey(),P4Info.getSwitchesFamilyName(),aSwitch.getColumns(),aSwitch.getValues());
        });
    }

    @Override
    public boolean createP4InfoTables() {
        return hBaseService.creatTable(p4TableName, Arrays.asList(P4Info.getFamilyNames()));
    }

    @Override
    public P4Info findByTimestamp(String timestamp) {
        Map<String,String> info = hBaseService.getRowData(p4TableName,timestamp);
        P4Info p4Info =  P4Info.getBaseInfoInstance(info);
        p4Info.setTimestamp(timestamp);
        return p4Info;
    }
}

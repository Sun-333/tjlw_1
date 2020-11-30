

package com.uestc.tjlw.service.imp;




import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.pojo.Switch;
import com.uestc.tjlw.common.util.GlobalRet;
import com.uestc.tjlw.hbase.HbaseApplication;
import com.uestc.tjlw.hbase.service.HBaseService;
import com.uestc.tjlw.hbase.service.P4Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;


/**
 * @author yushansun
 * @title: P4ServiceImplTest
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/45:09 下午
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HbaseApplication.class)
public class P4ServiceImplTest {
    @Autowired
    private P4Service p4Service;
    @Autowired
    private HBaseService hBaseService;

    @Test
    public void add2Hbase() {
        System.out.println(p4Service.createP4InfoTables());
    }

    @Test
    public void createP4InfoTables() {
        hBaseService.deleteRow("p4Info","28918941");
        P4Info p4Info = new P4Info("28918960005","1024","192.168.50.0","192.168.50.1",
                "80","80","http","28918942");
        List<Switch> switches = new ArrayList<>();
        for (int i=1;i<=4;i++){
            Switch switch_1 = new Switch(i+"","28918941","80","192.168.50"+"."+i,"80","192.168.50"+"."+i+1);
            switches.add(switch_1);
        }
        p4Info.setSwitchList(switches);
        p4Service.add2Hbase(p4Info);
    }

    @Test
    public void getAllP4Info(){
        Map<String, Map<String,String>> result2 = hBaseService.getResultScanner("p4Info");
        System.out.println("+++++++++++遍历查询+++++++++++");
        result2.forEach((k,value) -> {
            System.out.println(k + "---" + value);
        });
    }
    @Test
    public void  findByTimestamp(){
        System.out.println(p4Service.findByTimestamp("28918941").toString());
    }

    @Test
    public void findColumnsAndEqualCondition(){
        System.out.println(p4Service.findColumnsAndEqualCondition(new String[]{"sourcePort","sourceIp"}, new String[]{"80","192.168.50.2"}).toString());
    }

    @Test
    public void findColumnsOrEqualCondition(){
        System.out.println(p4Service.findColumnsOrEqualCondition(new String[]{"sourceIp","targetIp"}, new String[]{"192.168.50.517","192.168.50.4"}).toString());
    }

    @Test
    public void responseDDoSdemand() throws IOException {
        Map<String, Map<String,String>> result = hBaseService.getResultScanner("p4Info");
        System.out.println(p4Service.responseDDoSdemand("192.168.50." +
                "10.0.2.2", Long.toString(1605848286 ), Long.toString(289189506666l)));
        result.forEach((k,value) -> {
            System.out.println(k + "---" + value);
        });
    }
}

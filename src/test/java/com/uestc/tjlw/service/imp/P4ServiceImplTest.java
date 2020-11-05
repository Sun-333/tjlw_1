package com.uestc.tjlw.service.imp;

import com.uestc.tjlw.domain.P4Info;
import com.uestc.tjlw.domain.Switch;
import com.uestc.tjlw.service.HBaseService;
import com.uestc.tjlw.service.P4Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author yushansun
 * @title: P4ServiceImplTest
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/45:09 下午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class P4ServiceImplTest {
    @Autowired
    private  P4Service p4Service;
    @Autowired
    private HBaseService hBaseService;


    public void add2Hbase() {
        System.out.println(p4Service.createP4InfoTables());
    }

    @Test
    public void createP4InfoTables() {
        hBaseService.deleteRow("p4Info","28918941");
        P4Info p4Info = new P4Info("28918941","192.168.50.0","192.168.50.4",
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
}
package com.uestc.tjlw.service;

import com.uestc.tjlw.hbase.service.HBaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * @author yushansun
 * @title: HBaseServiceTest
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/42:58 下午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class HBaseServiceTest {
    private HBaseService hBaseService;
    @Test
    public void deleteRow()
    {
        hBaseService.deleteRow("p4Info","2891896666");
    }

}
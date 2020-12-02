package com.uestc.statistics.service.imp;

import com.uestc.statistics.StatisticsApplication;
import com.uestc.statistics.entity.DDosVO;
import com.uestc.statistics.service.DDosService;
import com.uestc.statistics.service.HBaseService;
import com.uestc.statistics.service.P4Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author yushansun
 * @title: DDosServiceImplTest
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/304:26 下午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StatisticsApplication.class)
public class DDosServiceImplTest {
    @Autowired
    private HBaseService hBaseService;
    @Autowired
    private DDosService dDosService;

    @Test
    public void save() {

    }

    @Test
    public void findByAndCondition() {

    }

    @Test
    public void creatDDosTable() {
        dDosService.creatDDosTable();
    }
}
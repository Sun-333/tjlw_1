package com.uestc.statistics.service.imp;

import com.uestc.statistics.StatisticsApplication;
import com.uestc.statistics.entity.DDosVO;
import com.uestc.statistics.service.DDosService;
import com.uestc.statistics.service.HBaseService;
import com.uestc.statistics.service.P4Service;
import com.uestc.statistics.util.RedisUtil;
import com.uestc.tjlw.common.pojo.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void save() {

    }

    @Test
    public void findByAndCondition() {
        List<Statistics> statisticsList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020,11,1,0,0,0);
        for (int i=0;i<6;i++){
            Statistics statistics = new Statistics();
            statistics.setTime(calendar.getTimeInMillis()+"");
            statistics.setSize(100);
            statisticsList.add(statistics);
            calendar.add(Calendar.MONTH,-1);
        }
        redisUtil.lSet("ddos_month",statisticsList);
    }

    @Test
    public void creatDDosTable() {
        dDosService.creatDDosTable();
    }
}
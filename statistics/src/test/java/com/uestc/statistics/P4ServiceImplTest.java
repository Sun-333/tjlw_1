

package com.uestc.statistics;




import com.uestc.statistics.service.HBaseService;
import com.uestc.statistics.service.P4Service;
import com.uestc.statistics.util.RedisUtil;
import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.pojo.Statistics;
import com.uestc.tjlw.common.pojo.Switch;
import com.uestc.tjlw.common.util.GlobalRet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


/**
 * @author yushansun
 * @title: P4ServiceImplTest
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/45:09 下午
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StatisticsApplication.class)
public class P4ServiceImplTest {
    @Autowired
    private P4Service p4Service;
    @Autowired
    private HBaseService hBaseService;
    @Resource
    private RedisUtil redisUtil;


    public void add2Hbase() {
        System.out.println(p4Service.createP4InfoTables());
    }

    @Test
    public void createP4InfoTables() {
        P4Info p4Info = new P4Info("28918941","1024","192.168.50.0","192.168.50.4",
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
    public void between() throws IOException {
        System.out.println(p4Service.findInfosBetween("160562", "160564"));
    }
    @Test
    public void test(){
        Calendar rightNow = Calendar.getInstance();
        Calendar beforeOneHour = Calendar.getInstance();
        beforeOneHour.add(Calendar.HOUR,-1);
        Statistics statistics = new Statistics(rightNow.getTimeInMillis()+"",100);
        redisUtil.lSet("statistics_hour", statistics);
    }
    @Test
    public void delete() throws IOException {
        Calendar rightNow = Calendar.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        rightNow.add(Calendar.DATE,-7);
        stringBuilder.append(rightNow.getTimeInMillis());
        stringBuilder.append("_");
        stringBuilder.append("FFFFFFFF");
        p4Service.cleanInfoBeforeTime(stringBuilder.toString());
    }
    @Test
    public void test_n(){
        long starTime=System.currentTimeMillis();
        Calendar calendar_now = Calendar.getInstance();
        Calendar calendar =Calendar.getInstance();
        calendar.add(Calendar.SECOND,-600000);
        System.out.println(p4Service.findSourcesStreamSizeByTargetIp(
                calendar.getTimeInMillis() + "",
                calendar_now.getTimeInMillis() + "",
                "10.0.2.2"
        ).toString());
        long endTime=System.currentTimeMillis();
        System.out.println((endTime - starTime));
    }
}

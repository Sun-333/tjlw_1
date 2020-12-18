
package com.uestc.statistics.task;

import com.uestc.statistics.service.DDosService;
import com.uestc.statistics.service.P4Service;
import com.uestc.statistics.util.RedisUtil;
import com.uestc.tjlw.common.pojo.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Calendar;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@EnableAsync        //3.开启异步执行任务
public class StatisticsScheduleTask {
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private P4Service p4Service;
    @Autowired
    private DDosService dDosService;

    /**
     * 每小时统计流量大小
     */

    @Scheduled(cron = "0 0 0-23 * * ?")
    private void statisticsHour() throws IOException {
        Calendar rightNow = Calendar.getInstance();
        Calendar beforeOneHour = Calendar.getInstance();
        beforeOneHour.add(Calendar.HOUR,-1);
        long res = p4Service.findInfosBetween(beforeOneHour.getTimeInMillis()+"",rightNow.getTimeInMillis()+"");
        Statistics statistics = new Statistics(rightNow.getTimeInMillis()+"",res);
        redisUtil.lSet("statistics_hour", statistics);
    }

    /**
     * 每天统计流量大小
     */

    @Scheduled(cron = "0 0 12 * * ?")
    private void statisticsDay() throws IOException {
        Calendar rightNow = Calendar.getInstance();
        Calendar beforeOneHour = Calendar.getInstance();
        beforeOneHour.add(Calendar.DATE,-1);
        long res = p4Service.findInfosBetween(beforeOneHour.getTimeInMillis()+"",rightNow.getTimeInMillis()+"");
        Statistics statistics = new Statistics(rightNow.getTimeInMillis()+"",res);
        redisUtil.lSet("statistics_day", statistics);
    }

    /**
     * 每天统计流量大小
     */

    @Scheduled(cron = "0 0 0 1 * ?")
    private void ddosStatistics()  {
        Calendar rightNow = Calendar.getInstance();
        Calendar beforeOneMonth = Calendar.getInstance();
        beforeOneMonth.add(Calendar.DATE,-30);
        int count = dDosService.findDDosInfo(beforeOneMonth.getTimeInMillis()+"",rightNow.getTimeInMillis()+"").size();
        Statistics statistics = new Statistics();
        statistics.setTime(rightNow.getTimeInMillis()+"");
        statistics.setSize(count);
        redisUtil.lSet("ddos_month", statistics);
    }

    /**
     * 数据只保存7天
     * @throws IOException
     */
    @Scheduled(cron = "0 0 12 ? * MON")
    private void cleanByDay() throws IOException {
        Calendar rightNow = Calendar.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        rightNow.add(Calendar.DATE,-7);
        stringBuilder.append(rightNow.getTimeInMillis());
        stringBuilder.append("_");
        stringBuilder.append("FFFFFFFF");
        p4Service.cleanInfoBeforeTime(stringBuilder.toString());
    }
}

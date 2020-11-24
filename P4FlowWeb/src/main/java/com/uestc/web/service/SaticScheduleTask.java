package com.uestc.web.service;

import com.uestc.web.handler.AddInfoHandler;
import com.uestc.web.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@EnableAsync        //3.开启异步执行任务
public class SaticScheduleTask {
    @Autowired
    private AddInfoHandler addInfoHandler;
    /**
     * 定时处理统计流量大小
     */
    @Scheduled(cron = "0/2 * * * * ?")
    private void broadcastTask() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        //向前端推送实时流大小
        addInfoHandler.sendMsg();
    }
}
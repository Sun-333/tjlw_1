package com.uestc.web.handler;

import com.uestc.tjlw.common.cocurrent.CallbackTask;
import com.uestc.tjlw.common.cocurrent.CallbackTaskScheduler;
import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.protocol.JsonMsg;
import com.uestc.tjlw.common.util.JsonUtil;
import com.uestc.web.server.WebSocketServer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongUnaryOperator;

/**
 * @author yushansun
 * @title: AddInfoHandler
 * @projectName tjlw
 * @description: 接收p4数据存储到Hbase中
 * @date 2020/11/103:50 下午
 */

@Slf4j
@Service("AddInfoHandler")
public class AddInfoHandler {
    @Autowired
    private WebSocketServer webSocketServer;
    private AtomicLong atomicLong = new AtomicLong();       //统计包大小
    private AtomicLong time = new AtomicLong();             //统计时间

    public void handle( String msg)  {
        JsonMsg jsonMsg = JsonUtil.jsonToPojo(msg,JsonMsg.class);
        P4Info p4Info = JsonUtil.jsonToPojo(jsonMsg.getContent(), P4Info.class);

        /**
         * 将信息上传到Hbase中
         */
        CallbackTaskScheduler.add(new CallbackTask<Boolean>() {
            @Override
            public Boolean execute() throws Exception {
                //加入当前包大小
                atomicLong.addAndGet(Integer.valueOf(p4Info.getBagsize()));
                //设置当前时间
                int index = p4Info.getTimestamp().indexOf("_");
                String str = p4Info.getTimestamp().substring(0,index);
                time.compareAndSet(0,Long.valueOf(str));
                return true;
            }

            @Override
            public void onBack(Boolean httpResponse) {
                log.info("成功加入");
            }

            @Override
            public void onException(Throwable t) {
                log.info("消息编号ID："+jsonMsg.getId()+"：流量统计失败:"+t.getMessage());
            }
        });
    }

    /**
     * 向前端推送实时网络流量大小
     */
    public void sendMsg(){
        LongUnaryOperator operator = (v)->0;
        long nowtime =time.getAndUpdate(operator);
        long size = atomicLong.getAndUpdate(operator);

        WebFlow webFlow = new WebFlow(size, nowtime);
        //广播消息
        webSocketServer.broadcast(webFlow);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class WebFlow{
        long size;  //流大小
        long time;  //时间戳
    }
}

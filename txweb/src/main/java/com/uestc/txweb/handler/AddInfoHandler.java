package com.uestc.txweb.handler;

import com.uestc.tjlw.common.cocurrent.CallbackTask;
import com.uestc.tjlw.common.cocurrent.CallbackTaskScheduler;
import com.uestc.tjlw.common.protocol.JsonMsg;
import com.uestc.txweb.server.WebSocketServer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void handle( String msg)  {

        /**
         * 将信息推送到web前台中中
         */
        CallbackTaskScheduler.add(new CallbackTask<Boolean>() {
            @Override
            public Boolean execute() throws Exception {
                webSocketServer.broadcast(msg);
                return true;
            }

            @Override
            public void onBack(Boolean httpResponse) {
                // log.info("成功推送到前台");
            }

            @Override
            public void onException(Throwable t) {
                log.info("tx消息推送异常");
            }
        });
    }
}

package com.uestc.tjlw.hbase.handler;

import com.uestc.tjlw.common.cocurrent.CallbackTask;
import com.uestc.tjlw.common.cocurrent.CallbackTaskScheduler;
import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.protocol.JsonMsg;
import com.uestc.tjlw.common.util.JsonUtil;
import com.uestc.tjlw.common.util.SHAUtil;
import com.uestc.tjlw.hbase.service.P4Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yushansun
 * @title: AddInfoHandler
 * @projectName tjlw
 * @description: 接收p4数据存储到Hbase中
 * @date 2020/11/103:50 下午
 */

@Slf4j
@Service("AddInfoHandler")
public class AddInfoHandler{
    @Autowired
    private P4Service p4Service;

    public void handle( String msg)  {
        JsonMsg jsonMsg = JsonUtil.jsonToPojo(msg,JsonMsg.class);
        P4Info p4Info = JsonUtil.jsonToPojo(jsonMsg.getContent(), P4Info.class);
        /**
         * 将信息上传到Hbase中
         */
        CallbackTaskScheduler.add(new CallbackTask<Boolean>() {
            @Override
            public Boolean execute() throws Exception {
                p4Service.add2Hbase(p4Info);
                return true;
            }

            @Override
            public void onBack(Boolean httpResponse) {
                log.info("消息编号ID："+jsonMsg.getId()+"：hbase存储成功");
            }

            @Override
            public void onException(Throwable t) {
                log.info("消息编号ID："+jsonMsg.getId()+"：hbase存储失败:"+t.getMessage());
            }
        });
    }
}

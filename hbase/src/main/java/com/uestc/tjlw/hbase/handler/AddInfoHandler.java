package com.uestc.tjlw.hbase.handler;

import com.google.common.base.Objects;
import com.uestc.tjlw.common.cocurrent.CallbackTask;
import com.uestc.tjlw.common.cocurrent.CallbackTaskScheduler;
import com.uestc.tjlw.common.cocurrent.FutureTaskScheduler;
import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.protocol.JsonMsg;
import com.uestc.tjlw.common.util.JsonUtil;
import com.uestc.tjlw.common.util.SHAUtil;
import com.uestc.tjlw.hbase.domain.HttpResponse;
import com.uestc.tjlw.hbase.service.P4Service;
import com.uestc.tjlw.hbase.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hbase.thirdparty.io.netty.channel.ChannelHandler;
import org.apache.hbase.thirdparty.io.netty.channel.ChannelHandlerContext;
import org.apache.hbase.thirdparty.io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yushansun
 * @title: AddInfoHandler
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/103:50 下午
 */

@Slf4j
@Service("AddInfoHandler")
@ChannelHandler.Sharable
public class AddInfoHandler  extends ChannelInboundHandlerAdapter {
    @Autowired
    private P4Service p4Service;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg==null||!(msg instanceof JsonMsg)){
            super.channelRead(ctx, msg);
            return;
        }
        JsonMsg jsonMsg = (JsonMsg) msg;
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
                log.info("消息编号ID："+jsonMsg.getId()+"：hbase存储失败");
            }
        });

        /**
         * 区块链上存证
         */
        CallbackTaskScheduler.add(new CallbackTask<Boolean>() {
            @Override
            public Boolean execute() throws Exception {
                String hash = SHAUtil.getMD5String(p4Info.toString());
                Map<String,Object> map = new HashMap<>();
                map.put("key",p4Info.getTimestamp());
                map.put("value",hash);
                HttpResponse httpResponse =  HttpUtil.request("http://localhost:8085/saveInfo/save",
                        map,HttpResponse.class);
                if (httpResponse.getCode()!=0) throw new RuntimeException("信息上链错误");
                return true;
            }

            @Override
            public void onBack(Boolean o) {
                log.info("消息编号ID："+jsonMsg.getId()+"：上链成功");
            }

            @Override
            public void onException(Throwable t) {
                log.info("消息编号ID："+jsonMsg.getId()+"：上链失败");
            }
        });
    }
}

package com.uestc.tjlw.hbase.handler;

import com.uestc.tjlw.common.cocurrent.FutureTaskScheduler;
import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.protocol.JsonMsg;
import com.uestc.tjlw.common.util.JsonUtil;
import com.uestc.tjlw.hbase.service.P4Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.hbase.thirdparty.io.netty.channel.ChannelHandler;
import org.apache.hbase.thirdparty.io.netty.channel.ChannelHandlerContext;
import org.apache.hbase.thirdparty.io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        FutureTaskScheduler.add(() ->
        {
            JsonMsg jsonMsg = (JsonMsg) msg;
            P4Info p4Info = JsonUtil.jsonToPojo(jsonMsg.getContent(), P4Info.class);
            p4Service.add2Hbase(p4Info);

        });
    }
}

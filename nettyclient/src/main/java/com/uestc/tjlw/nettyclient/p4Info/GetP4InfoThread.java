package com.uestc.tjlw.nettyclient.p4Info;

import com.uestc.tjlw.common.cocurrent.CallbackTask;
import com.uestc.tjlw.common.cocurrent.CallbackTaskScheduler;
import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.protocol.JsonMsg;
import com.uestc.tjlw.common.util.JsonUtil;
import com.uestc.tjlw.nettyclient.processer.EncodeP4Info;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hbase.thirdparty.io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author yushansun
 * @title: GetP4InfoThread
 * @projectName tjlw
 * @description: p4数据抓包解发送线程
 * @date 2020/11/121:14 下午
 */
@Slf4j
public class GetP4InfoThread extends Thread{
    private EncodeP4Info encodeP4Info = new EncodeP4Info();
    private Thread thread;
    private String name;
    private Channel channel;    //netty客户端通道

    public GetP4InfoThread(String name, Channel channel) {
        this.name = name;
        this.channel=channel;
    }

    /**
     * 执行抓包程序获取并解析数据并将解析数据发送给netty服务器
     */
    @SneakyThrows
    @Override
    public void run(){
        while (true){
            //模拟运行抓包程序获取一行数据(需要补充)

            //异步数据解析与发送
            CallbackTaskScheduler.add(new CallbackTask<P4Info>() {
                /**
                 * 执行异步数据包解析服务
                 * @return 解析的p4数据
                 * @throws Exception
                 */
                @Override
                public P4Info execute() throws Exception {
                    P4Info p4Info =  encodeP4Info.encode();
                    return p4Info;
                }

                /**
                 * 若解析成功将解析数据转化为JsonMsg对象并 以json格式发送给服务器
                 * @param p4Info p4数据
                 */
                @Override
                public void onBack(P4Info p4Info) {
                    JsonMsg jsonMsg = new JsonMsg();
                    jsonMsg.setId(1);
                    jsonMsg.setContent(JsonUtil.pojoToJson(p4Info));
                    channel.writeAndFlush(jsonMsg.convertToJson());
                }

                /**
                 * 若解析过程中报错采用日志记录报错信息
                 * @param t 异常
                 */
                @Override
                public void onException(Throwable t) {
                    log.error("解析p4数据错误："+t.getMessage());
                }
            });

            //正常开启后可删除
            Thread.sleep(1000);
        }
    }

    public void start()
    {
        log.info("启动p4数据抓包服务");
        if (thread==null){
            thread = new Thread(this,name);
            thread.run();
        }
    }
}

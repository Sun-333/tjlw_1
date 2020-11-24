package com.uestc.tjlw.nettyclient.producer;

import com.uestc.tjlw.common.cocurrent.CallbackTask;
import com.uestc.tjlw.common.cocurrent.CallbackTaskScheduler;
import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.protocol.JsonMsg;
import com.uestc.tjlw.common.util.JsonUtil;
import com.uestc.tjlw.nettyclient.processer.EncodeP4Info;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.jpcap.capture.PacketCapture;
import net.sourceforge.jpcap.capture.PacketListener;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


/**
 * @author yushansun
 * @title: GetP4InfoThread
 * @projectName tjlw
 * @description: p4数据抓包解发送线程
 * @date 2020/11/121:14 下午
 */
@Slf4j
@Service("GetP4InfoThread")
public class GetP4InfoThread extends Thread{
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    private EncodeP4Info encodeP4Info = new EncodeP4Info();
    private Thread thread;
    private volatile long count=0;

    /**
     * 执行抓包程序获取并解析数据并将解析数据发送给kafka
     */
    @SneakyThrows
    @Override
    public void run(){
        while (true){
            //模拟运行抓包程序获取一行数据(需要补充)
            PacketListener listener = (Packet packet)->{
                if(packet instanceof TCPPacket){

                } else if (packet instanceof UDPPacket){
                    UDPPacket udp = (UDPPacket)packet;
                    //异步数据解析与发送
                    CallbackTaskScheduler.add(new CallbackTask<P4Info>() {
                        /**
                         * 执行异步数据包解析服务
                         * @return 解析的p4数据
                         * @throws Exception
                         */
                        @Override
                        public P4Info execute() throws Exception {
                            P4Info p4Info =  encodeP4Info.encode(udp);
                            return p4Info;
                        }

                        /**
                         * 若解析成功将解析数据转化为JsonMsg对象并 以json格式发送kafka
                         * @param p4Info p4数据
                         */
                        @Override
                        public void onBack(P4Info p4Info) {
                            JsonMsg jsonMsg = new JsonMsg();
                            jsonMsg.setId(count++);
                            jsonMsg.setContent(JsonUtil.pojoToJson(p4Info));
                            //返回ListenableFuture 后期如何改进代码
                            kafkaTemplate.send("p4Info",jsonMsg.convertToJson());
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
                }
            };



            PacketCapture jp = new PacketCapture();
            String avaidev = jp.findDevice();
            jp.open("s2-eth1", true);
            System.out.println("open device success!");

            jp.setFilter("", true);

            jp.addPacketListener(listener);

            jp.capture(-1);
        }
    }

    public void start() {
        log.info("启动p4数据抓包服务");
        if (thread==null){
            thread = new Thread(this);
            thread.run();
        }
    }
}

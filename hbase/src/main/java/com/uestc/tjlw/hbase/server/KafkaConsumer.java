package com.uestc.tjlw.hbase.server;

import com.uestc.tjlw.hbase.handler.AddInfoHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author yushansun
 * @title: KafkaConsumer
 * @projectName tjlw
 * @description: kafka topic：p4Info 的消费者
 * @date 2020/11/178:15 下午
 */
@Component
public class KafkaConsumer {
    @Autowired
    private AddInfoHandler  addInfoHandler;
    /**
     * 定义此消费者接收topics = "p4Info"的消息
     * @param record 变量代表消息本身，可以通过ConsumerRecord<?,?>类型的record变量来打印接收的消息的各种信息
     */
    @KafkaListener(topics = "p4Info")
    public void listen (ConsumerRecord<?, ?> record){
        System.out.printf("topic is %s, offset is %d, value is %s \n", record.topic(), record.offset(), record.value());
        addInfoHandler.handle((String) record.value());
    }
}

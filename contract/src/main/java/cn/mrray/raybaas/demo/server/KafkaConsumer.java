package cn.mrray.raybaas.demo.server;


import cn.mrray.raybaas.demo.handler.AddInfoHandler;
import cn.mrray.raybaas.demo.service.IntegrityContractService;
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
    private AddInfoHandler addInfoHandler;

    /**
     * 定义此消费者接收topics = "p4Info"的消息
     * @param record 变量代表消息本身，可以通过ConsumerRecord<?,?>类型的record变量来打印接收的消息的各种信息
     */
    @KafkaListener(topics = "p4Info")
    public void listen (ConsumerRecord<?, ?> record){
        System.out.println(record.value().toString());
        addInfoHandler.handle((String) record.value());
    }
}

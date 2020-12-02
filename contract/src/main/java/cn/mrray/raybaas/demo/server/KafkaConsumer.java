package cn.mrray.raybaas.demo.server;


import cn.mrray.raybaas.demo.entity.Operator;
import cn.mrray.raybaas.demo.service.IntegrityContractService;
import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.protocol.JsonMsg;
import com.uestc.tjlw.common.util.JsonUtil;
import com.uestc.tjlw.common.util.SHAUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author yushansun
 * @title: KafkaConsumer
 * @projectName tjlw
 * @description: kafka topic：p4Info 的消费者
 * @date 2020/11/178:15 下午
 */
@Component
public class KafkaConsumer {
    private int size=10;
    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    @Autowired
    private IntegrityContractService integrityContractService;
    private BlockingQueue<P4Info> queue = new LinkedBlockingQueue<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2+1);

    /**
     * 定义此消费者接收topics = "p4Info"的消息
     * @param record 变量代表消息本身，可以通过ConsumerRecord<?,?>类型的record变量来打印接收的消息的各种信息
     */
    @KafkaListener(topics = "p4Info",id = "p4Upchain")
    public void listen (ConsumerRecord<?, ?> record){
        JsonMsg jsonMsg = JsonUtil.jsonToPojo((String) record.value(),JsonMsg.class);
        P4Info p4Info = JsonUtil.jsonToPojo(jsonMsg.getContent(), P4Info.class);
        if (queue.size()>=100){
            upChain();
        }
        queue.add(p4Info);
    }

    private void upChain(){
        //清理数据
        List<P4Info> p4Infos = new ArrayList<>(size);
        for(int i=0;i<size;i++){
            p4Infos.add(queue.poll());
        }
        System.out.println(p4Infos.toString());


        //数据上链
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                StringBuffer keyBuffer = new StringBuffer();
                Map<String,String> map = new HashMap<>();
                keyBuffer.append(p4Infos.get(0).getRowKey()).append("-").append(p4Infos.get(size-1).getRowKey());
                String key = keyBuffer.toString();
                String hash = SHAUtil.sha256BasedHutool(p4Infos.toString());
                Operator operator = new Operator(Operator.upInfo,hash,key);
                map.put("key",key);
                map.put("data",JsonUtil.pojoToJson(operator));
                System.out.println(integrityContractService.save(map).getMessage());
            }
        });
    }
}

package cn.mrray.raybaas.demo.server;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


/**
 * @author yushansun
 * @title: TxHashProducer
 * @projectName tjlw
 * @description: 将上链交易发送给kafka
 * @date 2020/11/121:14 下午
 */
@Slf4j
@Service("GetP4InfoThread")
public class TxHashProducer {
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
    /**
     * 执行抓包程序获取并解析数据并将解析数据发送给kafka
     */
    public void sendTxHash(String txHash){
          kafkaTemplate.send("txHash",txHash);
    }
}

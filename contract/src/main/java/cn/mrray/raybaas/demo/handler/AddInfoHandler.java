package cn.mrray.raybaas.demo.handler;

import cn.mrray.raybaas.common.data.vo.CommonResponse;
import cn.mrray.raybaas.demo.service.IntegrityContractService;
import com.uestc.tjlw.common.cocurrent.CallbackTask;
import com.uestc.tjlw.common.cocurrent.CallbackTaskScheduler;
import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.protocol.JsonMsg;
import com.uestc.tjlw.common.util.JsonUtil;
import com.uestc.tjlw.common.util.SHAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author yushansun
 * @title: AddInfoHandler
 * @projectName tjlw
 * @description: p4数据上链
 * @date 2020/11/103:50 下午
 */

@Slf4j
@Service("AddInfoHandler")
public class AddInfoHandler {
    @Autowired
    private IntegrityContractService integrityContractService;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2+1);

    public void handle(String msg)  {
        JsonMsg jsonMsg = JsonUtil.jsonToPojo(msg,JsonMsg.class);
        P4Info p4Info = JsonUtil.jsonToPojo(jsonMsg.getContent(), P4Info.class);
        /**
         * 区块链上存证
         */
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String hash = SHAUtil.getMD5String(p4Info.toString());
                Map<String,String> map = new HashMap<>();
                map.put("key",p4Info.getTimestamp());
                map.put("data",hash);
                integrityContractService.save(map);
            }
        });
        /*CallbackTaskScheduler.add(new CallbackTask<Boolean>() {
            @Override
            public Boolean execute() throws Exception {
                *//*String hash = SHAUtil.getMD5String(p4Info.toString());
                Map<String,String> map = new HashMap<>();
                map.put("key",p4Info.getTimestamp());
                map.put("value",hash);
                CommonResponse commonResponse = integrityContractService.save(map);

                if (!commonResponse.isSuccess()) throw new RuntimeException(commonResponse.getMessage());*//*
                return true;
            }

            @Override
            public void onBack(Boolean o) {
                log.info("消息编号ID："+jsonMsg.getId()+"：上链成功"+"上链hash:");
            }

            @Override
            public void onException(Throwable t) {
                log.info("消息编号ID："+jsonMsg.getId()+"：上链失败:"+t.getMessage());
            }
        });*/
    }
}

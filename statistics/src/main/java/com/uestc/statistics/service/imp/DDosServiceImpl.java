package com.uestc.statistics.service.imp;

import com.google.common.util.concurrent.AtomicLongMap;
import com.uestc.statistics.entity.DDosVO;
import com.uestc.statistics.entity.StatistcisVo;
import com.uestc.statistics.server.WebSocketServer;
import com.uestc.statistics.service.DDosService;
import com.uestc.statistics.service.HBaseService;
import com.uestc.statistics.service.P4Service;
import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.util.JsonUtil;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yushansun
 * @title: DDosServiceImpl
 * @projectName tjlw
 * @description: DDos 业务实现
 * @date 2020/11/304:11 下午
 */
@Service
public class DDosServiceImpl implements DDosService {
    @Autowired
    private HBaseService hBaseService;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private P4Service p4Service;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static String  p4TableName="p4Info";    //表格名称
    @Override
    public boolean save(DDosVO dDosVO) {
        Calendar calendar_before = Calendar.getInstance();
        calendar_before.setTimeInMillis(Long.valueOf(dDosVO.getTime()));
        calendar_before.add(Calendar.SECOND,-20);

        Filter filter = hBaseService.singleColumnValueFilter(P4Info.getBaseInfoFamilyName(),"targetIp", CompareOperator.EQUAL,dDosVO.getTargetIp());
        Map<String, Map<String,String>> map = hBaseService.getResultScanner(p4TableName,calendar_before.getTimeInMillis()+"",dDosVO.getRowKey(),filter);
        StringBuffer stringBuffer = new StringBuffer();
        Object[] kes =  map.keySet().toArray();
        Map<String,String> info = map.get(kes[0]);
        P4Info p4Info = P4Info.getBaseInfoInstance(info);
        p4Info.getSwitchList().forEach(aSwitch -> {
            stringBuffer.append(aSwitch.getSwitchId());
            stringBuffer.append("-");
        });
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        dDosVO.setPath(stringBuffer.toString());
        webSocketServer.broadcast(JsonUtil.pojoToJson(dDosVO));
        hBaseService.putData(DDosVO.getTableName(),dDosVO.getRowKey(),DDosVO.getFamilyName()[0],DDosVO.getColumns(),dDosVO.getValues());
        return true;
    }

    @Override
    public List<DDosVO> findByAndCondition(DDosVO dDosVO) {
        return null;
    }

    @Override
    public boolean creatDDosTable() {
        hBaseService.creatTable(DDosVO.getTableName(), Arrays.asList(DDosVO.getFamilyName()));
        return true;
    }

    @Override
    public StatistcisVo statistics(String beginTime, String endTime) {
        //1606801880709_10.0.2.2
        AtomicLongMap<String> res = AtomicLongMap.create();
        AtomicLong atomicLong = new AtomicLong();
        Map<String,Map<String,String>> ans = hBaseService.getResultScanner(
                DDosVO.getTableName(),
                        beginTime,
                        endTime);
        CountDownLatch countDownLatch = new CountDownLatch(ans.size());
        ans.forEach((k,v)->{
            String[] ipsStr = v.get("ips").split(",");
            for(String ip:ipsStr)
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        res.incrementAndGet(ip);
                        atomicLong.incrementAndGet();
                        countDownLatch.countDown();
                    }
                });
        });
        return new StatistcisVo(res.asMap(),atomicLong.longValue());
    }

    @Override
    public  List<DDosVO> findDDosInfo(String beginTime, String endTime) {
        List<DDosVO> res = new LinkedList<>();
        Map<String,Map<String,String>> ans = hBaseService.getResultScanner(
                DDosVO.getTableName(),
                beginTime,
                endTime);
        ans.forEach((k,v)->{
            res.add(DDosVO.getBaseInfoInstance(v));
        });
        return res;
    }
}

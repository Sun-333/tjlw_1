package com.uestc.statistics.config;

import com.uestc.statistics.service.HBaseService;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HBase相关配置
 *
 * @author zifangsky
 * @date 2018/7/12
 * @since 1.0.0
 */
@Configuration
public class HBaseConfig {
    @Value("${HBase.nodes}")
    private String nodes;
 
    @Value("${HBase.maxsize}")
    private String maxsize;
 
    @Bean
    public HBaseService getHbaseService(){
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum",nodes );
        conf.set("hbase.client.keyvalue.maxsize",maxsize);
 
        return new HBaseService(conf);
    }
}
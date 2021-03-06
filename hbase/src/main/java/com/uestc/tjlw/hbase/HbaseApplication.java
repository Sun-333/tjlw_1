package com.uestc.tjlw.hbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class HbaseApplication {

    public static void main(String[] args) {
       ApplicationContext applicationContext =  SpringApplication.run(HbaseApplication.class, args);
    }
}

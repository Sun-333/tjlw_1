package com.uestc.tjlw.nettyclient;

import com.uestc.tjlw.nettyclient.producer.GetP4InfoThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class NettyclientApplication {

    public static void main(String[] args) {
       ApplicationContext applicationContext =  SpringApplication.run(NettyclientApplication.class, args);
       applicationContext.getBean(GetP4InfoThread.class).start();
    }
}

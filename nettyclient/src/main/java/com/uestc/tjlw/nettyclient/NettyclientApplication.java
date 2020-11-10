package com.uestc.tjlw.nettyclient;

import com.uestc.tjlw.nettyclient.server.JsonSendClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class NettyclientApplication {

    public static void main(String[] args) {
       ApplicationContext applicationContext =  SpringApplication.run(NettyclientApplication.class, args);
       applicationContext.getBean(JsonSendClient.class).runClient();
    }

}

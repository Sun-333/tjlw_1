package com.uestc.tjlw.jpcap;

import com.uestc.tjlw.jpcap.process.App;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpcapApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpcapApplication.class, args);
        App.run();
    }
}

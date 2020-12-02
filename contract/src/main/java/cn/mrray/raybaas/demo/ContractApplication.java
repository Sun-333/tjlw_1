package cn.mrray.raybaas.demo;

import cn.mrray.raybaas.demo.server.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ContractApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ContractApplication.class, args);
    }
}

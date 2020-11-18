package cn.mrray.raybaas.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * BaaS配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "baas")
public class BaasConfig {
    private Sdk sdk;
    private String consensus;
    private String channel;
    private String order;
    private String peer;

    @Data
    static class Sdk {
        private String privateKeyPath;
        private String sslCertFilePath;
        private String sslPrivateKeyPath;
        private String appId;
    }

}


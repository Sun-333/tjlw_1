package cn.mrray.raybaas.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Contract配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "contract")
public class ContractConfig {
    /**
     * 样例合约
     */
    private Contract demo;

    public String getIdentity() {
        return demo.getIdentity();
    }

    public String getVersion() {
        return demo.getVersion();
    }

    @Data
    public static class Contract {
        // 合约标识
        private String identity;
        // 合约版本
        private String version;
        // 合约路径（相当路径）
        private String path;
    }
}


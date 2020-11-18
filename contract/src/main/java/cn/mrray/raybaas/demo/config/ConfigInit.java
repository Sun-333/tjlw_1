package cn.mrray.raybaas.demo.config;

import cn.mrray.raybaas.sdk.client.SdkClient;
import cn.mrray.raybaas.sdk.client.support.SdkClientImpl;
import cn.mrray.raybaas.sdk.grpc.start.client.Channel;
import cn.mrray.raybaas.sdk.model.Peer;
import cn.mrray.raybaas.sdk.model.SdkOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 初始化配置类
 */
@Component
public class ConfigInit {
    @Resource
    private BaasConfig baasConfig;
    @Resource
    private ContractConfig contractConfig;

    @PostConstruct
    public boolean checkConfig() {
        // check config of contract
        ContractConfig.Contract demo = contractConfig.getDemo();
        String identity = demo.getIdentity();
        String version = demo.getVersion();
        if (StringUtils.isEmpty(identity) || StringUtils.isEmpty(version)) {
            throw new RuntimeException("合约标识或合约版本未配置");
        }
        // check config of sdk
        BaasConfig.Sdk sdk = baasConfig.getSdk();
        String pkPath = sdk.getPrivateKeyPath();
        if (StringUtils.isEmpty(pkPath)) {
            throw new RuntimeException("私钥文件路径配置有误");
        }
        File pk = new File(pkPath);
        if (!pk.exists() || !pk.isFile()) {
            throw new RuntimeException("私钥文件不存在");
        }
        return true;
    }


    @Bean
    public SdkClient sdkClient() {
        return new SdkClientImpl(channel());
    }

    @Bean
    public Channel channel() {
        // sdk
        BaasConfig.Sdk sdk = baasConfig.getSdk();
        // peers
        List<Peer> peers = new ArrayList<>();
        String allPeers = baasConfig.getPeer();
        String[] ips = allPeers.split(";");
        for (String ip : ips) {
            String[] hostAndPort = ip.split(":");
            peers.add(new cn.mrray.raybaas.sdk.model.Peer(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
        }
        // orders
        List<SdkOrder> orders = new ArrayList<>();
        String allOrders = baasConfig.getOrder();
        String[] orderIps = allOrders.split(";");
        for (String ip : orderIps) {
            String[] hostAndPort = ip.split(":");
            orders.add(new SdkOrder(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
        }
        String signAlgorithm = "SM2";
        return new Channel(peers, orders, baasConfig.getChannel(), baasConfig.getConsensus(),
                sdk.getPrivateKeyPath(), sdk.getSslCertFilePath(), sdk.getSslPrivateKeyPath(),
                sdk.getAppId(), signAlgorithm, true);
    }
}

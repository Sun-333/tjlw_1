package cn.mrray.raybaas.demo.service;

import cn.mrray.raybaas.common.data.enums.ContractLanguageTypeEnum;
import cn.mrray.raybaas.common.data.vo.Block;
import cn.mrray.raybaas.common.data.vo.CommonResponse;
import cn.mrray.raybaas.demo.config.ContractConfig;
import cn.mrray.raybaas.sdk.client.SdkClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description: 智能合约调用基础类
 */
@Service
public class ContractService {
    @Resource
    private SdkClient sdkClient;
	@Resource
	private ContractConfig contractConfig;

	/**
	 * 智能合约调用
	 * @param identity 合约标识
	 * @param version 合约版本
	 * @param methodName 方法名
	 * @param methodParam 方法参数
	 * @return 调用结果
	 */
    public CommonResponse invoke(String identity, String version, String methodName, String methodParam) {
        return sdkClient.invoke(identity, version, ContractLanguageTypeEnum.JAVA, methodName, methodParam);
    }

	/**
	 * 智能合约调用
	 * @param methodName 方法名
	 * @param methodParam 方法参数
	 * @return 调用结果
	 */
	public CommonResponse invoke(String methodName, String methodParam) {
		return invoke(contractConfig.getIdentity(), contractConfig.getVersion(), methodName, methodParam);
	}

	/**
	 * 根据交易哈希值查询
	 * @param var1 交易hash
	 * @return 调用结果
	 */
	public Block findTransactionByHash(String var1){
		return sdkClient.findBlockByTxHash(var1);
	}
}

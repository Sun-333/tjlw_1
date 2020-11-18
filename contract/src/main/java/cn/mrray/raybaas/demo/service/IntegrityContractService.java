package cn.mrray.raybaas.demo.service;

import cn.mrray.raybaas.common.data.vo.Block;
import cn.mrray.raybaas.common.data.vo.CommonResponse;
import cn.mrray.raybaas.demo.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Description: 调用链上智能合约接口，实现基础服务API
 */
public interface IntegrityContractService {
    /**
     * 初始化管理员账户
     *
     * @param account 账号信息
     * @return 初始化结果
     */
    CommonResponse adminInfoUpChain(Account account);
    /**
     * 初始化普通用户账户
     *
     * @param account 普通用户账号信息
     * @return 初始化结果
     */
    CommonResponse accountInfoUpChain(Account account);
    /**
     * 数字资产化
     *
     * @param fileInfo  文件信息
     * @return 资产化结果
     */
    CommonResponse capitalizate(FileInfo fileInfo);

    /**
     *  数字化转账
     * @param accountId 数字化转账执行管理员账户ID
     * @param fileID 物质｜数字资产链上证照ID
     * @return 是否转账成功
     */
    CommonResponse payMoney(String accountId,String fileID,String adminId,String sig);
    /**
     * 物质|数字资产转移
     *
     * @param transfer 转移参数
     * @return 转移结果
     */
    CommonResponse owerTransfer(Transfer transfer);
    /**
     * 用户行为上链
     *
     * @param accountBehavior 用户行为
     * @return 上链结果
     */
    CommonResponse accountBehaviorInfoUpChain(AccountBehavior accountBehavior);
    /**
     * 查询链上用户信息
     *
     * @param account 输入参数
     * @return 查询结果
     */
    CommonResponse queryAccount(Account account);
    /**
     * 查询链上文件摘要信息
     *
     * @param fileInfo 输出参数
     * @return 查询结果
     */
    CommonResponse queryFileInfo(FileInfo fileInfo);
    /**
     * 上传文件
     *
     * @param accountId 账号ID
     * @param fileId 文件ID
     * @param fileInfo 文件
     * @param type
     * @return 上传结果
     * @throws Exception 异常
     */
    CommonResponse uploadFileInfo(String accountId, String fileId, MultipartFile fileInfo, Integer type, String sig) throws Exception;
    /**
     * 授权
     *
     * @param warrant 授权数据
     * @return 授权结果
     */
    CommonResponse authorize(Warrant warrant);


    /**
     * 真伪辨识
     *
     * @param fileId 文件ID
     * @param file 文件
     * @param accountId 账号ID
     * @return 辨别结果
     * @throws Exception 异常
     */
    CommonResponse comparison(String fileId, String accountId, MultipartFile file) throws Exception;
    /**
     * 查询链上用户行为
     *
     * @param accountBehavior 调用参数
     * @return 查询结果
     */
    CommonResponse queryAccountBehavior(AccountBehavior accountBehavior);

    /**
     * 记账Api
     * @param map 记账数据
     *      其中keys为 key,values
     * @return 记账结果
     */
    CommonResponse save(Map<String,String> map);

    /**
     * 取帐
     * @param key key
     * @return 调用结果
     */
    CommonResponse get(String key);

    /**
     * 根据交易hash查询交易
     * @param key 交易hash
     * @return 查询结果
     */
    Block findTransactionByHash(String key);

    /**
     * 进行账户间的转帐
     * @param transfer 转账信息
     * @return 转账结果
     */
    CommonResponse transfer(Transfer transfer);
}

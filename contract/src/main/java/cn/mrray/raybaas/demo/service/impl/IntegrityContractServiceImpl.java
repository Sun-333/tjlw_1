package cn.mrray.raybaas.demo.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.mrray.raybaas.common.data.vo.Block;
import cn.mrray.raybaas.common.data.vo.CommonResponse;
import cn.mrray.raybaas.common.util.JsonUtils;
import cn.mrray.raybaas.demo.service.ContractService;
import cn.mrray.raybaas.demo.service.IntegrityContractService;
import cn.mrray.raybaas.demo.entity.*;

import cn.mrray.raybaas.demo.util.MultipartFileToFile;
import cn.mrray.raybaas.demo.util.SHAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class IntegrityContractServiceImpl implements IntegrityContractService {


    @Autowired
    private ContractService contractService;
    private SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    @Override
    public CommonResponse adminInfoUpChain(Account account) {
        return contractService.invoke("adminInfoUpChain", JsonUtils.toJson(account));
    }

    @Override
    public CommonResponse transfer(Transfer transfer) {
        return contractService.invoke("transfer",JsonUtils.toJson(transfer));
    }

    @Override
    public CommonResponse accountInfoUpChain(Account account) {

        return contractService.invoke("accountInfoUpChain", JsonUtils.toJson(account));
    }

    @Override
    public CommonResponse capitalizate(FileInfo fileInfo) {
        return contractService.invoke("capitalizate", JsonUtils.toJson(fileInfo));
    }

    @Override
    public CommonResponse payMoney(String accountId,String fileId,String adminId,String sig) {
        Map<String,String> map = new HashMap<>();
        map.put("accountId",accountId);
        map.put("fileId",fileId);
        map.put("adminId",adminId);
        map.put("sig",adminId);
        return contractService.invoke("payMoney", JsonUtils.toJson(map));
    }

    @Override
    public CommonResponse owerTransfer(Transfer transfer) {
        return contractService.invoke("owerTransfer", JsonUtils.toJson(transfer));
    }

    @Override
    public CommonResponse accountBehaviorInfoUpChain(AccountBehavior accountBehavior) {

        return contractService.invoke("accountBehaviorInfoUpChain", JsonUtils.toJson(accountBehavior));
    }

    @Override
    public CommonResponse queryAccount(Account account) {
        return contractService.invoke("queryAccount", JsonUtils.toJson(account));
    }

    @Override
    public CommonResponse queryAccountBehavior(AccountBehavior accountBehavior) {
        return contractService.invoke("queryAccountBehavior", JsonUtils.toJson(accountBehavior));
    }

    @Override
    public CommonResponse save(Map<String, String> map) {
        return contractService.invoke("save", JsonUtils.toJson(map));
    }

    @Override
    public CommonResponse get(String key) {
        Map<String,String> map=new LinkedHashMap<>();
        map.put("key",key);
        return contractService.invoke("get", JsonUtils.toJson(map));
    }

    @Override
    public Block findTransactionByHash(String key) {
        return contractService.findTransactionByHash(key);
    }

    @Override
    public CommonResponse queryFileInfo(FileInfo fileInfo) {
        return contractService.invoke("queryFileInfo", JsonUtils.toJson(fileInfo));
    }

    @Override
    public CommonResponse uploadFileInfo(String accountId, String fileId, MultipartFile file, Integer type, String sig) throws Exception {
        String content =new String(FileUtil.readBytes(MultipartFileToFile.multipartFileToFile(file)));
        FileInfo fileInfo = new FileInfo();
        fileInfo.setAccountId(accountId);
        fileInfo.setFileId(fileId);
        fileInfo.setFileMdHash(SHAUtil.getMD5String(content));
        fileInfo.setFileName(file.getResource().getFilename());
        fileInfo.setFileShaHash(SHAUtil.sha256BasedHutool(content));
        fileInfo.setFileSize(String.valueOf(file.getSize()));
        fileInfo.setType(type);
        fileInfo.setUpChainTime(simpleDateFormat.format(new Date()));
        fileInfo.setSig(sig);
        return contractService.invoke("uploadFileInfo", JsonUtils.toJson(fileInfo));
    }

    @Override
    public CommonResponse authorize(Warrant warrant) {
        warrant.setAuthorizeTime(simpleDateFormat.format(new Date()));
        return contractService.invoke("authorize", JsonUtils.toJson(warrant));
    }

    @Override
    public CommonResponse comparison(String fileId, String accountId, MultipartFile file) throws Exception {
        String content =new String(FileUtil.readBytes(MultipartFileToFile.multipartFileToFile(file)));
        Comparison comparison = new Comparison("1",
                SHAUtil.getMD5String(content),
                SHAUtil.sha256BasedHutool(content),
                "1");
        System.out.println(comparison.toString());
        return contractService.invoke("comparison", JsonUtils.toJson(comparison));
    }

    private Account getAccount(String id){
        Account find = new Account();
        find.setAccountId(id);
        CommonResponse<Account> response = queryAccount(find);
        return response.getData();
    }
}
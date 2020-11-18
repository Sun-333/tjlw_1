package cn.mrray.raybaas.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * Description: 资产转移类。
 *
 *资产分为 链上数字资产、现实生活中的数字｜物质资产证照上链后的所有权。
 * 链上数字资产转移为时设置transferAmount。
 * 证照资产转移时设置 fileId。
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "交易信息类")
    public  class Transfer{
        /**
         * 转出账户
         */
        @ApiModelProperty(value = "转出账户", required = true, example = "1")
        private String fromUserId;
        /**
         * 转入账户
         */
        @ApiModelProperty(value = "转入账户", required = true, example = "1")
        private String toUserId;
        /**
         * 资金
         */
        @ApiModelProperty(value = "交易金额", required = true, example = "100")
        private int transferAmount;
        /**
         * 资产转移
         */
        @ApiModelProperty(value = "资产转移|文件ID", required = true, example = "1")
        private String  fileId;

        /**
         * 转出账户对次交易进行签名
         */
        @ApiModelProperty(value = "转出账户对次交易进行签名",required = true, example = "1")
        private String sig;
}
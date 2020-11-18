package cn.mrray.raybaas.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Description: 用户行为信息。
 * 上传用户行为时，用户行为需要被签名，签名可由由管理员或者此用户实现，签名字段由上层应用提供
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "用户行为信息")
public  class AccountBehavior {
        /**
         * 行为信息ID
         */
        @ApiModelProperty(value = "行为信息ID", required = true, example = "1")
        private String behaviorId;
        /**
         * 用户ID
         */
        @ApiModelProperty(value = "用户ID", required = true, example = "1")
        private String accountId;
        /**
         * 上传信息管理员账户ID
         */
        @ApiModelProperty(value = "管理员ID", required = true, example = "1")
        private String adminId;
        /**
         * 上链标题
         */
        @ApiModelProperty(value = "上链标题", required = true, example = "违纪")
        private String title;
        /**
         * 上链类容
         */
        @ApiModelProperty(value = "上链内容", required = true, example = "在xxx天，工作人员由于xxx原因导致xxx情况")
        private String content;
        /**
         * 业务类型（上链业务对应的类型）
         */
        @ApiModelProperty(value = "业务类型", required = true, example = "Integer.MAX|Integer.MAX-1")
        private Integer bizType;
        /**
         * 上链时间戳
         */
        @ApiModelProperty(value = "上链时间戳", required = true, example = "2019-12-20 12:30:00")
        private Date upChainTime;
        /**
         * 其他信息JSON字符串
         */
        @ApiModelProperty(value = "其他信息JSON字符串", example = "1")
        private Integral otherInfoJson;
        /**
         * 签名
         */
        @ApiModelProperty(value = "签名", example = "1")
        private String sig;
    }
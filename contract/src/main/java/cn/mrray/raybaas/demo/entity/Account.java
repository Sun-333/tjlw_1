package cn.mrray.raybaas.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Description: 账户信息。
 * 初始化账号时，不能填写链上资产，链上资产只能通过交易、数字｜物质资产化转账得到
 *
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel(value = "账户信息")
public class Account implements Serializable {
	/**
	 * 账号
	 */
	@ApiModelProperty(value = "账户ID", required = true, example = "1")
	private String accountId;
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名", required = true, example = "孙钰山")
	private String accountName;
	/**
	 * 账号
	 */
	@ApiModelProperty(value = "账号", required = true, example = "jason")
	private String accountNumber;
	/**
	 * 账号类型 0为管理员,1为普通用户
	 */
	@ApiModelProperty(value = "账户类型", required = true, example = "1",notes = "0为管理员|1为普通用户")
	private Integer type;
	/**
	 * 用户公钥
	 */
	@ApiModelProperty(value = "用户公钥", required = true, example = "dadodjoahfoiPs")
	private String pk;
	/**
	 * 诚信积分
	 */
	@ApiModelProperty(value = "诚信积分", example = "100")
	private Integer integral;
	/**
	 * 资产
	 */
	@ApiModelProperty(value = "资产", example = "0",notes ="初始化资产为0")
	private Integer asset;
}

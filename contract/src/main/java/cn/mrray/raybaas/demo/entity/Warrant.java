package cn.mrray.raybaas.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Description: 授权信息。
 *授权是对链上资产证照拥有者对其他人进行链上资产证照的访问授权。
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "授权信息")
public class Warrant {
	/**
	 * 授权账户ID
	 */
	@ApiModelProperty(value = "授权账户ID", required = true, example = "1")
	private String authorizeAccountId;
	/**
	 * 被授权账户ID
	 */
	@ApiModelProperty(value = "被授权账户ID", required = true, example = "1")
	private String authorizedAccountId;
	/**
	 * 文件ID
	 */
	@ApiModelProperty(value = "文件ID", required = true, example = "1")
	private String fileId;
	/**
	 * 授权时间
	 */
	@ApiModelProperty(hidden = true)
	private String authorizeTime;

	/**
	 * 授权签名
	 */
	@ApiModelProperty(value = "授权签名", required = true, example = "1")
	private String sig;
}

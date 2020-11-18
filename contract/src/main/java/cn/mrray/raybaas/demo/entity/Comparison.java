package cn.mrray.raybaas.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Description: 比对信息
 *
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel(value = "比对信息")
public class Comparison {
	/**
	 * 文件ID
	 */
	@ApiModelProperty(value = "文件ID", required = true, example = "1")
	private String fileId;
	/**
	 * 文件MD5哈希值
	 */
	@ApiModelProperty(value = "文件MD5哈希值", required = true, example = "1")
	private String fileMdHash;
	/**
	 * 文件SHA256哈希值
	 */
	@ApiModelProperty(value = "文件SHA256哈希值", required = true, example = "1")
	private String fileShaHash;
	/**
	 * 账户ID
	 */
	@ApiModelProperty(value = "账户ID", required = true, example = "1")
	private String accountId;

	public Comparison(String fileId, String fileMdHash, String fileShaHash, String accountId) {
		this.fileId = fileId;
		this.fileMdHash = fileMdHash;
		this.fileShaHash = fileShaHash;
		this.accountId = accountId;
	}
}

package cn.mrray.raybaas.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Description: 文件信息。
 * 上传的所有证照在7套基础服务中都为文件。
 * 区块链上只记录文件的摘要与上传人的签名 并上传人可为管理员或者普通用户
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel(value = "文件信息")
public class FileInfo {
	/**
	 * 文件ID
	 */
	@ApiModelProperty(value = "文件ID", required = true, example = "1")
	private String fileId;
	/**
	 * 文件名称
	 */
	@ApiModelProperty(value = "文件名称", required = true, example = "1")
	private String fileName;
	/**
	 * 文件大小
	 */
	@ApiModelProperty(value = "文件大小", required = true, example = "1")
	private String fileSize;
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
	 * 签名
	 */
	@ApiModelProperty(value = "签名", required = true, example = "1")
	private String sig;
	/**
	 * 上链时间
	 */
	@ApiModelProperty(value = "上链时间", required = true, example = "1")
	private String upChainTime;
	/**
	 * 账户ID
	 */
	@ApiModelProperty(value = "账户ID", required = true, example = "1")
	private String accountId;
	/**
	 * 操作管理员ID
	 */
	@ApiModelProperty(value = "操作管理员ID", required = true, example = "1")
	private String adminId;
	/**
	 * 文件类型定义
	 */
	@ApiModelProperty(value = "文件类型定义", required = true, example = "1")
	private Integer type;
	/**
	 * 评估价值
	 */
	@ApiModelProperty(value = "评估价值",required = true,example = "100")
	private Integer worth;
}

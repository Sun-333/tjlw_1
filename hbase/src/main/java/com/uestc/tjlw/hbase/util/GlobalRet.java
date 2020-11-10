package com.uestc.tjlw.hbase.util;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Description：通用响应对象
 *
 * @author rannuo1010@gmail.com
 * @date 2019/9/26
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "通用响应对象")
public class GlobalRet<T> implements Serializable {

	@ApiModelProperty(value = "状态码", required = true)
	private Integer code = GlobalConstants.SUCCESS;
	@ApiModelProperty(value = "描述信息", required = true)
	private String message = GlobalConstants.SUCCESS_DESC;
	@ApiModelProperty(value = "结果集(泛型)")
	private T data;

	public GlobalRet() {
	}

	public GlobalRet(T data) {
		this.data = data;
	}

	public GlobalRet(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public GlobalRet(Throwable e) {
		this.message = e.getMessage();
		this.code = GlobalConstants.FAIL;
	}

	/**
	 * 成功时候的调用
	 */
	public static <T> GlobalRet<T> success() {
		return new GlobalRet<>();
	}

	/**
	 * 成功时候的调用（带数据）
	 */
	public static <T> GlobalRet<T> success(T data) {
		return new GlobalRet<>(data);
	}

	/**
	 * 成功时候的调用
	 */
	public static <T> GlobalRet<T> failure() {
		GlobalRet<T> ret = new GlobalRet<>();
		ret.setCode(GlobalConstants.FAIL);
		ret.setMessage(GlobalConstants.FAIL_DESC);
		return ret;
	}

	public static <T> GlobalRet<T> response(Integer code, String message, T data) {
		return new GlobalRet<>(code, message, data);
	}

	/**
	 * 是否成功
	 *
	 * @return true/false
	 */
	@JsonIgnore
	@JSONField(serialize = false)
	public boolean isSuccess() {
		return GlobalConstants.SUCCESS == this.code;
	}

	/**
	 * 是否失败
	 *
	 * @return true/false
	 */
	@JsonIgnore
	@JSONField(serialize = false)
	public boolean isFail() {
		return !isSuccess();
	}

	/**
	 * 提取服务调用返回结果，若返回失败，则抛出业务指定的异常
	 *
	 * @param s
	 * @param <X>
	 * @return
	 * @throws X
	 */
	public <X extends Throwable> T orElseThrow(Supplier<? extends X> s) throws X {
		if (this.isSuccess()) {
			return this.getData();
		} else {
			throw s.get();
		}
	}

}

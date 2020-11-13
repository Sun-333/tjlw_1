package com.uestc.tjlw.hbase.domain;

import lombok.Data;

/**
 * @author yushansun
 * @title: HttpResponse
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/124:41 下午
 */
@Data
public class HttpResponse {
    //状态码
    Integer code;
    //消息
    String message;
    //内容
    String data;
}

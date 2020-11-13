package com.uestc.tjlw.hbase.util;

import cn.hutool.http.HttpRequest;
import com.uestc.tjlw.common.util.JsonUtil;
import com.uestc.tjlw.hbase.domain.HttpResponse;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yushansun
 * @title: HttpUtil
 * @projectName tjlw
 * @description: http请求封装
 * @date 2020/11/123:18 下午
 */
public class HttpUtil {
    /**
     * 发送http请求
     * @param address 地址
     * @param request 请求body
     * @param tClass 泛型类
     * @param <T> 泛型
     * @return
     */
    public static  <T> T request(String address,String request,Class<T> tClass){
        String json = HttpRequest.post(address).body(request).execute().body();
        return JsonUtil.jsonToPojo(json,tClass);
    }
    public static  <T> T request(String address, Map<String,Object> request, Class<T> tClass){
        String json = HttpRequest.post(address).form(request).execute().body();
        return JsonUtil.jsonToPojo(json,tClass);
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("key","1");
        map.put("value","123");
        System.out.println(HttpUtil.request("http://localhost:8085/saveInfo/save", map, HttpResponse.class));
    }
}

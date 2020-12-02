package cn.mrray.raybaas.demo.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author yushansun
 * @title: Operator
 * @projectName tjlw
 * @description: 上链信息操作类
 * @date 2020/11/3011:00 下午
 */
@Data
public class Operator {
    @JsonIgnore
    public static int upInfo = 1;
    @JsonIgnore
    public static int delete = 2;
    private int operator;
    private String infoHash;
    private String keyWindow;

    public Operator(int operator, String infoHash, String keyWindow) {
        this.operator = operator;
        this.infoHash = infoHash;
        this.keyWindow = keyWindow;
    }
}

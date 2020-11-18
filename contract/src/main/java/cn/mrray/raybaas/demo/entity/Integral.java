package cn.mrray.raybaas.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Description: 诚信积分修改值。
 * 默认实现的行为otherInfoJson字段扩展类：可在用户行为上链后设置诚信积分变化值，实现行为导致用户诚信积分的变化，
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "诚信积分修改")
public class Integral {
    /**
     * 诚信积分修改值
     */
    @ApiModelProperty(value = "诚信积分修改值", required = true, example = "1")
    int change;
}
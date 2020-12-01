package com.uestc.statistics.Controller;

import com.uestc.statistics.entity.DDosVO;
import com.uestc.statistics.entity.StreamSizeEntityVO;
import com.uestc.statistics.service.P4Service;
import com.uestc.tjlw.common.util.GlobalRet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Map;

/**
 * @author yushansun
 * @title: Controller
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/3012:38 下午
 */
@Api(tags = {"DDos统计"})
@Slf4j
@RestController
@RequestMapping("/ddos")
public class Controller {
    @Autowired
    private P4Service p4Service;

    @ApiOperation(value = "DDos检测 请求次数统计")
    @GetMapping("/findSourcesStreamSizeByTargetIp")
    public GlobalRet<Map<String,Long>> findSourcesStreamSizeByTargetIp(@RequestBody StreamSizeEntityVO entityVO){
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(entityVO.getEndTime());
        calendar.add(Calendar.SECOND,-entityVO.getWindow());
        return new GlobalRet(p4Service.findSourcesStreamSizeByTargetIp(
                calendar.getTimeInMillis()+"",
                entityVO.getEndTime().getTime()+"",
                entityVO.getTargetIp()
        ));
    }
    public GlobalRet<Boolean> postDDosRes(@RequestBody DDosVO entityVO){

    }
}

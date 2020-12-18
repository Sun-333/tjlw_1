package com.uestc.statistics.Controller;

import com.uestc.statistics.entity.DDosVO;
import com.uestc.statistics.entity.StatistcisVo;
import com.uestc.statistics.entity.StreamSizeEntityVO;
import com.uestc.statistics.service.DDosService;
import com.uestc.statistics.service.P4Service;
import com.uestc.tjlw.common.pojo.Statistics;
import com.uestc.tjlw.common.util.GlobalRet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    @Autowired
    private DDosService dDosService;

    @ApiOperation(value = "DDos检测 请求次数统计")
    @PostMapping("/findSourcesStreamSizeByTargetIp")
    @CrossOrigin(origins = "*",maxAge = 3600)
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
    @ApiOperation(value = "DDos检测结果提交")
    @PostMapping("/postDDosRes")
    @CrossOrigin(origins = "*",maxAge = 3600)
    public GlobalRet<Boolean> postDDosRes(@RequestBody DDosVO entityVO){
        return new GlobalRet<>(dDosService.save(entityVO));
    }

    @ApiOperation(value = "DDos统计接口")
    @PostMapping("/statistic")
    @CrossOrigin(origins = "*",maxAge = 3600)
    public GlobalRet<StatistcisVo> postDDosRes(@RequestParam String beginTime, @RequestParam String endTime){
        return new GlobalRet<StatistcisVo>(dDosService.statistics(beginTime,endTime));
    }

    @ApiOperation(value = "DDos月攻击次数统计")
    @GetMapping("/statisticDDos")
    @CrossOrigin(origins = "*",maxAge = 3600)
    public GlobalRet<List<Object>> getDDosByMonth(){
        return new GlobalRet<List<Object>>(dDosService.ddosStatistics());
    }

    @ApiOperation(value = "DDos数据查询")
    @PostMapping("/getDDosInfos")
    @CrossOrigin(origins = "*",maxAge = 3600)
    public GlobalRet<List<DDosVO>> getDDosInfos(@RequestParam String beginTime, @RequestParam String endTime){
        return new GlobalRet<List<DDosVO>>(dDosService.findDDosInfo(beginTime,endTime));
    }
}

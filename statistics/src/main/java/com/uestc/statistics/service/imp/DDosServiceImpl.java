package com.uestc.statistics.service.imp;

import com.uestc.statistics.entity.DDosVO;
import com.uestc.statistics.service.DDosService;
import com.uestc.statistics.service.HBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author yushansun
 * @title: DDosServiceImpl
 * @projectName tjlw
 * @description: DDos 业务实现
 * @date 2020/11/304:11 下午
 */
@Service
public class DDosServiceImpl implements DDosService {
    @Autowired
    private HBaseService hBaseService;
    @Override
    public boolean save(DDosVO dDosVO) {
        hBaseService.putData(DDosVO.getTableName(),dDosVO.getRowKey(),DDosVO.getFamilyName()[0],DDosVO.getColumns(),dDosVO.getValues());
        return true;
    }

    @Override
    public List<DDosVO> findByAndCondition(DDosVO dDosVO) {
        return null;
    }

    @Override
    public boolean creatDDosTable() {
        hBaseService.creatTable(DDosVO.getTableName(), Arrays.asList(DDosVO.getFamilyName()));
        return true;
    }
}

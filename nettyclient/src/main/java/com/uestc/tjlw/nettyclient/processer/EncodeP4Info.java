package com.uestc.tjlw.nettyclient.processer;

import com.uestc.tjlw.common.pojo.P4Info;
import com.uestc.tjlw.common.pojo.Switch;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yushansun
 * @title: EncodeP4Info
 * @projectName tjlw
 * @description: 将流数据转换为p4Info
 * @date 2020/11/121:28 下午
 */
public class EncodeP4Info {

    public P4Info encode(){
        P4Info p4Info = new P4Info("28918950","1024","192.168.50.0","192.168.50.4",
                "80","80","http","28918942");
        List<Switch> switches = new ArrayList<>();
        for (int j=1;j<=4;j++){
            Switch switch_1 = new Switch(j+"","28918941","80","192.168.50.2","80","192.168.50.3");
            switches.add(switch_1);
        }
        p4Info.setSwitchList(switches);
        return p4Info;
    }
}

package com.uestc.tjlw.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author yushansun
 * @title: HbaseUtilTest
 * @projectName tjlw
 * @description: TODO
 * @date 2020/11/35:27 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HbaseUtilTest {
    @Test
    public void test() throws IOException {
        HbaseUtil.createTable("user","user_id","address","info");
    }
}
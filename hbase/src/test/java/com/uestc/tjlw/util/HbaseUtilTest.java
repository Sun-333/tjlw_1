package com.uestc.tjlw.util;

import com.uestc.tjlw.hbase.util.HbaseUtil;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;

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
    /**
     * 创建表格测试
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        HbaseUtil.createTable("user","user_id","address","info");
    }

    /**
     * 获取列镞
     * @throws IOException
     */
    @Test
    public void listTables() throws IOException {
        System.out.println(HbaseUtil.listTables().get(0).getTableName().toString());
    }
    /**
     * 插入一条记录
     */
    @Test
    public void insertOne() throws IOException {
        HbaseUtil.insertOne("user","jason","info","age","20");
        HbaseUtil.insertOne("user","row-ab","info","age","19");
        HbaseUtil.insertOne("user","row-abc","info","age","18");
        HbaseUtil.insertOne("user","row-ac","info","age","17");
        HbaseUtil.insertOne("user","row-bc","info","age","16");
    }
    @Test
    public void select() throws IOException {
        System.out.println(HbaseUtil.select("user", "jason").toString());
    }
    @Test
    public void scan() throws IOException {
        Filter filter = HbaseUtil.rowFilter(CompareOperator.EQUAL,new BinaryComparator(Bytes.toBytes("row-ab")));
        ResultScanner  resultScanner = HbaseUtil.scan("user",filter);
        resultScanner.forEach(result -> {
            //System.out.println(result.toString());
            //System.out.println(Bytes.toString(result.getNoVersionMap().get(Bytes.toBytes("info")).get(Bytes.toBytes("age"))));
            System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("age"))));
        });
    }
}
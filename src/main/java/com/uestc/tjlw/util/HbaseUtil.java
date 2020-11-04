package com.uestc.tjlw.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Value;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
public class HbaseUtil {
 
    // 行键前缀固定长度，不足的用0补齐（后补）
    private static final int ROW_KEY_PRE_LENGTH = 40;
    // 行键参数固定长度，不足用0补齐（前补）
    private static final int ROW_KEY_OPTIONS_LENGTH = 100;
    // 行键前缀固定长度，不足的用0补齐（前补）
    private static final int ROW_KEY_SUF_LENGTH = 40;
 
 
 
 
    @Value("${hbase.zookeeper.quorum:h1,h2,h3}")
    static String hbase_zookeeper_quorum;
 
    @Value("${hbase_zookeeper_property_clientPort:2181}")
    static String hbase_zookeeper_property_clientPort;
 
    static Configuration conf;
    static Connection conn = null;
    static Admin admin = null;
 
    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "myhbase2");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        try {
            conn = ConnectionFactory.createConnection(conf);
            admin = conn.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
 
    /**
     * 创建数据表
     * @param tableName
     * @param columnFamilies
     * @throws IOException
     */
    public static void createTable(String tableName,String ...columnFamilies) throws IOException {
        TableName name = TableName.valueOf(tableName);
        if (!admin.isTableAvailable(name)) {
            //表描述器构造器
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(name);
            List<ColumnFamilyDescriptor> columnFamilyDescriptorList = new ArrayList<>();
            for (String columnFamily : columnFamilies) {
                //列族描述起构造器
                ColumnFamilyDescriptorBuilder columnFamilyDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(columnFamily));
                //获得列描述
                ColumnFamilyDescriptor columnFamilyDescriptor = columnFamilyDescriptorBuilder.build();
                columnFamilyDescriptorList.add(columnFamilyDescriptor);
            }
            // 设置列簇
            tableDescriptorBuilder.setColumnFamilies(columnFamilyDescriptorList);
            //获得表描述器
            TableDescriptor tableDescriptor = tableDescriptorBuilder.build();
            //创建表
            admin.createTable(tableDescriptor);
        }
    }
 
    /**
     * 禁用表
     * @param tableName
     * @throws IOException
     */
    public static void disableTable(String tableName) throws IOException {
        TableName name = TableName.valueOf(tableName);
        if (!admin.isTableDisabled(name)) {
            admin.disableTable(name);
        }
    }
 
    /**
     * 清空表
     * @param tableName
     */
    public static void truncate(String tableName) throws IOException {
 
        TableName name = TableName.valueOf(tableName);
        disableTable(tableName);
        admin.truncateTable(name,true);
 
    }
 
    /**
     * 删除表
     * @param tableName
     * @throws IOException
     */
    public static void deleteTable(String tableName) throws IOException {
        TableName name = TableName.valueOf(tableName);
        if (admin.isTableDisabled(name)) {
            admin.deleteTable(name);
        }
    }
 
    /**
     * 获取数据表列表
     * @return
     * @throws IOException
     */
    public static List<TableDescriptor> listTables() throws IOException {
        List<TableDescriptor> tableDescriptors = admin.listTableDescriptors();
        return tableDescriptors;
    }
 
 
    /**
     * 获取行键
     * @param prefixString
     * @param suffixString
     * @param options
     * @return
     */
    public static String getRowKey(String prefixString,String suffixString,Object ...options) {
        if (prefixString.length() >ROW_KEY_PRE_LENGTH  || suffixString.length() > ROW_KEY_SUF_LENGTH) {
            return null;
        }
        StringBuilder preStringBuilder = new StringBuilder();
        preStringBuilder.append(prefixString);
 
        for (int i = 0; i < (ROW_KEY_PRE_LENGTH - preStringBuilder.length()); i++) {
            preStringBuilder.append("0");
        }
 
        StringBuilder sufStringBuilder = new StringBuilder();
        sufStringBuilder.append(suffixString);
 
        for (int i = 0; i < (ROW_KEY_SUF_LENGTH - sufStringBuilder.length()); i++) {
            sufStringBuilder.append("0");
        }
 
        StringBuilder optBuilder = new StringBuilder();
 
        for (Object option : options) {
            optBuilder.append(option);
        }
 
        if (optBuilder.length() > ROW_KEY_OPTIONS_LENGTH) {
            return null;
        }
 
        StringBuilder optStringBuilder = new StringBuilder();
        for (int i = 0; i < (ROW_KEY_OPTIONS_LENGTH - optBuilder.length()); i++) {
            optStringBuilder.append("0");
        }
 
        optStringBuilder.append(optBuilder);
 
        return preStringBuilder.append("|").append(optStringBuilder).append("|").append(sufStringBuilder).toString();
    }


    /**
     * 插入一条记录
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param value
     * @throws IOException
     */
    public static void insertOne(String tableName,String rowKey,String columnFamily,String column,String value) throws IOException {
        Put put = new Put(Bytes.toBytes(rowKey));
        //下面三个分别为，列族，列名，列值
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        //得到 table
        Table table = conn.getTable(TableName.valueOf(tableName));
        //执行插入
        table.put(put);
    }
 
    /**
     * 多行多列多值-单列簇
     * @param tableName
     * @param columnFamily
     * @param mapList
     * @throws IOException
     */
    public static void insertAll(String tableName, String columnFamily, List<Map<String,String>> mapList) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        List<Put> puts = new ArrayList<>();
 
        for (Map<String, String> map : mapList) {
            Put put = new Put(Bytes.toBytes(map.get("rowKey")));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(map.get("column")), Bytes.toBytes(map.get("value")));
            puts.add(put);
        }
        table.put(puts);
    }
 
    /**
     * 单行多列多值-单列簇
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param mapList
     * @throws IOException
     */
    public static void insertAll(String tableName, String rowKey,String columnFamily, List<Map<String,String>> mapList) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        List<Put> puts = new ArrayList<>();
 
        for (Map<String, String> map : mapList) {
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(map.get("column")), Bytes.toBytes(map.get("value")));
            puts.add(put);
        }
        table.put(puts);
        // table.close();
    }
 
    /**
     * 更新数据
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param value
     * @throws IOException
     */
 
    public static void update(String tableName,String rowKey,String columnFamily,String column,String value) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        table.put(put);
        // table.close();
    }
 
    /**
     * 删除单行单列
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @throws IOException
     */
    public static void delete(String tableName,String rowKey,String columnFamily,String column) throws IOException{
        Table table = conn.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        delete.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        table.delete(delete);
    }
 
    /**
     * 删除单行多列
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param columnList
     * @throws IOException
     */
    public static void delete(String tableName,String rowKey,String columnFamily,String ...columnList) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        for (String column : columnList) {
            delete.addColumns(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        }
        table.delete(delete);
    }
 
    /**
     * 删除单行单列簇
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @throws IOException
     */
    public static void delete(String tableName,String rowKey,String columnFamily) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        delete.addFamily(Bytes.toBytes(columnFamily));
        table.delete(delete);
    }
 
    /**
     * 删除单行
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public static void delete(String tableName,String rowKey) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        table.delete(delete);
    }
 
    /**
     * 删除多行
     * @param tableName
     * @param rowKeyList
     * @throws IOException
     */
    public static void delete(String tableName,String ...rowKeyList) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        ArrayList<Delete> deleteList = new ArrayList<>();
        for (String rowKey : rowKeyList) {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            deleteList.add(delete);
        }
        table.delete(deleteList);
    }
 
    /**
     * 查询表
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public static Result select(String tableName, String rowKey) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        Result result = table.get(get);
        return result;
 
    }
 
    /**
     * 全表扫描
     * @param tableName
     * @return
     * @throws IOException
     */
    public static ResultScanner scan(String tableName) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
       
        return scanner;
    }
 
    /**
     * 全表扫描-列簇
     * @param tableName
     * @param columnFamily
     * @return
     * @throws IOException
     */
    public static ResultScanner scan(String tableName,String columnFamily) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes(columnFamily));
        ResultScanner scanner = table.getScanner(scan);
       
        return scanner;
 
    }
 
    /**
     * 全表扫描-列
     * @param tableName
     * @param columnFamily
     * @param column
     * @return
     * @throws IOException
     */
    public static ResultScanner scan(String tableName,String columnFamily,String column) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(column));
        ResultScanner scanner = table.getScanner(scan);
       
        return scanner;
    }
 
    /**
     * 全表扫描-过滤器
     * @param tableName
     * @param filter
     * @return
     * @throws IOException
     */
    public static ResultScanner scan(String tableName, Filter filter) throws IOException {
 
        Table table = conn.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        
        return scanner;
    }
 
    /**
     * 分页全表扫描-过滤器
     * @param tableName
     * @param filter
     * @return
     * @throws IOException
     */
    public static ResultScanner scan(String tableName, Filter filter,String startRowKey) throws IOException {
 
        Table table = conn.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        scan.setFilter(filter);
        scan.withStartRow(Bytes.toBytes(startRowKey));
        ResultScanner scanner = table.getScanner(scan);

 
        return scanner;
    }
 
    /**
     * 获取分页过滤器
     * @param size
     * @return
     */
    public static Filter pagetFilter(long size) {
 
        return new PageFilter(size);
 
    }
 
    /**
     * singleColumnValueFilter
     * @param columnFamily
     * @param column
     * @param compareOperator
     * @param value
     * @return
     */
    public static Filter singleColumnValueFilter(String columnFamily, String column,CompareOperator compareOperator, String value) {
        return new SingleColumnValueFilter(Bytes.toBytes(columnFamily), Bytes.toBytes(column), compareOperator, Bytes.toBytes(value));
    }
 
    /**
     * rowFilter
     * @param compareOperator
     * @param rowComparator
     * @return
     */
    public static Filter rowFilter(CompareOperator compareOperator,ByteArrayComparable rowComparator) {
        return new RowFilter(compareOperator, rowComparator);
    }
 
    /**
     * columnPrefixFilter
     * @param prefix
     * @return
     */
    public static Filter columnPrefixFilter(String prefix) {
        return new ColumnPrefixFilter(Bytes.toBytes(prefix));
    }
 
    /**
     * 过滤器集合
     * @param filterList
     * @return
     */
    public static FilterList filterListPassAll(Filter ...filterList) {
        FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        for (Filter filter : filterList) {
            list.addFilter(filter);
        }
        return list;
    }
    /**
     * 过滤器集合
     * @param
     * @return
     */
    public static FilterList filterListPassOne(Filter ...filterList) {
        FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        for (Filter filter : filterList) {
            list.addFilter(filter);
        }
        return list;
    }

 
 
    /**
     * 关闭连接
     * @throws IOException
     */
    public static void close(){
        try {
            conn.close();
        } catch (IOException e) {
            conn = null;
        }finally {
            conn = null;
        }
    }
}
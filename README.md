# tjlw_1
tjlw项目
  p4抓包时IP头长度为48 按照mri.p4说明包长度在数据流只流通2个交换机时IPhead长度为40。
  目前代码解码switchID=[255,521,512]与python[1,2]存在误差，其余数据一样
  
common module 为公共依赖
nettyClient module 通过jpcap抓包解析后，通过netty客户端发送给Hbase module
Hbase module 实现了web请求接口，接收并存储nettyClient发送的p4数据信息，并通过Http请求将数据上链
区块链接口：实现save，get通用数据上链与查询

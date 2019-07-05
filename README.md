#### 一、[Windows单节点搭建][1]
#### 二、[Centos单节点搭建][10]
#### 三、[Insert（插入数据）简单使用][2]
#### 四、[Find（查询数据）简单使用][3]
#### 五、[Update（更新数据）简单使用][4]
#### 六、[Remove（删除数据）简单使用][5]
#### 七、[Aggregate（聚合操作，流处理）简单使用][6]
#### 八、[Index（索引）简单使用][7]
#### 九、[数据过期 Index（索引）简单使用][9]
#### 十、[Explain（执行计划）简单使用][8]
#### 十一、主键简要说明
```bash
1，以 _id 字段标识，是在客户端生成的（这一点要注意）
2，可以快速自动生成，可以排序，长度为12个字节，前4个字节存储主键的生成时间，精确到秒
```
#### 十二、副本集（从节点）简要说明（注意：副本集选举也是使用了和ZK一样的Paxos算法）
```bash
1，主节点负责读写，从节点只能读，不能写
2，副本集最多50个节点（原因：当前节点要向其它节点发送心跳，节点太多，影响效率）
3，副本集以内节点相互发送心跳，默认每2秒发送一次，默认10秒没收到回复，移除节点
4，副本集以内参与选举节点不能超过7个
5，可以有只参与选举的节点，不储存副本数据（注意：这种节点只参与投票，不能给自己投票，不能储存数据）
```

[1]: https://github.com/firechiang/mongodb-test/blob/master/doc/windows-install-dev.md
[2]: https://github.com/firechiang/mongodb-test/blob/master/doc/shell_insert_use.md
[3]: https://github.com/firechiang/mongodb-test/blob/master/doc/shell_find_use.md
[4]: https://github.com/firechiang/mongodb-test/blob/master/doc/shell_update_use.md
[5]: https://github.com/firechiang/mongodb-test/blob/master/doc/shell_remove_use.md
[6]: https://github.com/firechiang/mongodb-test/blob/master/doc/shell_aggregate_use.md
[7]: https://github.com/firechiang/mongodb-test/blob/master/doc/shell_index_use.md
[8]: https://github.com/firechiang/mongodb-test/blob/master/doc/shell_explain_use.md
[9]: https://github.com/firechiang/mongodb-test/blob/master/doc/shell_index_use.md#%E5%88%9B%E5%BB%BA%E5%B8%A6%E8%BF%87%E6%9C%9F%E6%97%B6%E9%97%B4%E7%9A%84-index%E7%B4%A2%E5%BC%95%E8%BF%87%E6%9C%9F%E5%90%8E%E6%95%B0%E6%8D%AE%E4%B9%9F%E5%B0%86%E8%A2%AB%E5%88%A0%E9%99%A4%E6%B3%A8%E6%84%8F%E5%8F%AA%E8%83%BD%E5%9C%A8%E6%97%B6%E9%97%B4%E7%B1%BB%E5%9E%8B%E5%AD%97%E6%AE%B5%E4%B8%8A%E4%BD%BF%E7%94%A81-%E8%A1%A8%E7%A4%BA%E7%B4%A2%E5%BC%95%E7%9A%84%E9%94%AE%E5%80%BC%E6%8C%89%E7%85%A7%E4%BB%8E%E5%B0%8F%E5%88%B0%E5%A4%A7%E7%9A%84%E9%A1%BA%E5%BA%8F%E6%8E%92%E5%BA%8F-1-%E8%A1%A8%E7%A4%BA%E7%B4%A2%E5%BC%95%E7%9A%84%E9%94%AE%E5%80%BC%E6%8C%89%E7%85%A7%E4%BB%8E%E5%A4%A7%E5%88%B0%E5%B0%8F%E7%9A%84%E9%A1%BA%E5%BA%8F%E6%8E%92%E5%BA%8F
[10]: https://github.com/firechiang/mongodb-test/blob/master/doc/centos-install-single.md
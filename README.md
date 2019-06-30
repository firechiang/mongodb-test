#### 一、[Windows单节点开发搭建][1]
#### 二、[Insert（插入数据）简单使用][2]
#### 三、[Find（查询数据）简单使用][3]
#### 四、[Update（更新数据）简单使用][4]
#### 五、[Remove（删除数据）简单使用][5]
#### 六、[Aggregate（聚合操作，流处理）简单使用][6]
#### 七、[Index（索引）简单使用][7]
#### 八、[Index（索引）简单使用][9]
#### 九、[数据过期 Index（索引）简单使用][8]
```bash
1，以 _id 字段标识，是在客户端生成的（这一点要注意）
2，可以快速生成，可以排序，长度为12个字节，前4个字节存储主键的生成时间，精确到秒
```
#### 十、连接 Mongodb（注意：Mongodb Shell支持JavaScript语法，以下都是用JavaScript语法）
```bash
$ d:mongodb-win32-x86_64-2008plus-ssl-4.0.10/bin/mongo.exe # Windows连接本地的Mongodb
$ print('maomao')                                          # 在 Mongodb Shell 上打印一句话
$ use test                                                 # 使用 test 数据库
$ show collections                                         # 查看 test 数据库里面的所有集合
$ db."表名".drop                                           # 删除集合
$ exit                                                     # 退出  Mongodb Shell
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

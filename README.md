#### 一、[Windows单节点开发搭建][1]
#### 二、[Insert（插入数据）简单使用][2]
#### 三、[Find（查询数据）简单使用][3]
#### 四、[Update（更新数据）简单使用][4]
#### 五、[Remove（删除数据）简单使用][5]
#### 六、[Aggregate（聚合操作，流处理）简单使用][6]
#### 七、[Index（索引）简单使用][7]
#### 八、[Explain（执行计划）简单使用][8]
```bash
1，以 _id 字段标识，是在客户端生成的（这一点要注意）
2，可以快速生成，可以排序，长度为12个字节，前4个字节存储主键的生成时间，精确到秒
```
#### 九、连接 Mongodb（注意：Mongodb Shell支持JavaScript语法，以下都是用JavaScript语法）
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

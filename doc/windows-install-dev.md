#### 一、[下载Mongodb-Win32-x86_64-2008plus-ssl-4.0.10.zip](https://fastdl.mongodb.org/win32/mongodb-win32-x86_64-2008plus-ssl-4.0.10.zip)
#### 二、解压安装包到D盘
#### 三、启动服务
```bash
# 注意要手动创建data目录，测试服务是否启动成功：http://127.0.0.1:27017
$ d:mongodb-win32-x86_64-2008plus-ssl-4.0.10/bin/mongod.exe --dbpath=D:\mongodb-win32-x86_64-2008plus-ssl-4.0.10\data   
```
#### 四、连接 Mongodb（注意：Mongodb Shell支持JavaScript语法，以下都是用JavaScript语法）
```bash
$ d:mongodb-win32-x86_64-2008plus-ssl-4.0.10/bin/mongo.exe    # 连接本地的Mongodb
$ print('maomao')                                             # 在 Mongodb Shell 上打印一句话
$ use test                                                    # 使用 test 数据库
$ show collections                                            # 查看 test 数据库里面的所有集合
$ db."表名".drop                                              # 删除集合
$ exit                                                        # 退出  Mongodb Shell
```

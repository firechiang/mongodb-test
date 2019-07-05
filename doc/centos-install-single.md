#### 一、下载安装包，[官方下载地址](https://www.mongodb.com/download-center/community?jmp=docs)
```bash
$ cd /home/tools
# Server包，包含服务进程
$ wget https://repo.mongodb.org/yum/redhat/7/mongodb-org/4.0/x86_64/RPMS/mongodb-org-server-4.0.10-1.el7.x86_64.rpm
# Shell包，包含 Mongo Shell 工具
$ wget https://repo.mongodb.org/yum/redhat/7/mongodb-org/4.0/x86_64/RPMS/mongodb-org-shell-4.0.10-1.el7.x86_64.rpm
# Tools包，包含 Tools 工具
$ wget https://repo.mongodb.org/yum/redhat/7/mongodb-org/4.0/x86_64/RPMS/mongodb-org-tools-4.0.10-1.el7.x86_64.rpm
# Mongos包，包含 Mongos
$ wget https://repo.mongodb.org/yum/redhat/7/mongodb-org/4.0/x86_64/RPMS/mongodb-org-mongos-4.0.10-1.el7.x86_64.rpm
```

#### 二、安装相关服务，如需更详细的说明和优化，请参考[官方安装文档](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-red-hat)（注意：MongoDB所有源码都编译在一个文件里面，并没有安装目录）
```bash
$ cd /home/tools
$ sudo rpm -ivh mongodb-org-server-4.0.10-1.el7.x86_64.rpm         # 安装  MongoDB 服务进程
$ sudo rpm -ivh mongodb-org-shell-4.0.10-1.el7.x86_64.rpm          # 安装  Mongo Shell 工具（注意：如果不用可以不安装）
```


#### 三、修改[vi /etc/mongod.conf]配置
```bash
systemLog:
  path: /var/log/mongodb/mongod.log            # 日志文件目录
  
storage:
  dbPath: /var/lib/mongo                       # 数据存放目录
  
net:
  port: 27017                                  # 端口
  bindIp: server001                            # 绑定地址
```

#### 三、启动服务
```bash
$ sudo service mongod start                    # 启动服务
$ sudo service mongod stop                     # 停止服务
$ sudo service mongod restart                  # 新启服务
$ sudo chkconfig mongod on                     # 开启开机启动服务
$ sudo chkconfig mongod off                    # 关闭开机启动服务
```

#### 四、创建超级管理员（注意：超级管理员可以创建任何数据库的用户）
```bash
$ mongo server001:27017/admin                  # 连接到admin数据库
$ db.createUser({
      user:"jiang",
      pwd:"jiang",
      roles:[{role:"root", db:"admin"}]
  })
```

#### 五、修改[vi /etc/mongod.conf]配置，启用安全验证
```bash
security:
  authorization: enabled                       # 开启安全验证 
```

#### 六、使用 用户名密码，连接Mongo Shell和简单使用
```bash
$ mongo server001:27017/admin -ujiang -p       # 使用用户jiang连接test_test数据库（-p是连接后输入密码）
$ db.auth("jiang","jiang")                     # 验证用户名和密码（注意：如果是以用户名密码连接的是不需要这一步的）
$ use test_test                                # 进入 test_test 数据库
$ show databases                               # 查看所有数据库
$ show collections                             # 查看所有集合
$ db."表名".drop                               # 删除集合（数据表）
$ exit                                         # 退出  Mongodb Shell
```



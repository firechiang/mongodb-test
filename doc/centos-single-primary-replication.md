#### 一、单主多从高可用架构简要说明（注意：选举也是使用了和ZK一样的Paxos算法）
```bash
1，主节点负责读写，从节点只能读，不能写
2，副本集最多50个节点，最少3个节点包括主节点（原因：一个是Paxos算法，还有就是当前节点要向其它节点发送心跳，节点太多，影响效率）
3，副本集以内节点相互发送心跳，默认每2秒发送一次，默认10秒没收到回复，移除节点
4，副本集以内参与选举节点不能超过7个
5，可以有只参与选举的节点，不储存副本数据（注意：这种节点只参与投票，不能给自己投票，不能储存数据）
6，主从同步的数据其实是修改的日志文件，而且还实现了幂等性，默认都是在 local.oplog.rs 数据库里面
7，当副本集里面的主节点和选举同时发生故障时，副本集将变成只读模式
```
#### 二、下载安装包，[官方下载地址](https://www.mongodb.com/download-center/community?jmp=docs)
```bash
$ cd /home/tools
# Server包，包含服务进程
$ wget https://repo.mongodb.org/yum/redhat/7/mongodb-org/4.0/x86_64/RPMS/mongodb-org-server-4.0.10-1.el7.x86_64.rpm
# Shell包，包含 Mongo Shell 工具
$ wget https://repo.mongodb.org/yum/redhat/7/mongodb-org/4.0/x86_64/RPMS/mongodb-org-shell-4.0.10-1.el7.x86_64.rpm
```

#### 三、分发安装包到其它节点
```bash
$ cd /home/tools
$ scp -r mongodb-org-server-4.0.10-1.el7.x86_64.rpm mongodb-org-shell-4.0.10-1.el7.x86_64.rpm root@server002:/home/tools
$ scp -r mongodb-org-server-4.0.10-1.el7.x86_64.rpm mongodb-org-shell-4.0.10-1.el7.x86_64.rpm root@server003:/home/tools
```

#### 四、安装相关服务，如需更详细的说明和优化，请参考[官方安装文档](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-red-hat)（注意：每个节点都要安装，还有MongoDB所有源码都编译在一个文件里面，并没有安装目录）
```bash
$ cd /home/tools
$ sudo rpm -ivh mongodb-org-server-4.0.10-1.el7.x86_64.rpm         # 安装  MongoDB 服务进程
$ sudo rpm -ivh mongodb-org-shell-4.0.10-1.el7.x86_64.rpm          # 安装  Mongo Shell 工具（注意：如果不用可以不安装）
```

#### 五、修改[vi /etc/mongod.conf]配置（注意：每个节点都要修改）
```bash
systemLog:
  path: /var/log/mongodb/mongod.log            # 日志文件目录
  
storage:
  dbPath: /var/lib/mongo                       # 数据存放目录
  
net:
  port: 27017                                  # 端口
  bindIp: server001                            # 绑定地址，修改成本机IP或hostname
  
setParameter:
  enableLocalhostAuthBypass: false             # 本机 Mongo Shell 连接是否需要安全验证
  replWriterThreadCount: 1                     # 主从同步线程数，越大同步速度越快（建议不要超过32）  
  
replication:
  replSetName: myReplication                   # 复制集的名称（注意：同一个复制集里面，所有节点的复制集名称要相同） 
```

#### 六、启动服务（注意：每个节点都要启动）
```bash
$ sudo service mongod start                    # 启动服务
$ sudo service mongod stop                     # 停止服务
$ sudo service mongod restart                  # 新启服务
$ sudo chkconfig mongod on                     # 开启开机启动服务
$ sudo chkconfig mongod off                    # 关闭开机启动服务
```

#### 七、创建复制集，单主多从高可用（注意：顺便找一个节点执行即可）
```bash
# 连接到 Mongo Shell
$ mongo server001:27017
# 创建复制集，单主多从高可用
$ rs.initiate({
      // myReplication 就是我们配置文件里面配置的那个，复制集的名称      
      _id: "myReplication",
      // 复制集所有节点的信息（注意：id可以顺便取值，但不能相同）
      members: [
          {_id: 0,host: "server001:27017"},
          {_id: 1,host: "server002:27017"},
          {_id: 2,host: "server003:27017"}
     ]
  })
  
# 返回如下信息说明创建成功
{
	"ok" : 1,
	"operationTime" : Timestamp(1562411905, 1),
	"$clusterTime" : {
		"clusterTime" : Timestamp(1562411905, 1),
		"signature" : {
			"hash" : BinData(0,"AAAAAAAAAAAAAAAAAAAAAAAAAAA="),
			"keyId" : NumberLong(0)
		}
	}
}  
```

#### 八、复制集简单操作
```bash
$ mongo server001:27017        # 连接到 Mongo Shell
$ rs.status()                  # 查看复制集的详细信息（members 里面都是节点信息，stateStr=PRIMARY表示主节点，SECONDARY表示从节点）
```
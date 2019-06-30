#### 一、连接 Mongodb（注意：Mongodb Shell支持JavaScript语法，以下都是用JavaScript语法）
```bash
$ d:mongodb-win32-x86_64-2008plus-ssl-4.0.10/bin/mongo.exe # Windows连接本地的Mongodb
$ print('maomao')                                          # 在 Mongodb Shell 上打印一句话
$ use test                                                 # 使用 test 数据库
$ show collections                                         # 查看 test 数据库里面的所有集合
$ exit                                                     # 退出  Mongodb Shell
```

#### 二、Insert（插入数据）命令简单使用（注意：先使用命令行连接好 Mongodb 且选择好数据库）
```bash
# 使用 insert 命令往 test_user 集合里面写入单条数
$ db.test_user.insert({
    _id:"5",
    name:"dgfdggd"
  })

# 使用 insert 命令往 test_user 集合里面写多条数（注意：这种写入是要么都成功，要么都失败）
$ db.test_user.insert([
    {
        _id:"12",
        name:"fdfsfsd",
        hobby:["a","b","c"]
    },
    {
        _id:"13",
        name:"fdsfdsf",
        hobby:[
            ["g","t"],
            "a",
            "b",
            "c"
        ]
    }]) 

# 使用 insertOne 命令往 test_user 集合里面写一条数
$ db.test_user.insertOne(
    {
        _id:"1",
        name:"maomao"
    }) 

# 使用 insertOne 命令往 test_user 集合里面写多条数（注意：这种写入是要么都成功，要么都失败）
$ db.test_user.insertOne(
    {
        _id:"2",
        name:"wenjuan"
    },
    {
        _id:"3",
        name:"jiang"
    }) 

# 使用 insertMany 命令往 test_user 集合里面写多条数（注意：这种写入，是只会写入正确的文档，错误的文档将会失败）
$ db.test_user.insertMany(
    {
        _id:"2",
        name:"wenjuan"
    },
    {
        _id:"4",
        name:"tiantian"
    }) 
```

#### 三、Find（查询数据）命令简单使用（注意：先使用命令行连接好 Mongodb 且选择好数据库，默认只返回前20个数据）
```bash
# 查询 test_user 数据库里面 name 属性的值等于maomao的数据
$ db.test_user.find({
      name: "maomao"
  })
  
# 查询 test_user 数据库里面 name 属性的值等于fdfsfsd的数据且只返回name和hobby属性
# 注意：1表示返回，0不返回；除了和_id可以混用以外，其它是不能混用的（要么是全部是1，要么全部是0，不能两个都包含）
$ db.test_user.find(
    {
         name: "fdfsfsd"
    },{
         _id: 0,
        name: 1,
        hobby: 1
    })
    
# 查询 test_user 数据库里面 name 属性的值等于fdfsfsd的数据且只返回name和hobby属性
# 注意：1表示返回，0不返回；除了和_id可以混用以外，其它是不能混用的（要么是全部是1，要么全部是0，不能两个都包含）
$ db.test_user.find(
    {
         name: "fdfsfsd"
    },{
         _id: 0,
        name: 1,
        // 在结果里面再过滤只要数组元素大于a的所有元素
        hobby: {
            $elemMatch:{
                $gt:"a"
            }
        }
    })
    
# 查询 test_user 数据库里面 hobby 数组属性的元素有大于a的数据
$ db.test_user.find(
    {
         hobby: {
             $gt: "b"
        }
    },{
         _id: 0,
        name: 1,
        // 只显示上面匹配结果的hobby元素（上面写的是大于b的元素，那么这个就是只显示大于b的元素）
        "hobby.$": 1
    })             

# 查询 test_user 数据库里面 name 属性的值包含mao的数据（$options:"i"不区分大小写）（注意：这个是正则表达式匹配的一种，它兼容PCRE v8.41正则表达式库）
$ db.test_user.find(
    {
        name:{
            $regex:/mao/,
            $options:"i"
        }
    })
    
# 查询 test_user 数据库里面 name 属性的值以a开头或者以m开头的数据（注意：这个是正则表达式匹配的一种，它兼容PCRE v8.41正则表达式库）
$ db.test_user.find(
    {
        name:{
            $in:[/^a/,/^m/]
        }
    })

# 查询 test_user 数据库里面 age 属性的值大于20且小于35的数据
$ db.test_user.find(
    {
        age:{
            $elemMatch:{
                $gt:20,
                $lt:35
            }
        }
    })

# 查询 test_user 数据库里面 hobby 属性的值是数组且数组值同时包含"a"和"b"的数据
$ db.test_user.find(
    {
        hobby:{
            $all:["a","b"]
        }
    })

# 查询 test_user 数据库里面 hobby 属性的值是数组且数组值同时包含"g"和"t"的数据（注意：这个是嵌套数组数据查询）
$ db.test_user.find(
    {
        hobby:{
            $all:[["g","t"]]
        }
    })

# 查询 test_user 数据库里面 age 属性的值大于20且小于35的和值大于10且小于15的所有数据
$ db.test_user.find(
    {
        age:{
            $all:[
                {$elemMatch:{$gt:20,$lt:35}},
                {$elemMatch:{$gt:10,$lt:15}}
            ]
        }
    })
```

#### 四、使用游标操控数据简单使用，用完游标后要记得关闭（注意：先使用命令行连接好 Mongodb 且选择好数据库）
```bash
# 查询 test_user 数据库里面 name 属性的值包含mao的数据（$options:"i"不区分大小写）（注意：这个是正则表达式匹配的一种，它兼容PCRE v8.41正则表达式库）
# 游标函数的使用（注意：skip始终会在limit函数之前执行，sort排序始终会在skip和limit之前执行，所以skip加limit可以做分页）
$ var res = db.test_user.find(
    {
        name:{
            $regex:/mao/,
            $options:"i"
        }
    })
  // 只返回第1行数据  
  //res.limit(1)
  // 返回数据数量  （false表示忽略过滤函数（永远统计所有的数据，limit无效））
  //res.limit(1).count(false)
  // 跳过第1行数据，返回其它所有的数据
  //res.skip(1)
  // 遍历数据
  while(res.hasNext()){
      printjson(res.next())
  }  
  // 遍历数据
  res.forEach((val)=>{
      printjson(val)
  })
  //按照name和age排序 -1表示逆向排列，1表示正向排列
  res.sort({
      name: -1,
      age: 1
  })
  // 获取游标第0个位置的数据
  res[0]
  res.close()
  // 不自动关闭游标（前提是不调用close()函数）
  //res.noCursorTimeout()
```
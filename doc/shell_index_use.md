#### Index（索引）简单说明
```bash
1，联合索引只能加速前置查询（比如：A，B，C三个索引，使用A或AB可以加速。使用B或C或BC则不能加速）
```
#### 创建基本 Index（索引），每个文档的主键默认会有一个单键索引（注意：先使用命令行连接好 Mongodb 且选择好数据库）
#### 注意：修改索引一般是先删除，再创建
```bash
# 先插入2条数据，以便使用索引测试
$ db.test_index.insertMany([
      {
         user:{
             name: "tiantian",
             age: 12
         },
         orderNo: "dfdsfsdfsd",
         desc: "士dfs负少",
         price: 12.8,
         primary: "asdsadsada",
         hobby: ["a","b","c"],
         createDate: Date.now()
      },
      {
         user:{
             name: "jiangjiang",
             age: 50
         },
         price: 30.1,
         desc: "士大sdfsd少",
         orderNo: "sdfdsfsdf4545",
         primary: "gfgfgfgf",
         hobby: ["r","t","y"],
         createDate: Date.now()
      } 
  ])
  
# 查询 test_index 集合里面的所有索引
$ db.test_index.getIndexes()
  

# 创建一个单键索引（在这里是在 price 字段上创建单键索引，1 表示索引的键值按照从小到大的顺序排序，-1 表示索引的键值按照从大到小的顺序排序）
$ db.test_index.createIndex(
    {
         price: 1
    },
    {   // 当前数据没有索引字段，是否不创建索引（建议使用true，减少索引的数据）
        sparse: true
    })

# 创建一个唯一索引（在这里是在 primary 字段上创建唯一索引，1 表示索引的键值按照从小到大的顺序排序，-1 表示索引的键值按照从大到小的顺序排序）
# 注意：数据库会允许一条没有唯一索引字段的数据，如果 sparse: true 那就是2条
$ db.test_index.createIndex(
    {
        primary: 1
    },
    {   // 唯一限制
        unique: true,
        // 当前数据没有索引字段，是否不创建索引（建议使用true，减少索引的数据）
        sparse: true
    })

# 创建一个联合索引（在这里是在 desc 和 orderNo 字段上创建联合索引，1 表示索引的键值按照从小到大的顺序排序，-1 表示索引的键值按照从大到小的顺序排序）
$ db.test_index.createIndex(
    {
        desc: 1,
        orderNo: -1
    },
    {
        // 当前数据没有索引字段（联合索引是全部字段），是否不创建索引（建议使用true，减少索引的数据）
        sparse: true
    })

# 创建一个多键索引（注意：多键索引只能在数组数据类型上（数组数据类型默认就是多键索引）
# 在这里是在 hobby 字段上创建多键索引，1 表示索引的键值按照从小到大的顺序排序，-1 表示索引的键值按照从大到小的顺序排序）
$ db.test_index.createIndex(
    {
        hobby: 1
    },
    {
        // 当前数据没有索引字段，是否不创建索引（建议使用true，减少索引的数据）
        sparse: true
    })
```

#### 创建带过期时间的 Index（索引），过期后数据也将被删除（注意：只能在时间类型字段上使用。1 表示索引的键值按照从小到大的顺序排序，-1 表示索引的键值按照从大到小的顺序排序）
```bash
$ db.test_index.createIndex(
    {
        createDate: 1
    },
    {
        // 20秒后自动删除数据
        expireAfterSeconds: 20,
        // 当前数据没有索引字段，是否不创建索引（建议使用true，减少索引的数据）
        sparse: true
    }) 
```

#### 删除 Index（索引）
```bash
# 使用索引名称的方式删除索引（hobby_1是索引的名称，索引名称可使用： db.'集合名称'.getIndexes() 命令查看）
$ db.test_index.dropIndex("hobby_1")

# 使用索引定义的方式删除索引
$ db.test_index.dropIndex(
    {
        desc: 1,
        orderNo: -1
    },
    {
        // 当前数据没有索引字段，是否不创建索引（建议使用true，减少索引的数据）
        sparse: true
    })
```
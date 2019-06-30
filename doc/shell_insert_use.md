#### Insert（插入数据）命令简单使用（注意：先使用命令行连接好 Mongodb 且选择好数据库）
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
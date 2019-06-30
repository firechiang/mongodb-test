#### Remove（删除数据）命令简单使用（注意：先使用命令行连接好 Mongodb 且选择好数据库）
```bash
# 删除_id等于6的数据
$ db.test_user.remove({
      _id: "6"
  })
  
  
# 查询匹配数据，删除其中一条
$ db.test_user.remove(
    {    
        name:{
           $elemMatch:{
              $eq: "sadasda" 
         } 
      }
    },
    {   // 只删除匹配数据中的一条
        justOne: true
   })
```
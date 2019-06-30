#### Aggregate（聚合操作，流处理）简单说明
```bash
1，$<field>           # 使用$来获取属性，比如我们要获取当前文档的name属性，可以使用 $name获取；要获取嵌套对象，可以使用 $info.name
2，$$<variable>       # 使用$$来获取系统变量，比如我们要取当前操作文档，可以使用 $$CURRENT 获取；要获取当前文档的属性，可以使用  $$CURRENT.name
3，$literal:<value>   # 常量表达式
```

#### Aggregate（聚合操作，流处理）简单使用（注意：先使用命令行连接好 Mongodb 且选择好数据库）
```bash
# 先插入2条数据，以便测试聚合操作
$ db.test_order.insertMany([
      {
         user:{
             name: "tiantian",
             age: 12
         },
         price: 12.8,
         hobby: ["a","b","c"]
      },
      {
         user:{
             name: "jiangjiang",
             age: 50
         },
         price: 30.1,
         hobby: ["r","t","y"]
      } 
  ])
  
# 再先插入2条数据，以便测试聚合操作
$ db.test_dept.insertMany([
      {
         name: "jiangjiang",
         desc: "撒上的拉萨",
         createTime: Date.now() 
      },
      {
         name: "fdgfdg",
         desc: "士大夫是非得失",
         createTime: Date.now()
      },
  ])
  
# 查询并转换数据 ，返回游标集合（返回的结果和我们的find()函数返回值一样）
$ db.test_order.aggregate([
      {
          $project: {
             // id不显示
             _id: 0,
             // 单价显示
             price: 1,
             // 将内嵌对象user的name属性的值赋给 clientName属性
             clientName: "$user.name",
             // 将内嵌对象user的age属性的值添加到 nameArray属性数组
             nameArray: ["$user.age"]
         }
      }
  ])
  
# 查询并过滤数据 ，返回游标集合（返回的结果和我们的find()函数返回值一样）
$ db.test_order.aggregate([
      {
          $match: {
             // 我们只要内嵌对象user的name属性的值等于wenjuan的数据
             "user.name": "wenjuan"
         }
      }
  ])  
  
# 查询并过滤数据，复杂过滤（注意：这个里面可以写任何的查询过滤语句），返回游标集合（返回的结果和我们的find()函数返回值一样）
$ db.test_order.aggregate([
      {
         $match: {
             // 我们要内嵌对象user的name属性的值等于wenjuan的数据 或者 匹配第2个条件的数据
             $or: [
                     
               { 
                   "user.name": "wenjuan"
               },
               {
                   "user.age": {
                       $gt: 10,
                       $lt: 20
                 }
              }
            ]
         }
      }
  ])
  
  
# 聚合操作联合使用（流处理）（返回的结果和我们的find()函数返回值一样）
$ db.test_order.aggregate([
      // 过滤数据 （注意：这个里面可以写任何的查询过滤语句）
      {
          $match: {
             // 我们只要内嵌对象user的name属性的值等于jiangjiang的数据
             "user.name": "jiangjiang"
         }
      },
      // 转换数据
      {
          $project: {
             // id不显示
             _id: 0,
             // 将每条数据的price乘以10，结果起个别名叫 multiplyPrice
             multiplyPrice: {
                 $multiply: ["$price",10]
             
            }
         }   
      },
      // 跳过第0条数据
      {
          $skip: 0
      },
      // 只需要前10条数据
      {
          $limit: 10
      
     },
     // 展开 hobby 数组元素（就是将数组里面的每一个元素，和当前数据做join(连接)，最后的结果是：如果一条数据里面数组有2个元素，那么最后会变成2条数据）
     {
         $unwind: {
            path: "$hobby",
            // 最后的结果数据添加一个 hIndex 字段，显示数组元素的下标
            includeArrayIndex: "hIndex",
            // 是否不过滤没有数组的数据，默认是false（过滤）
            preserveNullAndEmptyArrays: true
         }
     },
     // 排序 1 表示从小到大，-1 表示从大到小
     {
         $sort: {
             // 内嵌对象user的age属性从小到大排序
             "user.age": 1,
              hIndex: -1
         }
     },
     // 关联其它集合，相当于join（注意：如果关联字段是个数组它会关联到所有相等的数据，相当于in操作。建议先使用 $unwind 展开数组元素，再作join）
     {
          $lookup: {
             // 当前集合的关联字段
             localField: "user.name",
             // 要关联集合的名称
             from: "test_dept",
             // 要关联集合的关联字段
             foreignField: "name",
             // 最后结果的别名
             as: "deptName"
         }
      },
      // 复杂关联其它集合（子查询关联），相当于join（注意：我们没有写关联字段，而是每一条数据，去查一次关联表）
      {
          $lookup: {
             // 要关联的集合的名称
             from: "test_dept",
             // 要关联集合的子查询（就是我只要，要关联集合的某些数据）
             pipeline: [
                 {   
                     // $$orderUserName是获取上面定义变量的值（注意：这下面可以写任何的查询语句）
                     $match:{
                         name: "jiangjiang"
                     }
                 }
             ],
             // 最后结果的别名
             as: "deptName1"
         }
      },
      // 更加复杂关联其它集合（子查询关联），相当于join（注意：我们没有写关联字段，而是根据每一条数据的某个值，去查一次关联表）
      {
          $lookup: {
             // 定义变量 orderPrice 它的值是，当前集合 price 属性的值
             let: {
                 orderPrice: "$price"
             },
             // 要关联的集合的名称
             from: "test_dept",
             // 要关联集合的子查询（就是我只要，要关联集合的某些数据）
             pipeline: [
                 {   
                     //（注意：这下面可以写任何的查询语句）
                     $match:{
                         // $expr 是为了支持下面获取自定义参数的
                         $expr: {
                             // 2个条件得同时成立
                             $and: [
                                 // $name 是当前要关联的集合 test_dept 里面的属性值
                                 {$eq: ["$name","jiangjiang"]},
                                 // $$orderPrice是获取上面定义变量的值
                                 {$gt: ["$$orderPrice",20]}
                             
                             ]
                         }
                     }
                 }
             ],
             // 最后结果的别名
             as: "deptName1"
         }
      },
      // 分组再聚合
      {
          $group: {
              // 不分组但使用聚合函数（其实就是把所有的数据分成一组）
              //_id: null,
              // 按照 price 分组
              _id: "$price",
              // 按照内嵌对象user的name属性进行分组
              //_id: "$user.name",
              // 求 price的和，最后的结果起个别名叫 totalPrice
              totalPrice: {
                  $sum: "$price"
              },
              // 将每个price乘以10再求和，最后的结果起个别名叫 totalPrice1
              totalPrice1: {
                  $sum: {
                      $multiply:[
                          "$price",10
                      ]
                  }
              },
              // 求price的平均值，最后的结果起个别名叫 avgPrice
              avgPrice: {
                  $avg: "$price"
              },
              // 求数据的总数量（每一条数据是1），最后的结果起个别名叫 count
              count: {
                  $sum: 1
              },
              // 求price的最大值，最后的结果起个别名叫 maxPrice
              maxPrice: {
                  $max: "$price"
              },
              // 求price的最小值，最后的结果起个别名叫 minPrice
              minPrice: {
                  $min: "$price"
              },
              // 将每个price乘以10再求最大值，最后的结果起个别名叫 maxPrice1
              maxPrice1: {
                  $max: {
                      $multiply:[
                          "$price",10
                      ]
                  }
              },
          }
      },
      // 最后将结果输出到新的集合 test_out（注意：每次输出会删除集合原有的数据）
      {
          $out: "test_out"
      }
  ],
  {   // 在使用聚合操作（流计算）的过程中是否使用磁盘存储临时数据（注意：默认每个聚合操作（流计算）过程使用的内存上限是100MB）
      allowDiskUse: true
  
  })
```
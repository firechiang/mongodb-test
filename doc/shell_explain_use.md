#### Explain（执行计划）简要说明
```bash
1，所有支持的函数：aggregate(),count(),distinct(),find(),group(),remove(),update()
```
#### Explain（执行计划）简单使用（注意：先使用命令行连接好 Mongodb 且选择好数据库）
```bash
# 查询find()函数的执行计划
$ db.test_index.explain().find({
    price: 30
})
```
#### Explain（执行计划）细节说明
```bash
{
    "queryPlanner" : {
	    "plannerVersion" : 1,
	    "namespace" : "test.test_index",
	    "indexFilterSet" : false,
	    "parsedQuery" : {
	        "price" : {
	            "$eq" : 30
	        }
	    },
	    // MongoDB选择出来的可能最有效的执行方法
	    "winningPlan" : {
	        // "FETCH"=通过索引检索数据，"PROJECTION"=直接返回索引的键就是数据的值（效率最高），"SORT"=内存排序
	        "stage" : "FETCH",
	        "inputStage" : {
	            // "IXSCAN"=使用索引，"COLLSCAN"=全表扫描
	            "stage" : "IXSCAN",
	            "keyPattern" : {
	                "price" : 1
	            },
	            "indexName" : "price_1",
	            "isMultiKey" : false,
	            "multiKeyPaths" : {
	                "price" : [ ]
	            },
	            "isUnique" : false,
	            "isSparse" : false,
	            "isPartial" : false,
	            "indexVersion" : 2,
	            "direction" : "forward",
	            "indexBounds" : {
	                "price" : [
	                    "[30.0, 30.0]"
	                ]
	            }
	        }
	    },
	    "rejectedPlans" : [ ]
    },
	"serverInfo" : {
	    "host" : "DESKTOP-CDRMIEP",
	    "port" : 27017,
	    "version" : "4.0.10",
	    "gitVersion" : "c389e7f69f637f7a1ac3cc9fae843b635f20b766"
	},
    "ok" : 1
}
```
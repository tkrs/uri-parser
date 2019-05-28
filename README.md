# uri-parser
URI parser for Scala

```
[info] Benchmark                                              Mode  Cnt        Score        Error   Units
[info] Bench.javaURIParser                                   thrpt   20  1053348.894 ±  67542.992   ops/s
[info] Bench.javaURIParser:·gc.alloc.rate                    thrpt   20      358.670 ±     22.996  MB/sec
[info] Bench.javaURIParser:·gc.alloc.rate.norm               thrpt   20      536.001 ±      0.003    B/op
[info] Bench.javaURIParser:·gc.churn.G1_Eden_Space           thrpt   20      363.005 ±    111.342  MB/sec
[info] Bench.javaURIParser:·gc.churn.G1_Eden_Space.norm      thrpt   20      545.986 ±    173.846    B/op
[info] Bench.javaURIParser:·gc.churn.G1_Old_Gen              thrpt   20        0.002 ±      0.006  MB/sec
[info] Bench.javaURIParser:·gc.churn.G1_Old_Gen.norm         thrpt   20        0.003 ±      0.008    B/op
[info] Bench.javaURIParser:·gc.churn.G1_Survivor_Space       thrpt   20        0.067 ±      0.259  MB/sec
[info] Bench.javaURIParser:·gc.churn.G1_Survivor_Space.norm  thrpt   20        0.104 ±      0.403    B/op
[info] Bench.javaURIParser:·gc.count                         thrpt   20       28.000               counts
[info] Bench.javaURIParser:·gc.time                          thrpt   20       63.000                   ms
[info] Bench.javaURLParser                                   thrpt   20  2768388.290 ± 117564.185   ops/s
[info] Bench.javaURLParser:·gc.alloc.rate                    thrpt   20     1280.218 ±     54.292  MB/sec
[info] Bench.javaURLParser:·gc.alloc.rate.norm               thrpt   20      728.001 ±      0.001    B/op
[info] Bench.javaURLParser:·gc.churn.G1_Eden_Space           thrpt   20     1272.482 ±    155.867  MB/sec
[info] Bench.javaURLParser:·gc.churn.G1_Eden_Space.norm      thrpt   20      724.435 ±     88.801    B/op
[info] Bench.javaURLParser:·gc.churn.G1_Old_Gen              thrpt   20        0.001 ±      0.001  MB/sec
[info] Bench.javaURLParser:·gc.churn.G1_Old_Gen.norm         thrpt   20        0.001 ±      0.001    B/op
[info] Bench.javaURLParser:·gc.count                         thrpt   20       62.000               counts
[info] Bench.javaURLParser:·gc.time                          thrpt   20      115.000                   ms
[info] Bench.myParser                                        thrpt   20    78626.674 ±   6872.265   ops/s
[info] Bench.myParser:·gc.alloc.rate                         thrpt   20      334.457 ±     29.228  MB/sec
[info] Bench.myParser:·gc.alloc.rate.norm                    thrpt   20     6696.019 ±      0.037    B/op
[info] Bench.myParser:·gc.churn.G1_Eden_Space                thrpt   20      341.823 ±    101.232  MB/sec
[info] Bench.myParser:·gc.churn.G1_Eden_Space.norm           thrpt   20     6793.529 ±   1652.902    B/op
[info] Bench.myParser:·gc.churn.G1_Old_Gen                   thrpt   20        0.009 ±      0.020  MB/sec
[info] Bench.myParser:·gc.churn.G1_Old_Gen.norm              thrpt   20        0.191 ±      0.409    B/op
[info] Bench.myParser:·gc.churn.G1_Survivor_Space            thrpt   20        0.133 ±      0.356  MB/sec
[info] Bench.myParser:·gc.churn.G1_Survivor_Space.norm       thrpt   20        2.411 ±      6.471    B/op
[info] Bench.myParser:·gc.count                              thrpt   20       24.000               counts
[info] Bench.myParser:·gc.time                               thrpt   20       63.000                   ms
```

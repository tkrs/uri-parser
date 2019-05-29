# uri-parser
URI parser for Scala

```
[info] Benchmark                                          Mode  Cnt        Score       Error   Units
[info] Bench.javaURIParser                               thrpt   20  1356142.965 ± 30613.456   ops/s
[info] Bench.javaURIParser:·gc.alloc.rate                thrpt   20      630.071 ±    14.219  MB/sec
[info] Bench.javaURIParser:·gc.alloc.rate.norm           thrpt   20      536.000 ±     0.001    B/op
[info] Bench.javaURIParser:·gc.churn.G1_Eden_Space       thrpt   20      629.518 ±    26.690  MB/sec
[info] Bench.javaURIParser:·gc.churn.G1_Eden_Space.norm  thrpt   20      535.427 ±    16.789    B/op
[info] Bench.javaURIParser:·gc.churn.G1_Old_Gen          thrpt   20        0.001 ±     0.001  MB/sec
[info] Bench.javaURIParser:·gc.churn.G1_Old_Gen.norm     thrpt   20        0.001 ±     0.001    B/op
[info] Bench.javaURIParser:·gc.count                     thrpt   20      234.000              counts
[info] Bench.javaURIParser:·gc.time                      thrpt   20      137.000                  ms
[info] Bench.javaURLParser                               thrpt   20  4184398.925 ± 61652.943   ops/s
[info] Bench.javaURLParser:·gc.alloc.rate                thrpt   20     2640.494 ±    38.921  MB/sec
[info] Bench.javaURLParser:·gc.alloc.rate.norm           thrpt   20      728.000 ±     0.001    B/op
[info] Bench.javaURLParser:·gc.churn.G1_Eden_Space       thrpt   20     2641.293 ±    55.798  MB/sec
[info] Bench.javaURLParser:·gc.churn.G1_Eden_Space.norm  thrpt   20      728.168 ±     7.781    B/op
[info] Bench.javaURLParser:·gc.churn.G1_Old_Gen          thrpt   20        0.005 ±     0.001  MB/sec
[info] Bench.javaURLParser:·gc.churn.G1_Old_Gen.norm     thrpt   20        0.001 ±     0.001    B/op
[info] Bench.javaURLParser:·gc.count                     thrpt   20      679.000              counts
[info] Bench.javaURLParser:·gc.time                      thrpt   20      410.000                  ms
[info] Bench.myParser                                    thrpt   20   137788.888 ±  3184.260   ops/s
[info] Bench.myParser:·gc.alloc.rate                     thrpt   20      382.195 ±     8.827  MB/sec
[info] Bench.myParser:·gc.alloc.rate.norm                thrpt   20     3200.002 ±     0.004    B/op
[info] Bench.myParser:·gc.churn.G1_Eden_Space            thrpt   20      384.688 ±    17.114  MB/sec
[info] Bench.myParser:·gc.churn.G1_Eden_Space.norm       thrpt   20     3221.419 ±   130.548    B/op
[info] Bench.myParser:·gc.churn.G1_Old_Gen               thrpt   20        0.002 ±     0.004  MB/sec
[info] Bench.myParser:·gc.churn.G1_Old_Gen.norm          thrpt   20        0.016 ±     0.030    B/op
[info] Bench.myParser:·gc.churn.G1_Survivor_Space        thrpt   20        0.018 ±     0.071  MB/sec
[info] Bench.myParser:·gc.churn.G1_Survivor_Space.norm   thrpt   20        0.156 ±     0.607    B/op
[info] Bench.myParser:·gc.count                          thrpt   20      143.000              counts
[info] Bench.myParser:·gc.time                           thrpt   20       87.000                  ms
```

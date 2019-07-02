### 1. 安装Redis
#### 1.1 编译安装
```
wget http://download.redis.io/releases/redis-3.2.12.tar.gz
tar -zvxf redis-3.2.12.tar.gz
// 安装gcc
yum install gcc
// 编译
cd redis-3.2.12
make
// 服务端启动Redis
./src/redis-server redis.conf
// 客户端连接
./src/redis-cli
./src/redis-cli -h 172.23.23.230 -p 6379
```
#### 1.2 修改配置文件
```
bind 127.0.0.1          //绑定ip，不绑定则注销掉即可
protected-mode yes      
port 6379               //修改端口
daemonize yes           //是否后台启动
databases 16            //数据库数
save 900 1              //SNAPSHOTTING参数
save 300 10
save 60 10000
requirepass foobared    //密码设置
requirepass 123456      // 设置密码为123456
appendonly no           //是否开启aof
```
#### 1.3 报错解决
```$xslt
# You need tcl 8.5 or newer in order to run the Redis test
yum install tcl
```
### 2. 使用
#### 2.1 设置密码后连接
```$xslt
redis-cli -h 172.23.23.230 -p 6379 -a 123456

或者
redis-cli -h 172.23.23.230 -p 6379
auth 123456
```
#### 2.2 性能测试
```
./src/redis-benchmark -h 172.23.23.230 -p 6379 -q
```
### 2.3 数据类型及操作
Redis所有单个操作都是原子操作，多个操作支持事务。  
Redis中key失效策略：
- 被动触发：GET时会检查key是否失效。
- 主动触发：后台每秒10次（redis.conf中hz参数）定时任务，随机选择100个设置了过期时间的key，对过期的key进行失效。
##### 2.3.1 String
```
SET key value
GET key
MSET key value [key value ...] // 设置多个key、value
MGET key [key ...]
SETNX key value // 只有在key不存在时，才设置
SETEX key seconds value // 设置key，同时设置生存时间

INCR key
DECR key
INCRBY key increment
DECRBY key decrement
```
##### 2.3.2 Hash
```
HSET key field value
HGET key field
HMGET key field [field ...]
HGETALL key
HDEL key filed [field ...]
HEXISTS key field
HINCRBY key field increment
HKEYS key // 返回哈希表key中的所有域
HLEN key // 返回哈希表key中域的数量
HVALS key
HMSET key field value [field value ...]
```
##### 2.3.3 List
```
LPUSH key value [value ...]
LPUSHX key value
RPUSH key value [value ...]
RPUSHX key value
LPOP key
RPOP key
LRANGE key start stop // -1表示列表最后一个元素，-2表示列表倒数第二个元素
LREM key count value
LSET key index value
LINDEX key index
LINSERT key BEFORE|AFTER pivot value
LLEN key
LTRIM key start stop
RPOPLPUSH source destination
BLPOP key timeout // 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
BRPOP key timeout //  如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
BRPOPLPUSH source destination timeout // 阻塞
```
##### 2.3.4 Set
```
SADD key member [member ...]
SCARD key
SDIFF key [key ...] // 差集
SDIFFSTORE destination key [key ...]
SINTER key [key ...] // 交集
SINTER destination key [key ...]
SISMEMBER key member 
SMEMBERS key
SMOVE source destination member
SPOP key [count]
SRANDMEMBER key [count]
SREM key member [member ...]
SUNION key [key ...]
SUNIONSTORE destination key [key ...]
```
##### 2.3.5 SortedSort
```
ZADD key score member [[score member]...]
ZCARD key
ZCOUNT key min max
ZRANGE key start stop [WITHSCORES]
ZREVRANGE key start stop [WITHSCORES]
ZRANK key member
ZREVRANK key member
ZREM key member [member ...]
ZREMRANGEBYRANK key start stop
ZREMRANGEBYSCORE key min max
```
##### 2.3.6 Key
```
DEL key [key ...]
EXISTS key
EXPIRE key seconds
EXPIREAT key timestamp
KEYS pattern
MIGRATE host port key destination-db timeout [COPY][REPLACE]
MOVE key db
PERSIST key
RANDOMKEY
RENAME key newkey
TTL key
PTTL key
TYPE key
```
##### 2.3.7 系统相关命令
```
BGREWRITEAOF
BGSAVE
CLIENT KILL host:port
CLIENT LIST
CONFIG GET parameter
CONFIG RESETSTAT
CONFIG REWRITE
CONFIG SET parameter value
SELECT index
DBSIZE
DEBUG OBJECT key
FLUSHALL
FLUSHDB
INFO [section]
LASTSAVE
MONITOR
SHUTDOWN [SAVE|NOSAVE]
```
##### 2.3.8 事务
```
WATCH
UNWATCH
MULTI
EXEC
DISCARD
```
#### 3. Redis高级
##### 3.1 单线程模型
**Redis单线程基本模型**  
Redis客户端对服务端的每次调用都经历了*发送命令，执行命令，返回结果*三个过程。其中执行命令阶段，所有到达服务端的命令不会立刻执行，而是会进入一个队列，然后逐个被执行，多个客户端发送的命令的执行顺序是不确定的。但是不会有两条命令被同时执行，不会产生并发问题。  

##### Appendix I：5种I/O模型
参见《UNIX网络编程：套接字联网API（第3版）》C6.2，电子书P136-P141
##### Appendix II：中断与轮询
**中断**  
8051有3个内部中断源T0、T1、串行口，2个外部中断源INT0、INT1。  

中断号|中断源|中断向量（中断入口地址）|
|:---:|:---:|:---:|
0|INT0|0003H|
1|T0|000BH|
2|INT1|0013H|
3|T1|001BH|
4|串行口中断|0023H|

中断向量到下一个中断向量之间只有8B的存储空间，不是用来存储中断服务子程序的，是用来存储中断服务子程序的入口地址。  
```
// 中断服务子程序（中断函数）
void 函数名() interrupt m{} // 如响应定时器中断，每秒指示灯亮一次。
// m为中断源的编号，有五个中断源（0、1、2、3、4），中断编号会告诉编译器中断程序的入口地址。
```
**轮询**  
polling，以一定频率查询  

阻塞等待时，线程挂起，不消耗CPU。
忙等待消耗CPU。

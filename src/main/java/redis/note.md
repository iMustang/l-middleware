## 1. 安装Redis
### 1.1 编译安装
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
### 1.2 修改配置文件
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
### 1.3 报错解决
```$xslt
# You need tcl 8.5 or newer in order to run the Redis test
yum install tcl
```
## 2. 使用
### 2.1 设置密码后连接
```$xslt
redis-cli -h 172.23.23.230 -p 6379 -a 123456

或者
redis-cli -h 172.23.23.230 -p 6379
auth 123456
```
### 2.2 性能测试
```
./src/redis-benchmark -h 172.23.23.230 -p 6379 -q
```
### 2.3 数据类型及操作
Redis所有单个操作都是原子操作，多个操作支持事务。  
Redis中key失效策略：
- 被动触发：GET时会检查key是否失效。
- 主动触发：后台每秒10次（redis.conf中hz参数）定时任务，随机选择100个设置了过期时间的key，对过期的key进行失效。
#### 2.3.1 String
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
#### 2.3.2 Hash
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
#### 2.3.3 List
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
#### 2.3.4 Set
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
#### 2.3.5 SortedSort
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
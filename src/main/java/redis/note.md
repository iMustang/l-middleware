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
参考文献：https://draveness.me/redis-io-multiplexing  

**I/O发起系统调用的原因**  
进程想要获取磁盘中的数据，能和磁盘打交道的只能是内核。进程通知内核要磁盘中的数据，此过程就是系统调用。  
阻塞： 阻塞的系统调用是指，当进行系统调用时，除非出错（被信号打断也视为出错），进程将会一直陷入内核态直到调用完成。  
非阻塞：非阻塞的系统调用是指无论I/O操作成功与否，调用都会立即返回。  
Linux通过fcntl系统调用，设置为非阻塞的
```
//将文件描述符设置为非阻塞的
int setnonblocking(int fd)
{
    int old_option = fcntl(fd,F_GETFL); //获得原属性
    int new_option = old_option | O_NONBLOCK; //新加非阻塞属性
    fcntl(fd,F_SETFL,new_option); //设置新属性给文件描述符fd
    return old_option;
}
```
**一次I/O完成的步骤**
![I/O完成的步骤](https://images2015.cnblogs.com/blog/830267/201601/830267-20160109235301465-2013686286.png)

进程需要对磁盘中的数据进行操作，会向内核发起一个系统调用，然后此进程，将会被切换出去（会被挂起或者进入睡眠状态，也叫不可中断的睡眠），只有等到系统调用的结果完成后，进程会被唤醒，继续接下来的操作。  
从系统调用的开始到系统调用结束经过的步骤：
1. 进程向内核发起一个系统调用
2. 内核接收到系统调用，知道是对文件的请求，于是告诉磁盘，把文件读取出来
3. 磁盘接收到来自内核的命令后，把文件载入到内核的内存空间里面
4. 内核的内存空间接收到数据之后，把数据copy到用户进程的内存空间
5. 进程内存空间得到数据后，给内核发送通知
6. 内核把接收到的通知回复给进程，此过程为唤醒进程，然后进程得到数据，进行下一步操作

**5种I/O模型**
1. 阻塞I/O  
![阻塞I/O](https://images2015.cnblogs.com/blog/830267/201601/830267-20160109235301934-1714715378.png)
进程调用recvfrom，从用户态转到内核态，直到数据准备好且拷贝到应用程序缓冲区或者出错(最常见的错误是信号中断)
才会返回。我们所说的进程阻塞的整段时间是从调用recvfrom开始到数据拷贝完成的这段时间。当进程返回成功时，应用程序就开始处理数据了。
2. 非阻塞I/O
![非阻塞I/O](https://images2015.cnblogs.com/blog/830267/201601/830267-20160109235302653-1288371123.png)
当设置一个套接口为非阻塞方式时，即通知内核：当请求的I/O操作非得让进程睡眠不能完成时，不要让进程睡眠，而应返回一个错误。应用程序像这样对一个非阻塞描述符调用recvfrom
时，我们称此过程为轮询。当系统调用期待的操作没有发生时，内核立即返回一个错误。应用程序不断地查询内核，看某操作是否准备好，占用CPU，对CPU是极大的浪费。当操作准备好（数据报准备好）时，将数据报拷贝到应用缓冲区这一段时间依旧是阻塞的。
3. I/O多路复用
![I/O多路复用](https://img.draveness.me/2016-11-26-I:O-Multiplexing-Model.png-1000width)
应用程序通过I/O复用函数向内核注册一组事件，内核通过I/O复用函数把其中就绪的事件通知给应用程序。I/O复用本身阻塞的，他们能提高程序运行的效率的原因在于他们具有同时监听多个I/O事件的能力。常用的I/O复用函数是select
/poll。以select为例，阻塞于select调用，等待数据报套接口可读。当select返回可读调用时，调用recvfrom将数据拷贝到应用程序缓冲区。
4. 信号驱动I/O
![信号驱动I/O](https://images2015.cnblogs.com/blog/830267/201601/830267-20160109235304762-1813381006.png)
通过系统调用注册信号处理程序（回调函数），此系统调用立即返回，进程继续工作。当数据报准备好之后，会为该进程生成一个SIGIO信号。随即可以在信号处理函数中调用recvfrom来处理数据，并通知主程序体数据已经准备好被处理了。
5. 异步I/O

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

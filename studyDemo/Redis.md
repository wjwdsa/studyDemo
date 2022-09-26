一、Redis基础命令
1. 启动服务
./redis-server /url/redis-conf  #redis指定配置文件启动的命令
./redis-server & #以后台的方式启动
2. 启动客户端
#-h ip -p：端口号；-a：密码
redis -h -p –a
3. 关闭服务、退出客户端命令操作界面
1、在redis客户客户端界面先shutdown关闭服务，shutdown作用将数据同步保存到磁盘，然后关闭服务
2、exit退出界面
3、直接ctrl+c退出界面
4、quit退出界面
5、kill -9 线程号。 查看 ps -ef |grep redis-server
4. 压力测试
#进入到redis安装位置
redis-benchmark -h 127.0.0.1 -p 6379 -c 50 -n 100000 
#如果不行就执行,因为这个不是Linux系统自带的命令
./redis-benchmark -h localhost -p 6379 -c 100 -n 100000
5. 查看版本
查看redis的版本有两种方式：
1、redis-server --version 和 redis-server -v
2、redis-cli --version 和 redis-cli -v
严格上说：通过　redis-cli 得到的结果应该是redis-cli 的版本，
但是 redis-cli和redis-server一般都是从同一套源码编译出的。所以应该是一样的。
6. 其他
ping：测试连接，成功返回pong
auth password：连接redis的密码。
select index：选择数据库，redis默认的数据库是0号数据库，系统默认共16个数据库。
dbsize：查看当前数据库大小，或者说有多少个key。
save：将数据同步保存到磁盘
bgsave：将数据异步保存到磁盘
lastsave：返回上次成功将数据保存到磁盘的Unix时戳
info：提供服务器的信息和统计
config get：获取配置文件信息（常用）
config set：动态地调整 Redis 服务器的配置(configuration)而无须重启，可以修改的配置参数可以使用命令 CONFIG GET * 来列出（常用）
config rewrite：Redis 服务器时所指定的 redis.conf 文件进行改写
flushdb：清除当前数据库的所有数据。谨慎使用
flushall：清空所有数据库的所有数据，并且还会删除掉持久化文件rdb里面的内容（flushdb命令不会）。慎之又慎
del key [key...]：万能删除key。不管你什么类型的key都可以删除掉。
二、Redis操作key的一些命令
randomkey：返回当前数据库里面的一个随机key
keys pattern：查询当前数据库的key。pattern：*、？[]
示列：
127.0.0.1：6379> keys *
127.0.0.1：6379> keys *t #这个就比较全面了。所以也是常用的
127.0.0.1：6379> keys ?? #可以全部通配，但是个数一定要一样。
127.0.0.1：6379> keys l[1eaw] #只能通配一个
type key：返回key存储的类型，返回其类型，不存在返回none。
exists key [key...]：判断某个key是否存在，返回存在的个数。
del key [key...]：删除key,返回存在的个数。
rename key newkey：成功返回ok，注意如果newkey存在了，那么就会把key的对应的值给覆盖过去，换句话说删除掉了newkey。如果key不存在会报错，(error) ERR syntax error。
renamenx key newkey：如果newkey不存在则修改成功。
move key db：将key移动到db数据库（从0开始），如果目标数据库db已经存在key会失败，如果key不存在与当前数据库会失败。返回0。不能自己移动自己。会报错。
expire key 整数值：设置key的生命周期以秒为单位,如果key不存在返回0，设置成功返回1。
pexpire key整数值：设置key的生命周期以毫秒为单位。如果key不存在返回0，设置成功返回1。
ttl key：获得一个key的活动时间（秒）。如果key不存在返回-2，key没有过期时间则返回-1。
pttl key：查询key 的生命周期（毫秒）。
dump key：序列化给定 key，并返回被序列化的值，使用 RESTORE 命令可以将这个值反序列化为 Redis 键。
三、Redis五大基本数据类型
1、String（字符串）类型
设置值
set key value [EX seconds|PX milliseconds|EXAT timestamp|PXAT milliseconds-timestamp|KEEPTTL] [NX|XX] [GET]：目前常用的就是set key value。
ex second：设置键的过期时间为 second 秒。 SET key value EX second 效果等同于 SETEX key second value。
px millisecond：设置键的过期时间为 millisecond 毫秒。 SET key value PX millisecond 效果等同于 PSETEX key millisecond value。ex和px同时写，则以后面的有效期为准。
nx：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value。
xx：只在键已经存在时，才对键进行设置操作。
因为 SET 命令可以通过参数来实现和 SETNX 、 SETEX 和 PSETEX 三个命令的效果，所以将来的 Redis 版本可能会废弃并最终移除 SETNX 、 SETEX 和 PSETEX 这三个命令。
setbit key offset value：
对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。位的设置或清除取决于 value 参数，可以是 0 也可以是 1。
当 key 不存在时，自动生成一个新的字符串值。字符串会进行伸展(grown)以确保它可以将 value 保存在指定的偏移量上。
当字符串值进行伸展时，空白位置以 0 填充。offset 参数必须大于或等于 0，小于 2^32 (bit 映射被限制在 512 MB 之内)。
setex key seconds value：
将值 value 关联到 key，并将 key 的生存时间设为 seconds (以秒为单位)。
如果 key 已经存在， SETEX 命令将覆写旧值。
这个命令类似于以下两个命令：不同之处是， SETEX 是一个原子性(atomic)操作，关联值和设置生存时间两个动作会在同一时间内完成，该命令在 Redis 用作缓存时，非常实用。
SET key value #设置值
EXPIRE key seconds  # 设置生存时间
psetex key milliseconds value：这个命令和 SETEX 命令相似，但它以毫秒为单位设置 key 的生存时间，而不是像 SETEX 命令那样，以秒为单位。
setnx key value：将 key 的值设为 value，当且仅当 key 不存在。如果key存在了就设置失败。
setrange key offset value：
用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始。
不存在的 key 当作空白字符串处理。SETRANGE 命令会确保字符串足够长以便将 value 设置在指定的偏移量上，如果给定 key 原来储存的字符串长度比偏移量小(比如字符串只有 5 个字符长，但你设置的 offset 是 10 )，那么原字符和偏移量之间的空白将用零字节(zerobytes, “\x00” )来填充。注意你能使用的最大偏移量是 2^29-1(536870911)。
strlen key：返回 key 所储存的字符串值的长度。当 key 储存的不是字符串值时，返回一个错误。
append key value：如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
如果 key 不存在， APPEND 就简单地将给定 key 设为 value，就像执行 SET key value 一样。
mset key value [key value ...]：同时设置一个或多个 key-value 对。如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值。
msetnx key value [key value ...]：同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。即使只有一个给定 key 已存在， MSETNX 也会拒绝执行所有给定 key 的设置操作。MSETNX是原子性的，因此它可以用作设置多个不同 key 表示不同字段(field)的唯一性逻辑对象(unique logic object)，所有字段要么全被设置，要么全不被设置。
获取值
get key：返回 key 所关联的字符串值。如果 key 不存在那么返回特殊值 nil。假如 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值。
getbit key offset：对 key 所储存的字符串值，获取指定偏移量上的位(bit)。当 offset 比字符串值的长度大，或者 key 不存在时，返回 0。
getrange key start end：返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。负数偏移量表示从字符串最后开始计数， -1 表示最后一个字符， -2 表示倒数第二个，以此类推。
mget key [key ...]：返回所有(一个或多个)给定 key 的值。如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil。因此，该命令永不失败。
getset key value：将给定 key 的值设为 value，并返回 key 的旧值(old value)。当 key 存在但不是字符串类型时，返回一个错误。
127.0.0.1：6379> GETSET db mongodb  # 没有旧值，返回 nil
127.0.0.1：6379> GET db
127.0.0.1：6379> GETSET db redis  # 返回旧值 mongodb
增量、减量：注意值必须是integer类型（incrbyfloat除外）
incr key： 将 key 中储存的数字值增一。如果 key 不存在，那么 key 的值会先被初始化为 0，然后再执行 INCR 操作。
如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
incrby key increment：将 key 所储存的值加上增量 increment。如果 key 不存在，那么 key 的值会先被初始化为 0，然后再执行 INCRBY 命令。如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。本操作的值限制在 64 位(bit)有符号数字表示之内。可以是用负数来表示减少量。
incrbyfloat key increment：为 key 中所储存的值加上浮点数增量 increment。如果 key 不存在，那么 INCRBYFLOAT 会先将 key 的值设为 0，再执行加法操作。如果命令执行成功，那么 key 的值会被更新为（执行加法之后的）新值，并且新值会以字符串的形式返回给调用者。当然改变后的值就无法使用其他命令来是实现增量的问题了。
decr key：将 key 中储存的数字值减一。如果 key 不存在，那么 key 的值会先被初始化为 0，然后再执行 DECR 操作。如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
decrby key decrement：将 key 所储存的值减去减量 decrement。如果 key 不存在，那么 key 的值会先被初始化为 0，然后再执行 DECRBY 操作。如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
本操作的值限制在 64 位(bit)有符号数字表示之内。
删除
del key [key ...]：删除给定的一个或多个 key。不存在的 key 会被忽略。
2、List（列表）类型
Redis的list类型其实就是一个每个子元素都是string类型的双向链表，链表的最大长度是2^32。list既可以用做栈，也可以用做队列。值是可以重复的。
设置值
lpush key element [element ...]：
将一个或多个值 value 插入到列表 key 的表头，如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头。比如说，对空列表 mylist 执行命令 LPUSH mylist a b c，列表的值将是 c b a，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。
如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。当 key 存在但不是列表类型时，返回一个错误。
lpushx key element [element ...]：与lpush命令区别在于：当且仅当 key 存在并且是一个列表，才能添加成功，当 key 不存在时， LPUSHX 命令什么也不做。
lset key index element：将列表 key 下标为 index 的元素的值设置为 value。当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。换言之就是给指定位置插入一个值。下标从0开始。
linsert key BEFORE|AFTER pivot element：
将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。
当 pivot 不存在于列表 key 时，不执行任何操作。
当 key 不存在时， key 被视为空列表，不执行任何操作。
如果 key 不是列表类型，返回一个错误。
rpush key element [element ...]：与lpush总体作用差不多，唯一区别在于：这个是从右往左（表尾）的顺序依次插入。
rpushx key element [element ...]：与lpushx总体作用差不多，唯一的区别在于：插入到表尾。
rpoplpush source destination：将一个list集合的最右边那个值移动到另一个list集合中去，返回操作的元素。（具有原子性）。
如果 source 不存在，值 nil 被返回，并且不执行其他动作。
如果 source 和 destination 相同，则列表中的表尾元素被移动到表头，并返回该元素，可以把这种特殊情况视作列表的旋转(rotation)操作。（头尾互换，挺有意思的）
删除
lpop key [count]：移除并返回列表 key 的头元素，换言之删除头部的count个数，默认为1。如果key不存在返回null。
rpop key [count]： 移除并返回列表 key 的尾元素。换言之删除尾部的count个数，默认为1。如果key不存在返回null。
lrem key count element：根据参数 count 的值，移除列表中与参数 value 相等的元素。返回被移除元素的数量。
因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM 命令总是返回 0。
count > 0： 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count。
count < 0： 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
count = 0： 移除表中所有与 value 相等的值。
ltrim key start stop：对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。当 key 不是列表类型时，返回一个错误。
获取
lrange key start stop：
返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
llen key：返回列表 key 的长度(个数）。如果 key 不存在，则 key 被解释为一个空列表，返回 0 .
如果 key 不是列表类型，返回一个错误。
特殊
blpop key [key ...] timeout：BLPOP 是列表的阻塞式(blocking)弹出原语。它是 LPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的头元素。可用于事件提醒模式。
1、非阻塞行为：当 BLPOP 被调用时，如果给定 key 内至少有一个非空列表，那么弹出遇到的第一个非空列表的头元素，并和被弹出元素所属的列表的名字一起，组成结果返回给调用者。当存在多个给定 key 时， BLPOP 按给定 key 参数排列的先后顺序，依次检查各个列表。
redis> DEL job command request   # 确保key都被删除
redis> LPUSH command "update system..."  # 为command列表增加一个值
redis> LPUSH request "visit page"# 为request列表增加一个值
# job 列表为空，被跳过，紧接着 command 列表的第一个元素被弹出。
redis> BLPOP job command request 0   # 弹出元素所属的列表
2、阻塞行为：如果所有给定 key 都不存在或包含空列表，那么 BLPOP 命令将阻塞连接，直到等待超时，或有另一个客户端对给定 key 的任意一个执行 LPUSH 或 RPUSH 命令为止。超时参数 timeout 接受一个以秒为单位的数字作为值。超时参数设为 0 表示阻塞时间可以无限期延长(block indefinitely)。
redis> EXISTS job# 确保两个 key 都不存在
redis> EXISTS command
# 因为key一开始不存在，所以操作会被阻塞，直到另一客户端对 job 或者 command 列表进行 PUSH 操作。
redis> BLPOP job command 300 
redis> BLPOP job command 5   # 等待超时的情况
3、相同的key被多个客户端同时阻塞：相同的 key 可以被多个客户端同时阻塞。
4、不同的客户端被放进一个队列中，按『先阻塞先服务原则』(first-BLPOP，first-served)的顺序为 key 执行 BLPOP 命令。
在MULTI/EXEC事务中的BLPOP：BLPOP 可以用于流水线(pipline,批量地发送多个命令并读入多个回复)，但把它用在 MULTI / EXEC 块当中没有意义。因为这要求整个服务器被阻塞以保证块执行时的原子性，该行为阻止了其他客户端执行 LPUSH 或 RPUSH 命令。因此，一个被包裹在 MULTI / EXEC 块内的 BLPOP 命令，行为表现得就像 LPOP 一样，对空列表返回 nil，对非空列表弹出列表元素，不进行任何阻塞操作。
# 对非空列表进行操作
redis> RPUSH job programming
redis> MULTI
redis> BLPOP job 30
redis> EXEC# 不阻塞，立即返回
# 对空列表进行操作
redis> LLEN job  # 空列表
redis> MULTI
redis> BLPOP job 30
redis> EXEC # 不阻塞，立即返回
blpop key [key ...] timeout：除了弹出元素的位置和 BLPOP 不同之外，其他表现一致。
brpoplpush source destination timeout：BRPOPLPUSH 是 RPOPLPUSH 的阻塞版本。可用于安全队列、循环列表模式。
当给定列表 source 不为空时， BRPOPLPUSH 的表现和 RPOPLPUSH 一样。
当列表 source 为空时， BRPOPLPUSH 命令将阻塞连接，直到等待超时，或有另一个客户端对 source 执行 LPUSH 或 RPUSH 命令为止。
超时参数 timeout 接受一个以秒为单位的数字作为值。超时参数设为 0 表示阻塞时间可以无限期延长。
假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
反之，返回一个含有两个元素的列表，第一个元素是被弹出元素的值，第二个元素是等待时长。
# 非空列表
redis> BRPOPLPUSH msg reciver 500 
redis> LLEN reciver
redis> LRANGE reciver 0 0
# 空列表
redis> BRPOPLPUSH msg reciver 1
3、Set（集合）类型
是一个无序的、惟一的（不重复的）的一个数据集合。所有命令都是s开头
设置值
sadd key member [member ...]：将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。假如 key 不存在，则创建一个只包含 member 元素作成员的集合（既创建添加亦可追加元素）。当 key 不是集合类型时，返回一个错误。被添加到集合中的新元素的数量，不包括被忽略的元素。
smove source destination member：将 member 元素从 source 集合移动到 destination 集合。
smove是原子性操作。
如果 source 集合不存在或不包含指定的 member 元素，则 SMOVE 命令不执行任何操作，仅返回 0。否则， member 元素从 source 集合中被移除，并添加到 destination 集合中去。当 destination 集合已经包含 member 元素时， SMOVE 命令只是简单地将 source 集合中的 member 元素删除。
当 source 或 destination 不是集合类型时，返回一个错误
sdiffstore destination key [key ...]：这个命令的作用和 sdiff类似，求差集后，将结果保存到 destination 集合，而不是简单地返回结果集。如果 destination 集合已经存在，则将其覆盖。
sinterstore destination key [key ...]：与上面的含义差不多，不过是结果集是交集。
sunionstore destination key [key ...]：与上面的含义差不多，不过是结果集是并集。
删除
spop key [count]：移除并返回集合中的一个随机元素。
如果只想获取一个随机元素，但不想该元素从集合中被移除的话，可以使用 srandmember命令。被移除的随机元素。当 key 不存在或 key 是空集时，返回 nil。
srem key member [member ...]：移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。当 key 不是集合类型，返回一个错误。返回移除成功的元素的个数。
获取
smembers key：得到集合 key 中的所有成员。不存在的 key 被视为空集合。
srandmember key [count]：获取指定key的count个数的随机元素。
从 Redis 2.6 版本开始， srandmember 命令接受可选的 count 参数：
如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数（数量/个数），那么返回整个集合。
如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
scard key：返回集合 key 的基数(集合中元素的数量)
sismember key member：判断 member 元素是否集合 key 的成员。
sdiff key [key ...]：返回一个集合的全部成员，该集合是所有给定集合之间的差集。
不存在的 key 被视为空集。换句话说：只会返回第一个key里面的元素中挑选出其他key中没有的元素。如果需要将结果集保存下来，那么可以使用sdiffstore命令。
redis> SMEMBERS peter's_movies
redis> SMEMBERS joe's_movies
redis> SDIFF peter's_movies joe's_movies
sinter key [key ...]：返回一个集合的全部成员，该集合是所有给定集合的交集。
不存在的 key 被视为空集。当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。如果需要将结果集保存下来，那么可以使用sinterstore命令。
sunion key [key ...]：返回一个集合的全部成员，该集合是所有给定集合的并集。不存在的 key 被视为空集。如果需要将结果集保存下来，那么可以使用sunionstore命令。
4、SortSet（有序集合）类型
是一个有序的、唯一的一个数据集合，常用于一些比如排序的地方，如排行榜啊，绩效啊，具有排名的地方。所有命令都是z开头。
设置值
zdd key [NX|XX] [GT|LT] [CH] [INCR] score member [score member ...]：将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
score 值可以是整数值或双精度浮点数。如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。当 key 存在但不是有序集类型时，返回一个错误。
XX： 仅仅更新存在的成员，不添加新成员。
NX： 不更新存在的成员。只添加新成员。
CH： 修改返回值为发生变化的成员总数，原始是返回新添加成员的总数 (CH 是 changed 的意思)。更改的元素是新添加的成员，已经存在的成员更新分数。
所以在命令中指定的成员有相同的分数将不被计算在内。注：在通常情况下，ZADD返回值只计算新添加成员的数量。
NCR： 当ZADD指定这个选项时，成员的操作就等同ZINCRBY命令，对成员的分数进行递增操作。
# 添加单个元素
 redis> ZADD page_rank 10 google.com
 		# 添加多个元素
redis> ZADD page_rank 9 baidu.com 8 bing.com
redis> ZRANGE page_rank 0 -1 WITHSCORES
# 添加已存在元素，且 score 值不变
redis> ZADD page_rank 10 google.com
redis> ZRANGE page_rank 0 -1 WITHSCORES  # 没有改变
# 添加已存在元素，但是改变 score 值
redis> ZADD page_rank 6 bing.com
redis> ZRANGE page_rank 0 -1 WITHSCORES  # bing.com 元素的 score 值被改变
zdiffstore destination numkeys key [key ...]：与set集合的sdiffstore差不多，可参考sdiffstore命令。
zinterstore destination numkeys key [key ...] [WEIGHTS weight] [AGGREGATE SUM|MIN|MAX]：参考sinterstore命令。
zunionstore destination numkeys key [key ...] [WEIGHTS weight] [AGGREGATE SUM|MIN|MAX]：参考sunionstore命令。
删除
zrem key member [member ...]：移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。当 key 存在但不是有序集类型时，返回一个错误。
zremrangebyscore key min max：移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
自版本2.1.6开始， score 值等于 min 或 max 的成员也可以不包括在内。
zremrangebyrank key start stop：移除有序集 key 中，指定排名(rank，命令带有rank的一般情况指成员的排名)区间内的所有成员。区间分别以下标参数 start 和 stop 指出，包含 start 和 stop 在内。下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
zremrangebylex key min max：删除名称按字典由低到高排序成员之间所有成员。（强调，一定要是分数相同）
注意：不要在成员分数不同的有序集合中使用此命令, 因为它是基于分数一致的有序集合设计的,如果使用,会导致删除的结果不正确。
zpopmax key [count]：移除key集合的最大分数的count个元素。
zpopmmin key [count]：移除key集合的最小分数的count个元素。
获取值
zrange key min max [BYSCORE|BYLEX] [REV] [LIMIT offset count] [WITHSCORES]：老版本命令格式是range key start stop [WITHSCORES]
返回存储在有序集合key中的指定范围的元素。 返回的元素可以认为是按得分从最低到最高排列（小到大排序）。 如果得分相同，将按字典排序。当你需要元素从最高分到最低分排列（由大到小）时zrevrange命令，
BYSCORE min max 用分数， BYLEX用值，[member1 [member5;rev：反转，即min max数值换位置
可以传递WITHSCORES选项，以便将元素的分数与元素一起返回。
127.0.0.1：6379> zadd z1 1 v1 2 v2 3 v3 #添加元素
127.0.0.1：6379> zrange z1 0 -1 #返回所有成员方式1 需要把分数带上时，加上withscores
127.0.0.1：6379> zrange z1 -inf +inf byscore #返回所有成员方式2 需要把分数带上时，加上withscores
注：不加 byscore是查找的区间成员（个数），当个数小于这个区间时，就会返回所有的成员，加上byscore以后是查找的分数区间，可以使用 -inf +inf来获取所有成员，不能使用0 -1的方式。
zrevrange key start stop [WITHSCORES]：返回有序集 key 中，指定区间内的成员。其中成员的位置按 score 值递减(从大到小)来排列。目前版本没有zrange 命令复杂，只能按照区间成员来排序。
zcard key：返回有序集合key的所有基数（个数）。
zcount key min max：返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。关于参数 min 和 max 的详细使用方法，请参考 ZRANGEBYSCORE 命令。
zrangebyscore key min max [WITHSCORES] [LIMIT offset count]：
返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
具有相同 score 值的成员按字典序(lexicographical order)来排列(该属性是有序集提供的，不需要额外的计算)。
可选的 LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，注意当 offset 很大时，定位 offset 的操作可能需要遍历整个有序集，此过程最坏复杂度为 O(N) 时间。
可选的 WITHSCORES 参数决定结果集是单单返回有序集的成员，还是将有序集成员及其 score 值一起返回。
区间及无限：
   		min 和 max 可以是 -inf 和 +inf，这样一来，你就可以在不知道有序集的最低和最高 score 值的情况下，使用 ZRANGEBYSCORE 这类命令。
zrank key member：返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
排名以 0 为底，也就是说， score 值最小的成员排名为 0。
zrangebylex key min max [limit offset count]：返回指定成员区间内的成员，按成员字典正序排序, 注意分数必须相同，如果分数不相同，否则结果并不准确。如果min和max位置换了就是倒序排序。
zrevrank key member：与zrank命令相比，除了是由大到小递减，其他一致。
zscore key member：返回有序集 key 中，成员 member 的 score 值。如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 nil。
zdiff numkeys key [key ...] [WITHSCORES]：与set集合的sdiff差不多，可参考sidff命令。
zinter numkeys key [key ...] [WEIGHTS weight] [AGGREGATE SUM|MIN|MAX] [WITHSCORES]：参考sinter命令。
zunion numkeys key [key ...] [WEIGHTS weight] [AGGREGATE SUM|MIN|MAX] [WITHSCORES]：参考sunion命令。
zincrby key increment member：为有序集key的成员member的score值加上增量increment。
如果key中不存在member，就在key中添加一个member，score是increment（就好像它之前的score是0.0）。
score值必须是整数值或双精度浮点数，并且能接受double精度的浮点数。也有可以给一个负数来减少score的值。
如果key不存在，就创建一个只含有指定member成员的有序集合。当key不是有序集类型时，返回一个错误。
5、Hash（哈希表/散列）类型
hash 是一个string类型的field和value的映射表（可以看成特殊的String类型），hash特别适合用于存储对象，将一个对象存储在hash类型中会占用更少的内存，并且可以方便的存取整个对象。所有命令都是h开头。
设置值
hset key field value [field value ...]：将哈希表 key 中的域 field 的值设为 value。可以一次设置多个值，就和hmset命令效果一样（6.2.6版本的命令）。
- 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
- 如果域 field 已经存在于哈希表中，旧值将被覆盖。
127.0.0.1：6379> hset h1 f1 v1 f2 v2 f3 v3
127.0.0.1：6379> hgetall h1 #获取所有的键值对。
hmset key field value [field value ...]： 同时将多个 field-value (域-值)对设置到哈希表 key 中。此命令会覆盖哈希表中已存在的域。
hsetnx key field value：将哈希表 key 中的域 field 的值设置为 value，当且仅当域 field 不存在。若域 field 已经存在，该操作无效。如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。成功返回1，失败返回0。
删除
hdel key field [field ...]：删除指定哈希表key中的域（属性），如果属性不存在，那么忽略掉，返回移除成功的域，不包括忽略的域。
获取值
hget key field：获取给定key的filed值。
hmget key field [field ...]：返回哈希表 key 中，一个或多个给定域的值。如果给定的域不存在于哈希表，那么返回一个 nil 值。
hgetall key：获取哈希表key中所有的域和值（属性—值）。
hlen key：获取哈希表key的属性的数量，key不存在时返回0。
hkeys key：获取哈希表key中所有的域（属性）。
hvals key：获取哈希表key中所有的值、
hexists key field：判断key的属性是否存在，存在返回1，不存在返回0。扩展：一般情况下这种判断性的命令在java中返回true或者false。
hincrby key field increment：为哈希表 key 中的域 field 的值加上增量 increment。增量也可以为负数，相当于对给定域进行减法操作。返回操作后的值。
如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
如果域 field 不存在，那么在执行命令前，域的值被初始化为 0。对一个储存字符串值或者浮点型的域的 field 执行 HINCRBY 命令将造成一个错误。
# increment 为正数
redis> HEXISTS counter page_view# 对空域进行设置
redis> HINCRBY counter page_view 200
redis> HGET counter page_view
# increment 为负数
redis> HGET counter page_view
redis> HINCRBY counter page_view -50
redis> HGET counter page_view
# 尝试对字符串值的域执行HINCRBY命令
redis> HSET myhash string hello,world   # 设定一个字符串值
redis> HGET myhash string
redis> HINCRBY myhash string 1  # 命令执行失败，错误。
redis> HGET myhash string   # 原值不变
hincrbyfloat key field increment：和hincrby命令差不多。功能要强大一丢丢，就是可以操作整数型和浮点型的数据。
特殊命令scan、sscan 、hscan、zscan
注意：没有lscan命令，list类型没有增量命令。
语法：
scan cursor [MATCH pattern] [COUNT count] [TYPE type]
sscan key cursor [MATCH pattern] [COUNT count]
hscan key cursor [MATCH pattern] [COUNT count]
zscan key cursor [MATCH pattern] [COUNT count]
SCAN 命令及其相关的 SSCAN 命令、 HSCAN 命令和 ZSCAN 命令都用于增量迭代（incrementally iterate）一集元素（a collection of elements）：
SCAN 命令用于迭代当前数据库中的数据库键。
SSCAN 命令用于迭代集合键中的元素。
HSCAN 命令用于迭代哈希键中的键值对。
ZSCAN 命令用于迭代有序集合中的元素（包括元素成员和元素分值）。
介绍：
以上列出的四个命令都支持增量式迭代， 它们每次执行都只会返回少量元素， 所以这些命令可以用于生产环境， 而不会出现像 KEYS 命令、 SMEMBERS 命令带来的问题 —— 当 KEYS 命令被用于处理一个大的数据库时， 又或者 SMEMBERS 命令被用于处理一个大的集合键时， 它们可能会阻塞服务器达数秒之久。
不过， 增量式迭代命令也不是没有缺点的：
举个例子， 使用 SMEMBERS 命令可以返回集合键当前包含的所有元素， 但是对于 SCAN 这类增量式迭代命令来说， 因为在对键进行增量式迭代的过程中， 键可能会被修改， 所以增量式迭代命令只能对被返回的元素提供有限的保证。
注意：
SSCAN 命令、 HSCAN 命令和 ZSCAN 命令的第一个参数总是一个数据库键。
SCAN 命令则不需要在第一个参数提供任何数据库键 —— 因为它迭代的是当前数据库中的所有数据库键。
SCAN 命令的基本用法
SCAN 命令是一个基于游标的迭代器（cursor based iterator）： SCAN 命令每次被调用之后， 都会向用户返回一个新的游标， 用户在下次迭代时需要使用这个新游标作为 SCAN 命令的游标参数， 以此来延续之前的迭代过程。
当 SCAN 命令的游标参数被设置为 0 时， 服务器将开始一次新的迭代， 而当服务器向用户返回值为 0 的游标时， 表示迭代已结束。
以下是一个 SCAN 命令的迭代过程示例：
redis 127.0.0.1：6379> scan 0
redis 127.0.0.1：6379> scan 17
从上面的示例可以看到， SCAN 命令的回复是一个包含两个元素的数组， 第一个数组元素是用于进行下一次迭代的新游标， 而第二个数组元素则是一个数组， 这个数组中包含了所有被迭代的元素。
在第二次调用 SCAN 命令时， 命令返回了游标 0， 这表示迭代已经结束， 整个数据集（collection）已经被完整遍历过了。
以 0 作为游标开始一次新的迭代， 一直调用 SCAN 命令， 直到命令返回游标 0， 我们称这个过程为一次完整遍历。
SCAN 命令的保证
SCAN 命令， 以及其他增量式迭代命令， 在进行完整遍历的情况下可以为用户带来以下保证： 从完整遍历开始直到完整遍历结束期间， 一直存在于数据集内的所有元素都会被完整遍历返回； 这意味着， 如果有一个元素， 它从遍历开始直到遍历结束期间都存在于被遍历的数据集当中， 那么 SCAN 命令总会在某次迭代中将这个元素返回给用户。
然而因为增量式命令仅仅使用游标来记录迭代状态， 所以这些命令带有以下缺点：
同一个元素可能会被返回多次。 处理重复元素的工作交由应用程序负责， 比如说， 可以考虑将迭代返回的元素仅仅用于可以安全地重复执行多次的操作上。
如果一个元素是在迭代过程中被添加到数据集的， 又或者是在迭代过程中从数据集中被删除的， 那么这个元素可能会被返回， 也可能不会， 这是未定义的（undefined）。
SCAN 命令每次执行返回的元素数量
增量式迭代命令并不保证每次执行都返回某个给定数量的元素。
增量式命令甚至可能会返回零个元素， 但只要命令返回的游标不是 0， 应用程序就不应该将迭代视作结束。
不过命令返回的元素数量总是符合一定规则的， 在实际中：
对于一个大数据集来说， 增量式迭代命令每次最多可能会返回数十个元素；
而对于一个足够小的数据集来说， 如果这个数据集的底层表示为编码数据结构（encoded data structure，适用于是小集合键、小哈希键和小有序集合键）， 那么增量迭代命令将在一次调用中返回数据集中的所有元素。
最后， 用户可以通过增量式迭代命令提供的 COUNT 选项来指定每次迭代返回元素的最大值。
COUNT 选项
虽然增量式迭代命令不保证每次迭代所返回的元素数量， 但我们可以使用 COUNT 选项， 对命令的行为进行一定程度上的调整。
基本上， COUNT 选项的作用就是让用户告知迭代命令， 在每次迭代中应该从数据集里返回多少元素。
虽然 COUNT 选项只是对增量式迭代命令的一种提示（hint）， 但是在大多数情况下， 这种提示都是有效的。
COUNT 参数的默认值为 10。
在迭代一个足够大的、由哈希表实现的数据库、集合键、哈希键或者有序集合键时， 如果用户没有使用 MATCH 选项， 那么命令返回的元素数量通常和 COUNT 选项指定的一样， 或者比 COUNT 选项指定的数量稍多一些。
在迭代一个编码为整数集合（intset，一个只由整数值构成的小集合）、 或者编码为压缩列表（ziplist，由不同值构成的一个小哈希或者一个小有序集合）时， 增量式迭代命令通常会无视 COUNT 选项指定的值， 在第一次迭代就将数据集包含的所有元素都返回给用户。
并非每次迭代都要使用相同的 COUNT 值。用户可以在每次迭代中按自己的需要随意改变 COUNT 值， 只要记得将上次迭代返回的游标用到下次迭代里面就可以了。
MATCH 选项
和 KEYS 命令一样， 增量式迭代命令也可以通过提供一个 glob 风格的模式参数， 让命令只返回和给定模式相匹配的元素， 这一点可以通过在执行增量式迭代命令时， 通过给定 MATCH [pattern] 参数来实现。
以下是一个使用 MATCH 选项进行迭代的示例：
redis 127.0.0.1：6379> sadd myset 1 2 3 foo foobar feelsgood
redis 127.0.0.1：6379> sscan myset 0 match f*
需要注意的是， 对元素的模式匹配工作是在命令从数据集中取出元素之后， 向客户端返回元素之前的这段时间内进行的， 所以如果被迭代的数据集中只有少量元素和模式相匹配， 那么迭代命令或许会在多次执行中都不返回任何元素。
以下是这种情况的一个例子：
redis 127.0.0.1：6379> scan 0 MATCH *11*
redis 127.0.0.1：6379> scan 288 MATCH *11*
redis 127.0.0.1：6379> scan 224 MATCH *11*
redis 127.0.0.1：6379> scan 80 MATCH *11*
redis 127.0.0.1：6379> scan 176 MATCH *11* COUNT 1000
在最后一次迭代， 我们通过将 COUNT 选项的参数设置为 1000， 强制命令为本次迭代扫描更多元素， 从而使得命令返回的元素也变多了。
并发执行多个迭代
在同一时间， 可以有任意多个客户端对同一数据集进行迭代， 客户端每次执行迭代都需要传入一个游标， 并在迭代执行之后获得一个新的游标， 而这个游标就包含了迭代的所有状态， 因此， 服务器无须为迭代记录任何状态。
中途停止迭代
因为迭代的所有状态都保存在游标里面， 而服务器无须为迭代保存任何状态， 所以客户端可以在中途停止一个迭代， 而无须对服务器进行任何通知。
即使有任意数量的迭代在中途停止， 也不会产生任何问题。
使用错误的游标进行增量式迭代
使用间断的（broken）、负数、超出范围或者其他非正常的游标来执行增量式迭代并不会造成服务器崩溃， 但可能会让命令产生未定义的行为。未定义行为指的是， 增量式命令对返回值所做的保证可能会不再为真。
只有两种游标是合法的：
在开始一个新的迭代时， 游标必须为 0。
增量式迭代命令在执行之后返回的， 用于延续（continue）迭代过程的游标。
迭代终结的保证
增量式迭代命令所使用的算法只保证在数据集的大小有界（bounded）的情况下， 迭代才会停止， 换句话说， 如果被迭代数据集的大小不断地增长的话， 增量式迭代命令可能永远也无法完成一次完整迭代。
从直觉上可以看出， 当一个数据集不断地变大时， 想要访问这个数据集中的所有元素就需要做越来越多的工作， 能否结束一个迭代取决于用户执行迭代的速度是否比数据集增长的速度更快。
返回值：
   		SCAN 命令、 SSCAN 命令、 HSCAN 命令和 ZSCAN 命令都返回一个包含两个元素的 multi-bulk 回复： 回复的第一个元素是字符串表示的无符号 64 位整数（游标）， 回复的第二个元素是另一个 multi-bulk 回复， 这个 multi-bulk 回复包含了本次被迭代的元素。
SCAN 命令返回的每个元素都是一个数据库键。
SSCAN 命令返回的每个元素都是一个集合成员。
HSCAN 命令返回的每个元素都是一个键值对，一个键值对由一个键和一个值组成。
ZSCAN 命令返回的每个元素都是一个有序集合元素，一个有序集合元素由一个成员（member）和一个分值（score）组成。
四、三种特殊类型（geospatial、hperloglog、bitmaps）
1、geospatial （地理空间位置）
简介
Redis3.2版本就推出了！这个功能可以推算地理位置的信息，两地之间的距离，方圆几里的人；主要适用于，朋友的定位，附近的人，打车距离计算等等与地理相关的场景。其实就是一个Zset集合的数据类型的特殊表现形式。
注意：很多中文文档里面介绍的都是地理空间位置（纬度、经度、名称），连中文官网翻译都是，其实不是的，而是先经度后纬度。按照命令语法翻译来是经度、纬度。即：地理空间位置（经度、纬度、名称）
geoadd key [NX|XX] [CH] longitude latitude member [longitude latitude member ...]：
将指定的地理空间位置（经度、纬度、名称）添加到指定的key中。这些数据将会存储到sorted set这样的目的是为了方便使用GEORADIUS或者GEORADIUSBYMEMBER命令对数据进行半径查询等操作。
该命令以采用标准格式的参数x,y,所以经度必须在纬度之前。这些坐标的限制是可以被编入索引的，区域面积可以很接近极点但是不能索引。规定范围：
有效的经度从-180度到180度。
有效的纬度从-85.05112878度到85.05112878度。
当坐标位置超出上述指定范围时，该命令将会返回一个错误。由于是zset集合，那么也可以使用Zset集合的命令来操作这些数据
示列：
127.0.0.1：6379> geoadd china:city 104.065735 30.659462 A # 添加一个城市A的地理位置
127.0.0.1：6379> geoadd china:city 104.773447 29.352765 B
127.0.0.1：6379> geoadd china:city 102.712251 25.040609 C
127.0.0.1：6379> geoadd china:city 106.504962 29.533155 D
127.0.0.1：6379> zrange china:city 0 -1 # 使用zset集合的命令获取的地理位置的名字
geodist key member1 member2 [unit]：返回两个给定位置之间的距离。如果两个位置之间的其中一个不存在， 那么命令返回空值。
指定单位的参数 unit 必须是以下单位的其中一个：
m 表示单位为米。
km 表示单位为千米。
mi 表示单位为英里。
ft 表示单位为英尺。
如果用户没有显式地指定单位参数， 那么 GEODIST 默认使用米作为单位。
GEODIST 命令在计算距离时会假设地球为完美的球形， 在极限情况下， 这一假设最大会造成 0.5% 的误差。
示列：
127.0.0.1：6379> geodist china:city A B km #表示A到B的直线距离
127.0.0.1：6379> geodist china:city C D km #获取C到D的直线距离
georadius key longitude latitude radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count]：以给定的经纬度为中心， 返回键包含的位置元素当中， 与中心的距离不超过给定最大距离的所有位置元素。
在给定以下可选项时， 命令会返回额外的信息：
WITHDIST： 在返回位置元素的同时， 将位置元素与中心之间的距离也一并返回。 距离的单位和用户给定的范围单位保持一致。
WITHCOORD： 将位置元素的经度和维度也一并返回。
WITHHASH： 以 52 位有符号整数的形式， 返回位置元素经过原始 geohash 编码的有序集合分值。 这个选项主要用于底层应用或者调试， 实际中的作用并不大。
命令默认返回未排序的位置元素。 通过以下两个参数， 用户可以指定被返回位置元素的排序方式：
ASC： 根据中心的位置， 按照从近到远的方式返回位置元素。
DESC： 根据中心的位置， 按照从远到近的方式返回位置元素。
在默认情况下， GEORADIUS 命令会返回所有匹配的位置元素。
虽然用户可以使用 COUNT 选项去获取前 N 个匹配元素，
但是因为命令在内部可能会需要对所有被匹配的元素进行处理， 所以在对一个非常大的区域进行搜索时， 即使只使用 COUNT 选项去获取少量元素， 命令的执行速度也可能会非常慢。
但是从另一方面来说， 使用 COUNT 选项去减少需要返回的元素数量， 对于减少带宽来说仍然是非常有用的。
示列：
127.0.0.1：6379> georadius china：city 100 20 1000 km withcoord #得到经度100 纬度20为圆心的成员
127.0.0.1：6379> georadius china:city 100 20 1000 km withdist
127.0.0.1：6379> georadius china:city 100 20 1000 km withhash
127.0.0.1：6379> georadius china:city 100 20 3000 km count 2
127.0.0.1：6379> georadius china:city 100 20 3000 km asc
georadiusbymember key member radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count]：
这个命令和 GEORADIUS 命令一样， 都可以找出位于指定范围内的元素， 但是 GEORADIUSBYMEMBER 的中心点是由给定的位置元素决定的，而不是像 GEORADIUS 那样， 使用输入的经度和纬度来决定中心点,指定成员的位置被用作查询的中心，注意返回值是包含自己本身的。其他用法和groraddius一模一样。
示列：
 			127.0.0.1：6379> georadiusbymember china:city beijing 1000 km withcoord
 			127.0.0.1：6379> georadiusbymember china:city beijing 1000 km withdist
127.0.0.1：6379> georadiusbymember china:city beijing 2000 km withdist
geopos key member [member ...]：从key里返回所有给定位置元素的位置（经度和纬度）。
给定一个sorted set表示的空间索引，密集使用 geoadd 命令，它以获得指定成员的坐标往往是有益的。
当空间索引填充通过 geoadd 的坐标转换成一个52位Geohash，所以返回的坐标可能不完全以添加元素的，但小的错误可能会出台。
因为 GEOPOS 命令接受可变数量的位置元素作为输入， 所以即使用户只给定了一个位置元素， 命令也会返回数组回复。
示列：
127.0.0.1：6379> geopos china:city zigong
127.0.0.1：6379> geopos china:city zigong aa #没有元素时返回空
geohash key member [member ...]：该命令将返回11个字符的Geohash字符串，所以没有精度Geohash，损失相比，使用内部52位表示。
示列：
127.0.0.1：6379> geohash china:city zigong beijing
127.0.0.1：6379> geohash china:city zigong beijing aa
GEOSEARCH key [FROMMEMBER member] [FROMLONLAT longitude latitude] [BYRADIUS radius m|km|ft|mi] [BYBOX width height m|km|ft|mi] [ASC|DESC] [COUNT count [ANY]] [WITHCOORD] [WITHDIST] [WITHHASH]：
返回使用地理空间信息填充的排序集的成员，这些成员GEOADD位于给定形状指定的区域的边界内。该命令对命令进行了扩展GEORADIUS，因此除了在圆形区域内搜索外，它还支持在矩形区域内搜索。应使用此命令代替已弃用的GEORADIUS和GEORADIUSBYMEMBER命令。
查询的中心点由以下强制选项之一提供：
FROMMEMBER： 使用给定存member在于排序集中的位置。
FROMLONLAT：使用给定的longitude和latitude位置。
查询的形状由以下强制选项之一提供：
BYRADIUS： 类似GEORADIUS，根据给定的圆形区域内搜索。
BYBOX：在轴对齐的矩形内搜索，由和确定。
该命令可以选择使用以下选项返回附加信息：
WITHDIST： 也返回返回的物品到指定中心点的距离。距离返回的单位与为半径或高度和宽度参数指定的单位相同。
WITHCOORD： 同时返回匹配项的经度和纬度。
WITHHASH：还以 52 位无符号整数的形式返回项目的原始 geohash 编码排序集分数。这仅对低级 hack 或调试有用，否则对一般用户来说没什么兴趣。
默认情况下，匹配项未排序返回。要对它们进行排序，请使用以下两个选项之一：
ASC：相对于中心点，从最近到最远对返回的项目进行排序。
DESC：相对于中心点，从最远到最近对返回的项目进行排序。
代码命令示例：
redis：6379> GEOADD Sicily 13.361389 38.115556 "Palermo" 15.087269 37.502669 "Catania"
redis：6379> GEOADD Sicily 12.758489 38.788135 "edge1" 17.241510 38.788135 "edge2"
redis：6379> GEOSEARCH Sicily FROMLONLAT 15 37 BYRADIUS 200 km ASC
redis：6379> GEOSEARCH Sicily FROMLONLAT 15 37 BYBOX 400 400 km ASC WITHCOORD WITHDIST
2、hperloglog（基数）
简介
Redis 在 2.8.9 版本添加了 HyperLogLog 结构，HyperLogLog 是用来做基数统计的算法，HyperLogLog 的优点是，在输入元素的数量或者体积非常非常大时，计算基数所需的空间总是固定的、并且是很小的。在 Redis 里面，每个 HyperLogLog 键只需要花费 12 KB 内存，就可以计算接近 2^64 个不同元素的基 数。这和计算基数时，元素越多耗费内存就越多的集合形成鲜明对比。但是，因为 HyperLogLog 只会根据输入元素来计算基数，而不会储存输入元素本身，所以 HyperLogLog 不能像集合那样，返回输入的各个元素。
基数
比如数据集 {1, 3, 5, 7, 5, 7, 8}， 那么这个数据集的基数集为 {1, 3, 5 ,7, 8}, 基数(不重复元素)为5。 基数估计就是在误差可接受的范围内，快速计算基数。适用于在可以接受误差的统计计数。误差约0.81%。
pfadd key element [element ...]：将除了第一个参数以外的参数存储到以第一个参数为变量名的HyperLogLog结构中.
这个命令的一个副作用是它可能会更改这个HyperLogLog的内部来反映在每添加一个唯一的对象时估计的基数(集合的基数).
如果一个HyperLogLog的估计的近似基数在执行命令过程中发了变化， PFADD 返回1，否则返回0，如果指定的key不存在，这个命令会自动创建一个空的HyperLogLog结构（指定长度和编码的字符串）.。
示列：
127.0.0.1：6379> pfadd hperloglog a b c d e f g h i j
pfmerge destkey sourcekey [sourcekey ...]：合并成一个新的hperloglog，基数接近于可见集合的并集。
示列：
127.0.0.1：6379> pfmerge hperlog hperloglog hperloglog1
127.0.0.1：6379> pfcount hperlog
适用场景：可以接受误差的统计地方，如访问量啊，粉丝数量啊什么的。
3、bitmaps（位图）
简介
Bitmaps并不属于Redis中数据结构的一种，它的命令基于String操作，是set、get等一系列字符串操作的一种扩展，与其不同的是，它提供的是位级别的操作，从这个角度看，我们也可以把它当成是一种位数组、位向量结构。当我们需要存取一些boolean类型的信息时，Bitmap是一个非常不错的选择，在节省内存的同时也拥有很好的存取速度(getbit/setbit操作时间复杂度为O(1))。
可以把Bitmaps想象成一个以位为单位数组，数组中的每个单元只能存0或者1，数组的下标在bitmaps中叫做偏移量。单个bitmaps的最大长度是512MB，即232个比特位。
setbit key offset value：设置值，只能存储0和1，适用二元判断类型,如果offset相同，会覆盖前面已存在的值。
getbit key offset：获取值 获取键的第offset位的值（从0开始计算），如果返回0代表没有访问，返回1代表访问过，注意不存在1000，自然返回0。
示列：
127.0.0.1：6379> setbit bitmaps 0 0 #设置值
127.0.0.1：6379> setbit bitmaps 1 1 #设置值
127.0.0.1：6379> getbit bitmaps 0 #获取值
127.0.0.1：6379> getbit bitmaps 1 
127.0.0.1：6379> setbit bitmaps 1 0 #覆盖了原来的
127.0.0.1：6379> getbit bitmaps 1
#获取Bitmaps指定范围值为1的个数，start和and代表字节数，一个字节8位，1到3个字节就是索引在8到23之间
127.0.0.1：6379> bitcount bitmaps 1 10 
127.0.0.1：6379> bitcount bitmaps 0 1 #表示获取偏移量(索引、下标) 在0-8之间为1的数量
bitcount key [start] [end]：获取Bitmaps指定范围值为1的个数，start和and代表字节数，一个字节8位，1到3个字节表示就是索引在8到23之间 1个字节=8bit。
bitpos key bit [start] [end]：第一个获取某种状态的偏移量 计算Bitmaps中第一个值为tergetBit的偏移量，注意字节是从0开始计算的。
bitop operation destkey key [key ...]：bitop是一个复合操作，它可以做多个Bitmaps的and（交集）、or（并集）、not（非）、xor（异或）操作，并将结果保存在destkey中。
示列：bitpos 和bitop 命令,眨眼一看似乎有点相近。别弄错了。
127.0.0.1：6379> setbit user：day1 0 1 #设置值
127.0.0.1：6379> setbit user：day1 1 1
127.0.0.1：6379> setbit user：day1 2 0
127.0.0.1：6379> setbit user：day1 3 1
127.0.0.1：6379> setbit user：day1 4 1
127.0.0.1：6379> setbit user：day1 5 1
127.0.0.1：6379> setbit user：day2 0 1
127.0.0.1：6379> setbit user：day2 2 1
127.0.0.1：6379> setbit user：day2 3 0
127.0.0.1：6379> setbit user：day2 4 0
127.0.0.1：6379> setbit user：day2 5 1
127.0.0.1：6379> bitop and user：and user：day1 user：day2 # 求那些用户2天都打卡了或者说都访问了我的网站
127.0.0.1：6379> bitop or user：or user：day1 user：day2 # 求这两天一共用多少员工或者用户打过卡或者访问过我的网站
127.0.0.1：6379> bitpos user：day1 0  #得到第一个没有打卡的员工是那个，返回2，表示偏离量是2的那个位置第一次出现没打卡或者说为0
127.0.0.1：6379> bitpos user：day1 1 1  #从第一个字节（偏移量（下标））8位开始查找第一个出现1的下标是那个。如果下标不够就返回-1
127.0.0.1：6379> bitpos user：day1 1 0 #得到第一个打卡的员工是那个，返回0，表示偏离量是0的那个位置第一次出现打卡或者说为1
127.0.0.1：6379> bitpos user：day1 1 0 3 #查找范围0-3个字节（0到24位）出现1的位置。
适用场景：常常用来，做如签到、考勤、是否活跃、是否过期等等一些精准是否完成的统计的一些地方。
五、事物
简介：
本质就是一次执行多个命令（一组命令的集合），按照入队的顺序执行命令，不会被其他命令插队进来。
总结就是：
redis事务就是一次性、顺序性、排他性的执行一个队列中的一系列命令。
没有事物隔离级别、不保证原子性、没有回滚机制。但是单条命令具有原子性，只要能够入队成功，其他命令执行失败不会影响其他命令的执行。
共有3个阶段：开启事物—>命令入队—>执行事物（执行队列里面的命令集或者放弃执行队列的命令）
命令
multi：开启事物的一个标记。事务块内的多条命令会按照先后顺序被放进一个队列当中，最后由 EXEC 命令原子性(atomic)地执行。
exec：执行队列（事物块）里的命令集。假如某些)key 正处于 WATCH 命令的监视之下，且事务块中有和这些key 相关的命令，那么 EXEC 命令只在这些key 没有被其他命令所改动的情况下执行并生效，否则该事务被打断(abort)。
discard 取消事务，放弃执行事务块内的所有命令。如果正在使用 WATCH 命令监视某些 key，那么取消所有监视，等同于执行命令 UNWATCH。
watch key [key ...]：监视一个或多个key，如果在事务执行之前这个或这些)key 被其他命令所改动，那么事务将被打断。
unwatch：取消watch命令监视的所有key。
情况说明：
如果执行了exec或者discard命令，那么就不用执行unwatch命令,也就是说执行了exec或者discard命令后，watch所监视的key将无效，如果还要监视，那么必须重新执行watch命令来监视需要监视的key。
如果在入队命令的时候报错了，那么很抱歉，你是虽然还可以成功入队其他命令的，不过做的是无用功，因为执行exec命令的时候会报错——有人犯错集体受罚。
如果事物在入队写命令的时候没有报错，但是执行（exec）的时候报错（类似于java的运行时异常）的，此时谁有错谁无法执行成功谁报错——冤有头债有主。
如果watch命令监视的key出现先了变化，那么此时事物也是无法执行成功的，此时会返回一个nil。实际开发中，基本上使用事物都会使用watch来监听。
其实redis的watch命令就充当着乐观锁的一个作用。
六、发布订阅
简介
redis发布消息订阅系统就是一个消息通信模式，发送者(pub)发送消息，订阅者(sub)接收消息。Redis 客户端可以订阅任意数量的频道。说实话，实际工作开发时，如果要使用消息通宵，一般都会使用消息中间件，如ActiveMQ、RabbitMQ，炙手可热的Kafka，阿里巴巴自主开发RocketMQ，来实现消息通信，不过redis还是可以用来实现简单的消息通信的。
命令
publish channel message：将信息 message 发送到指定的频道 channel，返回接收到信息 message 的订阅者数量。
pubsub subcommand [argument [argument ...]]：是一个查看订阅与发布系统状态的内省命令， 它由数个不同格式的子命令组成。
pubsub channels [pattern]：列出当前的活跃频道。活跃频道指的是那些至少有一个订阅者的频道， 订阅模式的客户端不计算在内。
pattern 参数是可选的：
如果不给出 pattern 参数，那么列出订阅与发布系统中的所有活跃频道。
如果给出 pattern 参数，那么只列出和给定模式 pattern 相匹配的那些活跃频道。
pubsub numsub [channel-1 ... channel-N]返回给定频道的订阅者数量， 订阅模式的客户端不计算在内。
pubsub numpat：返回订阅模式的数量。
psubscribe pattern [pattern ...]：订阅一个或多个符合给定模式的频道。每个模式以 * 作为匹配符，比如 it* 匹配所有以 it 开头的频道( it.news 、 it.blog 、 it.tweets 等等)， news.* 匹配所有以 news. 开头的频道( news.it 、 news.global.today 等等)，诸如此类。
punsubscribe [pattern [pattern ...]]：指示客户端退订所有给定模式。
如果没有模式被指定，也即是，一个无参数的 PUNSUBSCRIBE 调用被执行，那么客户端使用 PSUBSCRIBE 命令订阅的所有模式都会被退订。在这种情况下，命令会返回一个信息，告知客户端所有被退订的模式。
subscribe channel [channel ...]：订阅给定的一个或多个频道的信息。
unsubscribe [channel [channel …]]：取消订阅一个或多个频道信息。
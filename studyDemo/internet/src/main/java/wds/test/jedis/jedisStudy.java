package wds.test.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/******************************
 * @Description Jedis练习
 * @author Administrator
 * @date 2022-09-29 13:48
 ******************************/
public class jedisStudy {
    private static final String host = "127.0.0.1";
    private static final int port = 6379;

    /**
     * 连通性测试
     */
    @Test
    public void Test() {
        Jedis jedis = connectPool();
        String s = connectTest();
        if (s.equalsIgnoreCase("PONG")) {
            //操作key
//            jedis.flushDB();
            jedis.set("k1", "1");
            String k2 = "k2";
            jedis.set(k2, "2");
            jedis.set("k3", "3");
            Set<String> keys = jedis.keys("*");
            System.out.println("库中所有的key = " + keys);
            for (String key : keys) {
                System.out.println(key);
                System.out.println(key + "的值是否存在 = " + jedis.exists("k1"));
                System.out.println(key + "的过期时间 = " + jedis.ttl(key));
                System.out.println(key + "的值 = " + jedis.get(key));
            }
        } else {
            System.out.println("Redis连接失败");
        }
    }

    /**
     * 创建连接池
     */
    @org.jetbrains.annotations.NotNull
    @org.jetbrains.annotations.Contract(value = " -> new", pure = true)
    private static Jedis connectPool() {
        //创建jedis对象
        return new Jedis(host, port);
    }

    /**
     * 连通性测试
     *
     * @return value
     */
    private String connectTest() {
        Jedis jedis = connectPool();
        return jedis.ping();
    }
}
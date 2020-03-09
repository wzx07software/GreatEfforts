package com.popsun.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 吴志祥
 * @create 2020-03-06 18:27
 */
public class JedisTest {
    public static void main(String[] args) {
        String host = "192.168.1.143";
        int port = 6379;
        String password = "yitong";
        JedisPool jedisPool = new JedisPool();
        Jedis jedis = jedisPool.getResource();
    }
}

package com.popsun.redis;

import org.apache.commons.pool2.impl.BaseObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.List;

/**
 * @author 吴志祥
 * @create 2020-03-06 18:27
 */
public class JedisTest {
    public static void main(String[] args) throws InterruptedException {
        String host = "192.168.1.143";
        int port = 6379;
        String password = "yitong";
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setBlockWhenExhausted(BaseObjectPoolConfig.DEFAULT_BLOCK_WHEN_EXHAUSTED);//在资源好紧的时候是否阻塞,如果阻塞再看MaxWaitMillis
        jedisPoolConfig.setFairness(BaseObjectPoolConfig.DEFAULT_FAIRNESS);//如果是true，在资源被耗尽的情况下，所有请求会被block，当有资源被返回到资源池中，优先派给等待时间最长的请求
        jedisPoolConfig.setJmxEnabled(BaseObjectPoolConfig.DEFAULT_JMX_ENABLE);
        jedisPoolConfig.setJmxNameBase("JedisTest");
        jedisPoolConfig.setJmxNamePrefix(BaseObjectPoolConfig.DEFAULT_JMX_NAME_PREFIX);
        jedisPoolConfig.setLifo(BaseObjectPoolConfig.DEFAULT_LIFO);//资源池中的对象被使用后返回资源池中的位置。由于资源池取出资源的时候永远是取第一个，所以后进先出会导致繁忙的资源一直繁忙，空闲的资源可能一直空闲;但是如果是先进先出，则资源被轮询调动，可能会一直不能释放多余的资源
        jedisPoolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE);//最大空闲资源数量
        jedisPoolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL);//最大可以被同时使用的资源数量
        jedisPoolConfig.setMaxWaitMillis(BaseObjectPoolConfig.DEFAULT_MAX_WAIT_MILLIS);//获取资源等待的最大时间,超过时间抛出异常
        jedisPoolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE);//最小空闲资源数量
        jedisPoolConfig.setMinEvictableIdleTimeMillis(BaseObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);//资源被释放的最小空闲时间
        jedisPoolConfig.setNumTestsPerEvictionRun(BaseObjectPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN);//每次释放的资源数量
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(BaseObjectPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);//对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接释放,不再根据MinEvictableIdleTimeMillis判断
        jedisPoolConfig.setTestOnBorrow(BaseObjectPoolConfig.DEFAULT_TEST_ON_BORROW);
        jedisPoolConfig.setTestOnCreate(BaseObjectPoolConfig.DEFAULT_TEST_ON_CREATE);
        jedisPoolConfig.setTestOnReturn(BaseObjectPoolConfig.DEFAULT_TEST_ON_RETURN);
        jedisPoolConfig.setTestWhileIdle(BaseObjectPoolConfig.DEFAULT_TEST_WHILE_IDLE);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(BaseObjectPoolConfig.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS);//执行释放资源的间隔
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, Protocol.DEFAULT_TIMEOUT, password, 5);
        Jedis jedis = jedisPool.getResource();
        jedis.set("name", "吴志祥");
        //---------------------Jedis实现事务---------------------------------------
        jedis.watch("key", "key1", "key2");
        Transaction t = jedis.multi();
        t.set("key", "run");
        Response<String> key = t.get("key");
        if (key.toString().equals("something")) {//注意，由于是在事务中，所以这里的t.get("key")无法获取到key的值，必须等到事物提交后通过get方法才能获取到
            t.set("key1", "value1");
        } else {
            t.set("key2", "value2");
        }
        t.exec();
        //---------------------Jedis实现管道---------------------------------------
        Pipeline pipeline = jedis.pipelined();
        //pipeline.multi();
        pipeline.lpush("someList", "e0", "e1", "e2", "e3", "e4");
        pipeline.lpush("someList", "e5", "e6", "e7", "e8", "e9");
        Response<String> pop = pipeline.lpop("someList");
        pipeline.lpop("someList");
        pipeline.del("someList");
        //pipeline.exec();
        List<Object> list = pipeline.syncAndReturnAll();
        //System.out.println(pop.get());
        for(Object object :list ){
            System.out.println(object);
        }
        jedis.close();
        jedisPool.destroy();
    }
}

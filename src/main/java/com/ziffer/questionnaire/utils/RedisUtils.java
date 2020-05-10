package com.ziffer.questionnaire.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/*
* @author 帆
* */
@Component
public class RedisUtils {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String password;

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 100;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 10;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 1000;

    private static int EXPIRE = 1800;
    private JedisPool getRedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(MAX_ACTIVE);
        jedisPoolConfig.setMaxIdle(MAX_IDLE);
        jedisPoolConfig.setMaxWaitMillis(MAX_WAIT);
        return new JedisPool(jedisPoolConfig, redisHost,redisPort);
        //,TIMEOUT,password
    }
    /**
     * 实现命令：DEL key，删除一个key
     *
     * @param key
     */
    public void del(String key) {
        JedisPool pool = getRedisPool();
        Jedis jedis = pool.getResource();
        jedis.del(key);
        jedis.close();
        pool.close();
    }

    // String（字符串）

    /**
     * 实现命令：SET key value，设置一个key-value（将字符串值 value关联到 key）
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        JedisPool pool = getRedisPool();
        Jedis jedis = pool.getResource();
        jedis.set(key, value);
        jedis.expire(key,EXPIRE);
        jedis.close();
        pool.close();
    }

    /**
     * 实现命令：SET key value EX seconds，设置key-value和超时时间（秒）
     *
     * @param key
     * @param value
     * @param timeout
     *            （以秒为单位）
     */
    public void set(String key, String value, int timeout) {
        JedisPool pool = getRedisPool();
        Jedis jedis = pool.getResource();
        jedis.set(key, value);
        jedis.expire(key,timeout);
        jedis.close();
        pool.close();
    }

    /**
     * 实现命令：GET key，返回 key所关联的字符串值。
     *
     * @param key
     * @return value
     */
    public String get(String key) {
        JedisPool pool = getRedisPool();
        Jedis jedis = pool.getResource();
        String value = jedis.get(key);
        jedis.close();
        pool.close();
        return value;
    }
}

/**
 * create by lishuai on 2019-06-23
 */
package com.me.ls;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class LockService {
    private static JedisPool pool = null;


    static {
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(200);
        // 设置最大空闲数
        config.setMaxIdle(8);
        // 设置最大等待时间
        config.setMaxWaitMillis(1000 * 100);
        // 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "127.0.0.1", 6379, 3000);
    }

    private LockRedis lockRedis = new LockRedis(pool);

    public void lockService() {
        String val = lockRedis.getLock(5000l, 5000l);
        if (val != null) {
            System.out.println(Thread.currentThread().getName()+"获取锁成功,锁的Id为"+val);
            lockRedis.delLock(val);
        }else {
            System.out.println(Thread.currentThread().getName()+"获取锁失败原因可能是超时,锁的Id为"+val);
        }

    }


}

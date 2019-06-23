package com.me.ls;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;
/**
 * create by lishuai on 2019-06-23
 */
public class LockRedis {

    // redis线程池
    private JedisPool jedisPool;
    private final String redisKey = "lock";

    public LockRedis(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 获取锁
     *
     * @param beforeTimeOut 获取到锁之前到超时时间（秒）
     * @param timeOut       获取到锁之后到超时时间（秒）
     * @return value
     */
    public String getLock(Long beforeTimeOut, Long timeOut) {
        Jedis con = jedisPool.getResource();
        String val = UUID.randomUUID().toString();
        Long endTime = System.currentTimeMillis() + beforeTimeOut / 1000;
        try {
            //超时时间之前一直循环获取锁
            while (System.currentTimeMillis() < endTime) {
                if (con.setnx(redisKey, val) == 1) {
                    con.expire(redisKey, timeOut.intValue());
                    return val;
                }
            }
        } catch (Throwable arg) {
            arg.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public void delLock(String val) {
        Jedis con = jedisPool.getResource();
        try {
            if (con.get(redisKey).equals(val)) {
                con.del(redisKey);
                System.out.println(Thread.currentThread().getName() +"释放锁成功,锁的Id为" + val);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

}

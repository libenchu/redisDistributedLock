package com.me.ls;

/**
 * create by lishuai on 2019-06-22
 */
public class Test {
    public static void main(String[] args) {
        LockService lockService = new LockService();
        for (int i=0;i<1000;i++){
            new ThreadRedis(lockService).run();
        }
    }
}

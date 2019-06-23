package com.me.ls;

/**
 * create by lishuai on 2019-06-23
 */
public class ThreadRedis extends Thread {
    private LockService lockService;

    public ThreadRedis(LockService lockService) {
        this.lockService = lockService;
    }

    @Override
    public void run() {
        lockService.lockService();
    }
}

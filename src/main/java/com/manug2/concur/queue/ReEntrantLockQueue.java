package com.manug2.concur.queue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by maverick on 6/13/2016.
 */
public class ReEntrantLockQueue extends MyBlockingQueue {
    public ReEntrantLockQueue(int i) {
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean add(Integer integer) {
        return false;
    }

    @Override
    public Integer take() throws InterruptedException {
        return null;
    }
}

package com.manug2.concur.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by maverick on 6/13/2016.
 */
public class ReEntrantLockQueue extends MyBlockingQueue {

    final ReentrantLock lock;
    final Condition notEmpty, notFull;
    List<Integer> list = new LinkedList<>();
    int count = 0;
    final int capacity;

    public ReEntrantLockQueue(int i) {
        capacity = i;
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    @Override
    public boolean isEmpty() {
        try {
            lock.lockInterruptibly();
            return count == 0;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean add(Integer integer) {
        try {
            lock.lockInterruptibly();
            if (count == capacity)
                notFull.await();
            ++count;
            return list.add(integer);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            notEmpty.signalAll();
            lock.unlock();
        }
    }

    @Override
    public Integer take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count==0)
                notEmpty.await();
            --count;
            return list.remove(0);
        } finally {
            notFull.signalAll();
            lock.unlock();
        }
    }

}

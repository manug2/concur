
package com.manug2.concur.queue;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ReEntrantLockQueueTest {

    BlockingQueue<Integer> queue;

    @Before
    public void setup() {
        this.queue = new ReEntrantLockQueue(10);
    }

    @Test
    public void should_be_able_to_put_one_element_when_empty() throws InterruptedException {
        assertTrue(queue.add(10001));
    }

    @Test
    public void should_be_able_to_take_one_element_when_put() throws InterruptedException {
        queue.add(10001);
        assertEquals(new Integer(10001), queue.take());
    }

    @Test()
    public void should_block_when_taking_from_new_empty_queue() throws InterruptedException {
        final CountDownLatch waitLatch = new CountDownLatch(1);
        final CountDownLatch nowaitLatch = new CountDownLatch(1);
        Thread taker = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int item = queue.take();
                    System.out.println("taken item = " + item);
                    waitLatch.countDown();
                } catch(InterruptedException e) {
                    //
                } catch (IndexOutOfBoundsException e) {
                    nowaitLatch.countDown();
                }
            }
        });
        taker.start();
        assertFalse("it seems an item was taken from empty queue",
                waitLatch.await(100, TimeUnit.MILLISECONDS));

        assertFalse("it seems empty queue did not block",
                nowaitLatch.await(100, TimeUnit.MILLISECONDS));

        taker.interrupt();
    }

}

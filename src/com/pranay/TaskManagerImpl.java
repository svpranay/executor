package com.pranay;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public class TaskManagerImpl implements TaskManager {

    int threadPoolSize;
    Thread [] threads;
    Semaphore semaphore;
    Queue<Integer> jobs;
    boolean isTerminated = false;

    public TaskManagerImpl(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        this.jobs = new ConcurrentLinkedDeque<>();
        this.threads = new Thread[threadPoolSize];
        this.semaphore = new Semaphore(0);
        for (int i = 0; i < threadPoolSize; i++) {
            Thread t = new Thread(new TaskExecutor<Integer>(jobs, semaphore));
            threads[i] = t;
            t.start();
        }
    }

    @Override
    public int add(int job) {
        jobs.add(job);
        semaphore.release();
        return 0;
    }

    @Override
    public void remove(int jobId) {
    }

    @Override
    public int status(int jobId) {
        return 0;
    }

    @Override
    public void stop() {
        isTerminated = true;
    }

    public class TaskExecutor<T> implements Runnable {

        Queue<T> queue;
        Semaphore semaphore;

        public TaskExecutor(Queue<T> queue, Semaphore semaphore) {
            this.queue = queue;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            while(!isTerminated || !queue.isEmpty()) {
                try {
                    if (!isTerminated)
                        semaphore.acquire();
                    // execute the job
                    T t = queue.poll();
                    if (t == null) continue;
                    System.out.println("Next job executing : " + t.toString() + " by thread : " + this.toString());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Stopping : " + this.toString());
        }
    }

}

package Sem6.Mario.Ex3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CounterThread extends Thread{

    private CyclicBarrier barrier;
    public static final int NUM_THREADS = 10;
    private int id;

    public CounterThread(int id, CyclicBarrier barrier){
        this.barrier = barrier;
        this.id = id;
    }

    @Override
    public void run(){
        long startTime = System.nanoTime();
        for (int i = 0; i < 1e6; i++) {
            if (i == 1e3 && id < 5) {
                try {
                    barrier.await();
                    long endTime = System.nanoTime();
                    long time = (long) ((endTime - startTime)/(1e6));
                    System.out.println("Thread " + id + " finishing 1st round at:" + time);
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        try{
            if(id >= 5){
                barrier.await();
            }
            long endTime = System.nanoTime();
            long time = (long) ((endTime - startTime)/(1e6));
            System.out.println("Thread " + id + " finishing at:" + time);
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

    }

}

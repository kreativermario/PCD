package Sem6.Ex3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CounterThread extends Thread{

    private CyclicBarrier barrier;
    public static final int NUM_THREADS = 10;

    public CounterThread(CyclicBarrier barrier){
        this.barrier = barrier;
    }

    @Override
    public void run(){
        long startTime = System.nanoTime();
        try{
            for (int i = 0; i < 1e6; i++) {
                if(i == 1e3) barrier.await();
            }
            long endTime = System.nanoTime();
            long time = (long) ((endTime - startTime)/(1e6));
            System.err.println("Thread finishing at:" + time);
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

    }

}

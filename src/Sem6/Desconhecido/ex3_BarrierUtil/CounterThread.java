package Sem6.Desconhecido.ex3_BarrierUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CounterThread extends Thread{

    private double myCount;

    private double countToGet;

    private CyclicBarrier barrier;

    private double result = -1;

    public CounterThread(double myCount, double countToGet, CyclicBarrier barrier){
        this.myCount = myCount;
        this.countToGet = countToGet;
        this.barrier = barrier;
    }

    public double getMyCount() { return myCount; }

    public double getResult() { return result; }

    @Override
    public void run(){
        long startTime = System.nanoTime();
        result = countToGet;
        try{
            barrier.await();
            long endTime = System.nanoTime();
            long time = (long) ((endTime - startTime)/(1e6));
            System.err.println("Thread finishing at: " + time + "ms");
        } catch (InterruptedException | BrokenBarrierException e) {
        }
    }
}

package Sem6.Ex3;

import Sem6.Ex1Barrier.SearcherThread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Counter {

    public final static int NUM_THREADS = 10;
    private static CounterThread[] threads = new CounterThread[NUM_THREADS];

    public static void main(String[] args) {
        final long initTime=System.currentTimeMillis();
        CyclicBarrier barrier = new CyclicBarrier(NUM_THREADS/2, new Runnable() {
            @Override
            public void run() {
                System.out.println("Primeiros threads acabaram de contar " + "Time:" +
                        (System.currentTimeMillis() - initTime + " ms"));
                CyclicBarrier barrier2 = new CyclicBarrier(NUM_THREADS/2 + 1);
                for(int i=NUM_THREADS/2; i < NUM_THREADS;i++){
                    threads[i]=new CounterThread(barrier2);
                    threads[i].start();
                }
                try{
                    barrier2.await();
                    System.out.println("Todas as threads acabaram de contar " + "Time:" +
                            (System.currentTimeMillis() - initTime + " ms"));
                } catch (BrokenBarrierException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        for(int i=0; i != NUM_THREADS/2;i++){
            threads[i]=new CounterThread(barrier);
            threads[i].start();
        }




    }


}

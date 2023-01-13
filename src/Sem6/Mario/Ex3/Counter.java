package Sem6.Mario.Ex3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Counter {

    public final static int NUM_THREADS = 10;
    private static CounterThread[] threads = new CounterThread[NUM_THREADS];

    public static void main(String[] args) {
        final long initTime = System.currentTimeMillis();
        CyclicBarrier barrier = new CyclicBarrier(NUM_THREADS / 2, new Runnable() {
            @Override
            public void run() {
                System.out.println("Primeiros threads acabaram de contar " + "Time:" +
                        (System.currentTimeMillis() - initTime + " ms"));
                CyclicBarrier barrier2 = new CyclicBarrier(NUM_THREADS / 2, new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Todas as threads acabaram de contar " + "Time:" +
                                (System.currentTimeMillis() - initTime + " ms"));
                    }
                });

                System.out.println("Iniciando segunda ronda " + "Time:" +
                        (System.currentTimeMillis() - initTime + " ms"));
                for (int i = NUM_THREADS / 2; i < NUM_THREADS; i++) {
                    threads[i] = new CounterThread(i, barrier2);
                    threads[i].start();
                }

            }
        });

        for (int i = 0; i < NUM_THREADS / 2; i++) {
            threads[i] = new CounterThread(i, barrier);
            threads[i].start();
        }
    }
}


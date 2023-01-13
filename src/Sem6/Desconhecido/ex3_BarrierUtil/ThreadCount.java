package Sem6.Desconhecido.ex3_BarrierUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ThreadCount {

    static final double NUM_CONTAGEM=1e6;

    static final double HALF_NUM_CONTAGEM=1e3;

    static final int NUM_THREADS=10;





    public static void main(String[] args) {
        final long initTime = System.currentTimeMillis();

        CounterThread[] threads = new CounterThread[NUM_THREADS];
        CyclicBarrier barrier = new CyclicBarrier(NUM_THREADS);

        Thread summary = new Thread() {
            @Override
            public void run(){
                try{
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e){
                    e.printStackTrace();
                }

                // Summary - resume os resultados
                int count = 0;
                for (CounterThread t : threads)
                    if(t.getResult() != -1){
                        System.out.println("Found at " + (int) t.getResult());
                        count++;
                    }
                System.out.println("Search DONE. Found: "+ count + " Time: " +
                        (System.currentTimeMillis() - initTime) + "ms");
            }
        };
        summary.start();

        int counter = 0;
        for(int i = 0; i < NUM_THREADS; i++){
            threads[i] = new CounterThread(counter, NUM_CONTAGEM, barrier);
            threads[i].start();
        }


    }

}

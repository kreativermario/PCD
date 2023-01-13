package Sem6.Mario.Ex4_Corredores;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Corrida {

    public static final int TOTAL_DISTANCE = 10;
    private CyclicBarrier barrier;
    private AtomicInteger rank = new AtomicInteger(0);
    private final long initTime;

    public Corrida(int numCorredores){
        initTime = System.currentTimeMillis();
        barrier = new CyclicBarrier(numCorredores);
        for(int i = 0; i < numCorredores; i++){
            Corredor corredor = new Corredor(TOTAL_DISTANCE, this);
            corredor.start();
        }
    }

    public int cheguei() throws InterruptedException{
        System.out.println("Chegou " + Thread.currentThread().getName() + " @ " +
                (System.currentTimeMillis() - initTime));
        int place = rank.incrementAndGet();
        try {
            barrier.await();
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        return place;
    }

    public static void main(String[] args) {
        Corrida corrida = new Corrida(10);
    }
}

package Sem5;

import java.util.concurrent.Semaphore;

public class HorseTrack {

    public final static int TRACK_LIMIT = 30;
    private Semaphore mutex = new Semaphore(2);

    public void move(Horse h) throws InterruptedException{
        try{
            mutex.acquire();
            System.out.println("HORSE: " +
                    Thread.currentThread().toString() + " - ENTREI SEMAFORO -> PERMITS: " + mutex.availablePermits());
            h.move();
            System.out.println("HORSE: " +
                    Thread.currentThread().toString() + " - MOVI CAVALO -> PERMITS: " + mutex.availablePermits());
        }finally {
            mutex.release();
            System.out.println("HORSE: " +
                    Thread.currentThread().toString() + " - RELEASE -> PERMITS: " + mutex.availablePermits());
        }

    }



}

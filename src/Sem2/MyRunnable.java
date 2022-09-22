package Sem2;

import java.util.Random;

import static java.lang.Thread.sleep;

public class MyRunnable implements Runnable{

    private Car car;
    private int limit;

    public MyRunnable(Car car, int limit){
        this.car = car;
        this.limit = limit;
    }

    @Override
    public void run() {
        try{
            for(int i = 0; i< limit; i++) {
                Random r = new Random();
                int low = 0;
                int high = 100;
                int result = r.nextInt(high - low) + low;
                car.increasePosition();
                sleep(result);
            }
        } catch (InterruptedException e) {

        }

    }
}

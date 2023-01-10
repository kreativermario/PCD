package Sem4.Gold;

import Sem4.Scale;

public class GoldProducer extends Thread {

    public static final int TIME_TO_PRODUCE = 3;
    private int amount_produced = 0;
    private Scale scale;

    public GoldProducer(Scale scale) {
        this.scale = scale;
        start();
    }


    @Override
    public void run() {


        try {
            while (true) {
                if (this.isInterrupted()) {
                    System.out.println(Thread.currentThread().toString() + " - fim do run");
                    System.out.println(Thread.currentThread().toString() + " - PRODUZIU NO TOTAL: " + amount_produced);
                    return;
                }

                scale.remove();
                sleep(TIME_TO_PRODUCE);
                amount_produced++;
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().toString() + " - InterruptedException");
        }
    }
}



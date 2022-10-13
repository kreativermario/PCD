package Sem4;

public class GoldDigger extends Thread {

    private double goldDigged = 0;
    private Scale scale;

    public GoldDigger(Scale scale) {
        System.out.println(Thread.currentThread().toString() + " - inicio do run");
        this.scale = scale;
        start();
    }

    @Override
    public void run() {

        try {
            while (true) {
                if (this.isInterrupted()) {
                    System.out.println(Thread.currentThread().toString() + " - fim do run");
                    return;
                }
                double randomGold = Math.random();
                double gold = Math.round(randomGold * 10.0) / 10.0;
                scale.put(gold);
                sleep(500);

                goldDigged += gold;
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().toString() + " - InterruptedException");
        }
    }

}


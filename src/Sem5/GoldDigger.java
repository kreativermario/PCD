package Sem5;

public class GoldDigger extends Thread {

    private double goldDigged = 0;
    private Escala escala;

    public GoldDigger(Escala escala) {
        System.out.println("PRODUTOR: " + Thread.currentThread().toString() + " - inicio do run");
        this.escala = escala;
        this.start();
    }

    @Override
    public void run() {

        try {
            while (true) {
                if (this.isInterrupted()) {
                    System.out.println("PRODUTOR: " + Thread.currentThread().toString() + " - fim do run");
                    System.out.println("PRODUTOR: " + Thread.currentThread().toString() + " - diggei " + goldDigged);
                    return;
                }
                double randomGold = Math.random();
                double gold = Math.round(randomGold * 10.0) / 10.0;
                escala.put(gold);
                this.goldDigged += gold;
                sleep(500);

                goldDigged += gold;
            }
        } catch (InterruptedException e) {
            System.out.println("PRODUTOR: " + Thread.currentThread().toString() + " - fim do run");
            System.out.println("PRODUTOR: " + Thread.currentThread().toString() + " - InterruptedException");
        }
    }

}
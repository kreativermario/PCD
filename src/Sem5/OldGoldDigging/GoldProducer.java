package Sem5.OldGoldDigging;


public class GoldProducer extends Thread {

    public static final int TIME_TO_PRODUCE = 3;
    private int amount_produced = 0;
    private Escala escala;

    public GoldProducer(Escala escala) {
        this.escala = escala;
        System.out.println("CONSUMIDOR: " + Thread.currentThread().toString() + " - inicio do run");
        this.start();
    }


    @Override
    public void run() {


        try {
            while (true) {
                if (this.isInterrupted()) {
                    System.out.println("CONSUMIDOR: " +
                            Thread.currentThread().toString() + " - fim do run");
                    System.out.println("CONSUMIDOR: " +
                            Thread.currentThread().toString() + " - PRODUZIU NO TOTAL: " + amount_produced);
                    return;
                }

                escala.remove();
                sleep(TIME_TO_PRODUCE);
                amount_produced++;
            }
        } catch (InterruptedException e) {
            System.out.println("CONSUMIDOR: " + Thread.currentThread().toString() + " - InterruptedException");
        }
    }
}
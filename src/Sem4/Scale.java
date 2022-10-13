package Sem4;

import javax.swing.*;

public class Scale {

    public static final double CAPACITY = 50;
    public static final double GOLD_NEED = 12.5;
    private double currentCapacity = 0;
    private JTextField textField;
    private Thread[] threads = new Thread[2];

    public Scale(JTextField textField) {
        this.textField = textField;
        GoldDigger goldDigger = new GoldDigger();
        GoldProducer goldProducer = new GoldProducer();
        threads[0] = goldDigger;
        threads[1] = goldProducer;
    }

    public void stopThreads() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public synchronized void put(double amount) throws InterruptedException {
        while (currentCapacity + amount >= CAPACITY || currentCapacity > GOLD_NEED) {
            System.out.println("ESTOU ESPERANDO");
            wait();
        }
        currentCapacity += amount;
        currentCapacity = Math.round(currentCapacity * 10.0) / 10.0;
        System.out.println(Thread.currentThread().toString() + " - coloquei GOLD: " + amount +
                " | BALANCE: " + currentCapacity);
        textField.setText(Double.toString(currentCapacity));
        notifyAll();
    }


    public synchronized void remove() throws InterruptedException {
        while (currentCapacity == 0 || currentCapacity < GOLD_NEED) {
            System.out.println(Thread.currentThread().toString() + " - esperando RECOLHA");
            wait();
        }
        System.out.println(Thread.currentThread().toString() + " - retirei GOLD");
        currentCapacity -= GOLD_NEED;
        textField.setText(Double.toString(currentCapacity));
        notifyAll();
    }

    public class GoldDigger extends Thread {

        private double goldDigged = 0;

        public GoldDigger() {
            Thread t1 = new Thread(this);
            System.out.println(Thread.currentThread().toString() + " - inicio do run");
            t1.start();
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
                    put(gold);
                    sleep(500);

                    goldDigged += gold;
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().toString() + " - InterruptedException");
            }
        }

    }


    public class GoldProducer extends Thread {

        public static final int TIME_TO_PRODUCE = 3;
        private int amount_produced = 0;

        public GoldProducer() {
            Thread t1 = new Thread(this);
            t1.start();
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

                    remove();
                    sleep(TIME_TO_PRODUCE);
                    amount_produced++;
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().toString() + " - InterruptedException");
            }
        }
    }

}





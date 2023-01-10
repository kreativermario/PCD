package Sem4.Gold;

import javax.swing.*;

public class Scale {

    public static final double CAPACITY = 50;
    public static final double GOLD_NEED = 12.5;
    private double currentCapacity = 0;
    private JTextField textField;

    public Scale(JTextField textField) {
        this.textField = textField;
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

    public class GoldProducer extends Thread {

        public static final int TIME_TO_PRODUCE = 3;
        private int amount_produced = 0;

        @Override
        public void run() {

            try {
                while (true) {
                    remove();
                    sleep(TIME_TO_PRODUCE);
                    amount_produced++;
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().toString() + " - InterruptedException");
                System.out.println(Thread.currentThread().toString() + " - fim do run");
                System.out.println(Thread.currentThread().toString() + " - PRODUZIU NO TOTAL: " + amount_produced);
            }
        }
    }

    public class GoldDigger extends Thread {

        private double goldDigged = 0;

        public GoldDigger() {
            System.out.println(Thread.currentThread().toString() + " - inicio do run");
        }

        @Override
        public void run() {

            try {
                while (true) {
                    double randomGold = Math.random();
                    double gold = Math.round(randomGold * 10.0) / 10.0;
                    put(gold);
                    sleep(500);

                    goldDigged += gold;
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().toString() + " - InterruptedException");
                System.out.println(Thread.currentThread().toString() + " - fim do run");
            }
        }

    }





}





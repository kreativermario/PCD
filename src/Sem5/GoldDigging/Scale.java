package Sem5.GoldDigging;

import javax.swing.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Scale {

    public static final double CAPACITY = 50;
    public static final double GOLD_NEED = 12.5;
    private double currentCapacity = 0;
    private Lock lock = new ReentrantLock();
    private Condition readyPickup = lock.newCondition();
    private Condition atCapacity = lock.newCondition();
    private JTextField textField;

    public Scale(JTextField textField) {
        this.textField = textField;
    }


    public void put(double amount) throws InterruptedException {
        lock.lock();
        try{
            // Balança cheia!
            while (currentCapacity + amount >= CAPACITY || currentCapacity > GOLD_NEED) {
                System.out.println("ESTOU ESPERANDO");
                atCapacity.await();
            }
            currentCapacity += amount;
            currentCapacity = Math.round(currentCapacity * 10.0) / 10.0;
            System.out.println(Thread.currentThread().toString() + " - coloquei GOLD: " + amount +
                    " | BALANCE: " + currentCapacity);
            textField.setText(Double.toString(currentCapacity));

            readyPickup.signal();

        }finally {
            lock.unlock();
        }



    }


    public void remove() throws InterruptedException {
        lock.lock();
        try{
            while (currentCapacity < GOLD_NEED) {
                System.out.println(Thread.currentThread().toString() + " - esperando RECOLHA");
                readyPickup.await();
            }
            System.out.println(Thread.currentThread().toString() + " - retirei GOLD");
            currentCapacity -= GOLD_NEED;
            textField.setText(Double.toString(currentCapacity));

            atCapacity.signal();

        }finally {
            lock.unlock();
        }



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





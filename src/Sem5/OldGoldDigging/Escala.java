package Sem5.OldGoldDigging;

import javax.swing.*;
import java.util.concurrent.locks.*;

public class Escala {

    public static final double CAPACITY = 50;
    public static final double GOLD_NEED = 12.5;
    private double currentCapacity = 0;
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    private JTextField textField;

    public Escala(JTextField field){
        this.textField = field;
    }


    public void put(double amount) throws InterruptedException {
        lock.lock();
        try{
            //TODO condicao está cheio
            while (currentCapacity + amount >= CAPACITY || currentCapacity > GOLD_NEED) {
                System.out.println("PRODUTOR: " + Thread.currentThread().toString() + " ESTOU ESPERANDO");
                notFull.await();
            }
            //TODO condicao nao está cheio
            currentCapacity += amount;
            currentCapacity = Math.round(currentCapacity * 10.0) / 10.0;
            System.out.println("PRODUTOR: " + Thread.currentThread().toString() + " - coloquei GOLD: " + amount +
                    " | BALANCE: " + currentCapacity);
            textField.setText(Double.toString(currentCapacity));
            notEmpty.signal();
        }finally {
            lock.unlock();
        }

    }


    public void remove() throws InterruptedException {
        lock.lock();
        try{
            //TODO condicao está vazio
            while (currentCapacity == 0 || currentCapacity < GOLD_NEED) {
                System.out.println("CONSUMIDOR: " + Thread.currentThread().toString() + " - esperando RECOLHA");
                notEmpty.await();
            }
            System.out.println("CONSUMIDOR: " + Thread.currentThread().toString() + " - retirei GOLD");
            currentCapacity -= GOLD_NEED;
            textField.setText(Double.toString(currentCapacity));
            notFull.signal();
        }finally {
            lock.unlock();
        }

    }



}
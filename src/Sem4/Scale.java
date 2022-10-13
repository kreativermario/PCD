package Sem4;

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




}





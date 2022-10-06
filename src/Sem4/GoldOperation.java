package Sem4;

import javax.swing.*;
import java.util.Random;

/**
 * Exercício 2
 */
public class GoldOperation {

    public GoldOperation(){
        //Criar balança etc

    }

    public static class Scale{

        public static final double CAPACITY = 50;
        public static final double GOLD_NEED = 12.5;
        private int currentCapacity = 0;
        private JTextField textField;

        public Scale(JTextField textField) {
            this.textField = textField;
        }


        public synchronized void put(double amount) throws InterruptedException {
            while(currentCapacity + amount >= CAPACITY || currentCapacity < GOLD_NEED){
                wait();
            }
            currentCapacity += amount;
            textField.setText(Double.toString(currentCapacity));
            notifyAll();
        }


        public synchronized void remove() throws InterruptedException {
            while(currentCapacity == 0 || currentCapacity < 12.5){
                wait();
            }
            currentCapacity-=12.5;
            textField.setText(Double.toString(currentCapacity));
            notifyAll();
        }


    }



    public static class GoldDigger extends Thread{

        private double goldDigged = 0;
        private Scale scale;

        public GoldDigger(Scale s) {
            this.scale = s;
            Thread t1 = new Thread();
            System.out.println(Thread.currentThread().toString() + " - inicio do run");
            t1.start();
        }

        @Override
        public void run(){
            while(true){
                try {
                    Random r = new Random();
                    int low = 0;
                    int high = 100;
                    int time = r.nextInt(high - low) + low;
                    double randomGold = Math.random();
                    double gold = Math.round(randomGold * 10.0) / 10.0;
                    scale.put(gold);

                    goldDigged+=gold;

                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    break;
                }
            }

        }
    }

    public static class GoldProducer{




    }

}

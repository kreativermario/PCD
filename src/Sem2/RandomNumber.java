package Sem2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Exercicio 4 - Extra: Gerador de números aleatórios
 */
public class RandomNumber extends Thread{

    /**
     * A classe ThreadA
     */
    public static class zeroHundred extends Thread{
        private int count = 0;

        public void increaseCount(){
            this.count++;
        }

        @Override
        public void run(){

            while(true){
                Random r = new Random();
                int low = 1000;
                int high = 9999;
                int result = r.nextInt(high - low) + low;
                increaseCount();
                System.out.println(result);
                if(this.isInterrupted()){
                    System.out.println( getName() + " : " + count);
                    return;
                }
            }

        }

    }

    /**
     * Classe Thread B
     */
    public static class zeroNine extends Thread{

        private int count=0;

        public void increaseCount(){
            this.count++;
        }

        @Override
        public void run(){
            while(true){
                try {
                    Random r = new Random();
                    int low = 1;
                    int high = 9;
                    int result = r.nextInt(high - low) + low;
                    System.out.println(result);
                    increaseCount();
                    sleep(500);
                } catch (InterruptedException e) {
                    System.out.println( getName() + " : " + count);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{
        JFrame frame = new JFrame("Random number generators!s");
        JButton button = new JButton("STOP");
        zeroHundred threadA = new zeroHundred();
        zeroNine threadB = new zeroNine();
        Thread[] threads = new Thread[2];
        threads[0] = threadA;
        threads[1] = threadB;

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for(Thread thread: threads){
                    thread.interrupt();
                }
            }
        });

        frame.add(button);
        frame.setSize(500, 300);
        frame.setVisible(true);


        threadA.start();
        threadB.start();
    }


}

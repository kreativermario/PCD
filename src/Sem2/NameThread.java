package Sem2;

import javax.naming.Name;

public class NameThread extends Thread{

    @Override
    public void run () {
        try {
            for(int i=0; i<10; i++) {
                System.out.println(getName() + " i: " + i);
                int time = (Math.random() <= 0.5) ? 1 : 2;
                sleep(time * 1000);
            }
        } catch (InterruptedException e) {
            System.out.print(getName() + " foi interrompida!");
        }
        System.out.print(getName() + " terminou!");
    }
    public static void main ( String [] args ) throws InterruptedException {
        NameThread t1 = new NameThread();
        NameThread t2 = new NameThread();
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        }catch ( InterruptedException e ) {
            System.out.println("Interrupted main!");
        }
        System.out.println("Main done!");

    }


}

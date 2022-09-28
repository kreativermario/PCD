package Sem3;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicContador {
    private AtomicInteger counter;

    public AtomicContador(){
        this.counter = new AtomicInteger();
    }

    public void increaseCounter(){
        this.counter.incrementAndGet();
    }

    public int getCounter() {
        return counter.get();
    }

    public static class ContadorThread extends Thread{

        private AtomicContador contador;

        public ContadorThread(AtomicContador contador){
            this.contador = contador;
        }

        @Override
        public void run(){
            for(int i=0; i<1000; i++){
                this.contador.increaseCounter();
            }
        }


    }

    public static void main(String[] args) {
        AtomicContador contador = new AtomicContador();
        ContadorThread t1 = new ContadorThread(contador);
        ContadorThread t2 = new ContadorThread(contador);
        ContadorThread t3 = new ContadorThread(contador);
        ContadorThread t4 = new ContadorThread(contador);
        t1.start();
        t2.start();
        t3.start();
        t4.start();


        try{
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        }catch ( InterruptedException e ) {
            System.out.println("Interrupted main!");
        }
        System.out.println("Main done!");
        System.out.println(contador.getCounter());



    }


}

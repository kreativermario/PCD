package Sem3;

import java.util.Random;

/**
 * Exercício 2 - Fila de inteiros
 */
public class Fila {

    private int size, back;
    private int[] lista;

    public Fila(int size){
        this.back = 0;
        this.size = size;
        this.lista = new int[size];
    }

    public boolean empty(){
        return back==0;
    }

    public int peek(){
        if(this.empty()){
            throw new IllegalStateException();
        }
        return lista[0];
    }

    public synchronized int poll(){
        if(this.empty()){
            throw new IllegalStateException();
        }
        int number = lista[0];
        int [] aux = new int[size-1];
        for(int i = 1; i <= back-1; i++){
            aux[i-1] = lista[i];
        }
        back--;
        this.lista = aux;
        return number;
    }

    public synchronized void offer(int item){
        if(back == size){
            throw new IllegalStateException();
        }
        lista[back] = item;
        if(++back == size){
            back = size;
        }
    }

    public int size() {
        if(this.empty()) return 0;
        else return back;
    }

    public void getLista(){
        String texto = "";
        for(int a : lista){
            texto = texto + " " + a;
        }
        System.out.print(texto + System.lineSeparator()) ;
    }

    /**
     * A thread que vai colocando numeros
     */
    public static class MyThread extends Thread{
        private Fila fila;

        public MyThread(Fila fila){
            this.fila = fila;
        }

        @Override
        public void run(){
            for(int i = 0; i<100; i++){
                Random r = new Random();
                int low = 0;
                int high = 1000;
                int result = r.nextInt(high - low) + low;
                fila.offer(result);
            }
        }
    }


    public static void main(String[] args) {
        Fila fila = new Fila(100);
        MyThread t1 = new MyThread(fila);
        MyThread t2 = new MyThread(fila);
        MyThread t3 = new MyThread(fila);
        MyThread t4 = new MyThread(fila);
        MyThread t5 = new MyThread(fila);
        MyThread t6 = new MyThread(fila);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(fila.size());
        fila.getLista();
    }
}

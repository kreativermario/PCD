package Sem5;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.*;

public class Mesa{

    private List<Javali> buffer;
    private int capacidade;
    private Lock lock = new ReentrantLock ();
    private Condition notFull = lock.newCondition(); // variavel condicional para o metodo put
    private Condition notEmpty = lock.newCondition(); // variavel condicional para o metodo take


    public Mesa(int capacidade){
        this.capacidade = capacidade;
        buffer = new LinkedList<>();
    }


    public void put(Javali j) throws InterruptedException {
        //TODO Condicao [predicado] - buffer nao estar cheio
        lock.lock(); // Bloquear o cadeado
        try{
            while(buffer.size() == capacidade){ //isFull?
                notFull.await(); // notFull bloqueia
            }
            buffer.add(j);
            notEmpty.signal(); // notificar notEmpty, ja nao esta vazia, avisar os consumidores.
        }finally { // Desbloquear o cadeado
            lock.unlock();
        }
    }


    public synchronized Javali take() throws InterruptedException {
        //TODO Condicao [predicado] - buffer nao estar vazio
        lock.lock();
        try{
            while(buffer.isEmpty()) { //isEmpty?
                notEmpty.await(); // bloqueio condicao not empty
            }
            Javali j = buffer.remove(0); //FIFO
            notFull.signal(); // Condicao not full e verificada
            return j;
        }finally {
            lock.unlock();
        }

    }

    public class Cozinheiro extends Thread{

        private int id; //Id Cozinheiro
        private int max; //Max javalis produzidos
        private int n; //Numero real de javalis produzidos

        public Cozinheiro(int id, int max){
            this. id = id;
            this.max = max;
        }

        @Override
        public void run() {
            for(n = 0; n < max; n++) {
                Javali j = new Javali(n,id); //n-esimo javali do cozinhiero id
                try {
                    put(j);
                    System.out.println("[Produzido] " + j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Cozinheiro id " + id + " cozinhou " + n + " (de " + max + ")");
        }
    }

    public class Glutao extends Thread{

        private int id; //Id glutao
        private int max; //Max javalis consumidos
        private int n; //Numero real de javalis consumidos

        public Glutao(int id, int max){
            this. id = id;
            this.max = max;
        }

        @Override
        public void run() {
            for(n = 0; n < max; n++) {
                try {
                    Javali j = take();
                    System.out.println("[Consumido por glutao " + id + "] " + j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Glutao id "+ id +" consumiu " + n + " (de " + max + ")");
        }
    }

    public static void main(String[] args){
        Mesa mesa = new Mesa(1); //Buffer partilhado, tamanho max 10

        Glutao[] glutoes = new Glutao[2];
        Cozinheiro[] cozinheiros = new Cozinheiro[5];

        for(int i = 0; i < glutoes.length; i++){
            glutoes[i] = mesa.new Glutao(i, 5); // Partilha mesa, consome até 5 javalis
            glutoes[i].start();
        }

        for(int i = 0; i < cozinheiros.length; i++){
            cozinheiros[i] = mesa.new Cozinheiro(i, 1); // Partilha mesa, produz até 2 javalis
            cozinheiros[i].start();
        }

    }
}
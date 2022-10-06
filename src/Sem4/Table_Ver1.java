package Sem4;


import java.util.LinkedList;

/**
 * Exercício 1
 * Produtor - Cozinheiro
 * Consumidor - Glutão
 *  VERSÃO 1
 */
public class Table_Ver1 {
    public final int CAPACITY;
    private LimitedQueue<String> listaJavali;

    public Table_Ver1(int capacity) {
        CAPACITY = capacity;
        listaJavali = new LimitedQueue<>(capacity);
    }

    public int getSize(){
        return listaJavali.size();
    }

    /**
     * Método que vai colocar os javalis
     * @param javali Recebe o javali
     * @throws InterruptedException Exceção para interrupcao
     */
    public synchronized void put(String javali) throws InterruptedException {
        while(listaJavali.size() == CAPACITY){
            System.out.println("ESTÁ CHEIO!");
            wait();
        }
        listaJavali.add(javali);
        System.out.println("[COLOCADO] | " + javali + " | TAMANHO: " + listaJavali.size());
        notifyAll();
    }

    public synchronized String get() throws InterruptedException{
        while(listaJavali.isEmpty()){
            System.out.println("ESTÁ VAZIO!");
            wait();
        }
        notifyAll();
        return listaJavali.poll();
    }


    /**
     * Produtor - Cozinheiro
     */
    public static class Cozinheiro extends Thread{

        private int orderNumber = 1;
        private final int limit;
        private final Table_Ver1 table;

        public Cozinheiro(Table_Ver1 t, int limit){
            this.table = t;
            this.limit = limit;
            Thread t1 = new Thread();
            t1.start();
        }

        @Override
        public void run(){
            try {
                for(int i = 0; i < limit; i++){
                    String javali = getName() +  " --> JAVALI " + orderNumber;
                    table.put(javali);
                    orderNumber++;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("PRODUTOR | " + getName() + " TERMINOU!");
        }

    }


    public static class Glutao extends Thread{

        private final Table_Ver1 table;
        private final int limit;

        public Glutao(Table_Ver1 t, int limit){
            this.limit = limit;
            this.table = t;
            Thread t1 = new Thread();
            t1.start();
        }

        @Override
        public void run(){
            try {
                for(int i = 0; i < limit; i++){
                    String javali = table.get();
                    System.out.println("CONSUMIDOR " + getName() + " \\\\ "+ javali + " \\\\ TAMANHO: " + table.getSize());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


    /**
     * Estrutura de dados fila com tamanho fixo do tipo LinkedList
     * @param <E>
     */
    public static class LimitedQueue<E> extends LinkedList<E> {

        private int limit;

        public LimitedQueue(int limit) {
            this.limit = limit;
        }

        @Override
        public boolean add(E o) {
            boolean added = super.add(o);
            while (added && size() > limit) {
                super.remove();
            }
            return added;
        }
    }

    public static void main(String[] args) {
        Table_Ver1 t = new Table_Ver1(5);
        for(int i = 0; i < 2; i++){
            Cozinheiro c = new Cozinheiro(t,4);
            Glutao g = new Glutao(t,4);
            c.start();
            g.start();
        }
    }

}

package Sem4;


import java.util.LinkedList;

/**
 * Exercício 1
 * Produtor - Cozinheiro
 * Consumidor - Glutão
 *  VERSÃO 2
 */
public class Table_Ver2 {
    public final int CAPACITY;
    private LimitedQueue<String> listaJavali;

    public Table_Ver2(int capacity) {
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
        public static final int TIME = 10;
        private final Table_Ver2 table;

        public Cozinheiro(Table_Ver2 t){
            this.table = t;
            Thread t1 = new Thread();
            t1.start();
        }

        @Override
        public void run(){
            try {
                long t= System.currentTimeMillis();
                long end = t + (TIME * 1000);
                while(System.currentTimeMillis() < end) {
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

        private final Table_Ver2 table;
        public static final int TIME = 10;

        public Glutao(Table_Ver2 t){
            this.table = t;
            Thread t1 = new Thread();
            t1.start();
        }

        @Override
        public void run(){
            try {
                long t= System.currentTimeMillis();
                long end = t + (TIME * 1000);
                while(System.currentTimeMillis() < end) {
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
        Table_Ver2 t = new Table_Ver2(5);
        for(int i = 0; i < 2; i++){
            Cozinheiro c = new Cozinheiro(t);
            Glutao g = new Glutao(t);
            c.start();
            g.start();
        }
    }

}

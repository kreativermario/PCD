package Sem4;


import java.util.LinkedList;

/**
 * Exercício 1
 * Produtor - Cozinheiro
 * Consumidor - Glutão
 *  VERSÃO 3
 */
public class Table_Ver3 {

    private String javali = null;

    public Table_Ver3() {

    }


    /**
     * Método que vai colocar os javalis
     * @param javali Recebe o javali
     * @throws InterruptedException Exceção para interrupcao
     */
    public synchronized void put(String javali) throws InterruptedException {
        while(javali != null){
            System.out.println("ESTÁ CHEIO!");
            wait();
        }
        this.javali = javali;
        System.out.println("[COLOCADO] | " + javali);
        notify();
    }

    public synchronized String get() throws InterruptedException{
        while(javali == null){
            System.out.println("ESTÁ VAZIO!");
            wait();
        }
        String javaliReturn = javali;
        javali = null;
        notify();
        return javaliReturn;
    }


    /**
     * Produtor - Cozinheiro
     */
    public static class Cozinheiro extends Thread{

        private int orderNumber = 1;
        public static final int TIME = 10;
        private final Table_Ver3 table;

        public Cozinheiro(Table_Ver3 t){
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

        private final Table_Ver3 table;
        public static final int TIME = 10;

        public Glutao(Table_Ver3 t){
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
                    System.out.println("CONSUMIDOR " + getName() + " \\\\ "+ javali + " \\\\ ");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public static void main(String[] args) {
        Table_Ver3 t = new Table_Ver3();
        for(int i = 0; i < 2; i++){
            Cozinheiro c = new Cozinheiro(t);
            Glutao g = new Glutao(t);
            c.start();
            g.start();
        }
    }

}

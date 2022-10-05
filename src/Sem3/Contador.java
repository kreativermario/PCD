package Sem3;

/**
 * Exercicio 1 - Incremento concorrente ALINEA A)
 */
public class Contador {
    private int counter;

    public Contador(){
        this.counter = 0;
    }

    /**
     * Uso do synchronized para que seja so uma thread a entrar aqui
     */
    public synchronized void increaseCounter(){
        this.counter++;
    }

    public int getCounter() {
        return counter;
    }

    /**
     * A thread que aumenta o contador
     */
    public static class ContadorThread extends Thread{

        private Contador contador;

        public ContadorThread(Contador contador){
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
        Contador contador = new Contador();
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

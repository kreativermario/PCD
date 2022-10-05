package Sem2;

import javax.naming.Name;
import java.util.Random;

/**
 * Exercício 1 - Escrita concorrida dos nomes, já com as alinhas todas feitas
 */
public class NameThread extends Thread{

    @Override
    public void run () {
        try {
            // Executa 10 vezes
            for(int i=0; i<10; i++) {
                // Imprime o identificador da thread
                System.out.println(getName() + " i: " + i);
                // Tempo a dormir, um random entre 1 e 2 segundos inclusive
                Random r = new Random();
                int low = 1;
                int high = 2;
                int time = r.nextInt(high - low) + low;
                // Multiplicar por 1000 porque sleep é em milisegundos e nos temos segundos (1s = 1000ms)
                sleep(time * 1000);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " foi interrompida!");
        }
        System.out.println(getName() + " terminou!");
    }

    public static void main ( String [] args ) throws InterruptedException {
        // Cria as threads e executa
        NameThread t1 = new NameThread();
        NameThread t2 = new NameThread();
        t1.start();
        t2.start();

        // A thread "main" fica à espera das outras acabar
        try{
            t1.join();
            t2.join();

            /***
             * Alinea c) - dormir 4 segundos e depois interromper - REMOVER AS LINHAS anteriores t1.join() e t2.join()
             */
//            sleep(4000);
//            t1.interrupt();
//            t2.interrupt();

        }catch ( InterruptedException e ) {
            System.out.println("Interrupted main!");
        }
        System.out.println("Main done!");

    }


}

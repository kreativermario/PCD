package Sem5.HorseRace;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class HorseTrack {

    public static final int TRACK_LIMIT = 30;
    public static final int MAX_HORSES = 2;
    private Semaphore mutex = new Semaphore(MAX_HORSES);
    private List<Horse> horseList = new ArrayList<>();

    public void start(){
        for(Horse horse: horseList){
            horse.start();
        }
    }

    private void addHorse(Horse horse) {
        horseList.add(horse);
    }

    private void setWinner(Horse horse){
        for(Horse h:horseList){
            h.interrupt();
        }
        JOptionPane.showMessageDialog(null, "Horse " + horse + " won!");
    }

    private void race(Horse horse) throws InterruptedException {
        try{
            mutex.acquire();
            System.out.println("HORSE: " +
                    Thread.currentThread().toString() + " - ENTREI SEMAFORO -> PERMITS: " + mutex.availablePermits());
            horse.move();
            System.out.println("HORSE: " +
                    Thread.currentThread().toString() + " - MOVI CAVALO -> PERMITS: " + mutex.availablePermits());
        }finally {
            mutex.release();
            System.out.println("HORSE: " +
                    Thread.currentThread().toString() + " - RELEASE -> PERMITS: " + mutex.availablePermits());
        }
    }


    /**
     * A thread do cavalo
     */
    public class Horse extends Thread {
        // Posição do cavalo
        private JTextField location;
        public static final int MIN_TIME = 100;
        public static final int MAX_TIME = 200;

        /**
         * Construtor
         * @param location
         */
        public Horse(JTextField location) {
            this.location = location;
            addHorse(this);
        }

        @Override
        public void run() {
            System.out.println("Inicio run");
            try {
                // Correr durante o tal tamanho definido - 30 passos
                for (int i = 0; i < 30; i++) {
                    race(this);
                    int randomTime = (int) (MIN_TIME + Math.random() * (MAX_TIME-MIN_TIME));
                    sleep(randomTime * 10);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException();
            }

        }

        private void move(){
            // Buscar a posição atual e diminuir
            int value = Integer.parseInt(location.getText());
            value--;
            // verificar se chegou ao fim da corrida
            if(value==0){
                setWinner(this);
            }
            // Colocar o novo valor da posição
            String status = Integer.toString(value);
            location.setText(status);
        }

    }




}

package Sem5;

import Sem2.HorseRace;

import javax.swing.*;
import java.util.Random;

public class Horse extends Thread{

    private JTextField position;
    private HorseTrack track;

    public Horse(JTextField position, HorseTrack track){
        System.out.println("HORSE: " +
                Thread.currentThread().toString() + " - inicio do run");
        this.position = position;
        this.track = track;
    }


    public void move(){
        // Buscar a posição atual e diminuir
        int value = Integer.parseInt(position.getText());
        value--;

        // verificar se chegou ao fim da corrida
        if(value==0){
            // Colocar o pop-up que ganhou
            JOptionPane.showMessageDialog(null, "Horse " + getName() + " won!");
        }
        // Colocar o novo valor da posição
        String status = Integer.toString(value);
        position.setText(status);
    }


    @Override
    public void run(){
        try{
            for(int i = 0; i < HorseTrack.TRACK_LIMIT; i ++){
                track.move(this);

                // Quanto tempo deve dormir
                Random r = new Random();
                int low = 0;
                int high = 100;
                int result = r.nextInt(high - low) + low;

                sleep(result * 10);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}

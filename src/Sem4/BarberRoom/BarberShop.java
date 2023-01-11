package Sem4.BarberRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BarberShop {

    public static final int WAIT_CAPACITY = 3;
    public static final int MIN_WAIT_TIME = 3000; //in miliseconds
    public static final int MAX_WAIT_TIME = 10000; //in miliseconds
    private int numAvailableSeats = WAIT_CAPACITY;
    private Barber barber;

    public BarberShop(){
        barber = new Barber();
        barber.start();
    }


    private boolean isFull(){
        return numAvailableSeats == WAIT_CAPACITY;
    }

    /**
     * Metodo que o cliente usa para tentar cortar cabelo
     */
    public synchronized boolean queue() throws InterruptedException{
        // Cadeiras cheias
        while(isFull()){
            int randomTime = (int) (MIN_WAIT_TIME * Math.random() * (MAX_WAIT_TIME - MIN_WAIT_TIME));
            wait(randomTime);
        }
        // Nao está cheio
        numAvailableSeats--;
        notifyAll();
        return true;
    }

    public synchronized void cut(){



    }

    public class Barber extends Thread{



    }


    public class Client extends Thread{

        public static final int MIN_SLEEP_TIME = 1000; // em milisegundos
        public static final int MAX_SLEEP_TIME = 10000; // em milisegundos
        private int numHaircuts = 0;

        @Override
        public void run() {
            try {
                while(true){
                    if(queue()) numHaircuts++;
                    int randomTime = (int) (MIN_SLEEP_TIME + Math.random()*(MAX_SLEEP_TIME - MIN_SLEEP_TIME));
                    sleep(randomTime);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public void addNumHaircuts() {
            numHaircuts++;
        }
    }









    public static class GUI{
        private JFrame frame;
        private List<Client> clientList = new ArrayList<>();
        private BarberShop barberShop;


        public GUI(int numClients){
            frame = new JFrame("Distribuição Pingo Doce");
            addFrame();
            // para que o botao de fechar a janela termine a aplicacao
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
            frame.pack();
            barberShop = new BarberShop();
            for(int i = 0; i < numClients; i++){
                Client c = barberShop.new Client();
                clientList.add(c);
                c.start();
            }
        }

        private void addFrame(){
            JPanel panel = new JPanel(new GridLayout(2,1));
            JTextField textfield = new JTextField();
            JButton button = new JButton("STOP");



            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            panel.add(textfield);
            panel.add(button);
            frame.add(panel);
        }

        public void open(){
            frame.setVisible(true);
        }




    }







}

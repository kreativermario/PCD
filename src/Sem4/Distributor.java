package Sem4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Distributor {

    private final Supplier supplier;
    private final Client [] clients;
    public static final int CAPACITY = 10;
    private LinkedList<Integer> listaProdutos;
    private JTextField textField;

    public Distributor(JTextField field, int num_clients){
        this.textField = field;
        listaProdutos = new LinkedList<Integer>();
        this.clients = new Client[num_clients];
        this.supplier = new Supplier();
        for(int i = 0; i < clients.length; i++){
            Client c = new Client();
            clients[i] = c;
            c.start();
        }
        supplier.start();
        // escreve-se os conteúdos da lista no campo de texto assim...
        textField.setText(listaProdutos.toString());
    }

    public void stop(){
        for(Client c : clients){
            c.interrupt();
        }
        supplier.interrupt();
    }

    private void updateText(){
        textField.setText(listaProdutos.toString());
    }

    /**
     * Método que vai colocar o produto sucessivamente
     * @param product Produto
     * @throws InterruptedException wait()
     */
    public synchronized void put(Integer product) throws InterruptedException {
        this.listaProdutos.add(product);
        System.out.println("Supplier " + Thread.currentThread().toString() + " inseri produto [" + product + "]"
                + " SIZE: " + listaProdutos.size());
        updateText();
        notifyAll();
    }

    /**
     * Método que retira um produto
     * @return finalProducts Produtos a devolver
     * @throws InterruptedException wait()
     */
    //PROBLEMAS POIS NAO SEI, RETIRA QUANDO NAO ESTA 10 ACIMA...
    public synchronized LinkedList<Integer> take() throws InterruptedException {
        while(listaProdutos.isEmpty() || listaProdutos.size() < CAPACITY){
            System.out.println("RETALHISTA + " + Thread.currentThread().toString() + " - não tem produtos [ESPERANDO]");
            wait();
        }
        LinkedList<Integer> finalProducts = new LinkedList<Integer>(listaProdutos);
        for(int i = 0; i < CAPACITY; i++){
            listaProdutos.poll();
        }
        updateText();
        notifyAll();
        System.out.println("RETALHISTA + " + Thread.currentThread().toString() + " - retirei produtos"
            + " ||| " + finalProducts.toString());
        return finalProducts;
    }

    public class Supplier extends Thread{

        @Override
        public void run(){
            System.out.println("Supplier " + Thread.currentThread().toString() + " - inicio do run.");
            try{
                while(true) {
                    sleep(500);
                    int randomnumber = (int) (0 + Math.random()*(9-0));
                    put(randomnumber);
                }
            } catch (InterruptedException e) {
                System.out.println("SUPPLIER + " + Thread.currentThread().toString() + " - interrupted");
            }
        }
    }


    public class Client extends Thread{

        private int numBought = 0;
        private LinkedList<Integer> boughtItems = new LinkedList<>();

        @Override
        public void run(){
            System.out.println("Retalhista " + Thread.currentThread().toString() + " - inicio do run.");
            try{
                while(true) {
                    sleep(500);
                    boughtItems.addAll(take());
                    numBought++;
                }

            } catch (InterruptedException e) {
                System.out.println("RETALHISTA + " + Thread.currentThread().toString() + " - comprei um total de "
                        + numBought + " lotes");
            }

        }



    }

    public static class GUI{
        private JFrame frame;


        public GUI(){
            frame = new JFrame("Distribuição Pingo Doce");
            addFrame();

            // para que o botao de fechar a janela termine a aplicacao
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
            frame.pack();
        }

        private void addFrame(){
            JPanel panel = new JPanel(new GridLayout(2,1));
            JTextField textfield = new JTextField();
            JButton button = new JButton("STOP");
            Distributor distributor = new Distributor(textfield, 4);
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    distributor.stop();
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



    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.open();

    }


}

package Sem4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Distributor {

    private final Supplier supplier = new Supplier();
    private final Client [] clients;
    public static final int CAPACITY = 10;
    private LinkedList<Integer> listaProdutos;
    private JTextField textField;

    public Distributor(JTextField field, int num_clients){
        this.textField = field;
        listaProdutos = new LinkedList<Integer>();
        this.clients = new Client[num_clients];
        for(int i = 0; i < clients.length; i++){
            clients[i] = new Client();
        }
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
     * Método que vai colocar o produto
     * @param product Produto
     * @throws InterruptedException wait()
     */
    public synchronized void put(Integer product) throws InterruptedException {
        if(listaProdutos.size() >= CAPACITY){
            System.out.println("Supplier " + Thread.currentThread().toString() + " - fila está cheia. [ESPERANDO]"
                    + " SIZE: " + listaProdutos.size());
            wait();
        }
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
        if(listaProdutos.isEmpty() || listaProdutos.size() < CAPACITY){
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

        public Supplier(){
            Thread t = new Thread(this);
            System.out.println("Supplier " + Thread.currentThread().toString() + " - inicio do run.");
            t.start();
        }

        @Override
        public void run(){
            try{
                while(true) {
                    if (this.isInterrupted()) {
                        System.out.println("SUPPLIER + " + Thread.currentThread().toString() + " - interrupted");
                        break;
                    }
                    int min = 0;
                    int max = 9;
                    Random random = new Random();
                    int randomnumber = random.nextInt(max - min) + min;

                    put(randomnumber);

                }
            } catch (InterruptedException e) {
                System.out.println("SUPPLIER + " + Thread.currentThread().toString() + " - interrupted");
            }

        }

    }


    public class Client extends Thread{

        private int numBought = 0;
        private LinkedList<Integer> boughtItems = new LinkedList<Integer>();

        public Client(){
            Thread t = new Thread(this);
            System.out.println("Retalhista " + Thread.currentThread().toString() + " - inicio do run.");
            t.start();
        }

        @Override
        public void run(){

            try{
                while(true) {
                    if(this.isInterrupted()){
                        System.out.println("RETALHISTA + " + Thread.currentThread().toString() + " - comprei um total de "
                                + numBought + " lotes");
                        break;
                    }
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


    public static void main(String[] args) {
        JFrame frame = new JFrame("Distribuição Pingo Doce");
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

        // para que o botao de fechar a janela termine a aplicacao
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
        frame.pack();
        frame.setVisible(true);

    }


}

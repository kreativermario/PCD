package Sem4.BanqueteJavali;
import java.util.LinkedList;
import java.util.List;

public class Mesa {

    private List<Javali> buffer;
    private int capacidade;

    public Mesa(int capacidade){
        this.capacidade = capacidade;
        buffer = new LinkedList<>();
    }


    public synchronized void put(Javali j) throws InterruptedException {
        while(buffer.size() == capacidade){ //isFull?
            this.wait(); // Bloqueia not full
        }
        buffer.add(j);
        this.notifyAll(); // not empty e verificada
    }


    public synchronized Javali take() throws InterruptedException {
        while(buffer.isEmpty()) { //isEmpty?
            this.wait(); //Espera enquanto não tem javalis no buffer
        }
        Javali j = buffer.remove(0); //FIFO
        this.notifyAll(); ////Novo Javali no buffer
        return j;
    }

    public class Cozinheiro extends Thread{

        private int id; //Id Cozinheiro
        private int max; //Max javalis produzidos
        private int n; //Numero real de javalis produzidos

        public Cozinheiro(int id, int max){
            this. id = id;
            this.max = max;
        }

        @Override
        public void run() {
            for(n = 0; n < max; n++) {
                Javali j = new Javali(n,id); //n-esimo javali do cozinhiero id
                try {
                    put(j);
                    System.out.println("[Produzido] " + j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Cozinheiro id " + id + " cozinhou " + n + " (de " + max + ")");
        }
    }

    public class Glutao extends Thread{

        private int id; //Id glutao
        private int max; //Max javalis consumidos
        private int n; //Numero real de javalis consumidos

        public Glutao(int id, int max){
            this. id = id;
            this.max = max;
        }

        @Override
        public void run() {
            for(n = 0; n < max; n++) {
                try {
                    Javali j = take();
                    System.out.println("[Consumido por glutao " + id + "] " + j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Glutao id "+ id +" consumiu " + n + " (de " + max + ")");
        }
    }

    public static void main(String[] args){
        Mesa mesa = new Mesa(10); //Buffer partilhado, tamanho max 10

        Glutao[] glutoes = new Glutao[2];
        Cozinheiro[] cozinheiros = new Cozinheiro[5];

        for(int i = 0; i < glutoes.length; i++){
            glutoes[i] = mesa.new Glutao(i, 5); // Partilha mesa, consome até 5 javalis
            glutoes[i].start();
        }

        for(int i = 0; i < cozinheiros.length; i++){
            cozinheiros[i] = mesa.new Cozinheiro(i, 1); // Partilha mesa, produz até 2 javalis
            cozinheiros[i].start();
        }

    }
}
package Sem3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Account {
    private AtomicInteger balance;

    public Account(){
        this.balance = new AtomicInteger();
    }

    public void deposit(int amount){
        balance.addAndGet(amount);
    }

    public int getBalance(){
        return balance.get();
    }

    public static class Client extends Thread{
        private Account account;
        private int totalDeposited;

        public Client(Account account){
            this.account = account;
        }

        @Override
        public void run(){
            Random r = new Random();
            int low = 0;
            int high = 100;
            int result = r.nextInt(high - low) + low;
            account.deposit(result);
            totalDeposited+=result;
            if(this.isInterrupted()){
                System.out.println( getName() + "Interrupted!");
                return;
            }

        }

        public int getTotalDeposited(){
            return totalDeposited;
        }

    }

    public static void main(String[] args) {
        Account account = new Account();
        Client [] clients = new Client[10];
        int totalDeposited = 0;
        try{
            for(int i = 0; i< 10; i++){
                Client c = new Client(account);
                clients[i] = c;
                c.start();
            }

            sleep(10000);
            for(Client c : clients){
                totalDeposited += c.getTotalDeposited();
                c.interrupt();
                c.join();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Balanço: " + account.balance);
        System.out.println("Total Depositado: " + totalDeposited);








    }

}

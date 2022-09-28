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
        Client c1 = new Client(account);
        Client c2 = new Client(account);
        Client c3 = new Client(account);
        Client c4 = new Client(account);
        Client c5 = new Client(account);
        Client c6 = new Client(account);
        Client c7 = new Client(account);
        Client c8 = new Client(account);
        Client c9 = new Client(account);
        Client c10 = new Client(account);
        Client [] clients = new Client[10];
        clients[0] = c1;
        clients[1] = c2;
        clients[2] = c3;
        clients[3] = c4;
        clients[4] = c5;
        clients[5] = c6;
        clients[6] = c7;
        clients[7] = c8;
        clients[8] = c9;
        clients[9] = c10;
        int totalDeposited = 0;
        try{
            c1.start();
            c2.start();
            c3.start();
            c4.start();
            c5.start();
            c6.start();
            c7.start();
            c8.start();
            c9.start();
            c10.start();
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

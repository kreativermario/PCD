package Sem8;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Account implements Comparable{
	double balance;
	private int id;
	Lock lock= new ReentrantLock();
	public Account(int id, double balance) {
		super();
		this.id = id;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	void withdraw(double amount){
		lock.lock();
		balance -= amount;
		lock.unlock();
	} 
	void deposit(double amount){
		lock.lock();
		balance += amount;
		lock.unlock();
	} 
	void transfer(Account to, double amount){
		if(this.compareTo(to) > 0){
			lock.lock();
			to.lock.lock();
		}else{
			to.lock.lock();
			lock.lock();
		}
		withdraw(amount);
		to.deposit(amount);

		if(this.compareTo(to) > 0){
			lock.unlock();
			to.lock.unlock();
		}else{
			to.lock.unlock();
			lock.unlock();
		}
	}

	@Override
	public int compareTo(Object o) {
		Account other = (Account) o;
		return other.getId() - this.id;
	}

	private class Transferer extends Thread{
		private Account to;

		public Transferer(Account to) {
			this.to = to;
		}

		@Override
		public void run() {
			for(int i=0; i!=NUM_TRANSFERENCIAS;i++)
				transfer(to, 1);
		}
	}

	private static final int NUM_TRANSFERENCIAS = 1000;
	public static void main(String[] args) throws InterruptedException{
		Account a1=new Account(1,NUM_TRANSFERENCIAS+1);
		Account a2=new Account(2,NUM_TRANSFERENCIAS+1);
		Thread t1=a1.new Transferer(a2);
		t1.start();
		Thread t2=a2.new Transferer(a1);
		t2.start();
		t1.join();
		t2.join();
		System.out.println("Saldos:"+a1.balance+":"+a2.balance);
	}
}

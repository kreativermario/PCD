package Sem8.PhilosophersDinner.Option1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
	private int id;
	private Lock lock = new ReentrantLock(true);

	public Fork(int id){
		this.id=id;
	} 
	public void up(){
		lock.lock();
		try{
			System.out.println("Fork("+id+") up");
		}finally {
			lock.unlock();
		}
	} 
	public void down(){
		lock.lock();
		try{
			System.out.println("Fork("+id+") down");
		}finally {
			lock.unlock();
		}

	} 
}

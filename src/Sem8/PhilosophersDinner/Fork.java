package Sem8.PhilosophersDinner;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
	private int id;
	private Lock lock = new ReentrantLock(true);

	public Fork(int id){
		this.id=id;
	}

	public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
		boolean locked = lock.tryLock(timeout, unit);
		if (locked) {
			System.out.println("Fork(" + id + ") up");
		}
		return locked;
	}

	public void unlock() {
		lock.unlock();
		System.out.println("Fork(" + id + ") down");
	}

}

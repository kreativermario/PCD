package Sem8.PhilosophersDinner;

import java.util.concurrent.TimeUnit;

public class Philosopher extends Thread{
	private static final int NUM_ITERATIONS=2000;
	private static final long TIMEOUT = 1;
	private static final TimeUnit TIMEOUT_UNIT = TimeUnit.MILLISECONDS;
	int id,times_eat=0; 
	Fork leftFork, rightFork; 
	public Philosopher(int id,Fork leftFork,Fork rightFork){
		this.id=id; 
		this.leftFork=leftFork; 
		this.rightFork=rightFork;
	}


	public void thinking(){
		System.out.println("Philosopher("+id+") thinking");
//		try {
//			sleep(1);
//		} catch (InterruptedException e) {
//		}
	} 
	public void eating(){
		times_eat++; 
		System.out.println("Philosopher("+id+") eating");
	} 
	public void run(){
		while(times_eat!=NUM_ITERATIONS){
			thinking();
			boolean leftForkLocked = false;
			boolean rightForkLocked = false;
			try {
				rightForkLocked = rightFork.tryLock(TIMEOUT, TIMEOUT_UNIT );
				if (rightForkLocked) {
					leftForkLocked = leftFork.tryLock(TIMEOUT, TIMEOUT_UNIT);
					if (leftForkLocked) {
						eating();
					}
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				if (leftForkLocked) {
					leftFork.unlock();
				}
				if (rightForkLocked) {
					rightFork.unlock();
				}
			}
			System.out.println("Philosopher(" + id + ") ate #" + times_eat);
		} 
		System.out.println("Philosopher("+id+") leaves the room at "+System.currentTimeMillis());
	}

}

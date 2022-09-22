package Sem2;
import java.util.Observable;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Car extends Observable implements Runnable {
	private int id;
	private int limit;
	private Thread thread;
	private int position=0;
	
	public int getId() {
		return id;
	}

	public int getPosition() {
		return position;
	}

	public Thread getThread() {
		return thread;
	}

	public void increasePosition(){
		if(position == limit - 1){
			return;
		}
		this.position += 1;
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void run() {
		try{
			for(int i = 0; i< limit; i++) {
				Random r = new Random();
				int low = 0;
				int high = 100;
				int result = r.nextInt(high - low) + low;
				increasePosition();
				sleep(result);
			}
		} catch (InterruptedException e) {

		}

	}

	public Car(int id, int limit) {
		super();
		this.id = id;
		this.limit = limit;
		Thread thread1 = new Thread(this);
		this.thread = thread1;
		thread1.start();
	}
	
	
}

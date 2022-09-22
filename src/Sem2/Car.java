package Sem2;
import java.util.Observable;

public class Car extends Observable {
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

	public Car(int id, int limit) {
		super();
		this.id = id;
		this.limit = limit;
		MyRunnable mr = new MyRunnable(this, limit);
		Thread thread1 = new Thread(mr);
		this.thread = thread1;
		thread1.start();
	}
	
	
}

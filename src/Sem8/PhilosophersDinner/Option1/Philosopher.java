package Sem8.PhilosophersDinner.Option1;

public class Philosopher extends Thread {
	private static final int NUM_ITERATIONS=2000;
	int id,times_eat=0; 
	Fork leftFork, rightFork;
	public Philosopher(int id, Fork leftFork, Fork rightFork){
		this.id=id; 
		this.leftFork=leftFork; 
		this.rightFork=rightFork;
	} 
	public void thinking(){ 
		System.out.println("Philosopher("+id+") thinking");
		try {
			sleep(1);
		} catch (InterruptedException e) {
		}
	} 
	public void eating(){
		times_eat++; 
		System.out.println("Philosopher("+id+") eating");
	} 
	public void run(){
		while(times_eat!=NUM_ITERATIONS){ 
			thinking();

			leftFork.up();
			rightFork.up();

			eating();

			rightFork.down();
			leftFork.down(); 
			System.out.println("Philosopher("+id+") ate #"+times_eat);
		} 
		System.out.println("Philosopher("+id+") leaves the room at "+System.currentTimeMillis());
	}
}

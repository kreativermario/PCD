package Sem8.PhilosophersDinner;

public class Dinner {
	public static void main(String[] args){
		Fork f1=new Fork(1);
		Fork f2=new Fork(2);
		Fork f3=new Fork(3);
		Fork f4=new Fork(4);
		Fork f5=new Fork(5);
		new Philosopher(1,f1,f2).start();
		new Philosopher(2,f2,f3).start();
		new Philosopher(3,f3,f4).start();
		new Philosopher(4,f4,f5).start();
		new Philosopher(5,f5,f1).start();
		System.out.println("All Philosophers started");
	}
}

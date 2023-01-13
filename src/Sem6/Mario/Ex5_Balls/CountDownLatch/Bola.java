package Sem6.Mario.Ex5_Balls.CountDownLatch;

import java.awt.*;
import java.util.Observable;
import java.util.concurrent.CountDownLatch;

public class Bola extends Observable implements DrawableBall, Runnable {
	private float estado=0;
	public static final int SLEEP_TIME = 5; // em ms
	private Color color=new Color((int)(Math.random()*256), 
			(int)(Math.random()*256), (int)(Math.random()*256));
	private CountDownLatch latch;

	public Bola(CountDownLatch latch){
		this.latch = latch;
	}

	@Override
	public void run() {
		// TODO
		try{
			while(true){
				if(bolaAtingiuLimite()){
					latch.countDown();
					System.out.println(latch.getCount());
					break;
				}
				double random = 0.01 * Math.random() * (0.1 - 0.01);
				estado += random;
				setChanged();
				notifyObservers();
				Thread.sleep(SLEEP_TIME);
			}
		}catch (InterruptedException e){

		}




	}

	public boolean bolaAtingiuLimite(){
		return estado>=1;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public float getX() {
		return estado;
	}

	@Override
	public int getSize() {
		return 10;
	}

}

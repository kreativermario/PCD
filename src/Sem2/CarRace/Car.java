package Sem2.CarRace;
import java.util.Observable;
import java.util.Random;

import static java.lang.Thread.sleep;

/***
 * Exercício 3 - Corrida de automóveis
 *
 * Thread do carro
 * Como o java nao te deixa dar extend a varios, logo nao consegues fazer extend ao Observable e a Thread
 * Logo dá-se implement do Runnable
 */
public class Car extends Observable implements Runnable {
	// Id do carro
	private int id;
	// Limite da pista a percorrer
	private int limit;
	// Guarda a thread do carro
	private Thread thread;
	// Posicao atual do carro
	private int position=0;

	/**
	 * Construtor do carro
	 * @param id
	 * @param limit
	 */
	public Car(int id, int limit) {
		super();
		this.id = id;
		this.limit = limit;
		// Cria a thread associada ao carro
		Thread thread1 = new Thread(this);
		this.thread = thread1;
		thread1.start();
	}

	/**
	 * Método que vai aumentar a posição do carro
	 */
	public void increasePosition(){
		// Se o carro chegou ao limite da pista, nao aumentar
		if(position == limit - 1){
			return;
		}
		// Aumentar posicao, e avisar que houve mudanças! Vai para o Track.java
		this.position += 1;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Método da thread, que vai aumentado a posição do carro
	 */
	@Override
	public void run() {
		try {
			for (int i = 0; i < limit; i++) {
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

	/**
	 * Get do ID
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get da posicao atual
	 * @return position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Get da thread
	 * @return thread
	 */
	public Thread getThread() {
		return thread;
	}


}




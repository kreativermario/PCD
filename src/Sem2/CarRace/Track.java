package Sem2.CarRace;

import Sem2.CarRace.Car;

import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

/***
 * Exercício 3 - Corrida de automóveis
 */
public class Track extends JComponent implements Observer{
	// Quantos carros na corrida
	private int numCars;
	// Qual é a distancia a percorrer
	private int numSteps;
	// As posicoes dos carros todos
	private int[] carPositions;
	// Os carros todos
	private Car[] cars;

	// A imagem dos carros
	private ImageIcon icon = new ImageIcon("azul.gif");

	/**
	 * Construtor
	 * @param numCars
	 * @param numSteps
	 */
	public Track(int numCars, int numSteps) {
		this.numCars = numCars;
		this.numSteps = numSteps;
		carPositions = new int[numCars];
		cars = new Car[numCars];
	}

	/**
	 * Método que vai mexer o carro
	 * @param car id do carro a mexer
	 * @param position posicao para onde ele vai
	 */
	public void moveCar(int car, int position) {
		if (car < 0 || car >= numCars)
			throw new IllegalArgumentException("invalid car index: " + car);
		if (position < 0 || position >= numSteps)
			throw new IllegalArgumentException("invalid position: " + position);
		carPositions[car] = position;
		repaint();
	}

	/**
	 * Adiciona o carro à lista de "threads" carros.
	 * @param car
	 */
	public void addCar(Car car){
		this.cars[car.getId()] = car;
	}

	/**
	 * Quem ganhou?
	 * @param car
	 */
	private void setWinner(Car car){
		// O id do carro que ganhou
		int winnerId = car.getId();
		// Percorrer a lista de threads e interromper!
		for(Car carro: cars){
			carro.getThread().interrupt();
		}
		// Mostrar quem ganhou
		JOptionPane.showMessageDialog(null, "Car " + winnerId + " won!");
	}

	/**
	 * Método que atualiza as posições dos carros todos!
	 * @param positions
	 */
	public void updateCarPositions(int[] positions) {
		if(carPositions.length!=positions.length)
			throw new IllegalArgumentException("wrong array length: "+ positions.length);
		
		for(int i = 0; i < positions.length; i++)
			carPositions[i] = positions[i];
		
		repaint();
	}

	/**
	 * Aquilo que realmente pinta onde os carros estão
	 * @param g the <code>Graphics</code> object to protect
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		double deltaY = ((double) getHeight()) / (numCars + 1);
		double deltaX = ((double) getWidth() - icon.getIconWidth()) / numSteps;
		for (int i = 0; i < numCars; i++) {
			g.drawLine(0, (int) (deltaY * (i + 1)), getWidth(),
					(int) (deltaY * (i + 1)));
			g.drawImage(icon.getImage(), (int) (carPositions[i] * deltaX),
					(int) (deltaY * (i + 1)) - icon.getIconHeight(), null);
		}
	}


	/**
	 * O método mais importante, aquele que faz tudo
	 * @param arg0     the observable object.
	 * @param arg1   an argument passed to the {@code notifyObservers}
	 *                 method.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// O objeto Observable neste caso é o Carro
		// O carro (thread) vai alertar que houve mudanças, e vem para aqui!
		Car updatedCar=(Car)arg0;

		// Ver se chegou ao fim
		if(updatedCar.getPosition() == this.numSteps - 1){
			setWinner(updatedCar);
		}

		// Mexer a posicao do carro
		moveCar(updatedCar.getId(), updatedCar.getPosition());

		// Redraw everything!
		invalidate();

	}
}

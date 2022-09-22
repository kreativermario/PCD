package Sem2;

import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;


public class Track extends JComponent implements Observer{
	private int numCars;
	private int numSteps;
	private int[] carPositions;
	private Car[] cars;

	private int winnerId;
	private ImageIcon icon = new ImageIcon("azul.gif");

	public Track(int numCars, int numSteps) {
		this.numCars = numCars;
		this.numSteps = numSteps;
		carPositions = new int[numCars];
		cars = new Car[numCars];
	}

	public void moveCar(int car, int position) {
		if (car < 0 || car >= numCars)
			throw new IllegalArgumentException("invalid car index: " + car);
		if (position < 0 || position >= numSteps)
			throw new IllegalArgumentException("invalid position: " + position);
		carPositions[car] = position;
		repaint();
	}

	public void addCar(Car car){
		this.cars[car.getId()] = car;
	}

	private void setWinner(Car car){
		this.winnerId = car.getId();
		for(Car carro: cars){
			carro.getThread().interrupt();
		}
		JOptionPane.showMessageDialog(null, "Car " + winnerId + " won!");
	}

	public void updateCarPositions(int[] positions) {
		if(carPositions.length!=positions.length)
			throw new IllegalArgumentException("wrong array length: "+ positions.length);
		
		for(int i = 0; i < positions.length; i++)
			carPositions[i] = positions[i];
		
		repaint();
	}

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

	@Override
	public void update(Observable arg0, Object arg1) {
		Car updatedCar=(Car)arg0;
		if(updatedCar.getPosition() == this.numSteps - 1){
			setWinner(updatedCar);
		}
		moveCar(updatedCar.getId(), updatedCar.getPosition());
		// Redraw everything!
		invalidate();

	}
}

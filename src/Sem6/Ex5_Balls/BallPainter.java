package Sem6.Ex5_Balls;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

public class BallPainter extends JLabel implements Observer {

	LinkedList<DrawableBall> balls = new LinkedList<DrawableBall>();

	public void paint(Graphics g) {
		super.paint(g);

		for (int i=0; i<balls.size();i++) {
			paintBall(g, balls.get(i),i);
		}
	}

	public void paintBall(Graphics g, DrawableBall b, int numBall) {
		g.setColor(b.getColor());
		g.fillOval((int)(b.getX()*getWidth()) - b.getSize() / 2, 
				(int)((float)numBall/balls.size()*getHeight()) - b.getSize() / 2,
				b.getSize(), b.getSize());
	}

	public void addBall(DrawableBall b) {
		balls.add(b);
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}
}

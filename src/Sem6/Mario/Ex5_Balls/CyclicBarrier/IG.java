package Sem6.Mario.Ex5_Balls.CyclicBarrier;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.*;

import javax.swing.JButton;
import javax.swing.JFrame;

public class IG {
	ArrayList<Bola> bolas= new ArrayList<>();
	public static final int TAMANHO_POOL = 6;
	public static final int NUM_WINNERS = 3;
	public CyclicBarrier barrier = new CyclicBarrier(NUM_WINNERS + 1);
	private ExecutorService threadPool = Executors.newFixedThreadPool(TAMANHO_POOL);;

	public void addContent(){
		JFrame janela= new JFrame("hh");
		janela.setLayout(new BorderLayout());
		BallPainter painter=new BallPainter();
		janela.add(painter, BorderLayout.CENTER);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int i=0;i<25;i++){
			Bola bola=new Bola(barrier);
			bola.addObserver(painter);
			bolas.add(bola);
			painter.addBall(bola);
		}
		
		JButton start=new JButton("Start");
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO criar ThreadPool. Aqui são simplesmente lançadas as threads.
				for(Bola bola : bolas){
					threadPool.submit(bola);
				}

//				for(int i = 0; i < TAMANHO_POOL; i++){
//					int random =(int) (0 + Math.random() * (bolas.size()));
//					System.out.println(random);
//					threadPool.submit(bolas.get(random));
//				}

			}
		});
		janela.add(start, BorderLayout.SOUTH);
		janela.setSize(800, 600);
		janela.setVisible(true);
		Thread endingThread = new Thread(){
			@Override
			public void run(){
				try {
					barrier.await();
					threadPool.shutdownNow();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} catch (BrokenBarrierException e) {
					throw new RuntimeException(e);
				}
			}

		};
		endingThread.start();
	}
	public static void main(String[] args) {
		new IG().addContent();

	}

}

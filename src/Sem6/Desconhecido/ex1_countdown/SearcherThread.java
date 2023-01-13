package Sem6.Desconhecido.ex1_countdown;

import java.util.concurrent.CountDownLatch;


class SearcherThread extends Thread{
	private String myText;
	private String textToFind;
	private CountDownLatch latch;
	private int result=-1;

	public SearcherThread(String myText, String textToFind,
						  CountDownLatch latch) {
		this.myText = myText;
		this.textToFind = textToFind;
		this.latch = latch;
	}

	public String getMyText() {
		return myText;
	}

	public int getResult() {
		return result;
	}

	@Override
	public void run() {
		long startTime = System.nanoTime();
		result=myText.indexOf(textToFind);

		latch.countDown();

		long endTime = System.nanoTime();
		long time = (long) ((endTime - startTime)/(1e6));

		System.err.println("Thread finishing at:"+(time)+"ms");

	}
}


package Sem6.ex1_countdown;

import java.util.concurrent.CountDownLatch;


public class MainCountdown {
	static final int NUM_DOCUMENTS_TO_BE_SEARCHED=1000;
	static final int STRING_LENGTH=1024;
	static final String STRING_TO_BE_FOUND="huik";

	public static void main(String[] args) {
		final long initTime=System.currentTimeMillis();
		SearcherThread[] threads=new SearcherThread[NUM_DOCUMENTS_TO_BE_SEARCHED];
		CountDownLatch latch= new CountDownLatch(NUM_DOCUMENTS_TO_BE_SEARCHED);

		Thread summary = new Thread() {
			@Override
			public void run() {
				try{
					// Fica à espera que as SearcherThreads acabem
					latch.await();
				} catch (InterruptedException e){
					e.printStackTrace();
				}

				//Summary - Resume os resultados
				int count=0;
				for(SearcherThread t:threads)
					if(t.getResult()!=-1){
						System.out.println("Found at "+t.getResult()); //+" in "+t.getMyText());
						count++;
					}
				System.out.println("Search DONE. Found:"+count+" Time:"+
						(System.currentTimeMillis()-initTime)+"ms");

			}
		};
		summary.start();
		RandomString rs=new RandomString(STRING_LENGTH);
		for(int i=0; i!=NUM_DOCUMENTS_TO_BE_SEARCHED;i++){
			threads[i]=new SearcherThread(rs.nextString(),
					STRING_TO_BE_FOUND, latch);
			threads[i].start();
		}
	}
}

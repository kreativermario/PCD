package Sem6.Ex1Barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class SearcherThread extends Thread{
    private String myText;
    private String textToFind;
    private CyclicBarrier barrier;
    private int result=-1;

    public SearcherThread(String myText, String textToFind,
                          CyclicBarrier barrier) {
        this.myText = myText;
        this.textToFind = textToFind;
        this.barrier = barrier;
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
        try {
            barrier.await();
            long endTime = System.nanoTime();
            long time = (long) ((endTime - startTime)/(1e6));
            System.err.println("Thread finishing at:" + time);
        } catch (InterruptedException |
                 BrokenBarrierException e) {
        }
    }


}


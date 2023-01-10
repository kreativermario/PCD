package Sem4;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T>{
    private Queue<T> queue = new LinkedList<T>();
    private int limit;

    public BlockingQueue(int limit){
        if(limit <= 0){
            throw new IllegalArgumentException("Limite tem de ser número positivo!");
        }
        this.limit = limit;
    }

    public void put(T object) throws InterruptedException {
        if(queue.size() >= limit){
            wait();
        }
        queue.offer(object);
        notifyAll();
    }

    public T take() throws InterruptedException {
        if(queue.size()==0){
            wait();
        }
        T object = queue.poll();
        notifyAll();
        return object;
    }

    public int size(){
        return queue.size();
    }

    public void clear(){
        queue.clear();
    }

    public static class Client extends Thread{
        public Client(){
            Thread t1 = new Thread(this);
            t1.start();
        }

        @Override
        public void run(){

        }

    }



}

package Sem7.TimeServer;

import Sem7.EchoServer.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;

public class TimeServer {

    private ServerSocket serverSocket;
    public static final int PORT = 2424;
    public static final int TIME_INTERVAL = 1000; //em ms

    public TimeServer(){
        try{
            serverSocket = new ServerSocket(PORT); // Throws IOException
        }catch (IOException e){
            System.err.println("Error connecting server... aborting!");
            System.exit(1);
        }
    }

    public void runServer(){
        try {
            while (true){
                waitForConnection();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void waitForConnection() throws IOException{
        Socket connection = serverSocket.accept();
        ConnectionHandler connectionHandler = new ConnectionHandler(connection);
        connectionHandler.start();
    }

    public class ConnectionHandler extends Thread{

        private Socket connection;
        private ObjectInputStream input;
        private ObjectOutputStream output;

        public static final int TIMEOUT = 2000;

        public ConnectionHandler(Socket connection) throws IOException {
            this.connection = connection;
            output = new ObjectOutputStream(connection.getOutputStream());
            input = new ObjectInputStream(connection.getInputStream());
        }

        @Override
        public void run() {
            try{
                while(true){
                    processConnection();
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                closeConnection();
            }
        }

        private void processConnection() throws IOException, ClassNotFoundException {
            try {
                while(true){
                    TimeMessage timeMessage = new TimeMessage(System.currentTimeMillis());
                    output.writeObject(timeMessage);
                    output.reset();
                    connection.setSoTimeout(TIMEOUT);
                    ReceptionConfirmationMessage confirmationMessage = (ReceptionConfirmationMessage) input.readObject();
                    System.out.println((confirmationMessage.isReceivedStatus()));
                    sleep(TIME_INTERVAL);

                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void closeConnection(){
            System.out.println("Closing connection...");
            try {
                input.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                output.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                connection.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        TimeServer timeServer = new TimeServer();
        timeServer.runServer();
    }



}

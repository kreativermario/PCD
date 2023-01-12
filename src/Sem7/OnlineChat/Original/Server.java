package Sem7.OnlineChat.Original;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server extends Thread {
    private ServerSocket server;
    public static final int PORT = 2424;
    protected List<ObjectOutputStream> clients;

    public Server() {
        try {
            server = new ServerSocket(PORT); // Throws IOException
            clients = Collections
                    .synchronizedList(new ArrayList<ObjectOutputStream>());
        } catch (IOException e) {
            System.err.println("Error connecting server... aborting!");
            System.exit(1);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                waitForConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void waitForConnection() throws IOException {
        System.out.println("Waiting for connection...");
        Socket connection = server.accept();
        ConnectionHandler connectionHandler = new ConnectionHandler(connection);
        connectionHandler.start();
    }

    public synchronized void broadcast(String message) throws IOException, InterruptedException {
        Message send = new Message(message);
        for(ObjectOutputStream out : clients){
            out.writeObject(send);
            out.flush();
            sleep(1000);
        }

    }


    /**
     * Classe que trata das conexões dos vários clientes, é a thread que trata
     */
    public class ConnectionHandler extends Thread {

        // O que é necessário para ligar um cliente
        private Socket connection;
        private ObjectInputStream input;
        private ObjectOutputStream out;

        public ConnectionHandler(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                getStreams();
                proccessConnection();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }

        }

        public void getStreams() throws IOException {
            //TODO Autoflush, quando escrevo algo, manda logo
            out = new ObjectOutputStream(connection.getOutputStream());
            clients.add(out);
            input = new ObjectInputStream(connection.getInputStream());

        }

        public void proccessConnection() throws IOException, ClassNotFoundException {
            System.out.println("Successful connection, starting proccessing...");
            try {
                while (true) {
                    Message message = (Message) input.readObject();
                    System.out.println("Broadcasting message...");
                    broadcast(message.getMessage());
                    System.out.println("Received Message: " + message.getMessage());
                    sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public void closeConnection() {
            /**
             * Este método não é o mais correto, porque se input der erro não vai fechar o resto tipo o output,
             * RESOURCE LEAK! Teria que fazer try catch para cada um.
             */
            try {
                input.close();
                connection.close();
                clients.remove(out);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }




    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}

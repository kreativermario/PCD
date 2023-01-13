package Sem7.BroadcastEcho;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                    .synchronizedList(new ArrayList<>());
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

    public class ConnectionHandler extends Thread{

        private Socket socket;
        private ObjectInputStream in;

        public ConnectionHandler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new ObjectInputStream(socket.getInputStream());
                clients.add(new ObjectOutputStream(socket.getOutputStream()));
                while (true){
                    Message message = (Message) in.readObject();
                    broadcast(message.getMessage());
                    sleep(1000);
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }



}

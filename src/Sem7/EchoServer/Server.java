package Sem7.EchoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket server;

    // O que é necessário para ligar um cliente
    private Socket connection;
    private BufferedReader input;
    private PrintWriter output;
    private static final int PORT = 2022;

    public Server() {
        try{
            server = new ServerSocket(PORT); // Throws IOException
        }catch (IOException e){
            System.err.println("Error connecting server... aborting!");
            System.exit(1);
        }


    }

    public void runServer(){

    }


    public void waitForConnection() throws IOException{
        System.out.println("Waiting for connection...");
        connection = server.accept();
    }

    public void getStream(){

    }

    public void proccessConnection(){

    }

    public void closeConnection(){

    }


    public static void main(String[] args) {

    }

}

package Sem7.EchoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket server;

    // O que é necessário para ligar um cliente
    private Socket connection;
    private BufferedReader input;
    private PrintWriter output;
    public static final int PORT = 2022;

    public Server() {
        try{
            server = new ServerSocket(PORT); // Throws IOException
        }catch (IOException e){
            System.err.println("Error connecting server... aborting!");
            System.exit(1);
        }
    }

    public void runServer(){
        try {
            waitForConnection();
            getStreams();
            proccessConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }


    public void waitForConnection() throws IOException{
        System.out.println("Waiting for connection...");
        connection = server.accept();
    }

    public void getStreams() throws IOException{
        //TODO Autoflush, quando escrevo algo, manda logo
        output = new PrintWriter(connection.getOutputStream(), true);

        input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

    }

    public void proccessConnection() throws IOException{
        System.out.println("Successful connection, starting proccessing...");
        while(true){
            String message = input.readLine();

            if("FIM".equals(message)) break;

            System.out.println("ECHO: " + message);

            output.println("Echo: " + message);
        }
    }

    public void closeConnection() {
        /**
         * Este método não é o mais correto, porque se input der erro não vai fechar o resto tipo o output,
         * RESOURCE LEAK! Teria que fazer try catch para cada um.
         */
        try{
            input.close();
            output.close();
            connection.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        Server server = new Server();
        server.runServer();
    }

}

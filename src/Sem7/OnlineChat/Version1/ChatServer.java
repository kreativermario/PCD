package Sem7.OnlineChat.Version1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private ServerSocket server;
    public static final int PORT = 2022;
    private String chat;

    public ChatServer() {
        try{
            server = new ServerSocket(PORT); // Throws IOException
        }catch (IOException e){
            System.err.println("Error connecting server... aborting!");
            System.exit(1);
        }
    }

    public void writeChat(String message){
        chat += message;
    }

    public void runServer(){
        while (true){
            try {
                waitForConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void waitForConnection() throws IOException{
        System.out.println("Waiting for connection...");
        Socket connection = server.accept();
        ConnectionHandler connectionHandler = new ConnectionHandler(connection);
        connectionHandler.start();
    }

    /**
     * Classe que trata das conexões dos vários clientes, é a thread que trata
     */
    public class ConnectionHandler extends Thread{

        // O que é necessário para ligar um cliente
        private Socket connection;
        private BufferedReader input;


        public ConnectionHandler(Socket connection){
            this.connection = connection;
            OutputWriter outputWriter = new OutputWriter(connection);
            outputWriter.start();
        }

        @Override
        public void run(){
            try {
                getStreams();
                proccessConnection();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }

        }

        public void getStreams() throws IOException{
            //TODO Autoflush, quando escrevo algo, manda logo

            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        }

        public void proccessConnection() throws IOException{
            System.out.println("Successful connection, starting proccessing...");
            try {
                while(true){
                    String message = input.readLine();
                    if(message != null){
                        writeChat(message);
                    }
                    //System.out.println(message + System.lineSeparator());
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
            try{
                input.close();
                connection.close();
            }catch (IOException e){
                e.printStackTrace();
            }

        }

        public class OutputWriter extends Thread {

            private PrintWriter output;

            public OutputWriter(Socket socket) {
                try {
                    output = new PrintWriter(socket.getOutputStream(), true);
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }
            }

            @Override
            public void run() {
                try {
                    while (true) {
                        if(chat != null){
                            System.out.println("Sending chat..");
                            System.out.println(chat);
                            output.println(chat);
                            output.flush();
                        }
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    closeOutput();
                }

            }

            private void closeOutput(){
                output.close();
            }
        }



    }


    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.runServer();
    }

}


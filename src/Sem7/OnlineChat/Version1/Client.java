package Sem7.OnlineChat.Version1;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread{
    private Socket socket;
    private PrintWriter output;
    private String hostName;
    private ClientGUI gui;
    public static final int PORT = 2022;

    public Client(ClientGUI gui, String hostName){
        this.gui = gui;
        this.hostName = hostName;
    }

    @Override
    public void run(){
        try {
            connectToServer();
            getStreams();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }




    public void connectToServer(){
        try {
            socket = new Socket(InetAddress.getByName(hostName), 2022);
            InputReader inputReader = new InputReader(socket);
            inputReader.start();
        } catch (IOException e) {
            System.err.println("Client " + hostName + " error connecting... exiting");
            System.exit(1);
        }
    }

    public void getStreams() throws IOException{
        //TODO Autoflush, quando escrevo algo, manda logo
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendMessage(String message){
        output.println(message);
    }

    public void closeConnection(){
        /**
         * Este método não é o mais correto, porque se input der erro não vai fechar o resto tipo o output,
         * RESOURCE LEAK! Teria que fazer try catch para cada um.
         */
        try{
            output.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public class InputReader extends Thread {
        private BufferedReader input;

        public InputReader(Socket socket) {
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException ioE) {
                ioE.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String message;
                while (true) {
                    System.out.println("Input Reader beginning...");
                    message = input.readLine();
                    System.out.println(message);
                    //gui.setChatField(message);
                    sleep(100);
                }
            } catch (IOException ioE) {
                ioE.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                closeInput();
            }

        }

        private void closeInput(){
            try {
                input.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    public static void main(String[] args) {
        Sem7.EchoServer.Client client = new Sem7.EchoServer.Client("localhost");
        client.runClient();
    }

}

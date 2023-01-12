package Sem7.TimeServer;

import Sem7.EchoServer.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private String hostName;

    public Client(String hostName){
        this.hostName = hostName;
    }

    public void runClient(){
        try {
            connectToServer();
            getStreams();
            proccessConnection();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }

    }


    public void connectToServer(){
        try {
            socket = new Socket(InetAddress.getByName(hostName), 2424);
        } catch (IOException e) {
            System.err.println("Client " + hostName + " error connecting... exiting");
            System.exit(1);
        }
    }

    public void getStreams() throws IOException{
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    public void proccessConnection() throws IOException, ClassNotFoundException {
        try{
            while(true){
                TimeMessage message = (TimeMessage) input.readObject();
                long time = message.getTime();
                System.out.println(time);
                ReceptionConfirmationMessage confirmationMessage = new ReceptionConfirmationMessage(true);
                output.writeObject(confirmationMessage);
                Thread.sleep(500);
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    public void closeConnection(){
        /**
         * Este método não é o mais correto, porque se input der erro não vai fechar o resto tipo o output,
         * RESOURCE LEAK! Teria que fazer try catch para cada um.
         */
        try{
            input.close();
            output.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost");
        client.runClient();
    }

}

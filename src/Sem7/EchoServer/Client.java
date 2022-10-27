package Sem7.EchoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    
    private String hostName;
    
    public Client(String hostName){
        this.hostName = hostName;
    }
    
    public void runClient(){
        try {
            connectToServer();
            getStreams();
            proccessConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }

    }


    public void connectToServer(){
        try {
            socket = new Socket(InetAddress.getByName(hostName), Server.PORT);
        } catch (IOException e) {
            System.err.println("Client " + hostName + " error connecting... exiting");
            System.exit(1);
        }
    }
    
    public void getStreams() throws IOException{
        //TODO Autoflush, quando escrevo algo, manda logo
        output = new PrintWriter(socket.getOutputStream(), true);

        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void proccessConnection() throws IOException {
        for(int i = 0; i < 10; i++){
            output.println("Ola " + i);

            String message = input.readLine();
            System.out.println(message);

            try{
                Thread.sleep(3000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        output.println("FIM");
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

package Sem7.BroadcastEcho;

import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {
    private Socket socket;


    @Override
    public void run() {
        try {
            socket = new Socket("localHost", 2424);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}

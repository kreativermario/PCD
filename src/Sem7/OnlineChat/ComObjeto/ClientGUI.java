package Sem7.OnlineChat.ComObjeto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static java.lang.Thread.sleep;


public class ClientGUI {
    private JFrame frame;
    private String username;
    private JTextArea chatField;
    private String hostName = "localHost";
    private TCPClient clientConnection;

    // 2021-03-24 16:48:05
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");

    public ClientGUI(String username) {
        frame = new JFrame("Chat Cliente");
        this.username = username;


        // para que o botao de fechar a janela termine a aplicacao
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addFrameContent();

        // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
        frame.pack();

        clientConnection = new TCPClient();

    }

    public void open() {
        // para abrir a janela (torna-la visivel)
        frame.setVisible(true);
    }

    public void setChatField(String message) {
        System.out.println("Updating chat field...");
        chatField.append(message + "\n");
    }

    private void addFrameContent() {


		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */
        frame.setLayout(new GridLayout(1, 1));
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JLabel chatLabel = new JLabel("Chat:");
        panel.add(chatLabel);

        chatField = new JTextArea();
        chatField.setEditable(false);
        panel.add(chatField);

        JLabel inputLabel = new JLabel("Enter message:");
        panel.add(inputLabel);
        JTextField textField = new JTextField();
        panel.add(textField);

        JButton button = new JButton("Send message");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String message = sdf1.format(timestamp) + " " + username + ": " + textField.getText();
                try {
                    clientConnection.send(message);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.add(panel);
        frame.add(button);
    }


    public class TCPClient {
        private Socket socket;
        private ObjectOutputStream out;

        public TCPClient() {
            try {
                connect();
            } catch (IOException e) {
                System.err.println("Client " + hostName + " error connecting... exiting");
                System.exit(1);
            }
        }

        private void connect() throws IOException {
            socket = new Socket(hostName, 2424);
            System.out.println("Successfully connected!");
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Created output stream");
            // Start a separate thread to handle inputs from the server
            InputHandler inputHandler = new InputHandler();
            inputHandler.start();

        }

        // Send data to the server
        public void send(String data) throws IOException {
            System.out.println("Sending data " + data);
            Message message = new Message(data);
            out.writeObject(message);
            out.reset();
        }

        private class InputHandler extends Thread {
            private ObjectInputStream in;

            public InputHandler() throws IOException {
                System.out.println("Created InputHandler");
                in = new ObjectInputStream(socket.getInputStream());
            }

            @Override
            public void run() {
                System.out.println("Listening for inputs...");
                while (true) {
                    try {


                        Message message = (Message) in.readObject();
                        System.out.println("Received MESSAGE" + message);
                        String input = message.getMessage();
                        System.out.println("Received: " + input);
                        setChatField(input);

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        try {
                            in.close();
                        } catch (IOException ef) {
                            throw new RuntimeException(ef);
                        }
                    }
                }
            }
        }

    }
    public static void main(String[] args) {
        ClientGUI clientGUI = new ClientGUI("Sr. Tugao");
        clientGUI.open();
    }
}




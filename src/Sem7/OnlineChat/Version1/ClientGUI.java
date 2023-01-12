package Sem7.OnlineChat.Version1;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.*;

public class ClientGUI {
    private JFrame frame;
    private Client clientConnection;
    private String username;
    private JTextArea chatField;
    // 2021-03-24 16:48:05
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");

    public ClientGUI(String username) {
        frame = new JFrame("Chat Cliente");
        this.username = username;
        clientConnection = new Client(this, "localHost");
        clientConnection.start();
        // para que o botao de fechar a janela termine a aplicacao
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addFrameContent();

        // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
        frame.pack();
    }

    public void open() {
        // para abrir a janela (torna-la visivel)
        frame.setVisible(true);
    }

    public void setChatField(String chat) {
        System.out.println(chat);
        chatField.append(chat + System.lineSeparator());
    }

    private void addFrameContent() {


		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */
        frame.setLayout(new GridLayout(1,1));
        JPanel panel = new JPanel(new GridLayout(4,1));
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
                clientConnection.sendMessage(message);
            }
        });

        frame.add(panel);
        frame.add(button);
    }



    public static void main(String[] args) {
        ClientGUI client = new ClientGUI("kreativermario");
        client.open();

    }
}

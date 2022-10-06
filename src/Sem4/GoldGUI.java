package Sem4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Exercício 2 - GUI
 */
public class GoldGUI {

    private JFrame frame;

    public GoldGUI(String title){
        frame = new JFrame(title);
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


    public void addFrameContent(){
        frame.setLayout(new GridLayout(1,1));
        JPanel panel = new JPanel(new GridLayout(3,1));
        JTextField gold = new JTextField("0");

        ImageIcon icon = new ImageIcon("gold-digger-money.gif");
        JLabel imageLbl = new JLabel(icon);

        // Criar Gold Operation




        JButton button = new JButton("STOP");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        panel.add(gold);
        panel.add(imageLbl);
        panel.add(button);


        frame.add(panel);
    }









    public static void main(String[] args) {
        GoldGUI gui = new GoldGUI("Gold Operation 2022");
        gui.open();

    }


}

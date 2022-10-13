package Sem4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Exercício 2 - GUI
 */
public class GoldGUI {

    private JFrame frame;
    private Scale scale;


    public GoldGUI(String title, JTextField field){
        frame = new JFrame(title);
        // para que o botao de fechar a janela termine a aplicacao
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addFrameContent(field);
        scale = new Scale(field);

        // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
        frame.pack();
    }

    public void open() {
        // para abrir a janela (torna-la visivel)
        frame.setVisible(true);
    }


    public void addFrameContent(JTextField field){
        frame.setLayout(new GridLayout(1,1));
        JPanel panel = new JPanel(new GridLayout(3,1));


        JButton button = new JButton("STOP");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                scale.stopThreads();
            }
        });
        panel.add(field);
        panel.add(button);



        frame.add(panel);
    }


    public static void main(String[] args) {
        JTextField gold = new JTextField("0");
        GoldGUI gui = new GoldGUI("Gold Operation 2022", gold);

        gui.open();

    }


}

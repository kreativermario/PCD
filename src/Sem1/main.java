package Sem1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;
import javax.swing.*;

public class main {

    public static void main(String[] args) {
        JFrame frame = new JFrame ("Teste" );
        GridLayout layout = new GridLayout(4,2);
        frame.setLayout(layout);
        //frame.setLayout(new FlowLayout(FlowLayout.LEADING));

        frame.setSize (500 , 400);
        frame.setLocation (200 , 100);


        //frame.add(new JLabel("Title:"));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,2));
        panel.setPreferredSize(new Dimension(100,100));

        JTextField title = new JTextField(1);
        JTextField width = new JTextField(1);
        JTextField height = new JTextField(1);
        JCheckBox check = new JCheckBox("Center");
        JButton button = new JButton("Update");

        panel.add(new JLabel("Title:"));
        panel.add(title);

        panel.add(new JLabel("Width:"));
        panel.add(width);

        panel.add(new JLabel("Height:"));
        panel.add(height);
        panel.add(check);
        panel.add(button);

        frame.add(panel);

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String t;
                if(width != null && height != null) {
                    if(!title.getText().isEmpty()){
                        t = title.getText();
                        frame.setTitle(t);
                    }
                    int w = Integer.valueOf(width.getText());
                    int h = Integer.valueOf(height.getText());
                    frame.setSize(w, h);
                }
                if(check.isSelected()){
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    int newWidth = (int) dimension.getWidth() / 2;
                    int newHeight = (int) dimension.getHeight() / 2;
                    frame.setLocationRelativeTo(null);
                }
            }
        });



        frame.setResizable ( false );
        // para que o botao de fechar a janela termine a aplicacao
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible ( true );
        // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
        //frame.pack();


    }


}
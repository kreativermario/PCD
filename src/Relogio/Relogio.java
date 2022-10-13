package Relogio;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Relogio{

    static void display(){

        // Cria a janela para o relógio
        JFrame clockFrame = new JFrame("Relógio Digital");
        clockFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Cria a etiqueta para mostrar as horas
        JLabel timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setPreferredSize(new Dimension(1000, 200));
        timeLabel.setFont(new Font("Calibri", Font.BOLD, 100));
        timeLabel.setForeground(Color.green);
        clockFrame.getContentPane().add(timeLabel, BorderLayout.CENTER);

        //Mostrar a janela
        clockFrame.setLocation(500,350);
        clockFrame.pack();
        clockFrame.setVisible(true);

        int delay = 100;
        Timer timer = new Timer(delay, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy " + " HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                timeLabel.setText(formattedDateTime);
            }
        });
        timer.start();
    }

    public static void main(String[] args){
        display();
    }
}

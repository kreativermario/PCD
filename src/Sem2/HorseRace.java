package Sem2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class HorseRace {
    private JFrame frame;
    private Horse winner;
    private Horse [] horses = new Horse[3];


    public HorseRace(JTextField field1, JTextField field2, JTextField field3) {
        frame = new JFrame("Corrida de Cavalos");

        // para que o botao de fechar a janela termine a aplicacao
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addFrameContent(field1, field2, field3);

        // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
        frame.pack();
    }

    public void open() {
        // para abrir a janela (torna-la visivel)
        frame.setVisible(true);
    }

    public Horse[] getHorses() {
        return horses;
    }

    private void addFrameContent(JTextField field1, JTextField field2, JTextField field3) {


		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */
        frame.setLayout(new GridLayout(1,1));
        JPanel panel = new JPanel(new GridLayout(2,3));
        panel.add(field1);
        panel.add(field2);
        panel.add(field3);

        JButton button = new JButton("button");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Horse h1 = new Horse(field1);
                Horse h2 = new Horse(field2);
                Horse h3 = new Horse(field3);
                horses[0] = h1;
                horses[1] = h2;
                horses[2] = h3;
                h1.start();
                h2.start();
                h3.start();
            }
        });
        frame.add(button);

        frame.add(panel);
    }
    public class Horse extends Thread {
        private JTextField location;

        public Horse(JTextField location) {
            this.location = location;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 30; i++) {
                    int value = Integer.valueOf(location.getText());
                    value--;
                    String status = Integer.toString(value);
                    location.setText(status);
                    Random r = new Random();
                    int low = 0;
                    int high = 100;
                    int result = r.nextInt(high - low) + low;
                    if(value==0){
                        winner = this;
                        for(Horse horse:horses){
                            if(!horse.equals(this)){
                                horse.interrupt();
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Horse " + getName() + " won!");
                    }
                    sleep(result * 10);

                }
            } catch (InterruptedException e) {

            }

        }
    }



        public static void main(String[] args) {

        JTextField field1 = new JTextField("30");
        JTextField field2 = new JTextField("30");
        JTextField field3 = new JTextField("30");
        HorseRace window = new HorseRace(field1, field2, field3);
        window.open();
    }
}

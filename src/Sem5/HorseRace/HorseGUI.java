package Sem5.HorseRace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/***
 * Exercício 2 - Corrida de Cavalos
 *  NOTA: NAO AUMENTAR MUITO A JANELA, tem um bug esquisito! Os numeros bruh
 */
public class HorseGUI {
    private JFrame frame;
    private JPanel panel;
    private final HorseTrack track = new HorseTrack();
    private final int numHorses;

    /**
     * Método construtor
     * @param num_horses Numero de cavalos
     */
    public HorseGUI(int num_horses) {
        frame = new JFrame("Corrida de Cavalos");

        // para que o botao de fechar a janela termine a aplicacao
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.panel = new JPanel(new GridLayout(2,num_horses));
        this.numHorses = num_horses;
        addFrameContent();

        // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
        frame.pack();
    }

    public void open() {
        // para abrir a janela (torna-la visivel)
        frame.setVisible(true);
    }

    /**
     * Adiciona conteudo
     */
    private void addFrameContent() {

		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */
        frame.setLayout(new GridLayout(1,1));

        JButton button = new JButton("Inicia");
        for(int i = 0; i < numHorses; i++) {
            JTextField field = new JTextField(Integer.toString(HorseTrack.TRACK_LIMIT));
            HorseTrack.Horse horse = track.new Horse(field);
            panel.add(field);
        }


        // Iniciar a corrida quando clica no botão
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Iniciar a corrida
               track.start();
            }
        });
        panel.add(button);
        frame.add(panel);
    }





    public static void main(String[] args) {

        HorseGUI window = new HorseGUI(5);
        window.open();
    }
}

package Sem2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/***
 * Exercício 2 - Corrida de Cavalos
 *  NOTA: NAO AUMENTAR MUITO A JANELA, tem um bug esquisito! Os numeros bruh
 */
public class HorseRace {
    private JFrame frame;

    // Lista de threads com os tres cavalos
    private Horse [] horses = new Horse[3];

    /**
     * Construtor da corrida
     * @param field1 Recebe a distancia do cavalo 1
     * @param field2 Recebe a distancia do cavalo 2
     * @param field3 Recebe a distancia do cavalo 3
     */
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

    /**
     * Adiciona o contéudo à frame
     * @param field1 TextField do cavalo 1
     * @param field2 TextField do cavalo 2
     * @param field3 TextField do cavalo 3
     */
    private void addFrameContent(JTextField field1, JTextField field2, JTextField field3) {

		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */
        frame.setLayout(new GridLayout(1,1));
        JPanel panel = new JPanel(new GridLayout(2,3));
        panel.add(field1);
        panel.add(field2);
        panel.add(field3);

        JButton button = new JButton("Inicia");

        // Iniciar a corrida quando clica no botão
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Criar as threads "Horse" cavalos, adicioná-los à lista (para podermos dar interrupt a todas)
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
        panel.add(button);

        frame.add(panel);
    }

    /**
     * A thread do cavalo
     */
    public class Horse extends Thread {
        // Posição do cavalo
        private JTextField location;

        /**
         * Construtor
         * @param location
         */
        public Horse(JTextField location) {
            this.location = location;
        }

        @Override
        public void run() {
            try {
                // Correr durante o tal tamanho definido - 30 passos
                for (int i = 0; i < 30; i++) {
                    // Buscar a posição atual e diminuir
                    int value = Integer.parseInt(location.getText());
                    value--;

                    // verificar se chegou ao fim da corrida
                    if(value==0){
                        /* Terminar todas as threads menos esta. Para que esta thread (a que ganhou) possa colocar
                        a mensagem que ganhou
                        */
                        for(Horse horse:horses){
                            if(!horse.equals(this)){
                                horse.interrupt();
                            }
                        }
                        // Colocar o pop-up que ganhou e terminar a thread.
                        JOptionPane.showMessageDialog(null, "Horse " + getName() + " won!");
                        return;
                    }

                    // Colocar o novo valor da posição
                    String status = Integer.toString(value);
                    location.setText(status);

                    // Quanto tempo deve dormir
                    Random r = new Random();
                    int low = 0;
                    int high = 100;
                    int result = r.nextInt(high - low) + low;

                    sleep(result * 10);

                }
            // A encher chouriços mas podes enfiar o que quiseres aqui
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

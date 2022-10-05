package Sem1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/* Exercício 1
* Janela - JFrame, JLabel, JTextField, JButton, JCheckBox, FlowLayout, GridLayout
* */
public class MyFrame {
    private JFrame frame;


    /**
    * Construtor da janela que recebe o título, largura e altura
    * @param title titulo
    * @param width largura da janela
     * @param height altura da janela
    *  */
    public MyFrame(String title, int width, int height) {

        // Cria a janela com o título e configura a dimensão
        frame = new JFrame(title);
        frame.setSize(new Dimension(width, height));

        // para que o botao de fechar a janela termine a aplicacao
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Adiciona o conteúdo à janela
        addFrameContent();

        // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
        frame.pack();
    }



    public void open() {
        // para abrir a janela (torna-la visivel)
        frame.setVisible(true);
    }


    /**
     * Método que adiciona conteúdo à janela
     * */
    private void addFrameContent() {


		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */

        // 1 linha e 1 coluna porque vou só colocar um panel e dentro do panel coloco todos os elementos
        GridLayout layout = new GridLayout(1,1);
        // Configurar o layout da frame
        frame.setLayout(layout);

        /*
        * Cria o painel e configura o seu layout e tamanho. (rows, cols, hgap, vgap) hgap = horizontal gap
        * vgap = vertical gap
        */
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,2,2,10));
        panel.setPreferredSize(null);

        // Cria os text fields (para inserir) a ocupar uma coluna do painel (que tem 2 colunas)
        JTextField title = new JTextField(1);
        JTextField width = new JTextField(1);
        JTextField height = new JTextField(1);

        // Cria o checkbox e o botão
        JCheckBox check = new JCheckBox("Center");
        JButton button = new JButton("Update");

        // Adiciona os elementos criados ao painel
        panel.add(new JLabel("Title:"));
        panel.add(title);

        panel.add(new JLabel("Width:"));
        panel.add(width);

        panel.add(new JLabel("Height:"));
        panel.add(height);
        panel.add(button);
        panel.add(check);

        // Adiciona o painel
        frame.add(panel);



        /**
         * O programa está um becas bugado se nao colocares nada e tentar fazer cenas, por isso se colocares coisas
         * em cada campo, ele roda
         * */
        // O tal listener "trigger" é criado para o botão. Quando se clica no botão faz o que está aqui dentro
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String t;

                // Verificações - ver se os inputs de largura ou altura não estão nulls
                if(width != null && height != null) {
                    // Se titulo nao estiver vazio
                    if(!title.getText().isEmpty()){
                        // Altera o titulo
                        t = title.getText();
                        frame.setTitle(t);
                    }
                    // Obter os valores de largura e altura e alterar o tamanho da janela
                    int w = Integer.valueOf(width.getText());
                    int h = Integer.valueOf(height.getText());
                    frame.setSize(w, h);
                }
                // Se a checkbox estiver selecionada
                if(check.isSelected()){
                    // Colocar a janela a meio
                    frame.setLocationRelativeTo(null);
                }
            }
        });
    }


    public static void main(String[] args) {
        MyFrame window = new MyFrame("Title", 300, 150);
        window.open();
    }
}

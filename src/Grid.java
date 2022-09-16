import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Grid {
    private JFrame frame;
    private JPanel panel;
    private MouseListener mouseListener;
    private JLabel [] squares;

    public Grid(String title, int rows, int columns, int pixels) {
        frame = new JFrame(title);

        // para que o botao de fechar a janela termine a aplicacao
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        squares = new JLabel[rows*columns];
        mouseListener = createMouseListener();
        addFrameContent(rows, columns, pixels);

        // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
        frame.pack();
    }

    public Grid(String title, int rows, int columns, int pixels, MouseListener mouseListener) {
        Grid grid = new Grid(title, rows, columns, pixels);
        this.setMouseListener(mouseListener);
    }


    public void open() {
        // para abrir a janela (torna-la visivel)
        frame.setVisible(true);
    }

    private void createSquares(int rows, int columns, int pixels){
        String name = "label";
        for(int i = 0; i < rows * columns; i++){
            name = "label" + i;
            JLabel label = new JLabel();
            Border border = BorderFactory.createLineBorder(Color.black, 2);
            label.setBorder(border);
            label.setPreferredSize(new Dimension(pixels,pixels));
            label.addMouseListener(mouseListener);

            squares[i] = label;
            panel.add(label);
        }
    }


    private MouseListener createMouseListener(){
        // Criar mouse listener
        mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JLabel clickedPanel = (JLabel) e.getSource();
                clickedPanel.setText("CLICADO!");

            }
        };
        return mouseListener;
    }


    private void updateListeners(MouseListener listener){
        for(int i = 0; i < squares.length; i++){
            squares[i].removeMouseListener(mouseListener);
            squares[i].addMouseListener(listener);
        }
    }

    // Setter
    public void setMouseListener(MouseListener mouseListener){
        updateListeners(mouseListener);
        this.mouseListener = mouseListener;
    }


    private void addFrameContent(int rows, int columns, int pixels) {

		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */
        frame.setLayout(new GridLayout(1,1));

        panel = new JPanel();
        panel.setPreferredSize(null);
        panel.setLayout(new GridLayout(rows,columns));
        createSquares(rows, columns, pixels);

        frame.add(panel);

    }

    public static void main(String[] args) {
        Grid window = new Grid("Test", 3, 3, 150);
        window.open();
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame {
    private JFrame frame;

    public MyFrame(String title, int width, int height) {
        frame = new JFrame("Test");
        frame.setSize(new Dimension(width, height));

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

    private void addFrameContent() {


		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */
        GridLayout layout = new GridLayout(1,1);
        frame.setLayout(layout);
        //frame.setLayout(new FlowLayout(FlowLayout.LEADING));

        //frame.add(new JLabel("Title:"));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,2,2,10));
        panel.setPreferredSize(null);

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
        panel.add(button);
        panel.add(check);

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
    }

    public static void main(String[] args) {
        MyFrame window = new MyFrame("Title", 300, 150);
        window.open();
    }
}

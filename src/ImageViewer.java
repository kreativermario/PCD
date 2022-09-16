import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

public class ImageViewer {
    private JFrame frame;
    private int counter = 0;
    private File [] images = readImages();
    private ImageIcon icon;
    private JLabel image = new JLabel();
    JLabel imageName = new JLabel(images[counter].getName());

    public ImageViewer() {
        frame = new JFrame("Test");

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

    private void updateImage(){
        icon = new ImageIcon(images[counter].getAbsolutePath());
        image.setIcon(icon);
        imageName.setText(images[counter].getName());
    }


    private void addFrameContent() {


		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */

        frame.setLayout(new BorderLayout());

        JButton leftButton = new JButton("<");
        frame.add(leftButton, BorderLayout.WEST);

        JButton rightButton = new JButton(">");
        frame.add(rightButton, BorderLayout.EAST);

        JButton updateButton = new JButton("Update");
        frame.add(updateButton, BorderLayout.SOUTH);


        frame.add(image);
        images = readImages();

        updateImage();


        frame.add(imageName, BorderLayout.NORTH);




        rightButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(counter + 1 < images.length){
                    counter += 1;
                    updateImage();
                }else{
                    image.setIcon(null);
                    image.setText("Não há mais imagens!");
                }

            }
        });

        leftButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(counter - 1 >= 0){
                    counter -= 1;
                    updateImage();
                }else{
                    image.setIcon(null);
                    image.setText("Não há mais imagens!");
                }

            }
        });


        updateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                images = readImages();
                counter = 0;
                updateImage();
            }
        });




    }

    public File [] readImages(){
        String path = "images"; // pasta criada dentro do projeto Eclipse,

        //onde devem ser colocadas as imagens

        File[] files = new File(path).listFiles(new FileFilter() {
            public boolean accept(File f) {
                // se retornar verdadeiro, f será incluido
                // colocar return true para serem escolhidos todos os ficheiros
                return f.getName().endsWith(".jpg");
            }
        });


        return files;
    }


    public static void main(String[] args) {
        ImageViewer window = new ImageViewer();
        window.open();
    }
}

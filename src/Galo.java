import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Galo extends Grid{

    public Galo(String title, int rows, int columns, int pixels) {
        super(title, rows, columns, pixels);
        MouseListener mouseListener = createMouseListener();
        super.setMouseListener(mouseListener);
    }

    public MouseListener createMouseListener(){
        // Criar mouse listener
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JLabel clickedPanel = (JLabel) e.getSource();
                clickedPanel.setFont(new Font("Arial", Font.BOLD, 42));
                clickedPanel.setHorizontalAlignment(SwingConstants.CENTER);

                if(clickedPanel.getText().isEmpty()){
                    clickedPanel.setText("X");
                }else if(clickedPanel.getText().equals("X")){
                    clickedPanel.setText("O");
                }else if(clickedPanel.getText().equals("O")){
                }

            }
        };
        return mouseListener;
    }

    public static void main(String[] args) {
        Galo jogo = new Galo("Jogo do Galo", 3, 3, 50);
        jogo.open();
    }

}

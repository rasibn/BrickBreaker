package  Main;
//import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.EventQueue;

public class Application extends JFrame {

    public Application() {

        initUI();
    }
    private void initUI() {
        add(new Board("src//PNG//background.jpeg"));
        setTitle("Breakout");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var game = new Application();
            game.setVisible(true);
        });
    }
}

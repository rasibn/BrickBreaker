package Main;

import javax.swing.JFrame;

import java.awt.Component;
import java.awt.EventQueue;

public class Application extends JFrame {
    static Component board;

    public Application() {

        initUI();
    }
    private void initUI() {
        board =new Board();
        add(board);
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
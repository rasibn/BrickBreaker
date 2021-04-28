package Main;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    private final JWindow menuWindow = new JWindow();
    private static Menu menu;

    private Menu() {
    }
    public static Menu getMenu(){
        if(menu == null) {
            menu = new Menu();
        }
        return menu;
    }
    public JWindow makingTheMenu(Board board){
        JLabel nameLabel = new JLabel("MENU");
        nameLabel.setFont(new Font("Ariel", Font.BOLD, 30));
        
        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        
        topPanel.add(nameLabel);
        
        //ingame buttons
        //JButton resumeButton = new JButton("RESUME [ECS]");
        //resumeButton.setFont(new Font("RESUME [ECS]", Font.BOLD, 14));
        //JButton saveButton = new JButton("SAVE GAME");
        //saveButton.setFont(new Font("Ariel", Font.BOLD, 14));
        JButton loadSaveButton = new JButton("LOAD SAVE");
        loadSaveButton.setFont(new Font("Ariel", Font.BOLD, 14));
        JButton newgameButton = new JButton("NEW GAME");
        newgameButton.setFont(new Font("Ariel", Font.BOLD, 14));
        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(new Font("Ariel", Font.BOLD, 14));
    
        //middlePanel.add(BorderLayout.CENTER, resumeButton);
        middlePanel.add(BorderLayout.CENTER, loadSaveButton);
        //middlePanel.add(BorderLayout.CENTER, saveButton);
    
        middlePanel.add(BorderLayout.CENTER, newgameButton);
        middlePanel.add(BorderLayout.CENTER, exitButton);
    
        //resumeButton.setVisible(false);
        //saveButton.setVisible(false);
    
        menuWindow.add(BorderLayout.NORTH, topPanel);
        menuWindow.add(BorderLayout.CENTER, middlePanel);
        menuWindow.add(BorderLayout.SOUTH, bottomPanel); 
    
    
        newgameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.makeNewInstance();
                board.startGame("Press NEW GAME to start over, or LOAD SAVE to load a saved game");
            }
        });
        loadSaveButton.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                if(!board.SavedInstance.isEmpty()) {
                    try {
                        board.getsavedInstance();

                    } catch (CloneNotSupportedException e1) {
                        e1.printStackTrace();
                    }
                    board.startGame("Press NEW GAME to start over, or LOAD SAVE to load a saved game");
                }
                else {
                    board.stopGame("You have no Saved Data! Try NEW GAME.");
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
    
            }
        });
        //These are the settings for the frame to have
        menuWindow.setPreferredSize(new Dimension(400, 350));
        menuWindow.pack();
        menuWindow.setAlwaysOnTop(true);
        menuWindow.setLocationRelativeTo(null);
        menuWindow.setVisible(true);
        menuWindow.repaint();
        return menuWindow;
        }
}

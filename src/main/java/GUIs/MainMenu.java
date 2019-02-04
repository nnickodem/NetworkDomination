package GUIs;

import Objects.GameLevel;
import ResourceHandlers.FileHandler;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import static java.lang.System.exit;

public class MainMenu extends JFrame {
    private JButton playButton = new JButton("Play");
    private JButton loadButton = new JButton("Load Game");
    private JButton optionsButton = new JButton("Settings");
    private JButton exitButton = new JButton("Exit");

    private int buttonHeight = 40;
    private int buttonWidth = 150;

    //Add each button to the JPanel
    public MainMenu(){
        JFrame gameWindow = new JFrame();
        JPanel mainMenu = new JPanel(new GridBagLayout());
        mainMenu.setBorder(BorderFactory.createEmptyBorder());
        mainMenu.setBackground(Color.DARK_GRAY);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(3,0,3,0);
        constraints.fill = GridBagConstraints.CENTER;
        playButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        playButton.addActionListener(e -> {
            add(new Tutorial());
            mainMenu.setVisible(false);
        });
        mainMenu.add(playButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(3,0,3,0);
        loadButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        mainMenu.add(loadButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = new Insets(3,0,3,0);
        optionsButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        mainMenu.add(optionsButton, constraints);

        exitButton.addActionListener(e-> exit(0));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = new Insets(3,0,3,0);
        exitButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        mainMenu.add(exitButton, constraints);

        //Add the mainMenu panel to the frame and set the window size
        add(mainMenu);
        getContentPane().setBackground(Color.DARK_GRAY);

        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

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
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.lang.System.exit;

public class MainMenu extends JFrame {
    private JButton playButton = new JButton("New Game");
    private JButton loadButton = new JButton("Load Game");
    private JButton optionsButton = new JButton("Settings");
    private JButton exitButton = new JButton("Exit");
    private JButton saveButton = new JButton("Save Game");
    private Dimension buttonSize = new Dimension(150, 40);
    private Dimension dimMax = Toolkit.getDefaultToolkit().getScreenSize();

    //Add each button to the JPanel
    public MainMenu(){
        setMaximumSize(dimMax);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setTitle("Network Domination");
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == VK_ESCAPE){
                    //TODO Work in progress
                    JFrame pauseFrame = new JFrame();
                    pauseFrame.setSize(new Dimension(100,100));
                    pauseFrame.add(exitButton);
                    pauseFrame.add(saveButton);
                    pauseFrame.setVisible(true);
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        JPanel mainMenu = new JPanel(new GridBagLayout());
        mainMenu.setBorder(BorderFactory.createEmptyBorder());
        mainMenu.setBackground(Color.DARK_GRAY);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(3,0,3,0);
        constraints.fill = GridBagConstraints.CENTER;
        playButton.setPreferredSize(buttonSize);
        GameLevel level = FileHandler.readLevel(1);
        playButton.addActionListener(e -> {
            setTitle("Network Domination - Level 1");
            add(new Level(level));
            mainMenu.setVisible(false);
        });
        mainMenu.add(playButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(3,0,3,0);
        loadButton.setPreferredSize(buttonSize);
        mainMenu.add(loadButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = new Insets(3,0,3,0);
        optionsButton.setPreferredSize(buttonSize);
        mainMenu.add(optionsButton, constraints);

        exitButton.addActionListener(e-> exit(0));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = new Insets(3,0,3,0);
        exitButton.setPreferredSize(buttonSize);
        mainMenu.add(exitButton, constraints);

        //Add the mainMenu panel to the frame and set the window size
        getContentPane().add(mainMenu);
        getContentPane().setBackground(Color.DARK_GRAY);

        setMaximumSize(dimMax);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

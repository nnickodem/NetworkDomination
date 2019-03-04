package GUIs;

import GameHandlers.GameHandler;
import Objects.GameLevel;
import ResourceHandlers.FileHandler;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.lang.System.exit;
import static java.lang.System.load;

/**
 * Creates the main menu gui
 */
public class MainMenu extends JFrame {
    private JButton playButton = new JButton("New Game");
    private JButton loadButton = new JButton("Load Game");
    private JButton optionsButton = new JButton("Settings");
    private JButton exitButton = new JButton("Exit");
    private JButton saveButton = new JButton("Save Game");
    private JButton returnToMainMenu = new JButton("Return to Main Menu");
    private Dimension buttonSize = new Dimension(150, 40);
    private Dimension dimMax = Toolkit.getDefaultToolkit().getScreenSize();
    private GameHandler gameHandler;

    /**
     * Constructs the main menu JPanel
     */
    public MainMenu(){
        JPanel mainMenu = new JPanel(new GridBagLayout());
        setMaximumSize(dimMax);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Network Domination");
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!mainMenu.isVisible()) {
                    if (e.getKeyCode() == VK_ESCAPE) {
                        //Create JFrame and JPanel instances
                        JFrame pauseFrame = new JFrame();
                        JPanel pausePanel = new JPanel();

                        //Add Save button to pause menu
                        saveButton.setPreferredSize(buttonSize);
                        pausePanel.add(saveButton);

                        //Add return to main menu button to pause menu
                        returnToMainMenu.setPreferredSize(buttonSize);
                        returnToMainMenu.addActionListener(e1 -> {
                            mainMenu.setVisible(true);
                            pauseFrame.dispose();
                            setContentPane(mainMenu);
                            gameHandler.stopTimer();
                        });
                        pausePanel.add(returnToMainMenu);

                        //Add Exit button to pause menu
                        JButton pauseExitButton = new JButton("Exit");
                        pauseExitButton.setPreferredSize(buttonSize);
                        pauseExitButton.addActionListener(e1->{
                            exit(0);
                        });
                        pausePanel.add(pauseExitButton);

                        //Add the JFrame criteria
                        pauseFrame.setSize(new Dimension(250, 300));
                        pauseFrame.add(pausePanel);
                        pauseFrame.setLocationRelativeTo(null);
                        pauseFrame.setVisible(true);
                    }
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        mainMenu.setBorder(BorderFactory.createEmptyBorder());
        mainMenu.setBackground(Color.DARK_GRAY);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(3,0,3,0);
        constraints.fill = GridBagConstraints.CENTER;
        playButton.setPreferredSize(buttonSize);
        playButton.setFont(FileHandler.getGameFont().deriveFont(Font.PLAIN, 15));
        //TODO: move this object creation somewhere else?
        playButton.addActionListener(e -> {
            GameLevel level = FileHandler.readLevel("1");
            Level levelGui = new Level(level);
            gameHandler = new GameHandler(level, levelGui);
            setTitle("Network Domination - Level 1");
            add(levelGui);
            setContentPane(levelGui);
            remove(mainMenu);
            mainMenu.setVisible(false);
            gameHandler.run();
        });
        mainMenu.add(playButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(3,0,3,0);
        loadButton.setPreferredSize(buttonSize);
        loadButton.setFont(FileHandler.getGameFont().deriveFont(Font.PLAIN, 15));
        mainMenu.add(loadButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = new Insets(3,0,3,0);
        optionsButton.setPreferredSize(buttonSize);
        optionsButton.setFont(FileHandler.getGameFont().deriveFont(Font.PLAIN, 15));
        mainMenu.add(optionsButton, constraints);

        exitButton.addActionListener(e-> exit(0));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = new Insets(3,0,3,0);
        exitButton.setPreferredSize(buttonSize);
        exitButton.setFont(FileHandler.getGameFont().deriveFont(Font.PLAIN, 15));
        mainMenu.add(exitButton, constraints);

        //Add the mainMenu panel to the frame and set the window size
        setFocusable(true);
        getContentPane().add(mainMenu);
        getContentPane().setBackground(Color.DARK_GRAY);

        setMaximumSize(dimMax);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

package GUIs;

import GameHandlers.GameHandler;
import Objects.GameLevel;
import ResourceHandlers.FileHandler;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.lang.System.exit;

/**
 * Creates the main menu gui
 */
public class MainMenu extends JFrame {

	private final Dimension buttonSize = new Dimension(120, 60);
	private GameHandler gameHandler;

	/**
	 * Constructs the main menu JPanel
	 */
	public MainMenu() {
		JPanel mainMenu = new JPanel(new GridBagLayout());
		setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
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
						JButton saveButton = new JButton(GUIUtils.scaleImage("resources/ui/button/buttonBase.png", 120, 60));
						saveButton.setPreferredSize(buttonSize);
						saveButton.setContentAreaFilled(false);
						saveButton.setFocusPainted(false);
						JLabel saveLabel = new JLabel("Save");
						saveLabel.setFont(new Font("Arial", Font.PLAIN, 16));
						saveLabel.setForeground(Color.WHITE);
						saveLabel.setAlignmentY(.3f);
						saveLabel.setAlignmentX(Label.CENTER_ALIGNMENT);
						saveButton.add(saveLabel);
						pausePanel.add(saveButton);

						//Add return to main menu button to pause menu
						JButton returnButton = new JButton(GUIUtils.scaleImage("resources/ui/button/buttonBase.png", 120, 60));
						returnButton.setPreferredSize(buttonSize);
						returnButton.setContentAreaFilled(false);
						returnButton.setFocusPainted(false);
						JLabel returnLabel = new JLabel("Main Menu");
						returnLabel.setFont(new Font("Arial", Font.PLAIN, 16));
						returnLabel.setForeground(Color.WHITE);
						returnLabel.setAlignmentY(.3f);
						returnLabel.setAlignmentX(Label.CENTER_ALIGNMENT);
						returnButton.add(returnLabel);
						returnButton.addActionListener(e1 -> {
							mainMenu.setVisible(true);
							pauseFrame.dispose();
							setContentPane(mainMenu);
							gameHandler.stopTimer();
						});
						pausePanel.add(returnButton);

						//Add Exit button to pause menu
						JButton pauseExitButton = new JButton(GUIUtils.scaleImage("resources/ui/button/buttonBase.png", 120, 60));
						pauseExitButton.setPreferredSize(buttonSize);
						pauseExitButton.setContentAreaFilled(false);
						pauseExitButton.setFocusPainted(false);
						JLabel pauseExitLabel = new JLabel("Exit");
						pauseExitLabel.setFont(new Font("Arial", Font.PLAIN, 16));
						pauseExitLabel.setForeground(Color.WHITE);
						pauseExitLabel.setAlignmentY(.3f);
						pauseExitLabel.setAlignmentX(Label.CENTER_ALIGNMENT);
						pauseExitButton.add(pauseExitLabel);
						pauseExitButton.addActionListener(e1 -> {
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

		JButton playButton = new JButton(GUIUtils.scaleImage("resources/ui/button/buttonBase.png", 120, 60));
		playButton.setPreferredSize(buttonSize);
		playButton.setContentAreaFilled(false);
		playButton.setFocusPainted(false);
		JLabel playLabel = new JLabel("Play");
		playLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		playLabel.setForeground(Color.WHITE);
		playLabel.setAlignmentY(.3f);
		playLabel.setAlignmentX(Label.CENTER_ALIGNMENT);
		playButton.add(playLabel);

		//TODO: move this object creation somewhere else?

		playButton.addActionListener(e -> {
			GameLevel level = FileHandler.readLevel("1");
			LevelGUI levelGUI = new LevelGUI(level);
			gameHandler = new GameHandler(level, levelGUI);
			setTitle("Network Domination - Level 1");
			add(levelGUI);
			setContentPane(levelGUI);
			remove(mainMenu);
			mainMenu.setVisible(false);
			gameHandler.run();
		});
		mainMenu.add(playButton, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(3,0,3,0);

		JButton loadButton = new JButton(GUIUtils.scaleImage("resources/ui/button/buttonBase.png", 120, 60));
		loadButton.setPreferredSize(buttonSize);
		loadButton.setContentAreaFilled(false);
		loadButton.setFocusPainted(false);
		JLabel loadLabel = new JLabel("Load");
		loadLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		loadLabel.setForeground(Color.WHITE);
		loadLabel.setAlignmentY(.3f);
		loadLabel.setAlignmentX(Label.CENTER_ALIGNMENT);
		loadButton.add(loadLabel);
		mainMenu.add(loadButton, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(3,0,3,0);

		JButton optionsButton = new JButton(GUIUtils.scaleImage("resources/ui/button/buttonBase.png", 120, 60));
		optionsButton.setPreferredSize(buttonSize);
		optionsButton.setContentAreaFilled(false);
		optionsButton.setFocusPainted(false);
		JLabel optionsLabel = new JLabel("Settings");
		optionsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		optionsLabel.setForeground(Color.WHITE);
		optionsLabel.setAlignmentY(.3f);
		optionsLabel.setAlignmentX(Label.CENTER_ALIGNMENT);
		optionsButton.add(optionsLabel);
		mainMenu.add(optionsButton, constraints);

		JButton exitButton = new JButton(GUIUtils.scaleImage("resources/ui/button/buttonBase.png", 120, 60));
		exitButton.addActionListener(e-> exit(0));
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(3,0,3,0);

		exitButton.setPreferredSize(buttonSize);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		JLabel exitLabel = new JLabel("Exit");
		exitLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		exitLabel.setForeground(Color.WHITE);
		exitLabel.setAlignmentY(.3f);
		exitLabel.setAlignmentX(Label.CENTER_ALIGNMENT);
		exitButton.add(exitLabel);
		mainMenu.add(exitButton, constraints);

		//Add the mainMenu panel to the frame and set the window size
		setFocusable(true);
		getContentPane().add(mainMenu);
		getContentPane().setBackground(Color.DARK_GRAY);

		setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

package GUIs;

import GameHandlers.GameHandler;
import Objects.GameLevel;
import ResourceHandlers.FileHandler;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.lang.System.exit;

/**
 * Master GUI class
 */
public class MainGui extends JFrame {

	private final Dimension buttonSize = new Dimension(120, 60);
	private JPanel currentPanel;

	public MainGui() {
		setVisible(true);
		setFocusable(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Network Domination");
		setBackground(Color.DARK_GRAY);
		createMainMenu();
		createPauseMenu();
	}

	/**
	 * Creates the main menu JPanel
	 */
	public void createMainMenu() {
		MainMenu mainMenu = new MainMenu(this);
		remove(mainMenu);
		if(currentPanel != null){
			remove(currentPanel);
			currentPanel.setVisible(false);
		}
		add(mainMenu);
		mainMenu.setVisible(true);
		setContentPane(mainMenu);
		revalidate();
		currentPanel = mainMenu;
	}

	/**
	 * Adds a key listener which creates a pause menu JFrame on escape keypress
	 */
	public void createPauseMenu() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
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
						setVisible(true);
						pauseFrame.dispose();
						createMainMenu();


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
					pauseExitButton.addActionListener(e1 -> exit(0));
					pausePanel.add(pauseExitButton);

					//Add the JFrame criteria
					pauseFrame.setSize(new Dimension(250, 300));
					pauseFrame.add(pausePanel);
					pauseFrame.setLocationRelativeTo(null);
					pauseFrame.setVisible(true);
					pauseFrame.setResizable(false);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}
			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
	}

	/**
	 * Creates a level JPanel
	 * @param levelName name/number of the level
	 */
	public void createLevel(final String levelName) {
		GameLevel gameLevel = FileHandler.readLevel(levelName);
		LevelGUI levelGui = new LevelGUI(gameLevel);
		GameHandler gameHandler = new GameHandler(gameLevel, levelGui, this);
		revalidate();
		remove(currentPanel);
		currentPanel.setVisible(false);
		add(levelGui);
		setTitle("Network Domination - " + levelName);
		setContentPane(levelGui);
		gameHandler.run();
		currentPanel = levelGui;
	}

	/**
	 * Creates a campaign JPanel
	 */
	public void createCampaignScreen() {
		CampaignGUI campaignScreen = new CampaignGUI(this);
		setTitle("Campaign Screen");
		remove(currentPanel);
		currentPanel.setVisible(false);
		add(campaignScreen);
		setContentPane(campaignScreen);
		revalidate();
		currentPanel = campaignScreen;
	}
}

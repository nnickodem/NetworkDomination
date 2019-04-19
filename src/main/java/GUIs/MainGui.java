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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.lang.System.exit;

/**
 * Master GUI class
 */
public class MainGui extends JFrame {

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

					List<String> pauseMenuLabels = Arrays.asList("Save", "Main Menu", "Exit");

					for(String pauseLabel : pauseMenuLabels){
						JButton button = GUIUtils.createButtonDesigns();
						JLabel label = GUIUtils.createButtonLabelDesigns(pauseLabel);
						button.add(label);
						if(pauseLabel.equals("Main Menu")){
							button.addActionListener(e1 -> {
								setVisible(true);
								pauseFrame.dispose();
								createMainMenu();
							});
						}

						else if(pauseLabel.equals("Exit")){
							button.addActionListener(e1 -> exit(0));
						}
						pausePanel.add(button);
					}

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
		assert gameLevel != null;
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

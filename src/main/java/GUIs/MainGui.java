package GUIs;

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

public class MainGui extends JFrame {

	private MainMenu mainMenu;
	private final Dimension buttonSize = new Dimension(120, 60);

	public MainGui() {
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Network Domination");
		setBackground(Color.DARK_GRAY);
		createMainMenu();
		//createPauseMenu();
	}

	public void createMainMenu() {
		mainMenu = new MainMenu(this);
		add(mainMenu);
		mainMenu.setVisible(true);
		getContentPane().add(mainMenu);
		createPauseMenu();
	}

	public void createPauseMenu(){
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!isVisible()) {
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
							//setContentPane(mainMenu);
							//gameHandler.stopTimer();
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
	}

	public void createLevel() {
		//add(new LevelGui());
	}

	public void createCampaignScreen() {
		add(new CampaignGUI());
	}

	public void initCampaignScreen(final JPanel panel){
		CampaignGUI campaignScreen = new CampaignGUI();
		//setTitle("Campaign Screen");
		add(campaignScreen);
		//setContentPane(campaignScreen);
		invalidate();
		validate();
		remove(panel);
		panel.setVisible(false);
	}
}

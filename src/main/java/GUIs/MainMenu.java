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

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.lang.System.exit;

/**
 * Creates the main menu gui
 */
public class MainMenu extends JPanel {

	private final Dimension buttonSize = new Dimension(120, 60);
	private GameHandler gameHandler;

	/**
	 * Constructs the main menu JPanel
	 */
	public MainMenu(final MainGui mainGui) {
		setLayout(new GridBagLayout());
		setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		setBorder(BorderFactory.createEmptyBorder());
		setBackground(Color.DARK_GRAY);
		GridBagConstraints constraints = new GridBagConstraints();

		JButton playButton = new JButton(GUIUtils.scaleImage("resources/ui/button/buttonBase.png", 120, 60));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(3,0,3,0);
		constraints.fill = GridBagConstraints.CENTER;

		playButton.setPreferredSize(buttonSize);
		playButton.setContentAreaFilled(false);
		playButton.setFocusPainted(false);
		JLabel playLabel = new JLabel("Play");
		playLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		playLabel.setForeground(Color.WHITE);
		playLabel.setAlignmentY(.3f);
		playLabel.setAlignmentX(Label.CENTER_ALIGNMENT);
		playButton.add(playLabel);

		playButton.addActionListener(e -> {
			mainGui.createCampaignScreen(this);
		});
		add(playButton, constraints);

		JButton loadButton = new JButton(GUIUtils.scaleImage("resources/ui/button/buttonBase.png", 120, 60));
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(3,0,3,0);

		loadButton.setPreferredSize(buttonSize);
		loadButton.setContentAreaFilled(false);
		loadButton.setFocusPainted(false);
		JLabel loadLabel = new JLabel("Load");
		loadLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		loadLabel.setForeground(Color.WHITE);
		loadLabel.setAlignmentY(.3f);
		loadLabel.setAlignmentX(Label.CENTER_ALIGNMENT);
		loadButton.add(loadLabel);
		add(loadButton, constraints);

		JButton optionsButton = new JButton(GUIUtils.scaleImage("resources/ui/button/buttonBase.png", 120, 60));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(3,0,3,0);

		optionsButton.setPreferredSize(buttonSize);
		optionsButton.setContentAreaFilled(false);
		optionsButton.setFocusPainted(false);
		JLabel optionsLabel = new JLabel("Settings");
		optionsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		optionsLabel.setForeground(Color.WHITE);
		optionsLabel.setAlignmentY(.3f);
		optionsLabel.setAlignmentX(Label.CENTER_ALIGNMENT);
		optionsButton.add(optionsLabel);
		optionsButton.addActionListener(e -> {
			System.out.println("Button clicked");
		});
		add(optionsButton, constraints);

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
		exitButton.addActionListener(e-> exit(0));
		add(exitButton, constraints);

		//Add the mainMenu panel to the frame and set the window size
		setFocusable(true);

		setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
	}
}

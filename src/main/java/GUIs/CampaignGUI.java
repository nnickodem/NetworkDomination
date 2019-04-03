package GUIs;

import GameHandlers.GameHandler;
import Objects.GameLevel;
import ResourceHandlers.FileHandler;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

public class CampaignGUI extends JPanel{
	private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private final Dimension buttonSize = new Dimension(120,60);
	private final List<JButton> levelButtons = new ArrayList<>();
	private GameHandler gameHandler;

	public CampaignGUI(){
		setVisible(true);
		setLayout(null);
		setBackground(Color.DARK_GRAY);
		createSideComponent();

		JLabel globalScore = new JLabel("Global Score: " + "0");
		globalScore.setBounds(25, 25, 200, 50);
		globalScore.setFont(globalScore.getFont().deriveFont(24.0F));
		globalScore.setForeground(Color.WHITE);
		add(globalScore);

		add(addLevelButton("Level 1", 25, 500));
		add(addLevelButton("Level 2", 250, 500));
		add(addLevelButton("Level 3", 500, 250));
		add(addLevelButton("Level 4", 500, 750));
		add(addLevelButton("Level 5", 750, 500));
	}

	/**
	 * Creates the side panel on the level which has two panels. One panel with buttons for sending packets and one
	 * panel for upgrading a network device.
	 */
	private void createSideComponent() {
		final double screenWidth = screenSize.getWidth();
		final double screenHeight = screenSize.getHeight();
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
		JPanel packetPanel = new JPanel();
		JPanel upgradePanel = new JPanel();

		packetPanel.setBorder(BorderFactory.createEtchedBorder());

		upgradePanel.setBorder(BorderFactory.createEtchedBorder());

		containerPanel.add(packetPanel);
		containerPanel.add(upgradePanel);
		containerPanel.setBackground(Color.lightGray);
		containerPanel.setBounds((int)screenWidth - 140, 0, 140, (int)screenHeight);
		add(containerPanel);

		//reset container to read the new components added
		revalidate();
	}

	/**
	 * Adds each level button to the campaign screen
	 * @param name - name of the button
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @return - level JButton
	 */
	private JButton addLevelButton(String name, int x, int y){
		JButton level = new JButton(name);
		level.setPreferredSize(buttonSize);
		level.setBounds(x,y,100, 50);
		level.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		level.addActionListener(e -> {
			GameLevel gameLevel = FileHandler.readLevel(level.getText().substring(6));
			LevelGUI levelGui = new LevelGUI(gameLevel);
			gameHandler = new GameHandler(gameLevel, levelGui);
			revalidate();
			setVisible(false);
			add(levelGui);
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
			frame.setTitle("Network Domination - " + level.getText());
			frame.setContentPane(levelGui);
			gameHandler.run();
		});
		levelButtons.add(level);
		return level;

	}
}

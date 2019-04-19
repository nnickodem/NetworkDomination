package GUIs;

import ResourceHandlers.FileHandler;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CampaignGUI extends JPanel {

	private final List<JButton> levelButtons = new ArrayList<>();
	private List<Map.Entry<JButton, JButton>> deviceConnections = new ArrayList<>();
	private MainGui mainGui;

	public CampaignGUI(final MainGui mainGui) {
		this.mainGui = mainGui;
		setVisible(true);
		setLayout(null);
		setBackground(Color.DARK_GRAY);
		JPanel upgradePanel = new JPanel();
		JPanel packetPanel = new JPanel();
		GUIUtils.organizeSideComponent(this, packetPanel, upgradePanel);

		JLabel globalScore = new JLabel("Global Score: " + "0");
		globalScore.setBounds(25, 25, 200, 50);
		globalScore.setFont(globalScore.getFont().deriveFont(24.0F));
		globalScore.setForeground(Color.WHITE);
		add(globalScore);

		List<String> levelStatus = FileHandler.getSave();

		add(addLevelButton("1", 25, 500, levelStatus.get(0)));
		add(addLevelButton("2", 250, 500, levelStatus.get(1)));
		add(addLevelButton("3", 500, 250, levelStatus.get(2)));
		add(addLevelButton("4", 500, 750, levelStatus.get(3)));
		add(addLevelButton("5", 750, 500, levelStatus.get(4)));

		//Create the connections between the buttons
		deviceConnections.add(new AbstractMap.SimpleEntry<>(levelButtons.get(0), levelButtons.get(1)));
		deviceConnections.add(new AbstractMap.SimpleEntry<>(levelButtons.get(1), levelButtons.get(2)));
		deviceConnections.add(new AbstractMap.SimpleEntry<>(levelButtons.get(1), levelButtons.get(3)));
		deviceConnections.add(new AbstractMap.SimpleEntry<>(levelButtons.get(2), levelButtons.get(4)));
		deviceConnections.add(new AbstractMap.SimpleEntry<>(levelButtons.get(3), levelButtons.get(4)));
	}

	/**
	 * Adds each level button to the campaign screen
	 * @param name - name of the button
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @return - level JButton
	 */
	private JButton addLevelButton(final String name, final int x, final int y, final String status) {
		String color;
		switch(status) {
			case "1":
				color = "Blue";
				break;
			case "-1":
				color = "Red";
				break;
			default:
				color = "White";
		}
		JButton level = new JButton(GUIUtils.scaleImage("resources/objects/NetworkDevices/pc/pc" + color + ".png",60,60));
		level.setBounds(x,y,60, 60);
		level.setOpaque(false);
		level.setContentAreaFilled(false);
		level.addActionListener(e -> mainGui.createLevel(name));
		levelButtons.add(level);

		GUIUtils.createCampaignLabel(level, new JLabel(name));
		return level;
	}

	/**
	 * Paints a line between two buttons depending on the entries in the lineMap map.
	 * @param g Graphics object
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(4));
		for(Map.Entry<Point, Point> entry : GUIUtils.mapConnections(deviceConnections)){
			Line2D line2D = new Line2D.Double(entry.getKey(), entry.getValue());
			g2d.draw(line2D);
		}
	}
}

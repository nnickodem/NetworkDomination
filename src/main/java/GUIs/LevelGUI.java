package GUIs;

import Objects.GameLevel;
import Objects.NetworkDevices.NetworkDevice;
import Objects.Packets.PacketInfo;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads the level gui dynamically
 */
public class LevelGUI extends JPanel {

	private static final Logger logger = Logger.getLogger("errorLogger");
	private static final int PACKET_TIME = 2000;
	private final String deviceImagePath = "resources/objects/NetworkDevices/";
	private final String packetImagePath = "resources/objects/Packets/";
	private final Dimension buttonSize = new Dimension(100,60);
	private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private final List<JButton> packetButtons = new ArrayList<>(); //TODO: Get rid of?
	private GameLevel gameLevel;
	private List<Map.Entry<Point, Point>> lineMap;
	private BiMap<String, JButton> idToDeviceButton = HashBiMap.create();
	private Map<JLabel, PacketInfo> packetToInfo = new HashMap<>();
	private Map<String, JLabel> idToPacketCounter = new HashMap<>();
	private JButton selectedDevice; //TODO: get rid of somehow?
	private JButton targetDevice;
	int upgradeNumber = 1; //demo for upgrade button TODO: remove

	/**
	 * Constructs the level JPanel
	 * @param gameLevel game level object
	 */
	public LevelGUI(final GameLevel gameLevel) {
		this.gameLevel = gameLevel;

		setLayout(null);
		setBackground(Color.DARK_GRAY);

		List<Map.Entry<JButton, JButton>> deviceConnections = new ArrayList<>();

		for(int i = 0; i < gameLevel.getLevelMap().length; i++) {
			for(int k = 0; k < gameLevel.getLevelMap()[i].length; k++) {
				String deviceId = gameLevel.getLevelMap()[i][k];
				if(deviceId != null && !deviceId.equals("-")) {
					createDeviceButton(deviceId, new Point(i, k));
				}
			}
		}

		JLabel levelScore = new JLabel("Level Score: " + 0);
		levelScore.setBounds(25,0,200,50);
		levelScore.setFont(levelScore.getFont().deriveFont(24.0F));
		levelScore.setForeground(Color.WHITE);
		add(levelScore);

		JLabel globalScore = new JLabel("Global Score: " + "0");
		globalScore.setBounds(25, 25, 200, 50);
		globalScore.setFont(globalScore.getFont().deriveFont(24.0F));
		globalScore.setForeground(Color.WHITE);
		add(globalScore);

		for(Map.Entry<String, String> connection : gameLevel.getConnections()) {
			deviceConnections.add(new AbstractMap.SimpleEntry<>(idToDeviceButton.get(connection.getKey()), idToDeviceButton.get(connection.getValue())));
		}
		mapConnections(deviceConnections);
		createSideComponent();
		packetTimer();
		logger.log(Level.INFO, "Level GUI created");
	}

	/**
	 * Creates a device button and accompanying packet counter, adds them to the JPanel
	 * @param deviceID device ID
	 * @param coordinate coordinate of device on the original level map
	 */
	private void createDeviceButton(final String deviceID, final Point coordinate) {
		NetworkDevice device = gameLevel.getIdToDeviceObject().get(deviceID);
		JButton deviceButton = new JButton(scaleImage(deviceImagePath + device.getType() + "/" + device.getType() + device.getTeam() + ".png", 60, 60));

		idToDeviceButton.put(deviceID, deviceButton);
		deviceButton.addActionListener(e -> {
			setButtonUsage(deviceID);
			selectedDevice = deviceButton;
			transferFocusBackward();
			updateTargetSelection(idToDeviceButton.get(device.getTarget()));
		});
		deviceButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.isMetaDown()) {
					updateTargetSelection(deviceButton);
					gameLevel.getIdToDeviceObject().get(idToDeviceButton.inverse().get(selectedDevice)).setTarget(idToDeviceButton.inverse().get(deviceButton));
				}
			}
		});
		deviceButton.setBounds(coordinate.x*125, coordinate.y*125, 60,60);
		deviceButton.setContentAreaFilled(false);
		deviceButton.setFocusPainted(false);
		add(deviceButton);

		JLabel packetCounter = new JLabel();
		idToPacketCounter.put(deviceID, packetCounter);
		packetCounter.setBounds(coordinate.x*125 + 70, coordinate.y*125, 40, 20);
		packetCounter.setText("10/" + device.getMaxPacket());
		packetCounter.setFont(packetCounter.getFont().deriveFont(12.0F));
		packetCounter.setForeground(Color.WHITE);
		add(packetCounter);
	}

	/**
	 * Scales the image files that are loaded into the proper game image size wanted
	 * @param imageFile
	 * @return imageIcon
	 */
	private ImageIcon scaleImage(final String imageFile, final Integer width, final Integer height){
		ImageIcon imageIcon = new ImageIcon(imageFile);
		Image image = imageIcon.getImage();
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newImage);
		return imageIcon;
	}

	/**
	 * Creates a Map of coordinates of each network device.
	 * @param connections List of connections between buttons
	 */
	private void mapConnections(final List<Map.Entry<JButton, JButton>> connections){
		lineMap = new ArrayList<>();
		for(Map.Entry<JButton, JButton> connection : connections){
			Point tempA = connection.getKey().getLocation();
			Point tempB = connection.getValue().getLocation();
			tempA = new Point((int)tempA.getX() + 30, (int)tempA.getY() + 30);
			tempB = new Point((int)tempB.getX() + 30, (int)tempB.getY() + 30);
			lineMap.add(new AbstractMap.SimpleEntry<>(tempA, tempB));
		}
	}

	/**
	 * Paints a line between two buttons depending on the entries in the lineMap map.
	 * @param g Graphics object
	 */
	@Override
	protected void paintComponent(final Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(4));
		for(Map.Entry<Point, Point> entry : lineMap){
			Line2D line2D = new Line2D.Double(entry.getKey(), entry.getValue());
			g2d.draw(line2D);
		}
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

		List<String> packetTypes = Arrays.asList("ICMP", "SYN", "CryptoJack"); //TODO: add toggle button for sending botnets?
		for(String packetType : packetTypes){
			JButton packetButton = new JButton(packetType);
			packetButtons.add(packetButton);
			packetButton.addActionListener(e -> {
				sendPacket(packetType.toLowerCase(), selectedDevice, targetDevice, "Blue");
				transferFocusBackward();
			});
			packetButton.setEnabled(false);
			packetButton.setPreferredSize(buttonSize);
			packetPanel.add(packetButton);
		}
		packetPanel.setBorder(BorderFactory.createEtchedBorder());

		List<String> upgrades = Arrays.asList("Upgrade 1", "Upgrade 2");
		for(String upgrade : upgrades) {
			JButton upgradeButton = new JButton(scaleImage("resources/ui/upgradeButton/upgradeButton1.png", 120, 60));
			upgradeButton.setPreferredSize(new Dimension(120, 60));
			upgradePanel.add(upgradeButton);
			upgradeButton.setContentAreaFilled(false);
			upgradeButton.setFocusPainted(false);
			JLabel upgradeLabel = new JLabel(upgrade);
			upgradeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
			upgradeLabel.setForeground(Color.WHITE);
			upgradeLabel.setAlignmentY(.3f);
			upgradeButton.add(upgradeLabel);
			upgradeButton.addActionListener(e -> { //TODO: remove, demo for button
				JButton button = (JButton) e.getSource();
				upgradeNumber = (upgradeNumber + 1) % 4;
				button.setIcon(scaleImage("resources/ui/upgradeButton/upgradeButton" + upgradeNumber + ".png", 120, 60));
			});
		}
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
	 * Sets the visibility of buttons depending on which device that you press.
	 * @param deviceId String variable for the type of device that was clicked
	 */
	private void setButtonUsage(final String deviceId){
		for(JButton button : packetButtons) { //TODO: change Blue to player's team
			if (gameLevel.getIdToDeviceObject().get(deviceId).getTeam().equals("Blue") && gameLevel.getIdToDeviceObject().get(deviceId).getPackets().contains(button.getText())) {
				button.setEnabled(true);
			} else {
				button.setEnabled(false);
			}
		}
	}

	/**
	 * generates a packet JLabel for packetTimer to move
	 * @param packetType packet type
	 * @param source source device JButton
	 * @param target target device JButton
	 * @param team source device team
	 */
	private void sendPacket(final String packetType, final JButton source, final JButton target, final String team) {
		String packetCounter = idToPacketCounter.get(idToDeviceButton.inverse().get(source)).getText();
		if(target != null && Integer.valueOf(packetCounter.substring(0,packetCounter.indexOf("/"))) > 0) {
			JLabel packet = new JLabel(scaleImage(packetImagePath + packetType + "/" + packetType + team + ".png", 30, 30));
			packet.setBounds(source.getLocation().x + 20, source.getLocation().y + 20, 30, 30);
			add(packet);
			PacketInfo packetInfo = new PacketInfo(System.currentTimeMillis(), team, packetType, source, target);
			packetToInfo.put(packet, packetInfo);
			updatePacketCounter(idToDeviceButton.inverse().get(source), team, -1);
		}
	}

	/**
	 * overload of sendPacket above
	 * @param packetType packet type
	 * @param sourceId source device ID
	 * @param targetId target device ID
	 * @param team source device team
	 */
	public void sendPacket(final String packetType, final String sourceId, final String targetId, final String team) {
		sendPacket(packetType, idToDeviceButton.get(sourceId), idToDeviceButton.get(targetId), team);
	}

	/**
	 * Creates and starts a timer that updates the position of all packets every 10 ms
	 */
	private void packetTimer() {
		Timer timer = new Timer(10, e -> {
			for (Map.Entry<JLabel, PacketInfo> packet : packetToInfo.entrySet()) {
				JLabel label = packet.getKey();
				Point start = packet.getValue().getSource().getLocation();
				Point end = packet.getValue().getTarget().getLocation();
				start.x += 15;
				start.y += 15;
				end.x += 15;
				end.y += 15;
				if(label.getLocation() == end || (Math.abs(label.getLocation().x - end.x) < 10 && Math.abs(label.getLocation().y - end.y) < 10)) {
					updatePacketCounter(idToDeviceButton.inverse().get(packet.getValue().getTarget()), packet.getValue().getTeam(),1);
					packetToInfo.remove(packet.getKey());
					remove(label);
					break;
				}
				long duration = System.currentTimeMillis() - packetToInfo.get(label).getTime();
				float progress = (float) duration / (float) PACKET_TIME; //TODO: pre-calculate based on distance
				if (progress > 1f) {
					progress = 1f;
				}
				int x = start.x + Math.round((end.x - start.x) * progress);
				int y = start.y + Math.round((end.y - start.y) * progress);

				label.setLocation(x, y);
			}
		});
		timer.start();
	}

	/**
	 * Updates the packet counter of a device based off of how many packets and what team the packets are from
	 * If an enemy packet hits the device at 0, the device changes control to the enemy team
	 * @param deviceID network device ID
	 * @param packetTeam packet team
	 * @param packetCount number of packets
	 */
	public void updatePacketCounter(final String deviceID, final String packetTeam, final Integer packetCount) {
		String packetCounter = idToPacketCounter.get(deviceID).getText();
		Integer current = Integer.valueOf(packetCounter.substring(0,packetCounter.indexOf("/")));
		NetworkDevice device = gameLevel.getIdToDeviceObject().get(deviceID);
		if(packetTeam.equals(device.getTeam())) {
			current += packetCount;
		} else {
			current -= packetCount;
		}
		if(current <= device.getMaxPacket() && current >= 0) {
			idToPacketCounter.get(deviceID).setText(String.valueOf(current) + "/" + device.getMaxPacket());
		} else if(current < 0 && !device.getTeam().equals(packetTeam)) {
			idToDeviceButton.get(deviceID).setIcon(scaleImage(deviceImagePath + device.getType() + "/" + device.getType() + packetTeam + ".png", 60, 60));
			device.setTeam(packetTeam);
			device.setTarget(null);
		}
	}

	/**
	 * Updated the border visual for the selected device's target
	 * @param deviceButton target device JButton
	 */
	public void updateTargetSelection(final JButton deviceButton) {
		if(targetDevice != null) {
			targetDevice.setBorder(BorderFactory.createEmptyBorder());
		}
		targetDevice = deviceButton;
		if(deviceButton != null) {
			deviceButton.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
		}
	}
}
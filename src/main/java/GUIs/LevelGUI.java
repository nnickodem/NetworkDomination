package GUIs;

import GameHandlers.DeviceHandler;
import Objects.GameLevel;
import Objects.NetworkDevices.NetworkDevice;
import Objects.Packets.PacketInfo;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
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
	private static final int PACKET_TIME = 1300;
	private final String deviceImagePath = "resources/objects/NetworkDevices/";
	private final Dimension buttonSize = new Dimension(120,60);
	private final List<JButton> packetButtons = new ArrayList<>();
	private List<Map.Entry<JButton, JButton>> deviceConnections = new ArrayList<>();
	private GameLevel gameLevel;
	private BiMap<String, JButton> idToDeviceButton = HashBiMap.create();
	private Map<String, JLabel> idToPacketCounter = new HashMap<>();
	private Timer packetTimer;
	private Map<JLabel, PacketInfo> packetToInfo = new HashMap<>();
	private Map<JButton, JLabel> buttonToLabel = new HashMap<>();
	private JButton selectedDevice;
	private JButton targetDevice;
	private JLabel levelScore;
	private int upgradeNumber = 1; //demo for upgrade button TODO: remove
	private int localScore = 0;

	/**
	 * Constructs the level JPanel
	 * @param gameLevel game level object
	 */
	public LevelGUI(final GameLevel gameLevel) {
		this.gameLevel = gameLevel;

		setLayout(null);
		setBackground(Color.DARK_GRAY);

		preLevelDialog();

		//List<Map.Entry<JButton, JButton>> deviceConnections = new ArrayList<>();

		for(int i = 0; i < gameLevel.getLevelMap().length; i++) {
			for(int k = 0; k < gameLevel.getLevelMap()[i].length; k++) {
				String deviceId = gameLevel.getLevelMap()[i][k];
				if(deviceId != null && !deviceId.equals("-")) {
					createDeviceButton(deviceId, new Point(i, k));
				}
			}
		}

		levelScore = new JLabel("Level Score: " + localScore);
		levelScore.setBounds(25,0,200,50);
		levelScore.setFont(levelScore.getFont().deriveFont(24.0F));
		levelScore.setForeground(Color.WHITE);
		add(levelScore);

		JLabel globalScore = new JLabel("Global Score: " + "0");
		globalScore.setBounds(25, 25, 200, 50);
		globalScore.setFont(globalScore.getFont().deriveFont(24.0F));
		globalScore.setForeground(Color.WHITE);
		add(globalScore);

		//For each connection in the connection list, it adds the connection to the deviceConnections list variable.
		//idToDeviceButton gets the desired button from the id String.
		for(Map.Entry<Map.Entry<String, String>, Integer> connection : gameLevel.getConnections().entrySet()) {
			deviceConnections.add(new AbstractMap.SimpleEntry<>(idToDeviceButton.get(connection.getKey().getKey()), idToDeviceButton.get(connection.getKey().getValue())));
		}
		createSideComponent();
		packetTimer();
		logger.log(Level.INFO, "Level GUIs created");
	}

	/**
	 * Creates a device button and accompanying packet counter, adds them to the JPanel
	 * @param deviceID device ID
	 * @param coordinate coordinate of device on the original level map
	 */
	private void createDeviceButton(final String deviceID, final Point coordinate) {
		NetworkDevice device = gameLevel.getIdToDeviceObject().get(deviceID);
		JButton deviceButton = new JButton(GUIUtils.scaleImage(deviceImagePath + device.getType() + "/" + device.getType() + device.getTeam() + ".png", 60, 60));

		idToDeviceButton.put(deviceID, deviceButton);
		deviceButton.addActionListener(e -> {
			setButtonUsage(deviceID);
			if(selectedDevice != null) {
				selectedDevice.setBorder(BorderFactory.createEmptyBorder());
			}
			selectedDevice = deviceButton;
			transferFocusBackward();
			updateTargetSelection(idToDeviceButton.get(device.getTarget()));
			deviceButton.setBorder(BorderFactory.createMatteBorder(0,2,2,0,Color.CYAN));
		});
		deviceButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.isMetaDown() && gameLevel.getIdToDeviceObject().get(idToDeviceButton.inverse().get(selectedDevice)).getTeam().equals("Blue")) {
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
	 * Paints a line between two buttons depending on the entries in the lineMap map.
	 * @param g Graphics object
	 */
	@Override
	protected void paintComponent(final Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(4));
		for(Map.Entry<Point, Point> entry : GUIUtils.mapConnections(deviceConnections)){
			Line2D line2D = new Line2D.Double(entry.getKey(), entry.getValue());
			g2d.draw(line2D);
		}
	}

	/**
	 * Creates the side panel on the level which has two panels. One panel with buttons for sending packets and one
	 * panel for upgrading a network device.
	 */
	private void createSideComponent() {
		JPanel packetPanel = new JPanel();
		JPanel upgradePanel = new JPanel();

		List<String> packetTypes = Arrays.asList("Botnet", "ICMP", "SYN", "CryptoJack");
		for(String packetType : packetTypes){
			JButton packetButton = GUIUtils.createButtonDesigns();
			packetButtons.add(packetButton);

			JLabel packetLabel = GUIUtils.createButtonLabelDesigns(packetType);
			packetButton.add(packetLabel);
			if(!packetType.equals("Botnet")) {
				packetButton.addActionListener(e -> {
					sendPacket(packetType.toLowerCase(), selectedDevice, targetDevice, "Blue");
					transferFocusBackward();
				});
			} else {
				packetButton.addActionListener(e -> {
					NetworkDevice device = gameLevel.getIdToDeviceObject().get(idToDeviceButton.inverse().get(selectedDevice));
					device.setSending(!device.isSending());
					transferFocusBackward();
				});
			}
			packetButton.setEnabled(false);
			packetPanel.add(packetButton);
			buttonToLabel.put(packetButton, packetLabel);
		}
		packetPanel.setBorder(BorderFactory.createEtchedBorder());

		List<String> upgrades = Arrays.asList("Upgrade 1", "Upgrade 2");
		for(String upgrade : upgrades) {
			JButton upgradeButton = new JButton(GUIUtils.scaleImage("resources/ui/upgradeButton/upgradeButton1.png", 120, 60));
			upgradeButton.setPreferredSize(buttonSize);
			upgradePanel.add(upgradeButton);
			upgradeButton.setContentAreaFilled(false);
			upgradeButton.setFocusPainted(false);
			JLabel upgradeLabel = new JLabel(upgrade);
			GUIUtils.createButtonLabel(upgradeButton, upgradeLabel);
			upgradeButton.addActionListener(e -> { //TODO: remove, demo for button
				upgradeNumber = (upgradeNumber + 1) % 4;
				upgradeButton.setIcon(GUIUtils.scaleImage("resources/ui/upgradeButton/upgradeButton" + upgradeNumber + ".png", 120, 60));
				transferFocusBackward();
			});
		}
		GUIUtils.organizeSideComponent(this, packetPanel, upgradePanel);
	}

	/**
	 * Sets the visibility of buttons depending on which device that you press.
	 * @param deviceId String variable for the type of device that was clicked
	 */
	private void setButtonUsage(final String deviceId){
		for(JButton button : packetButtons) {
			if (gameLevel.getIdToDeviceObject().get(deviceId).getTeam().equals("Blue") &&
					gameLevel.getIdToDeviceObject().get(deviceId).getPackets().contains(buttonToLabel.get(button).getText())) {
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
			String packetImagePath = "resources/objects/Packets/";
			JLabel packet = new JLabel(GUIUtils.scaleImage(packetImagePath + packetType + "/" + packetType + team + ".png", 30, 30));
			packet.setBounds(source.getLocation().x + 20, source.getLocation().y + 20, 30, 30);
			add(packet);
			List<String> path = DeviceHandler.getPath(idToDeviceButton.inverse().get(source), idToDeviceButton.inverse().get(target), gameLevel);
			List<JButton> buttonPath = new ArrayList<>();
			for(String hop : path) {
				buttonPath.add(idToDeviceButton.get(hop));
			}
			String sourceId = idToDeviceButton.inverse().get(source);
			String targetId = idToDeviceButton.inverse().get(buttonPath.get(0));
			PacketInfo packetInfo = new PacketInfo(System.currentTimeMillis(), team, packetType, source, buttonPath,
					gameLevel.getConnections().get(new AbstractMap.SimpleEntry<>(sourceId, targetId)));
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
		packetTimer = new Timer(10, e -> {
			for(Map.Entry<JLabel, PacketInfo> packet : packetToInfo.entrySet()) {
				JLabel label = packet.getKey();
				PacketInfo packetInfo = packet.getValue();
				Point start = packetInfo.getSource().getLocation();
				Point end = packetInfo.getPath().get(0).getLocation();
				start.x += 15;
				start.y += 15;
				end.x += 15;
				end.y += 15;
				if(label.getLocation() == end || (Math.abs(label.getLocation().x - end.x) < 10 && Math.abs(label.getLocation().y - end.y) < 10)) {
					NetworkDevice target = gameLevel.getIdToDeviceObject().get(idToDeviceButton.inverse().get(packetInfo.getPath().get(0)));
					if(packetInfo.getPath().size() <= 1 || !packetInfo.getTeam().equals(target.getTeam())) {
						updatePacketCounter(idToDeviceButton.inverse().get(packetInfo.getPath().get(0)), packetInfo.getTeam(), 1);
						packetToInfo.remove(label);
						remove(label);
						break;
					} else {
						packetInfo.setSource(packetInfo.getPath().get(0));
						packetInfo.getPath().remove(0);
						packetInfo.setTime(System.currentTimeMillis());
						String sourceId = idToDeviceButton.inverse().get(packetInfo.getSource());
						String targetId = idToDeviceButton.inverse().get(packetInfo.getPath().get(0));
						packetInfo.setTimeToTarget(gameLevel.getConnections().get(new AbstractMap.SimpleEntry<>(sourceId, targetId)));
					}
				}
				long duration = System.currentTimeMillis() - packetToInfo.get(label).getTime();
				float progress = (float) duration / (float) (PACKET_TIME * packetInfo.getTimeToTarget());
				if (progress > 1f) {
					progress = 1f;
				}
				int x = start.x + Math.round((end.x - start.x) * progress);
				int y = start.y + Math.round((end.y - start.y) * progress);

				label.setLocation(x, y);
				JLabel intersect = checkPacketCollision(label, packetInfo.getTeam());
				if(intersect != null) {
					packetToInfo.remove(label);
					remove(label);
					packetToInfo.remove(intersect);
					remove(intersect);
					break;
				}
			}
		});
		packetTimer.start();
	}

	/**
	 * Stops the packet animation timer
	 */
	public void stopPacketTimer() {
		packetTimer.stop();
	}

	/**
	 * Checks for collision with a non-friendly packet
	 * @param packetLabel packet JLabel collision is being checked against
	 * @param team team of packet being checked
	 * @return JLabel of packet that checked packet is intersecting
	 */
	private JLabel checkPacketCollision(final JLabel packetLabel, final String team) {
		Area labelArea = new Area(packetLabel.getBounds());
		for(Map.Entry<JLabel, PacketInfo> packet : packetToInfo.entrySet()) {
			if(!packet.getKey().equals(packetLabel)) {
				if(labelArea.intersects(packet.getKey().getBounds()) && !team.equals(packet.getValue().getTeam())) {
					return packet.getKey();
				}
			}
		}
		return null;
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
			idToPacketCounter.get(deviceID).setText(current + "/" + device.getMaxPacket());
		} else if(current < 0 && !device.getTeam().equals(packetTeam)) {
			idToDeviceButton.get(deviceID).setIcon(GUIUtils.scaleImage(deviceImagePath + device.getType() + "/" + device.getType() + packetTeam + ".png", 60, 60));
			device.setTeam(packetTeam);
			if (device.getTeam().equals("Blue")) {
				localScore = localScore + 100;
				levelScore.setText("Level Score: " + localScore);
			}
			device.setTarget(null);
			device.setSending(false);
			gameLevel.incrementMapVersion();
			if(deviceID.equals(idToDeviceButton.inverse().get(selectedDevice))) {
				setButtonUsage(deviceID);
			}
		}
	}

	/**
	 * Updated the border visual for the selected device's target
	 * @param deviceButton target device JButton
	 */
	private void updateTargetSelection(final JButton deviceButton) {
		if(targetDevice != null) {
			targetDevice.setBorder(BorderFactory.createEmptyBorder());
		}
		targetDevice = deviceButton;
		if(deviceButton != null) {
			Border border = BorderFactory.createMatteBorder(2,0,0,2,Color.CYAN);
			deviceButton.setBorder(border);
		}
	}

	/**
	 * Create the pre level dialog pop up frame which has a description of the levels and the objectives.
	 */
	private void preLevelDialog(){
		JFrame levelDescription = new JFrame();
		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.PAGE_AXIS));
		levelDescription.add(descriptionPanel);

		List<String> titles = Arrays.asList("Level Description", "Primary Objectives" ,"Secondary Objectives");

		for(String title : titles) {

			JLabel descriptionTitle = new JLabel("<html>" + title + "</html>");
			descriptionTitle.setBorder(new EmptyBorder(0,70,0,70));
			descriptionTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
			descriptionPanel.add(descriptionTitle);

			String text;
			switch(title){
				case "Level Description":
					text = String.join("<br/>", gameLevel.getDescription());
					break;
				case "Primary Objectives":
					text = String.join("",gameLevel.getPrimaryObjectives());
					break;
				case "Secondary Objectives":
					text = String.join("",gameLevel.getSecondaryObjectives());
					break;
				default:
					text = "";
			}
			JLabel description = new JLabel("<html><div style='font-size: 12px'>" + text + "</div></html>");
			description.setBorder(new EmptyBorder(0, 40, 0, 70));
			descriptionPanel.add(description);
		}

		JPanel closePanel = new JPanel();
		JButton closeButton = new JButton(GUIUtils.scaleImage("resources/ui/button/buttonBase.png", 120, 60));
		closeButton.setContentAreaFilled(false);
		closeButton.setFocusPainted(false);
		closeButton.setSize(buttonSize);
		JLabel closeLabel = new JLabel("Close");
		GUIUtils.createButtonLabel(closeButton, closeLabel);
		closeButton.addActionListener(e-> levelDescription.dispose());
		closePanel.add(closeButton);
		descriptionPanel.add(closePanel);

		levelDescription.setVisible(true);
		levelDescription.setSize(500,500);
		levelDescription.setLocationRelativeTo(null);
		levelDescription.setResizable(false);
	}
}
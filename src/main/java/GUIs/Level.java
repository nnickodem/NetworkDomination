package GUIs;

import Objects.GameLevel;
import Objects.NetworkDevices.NetworkDevice;
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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Loads the level gui dynamically
 */
public class Level extends JPanel {

    private static final Logger logger = Logger.getLogger("errorLogger");
    private static final int PACKET_TIME = 2000;
    private final String imagePath = "resources/objects/NetworkDevices/";
    private final String packetImagePath = "resources/objects/Packets/botnet/";
    private final Dimension buttonSize = new Dimension(100,60);
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final double screenWidth = screenSize.getWidth();
    private final double screenHeight = screenSize.getHeight();
    private final List<JButton> packetButtons = new ArrayList<JButton>();
    private List<Map.Entry<Point, Point>> lineMap;
    private JButton selected;
    private Map<JLabel, Map.Entry<JButton, JButton>> packets = new HashMap<>();
    private BiMap<String, JButton> devices = HashBiMap.create();
    private Map<JLabel, Long> packetToTime = new HashMap<>();
	private GameLevel gameLevel;
    private Map<String, JLabel> idToPackets = new HashMap<>();
    private JButton targetDevice;

    /**
     * Constructs the level JPanel
     * @param gameLevel
     */
    public Level(final GameLevel gameLevel) {
        this.gameLevel = gameLevel;
        List<Map.Entry<JButton, JButton>> deviceConnections = new ArrayList<>();
        String temp;
        String deviceType;
        NetworkDevice tempDevice;

        this.setLayout(null);
        setBackground(Color.DARK_GRAY);

        for(int i = 0; i < gameLevel.getLevelMap().length; i++) {
            for(int k = 0; k < gameLevel.getLevelMap()[i].length; k++) {
                temp = gameLevel.getLevelMap()[i][k];
                final String deviceId = temp;
                if(temp != null && !temp.equals("-")) {
                    tempDevice = gameLevel.getIdToDeviceObject().get(temp);
                    deviceType = tempDevice.getClass().toString();
                    deviceType = tempDevice.getClass().toString().substring(deviceType.lastIndexOf(".")+1).toLowerCase();
                    final String deviceTeams = tempDevice.getTeam();
                    final JButton button = new JButton(scaleImage(imagePath + deviceType + "/" + deviceType + tempDevice.getTeam() + ".png"));
                    devices.put(temp, button);
                    button.addActionListener(e-> {
                        setButtonUsage(deviceId, deviceTeams);
                        selected = button;
                        transferFocusBackward();
                    });
                    button.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if(e.isMetaDown()) {
                                if(targetDevice != null) {
                                    targetDevice.setBorder(BorderFactory.createEmptyBorder());
                                }

                                //If Button is right clicked, set the target device for sending packets
                                targetDevice = button;
                                button.setBorder(BorderFactory.createLineBorder(Color.CYAN,2));
                            }
                        }
                    });
                    this.add(button);
                    button.setBounds(i*125, k*125, 60,60);
                    button.setContentAreaFilled(false);
                    button.setFocusPainted(false);
                    JLabel packetCounter = new JLabel(); //TODO: this is the first iteration of packet counters, needs to be redone
                    idToPackets.put(temp, packetCounter);
                    this.add(packetCounter);
                    packetCounter.setBounds(i*125 + 70, k*125, 40, 20);
                    packetCounter.setText("0");
                    packetCounter.setFont(packetCounter.getFont().deriveFont(12.0F));
                    packetCounter.setForeground(Color.WHITE);
                }
            }
        }

        for(Map.Entry<String, String> connection : gameLevel.getConnections()) {
            deviceConnections.add(new AbstractMap.SimpleEntry<>(devices.get(connection.getKey()), devices.get(connection.getValue())));
        }
        listCoordinates(deviceConnections);
        createSideComponent();
        startTimer();
    }

    //Scales the image files that are loaded in to the proper game image size wanted

    /**
     * Scales the image files that are loaded into the proper game image size wanted
     * @param imageFile
     * @return imageIcon
     */
    public ImageIcon scaleImage(String imageFile){
        ImageIcon imageIcon = new ImageIcon(imageFile);
        Image image = imageIcon.getImage();
        //TODO: adjust scaling?
        Image newImage = image.getScaledInstance(60,60, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);
        return imageIcon;
    }

    /**
     * Creates a Map of coordinates of each network device.
     * @param connections List of connections between buttons
     */
    public void listCoordinates(List<Map.Entry<JButton, JButton>> connections){
        Point tempA;
        Point tempB;
        lineMap = new ArrayList<>();
        for(Map.Entry<JButton, JButton> connection : connections){
            tempA = connection.getKey().getLocation();
            tempB = connection.getValue().getLocation();
            tempA = new Point((int)tempA.getX() + 30, (int)tempA.getY() + 30);
            tempB = new Point((int)tempB.getX() + 30, (int)tempB.getY() + 30);
            lineMap.add(new AbstractMap.SimpleEntry<>(tempA, tempB));
        }
    }

    /**
     * Paints a line between two buttons depending on the entries in the lineMap map.
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics g2d = (Graphics2D) g;
        Line2D line2D;
        ((Graphics2D) g2d).setStroke(new BasicStroke(4));
        for(Map.Entry<Point, Point> entry : lineMap){
            line2D = new Line2D.Double(entry.getKey(), entry.getValue());
            ((Graphics2D) g2d).draw(line2D);
        }
    }

    /**
     * Creates the side panel on the level which has two panels. One panel with buttons for sending packets and one
     * panel for upgrading a network device.
     */
    protected void createSideComponent(){
        JButton botNet = new JButton("Botnet");
        packetButtons.add(botNet);
        JButton ICMP = new JButton("ICMP");
        packetButtons.add(ICMP);
        JButton SYN = new JButton("SYN");
        packetButtons.add(SYN);
        JButton crypto = new JButton("CryptoJack");
        packetButtons.add(crypto);
        for(JButton button : packetButtons){
            button.addActionListener(e->{
                    sendPacket(targetDevice);
                transferFocusBackward();
            });
            button.setEnabled(false);
        }

        JButton upgradeButton1 = new JButton("Upgrade 1");
        JButton upgradeButton2 = new JButton("Upgrade 2");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JPanel packetTypes = new JPanel();
        JPanel upgradeButtons = new JPanel();
        this.add(panel);
        panel.add(packetTypes);
        panel.add(upgradeButtons);

        botNet.setPreferredSize(buttonSize);
        packetTypes.add(botNet);

        ICMP.setPreferredSize(buttonSize);
        packetTypes.add(ICMP);

        SYN.setPreferredSize(buttonSize);
        packetTypes.add(SYN);

        crypto.setPreferredSize(buttonSize);
        packetTypes.add(crypto);

        packetTypes.setBorder(BorderFactory.createEtchedBorder());

        //Add upgrade buttons to the upgradePanel
        upgradeButton1.setPreferredSize(buttonSize);
        upgradeButtons.add(upgradeButton1);

        upgradeButton2.setPreferredSize(buttonSize);
        upgradeButtons.add(upgradeButton2);
        upgradeButtons.setBorder(BorderFactory.createEtchedBorder());

        panel.setBackground(Color.lightGray);
        //reset container to read the new components added
        revalidate();
        panel.setBounds((int)screenWidth - 150, 0, 150, (int)screenHeight);
    }

    /**
     * Sets the visibility of buttons depending on which device that you press.
     * @param deviceId String variable for the type of device that was clicked
     * @param team String variable for the team of the selected device
     */
    public void setButtonUsage(String deviceId, String team){
            for(JButton button : packetButtons) {
                if (gameLevel.getIdToDeviceObject().get(deviceId).getTeam().equals("Blue") && gameLevel.getIdToDeviceObject().get(deviceId).getPackets().contains(button.getText())) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
    }

    /**
     * Adds a packet to the list, starting from the currently selected device and going to the current target device
     */
    private void sendPacket(JButton target) {
        if(target != null && Integer.valueOf(idToPackets.get(devices.inverse().get(selected)).getText()) > 0) {
            JLabel packet = new JLabel(scaleImage(packetImagePath + "botnetBlue.png"));
            packet.setBounds(selected.getLocation().x + 20, selected.getLocation().y + 20, 20, 20);
            add(packet);
            packets.put(packet, new AbstractMap.SimpleEntry<>(selected, target));
            packetToTime.put(packet, System.currentTimeMillis());
            updatePacketCounter(devices.inverse().get(selected), -1);
        }
    }

    /**
     * Creates and starts a timer that updates the position of all packets every 10 ms
     */
    private void startTimer() {
        Timer timer = new Timer(10, e -> {
            for (Map.Entry<JLabel, Map.Entry<JButton, JButton>> packet : packets.entrySet()) {
                JLabel label = packet.getKey();
                Point start = packet.getValue().getKey().getLocation();
                Point end = packet.getValue().getValue().getLocation();
                start.x += 20;
                start.y += 20;
                end.x += 20;
                end.y += 20;
                if(label.getLocation() == end || (label.getLocation().x - end.x < 5 && label.getLocation().y - end.y < 5)) {
                    updatePacketCounter(devices.inverse().get(packets.get(packet.getKey()).getValue()), 1);
                    packets.remove(packet.getKey());
                    packetToTime.remove(packet.getKey());
                    remove(label);
                    break;
                }
                long duration = System.currentTimeMillis() - packetToTime.get(label);
                float progress = (float) duration / (float) PACKET_TIME;
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

    public void updatePacketCounter(final String deviceID, final Integer packetCount) {
        Integer current = Integer.valueOf(idToPackets.get(deviceID).getText());
        current += packetCount;
        if(current <= 25 && current >= 0) {
            idToPackets.get(deviceID).setText(String.valueOf(current));
        }
        //TODO: implement more
    }
}

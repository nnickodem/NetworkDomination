package GUIs;

import Objects.GameLevel;
import Objects.NetworkDevices.NetworkDevice;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Level extends JPanel {

    private final String imagePath = "resources/objects/NetworkDevices/";
    private final Dimension buttonSize = new Dimension(100,60);
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final double screenWidth = screenSize.getWidth();
    private final double screenHeight = screenSize.getHeight();
    private final List<JButton> packetButtons = new ArrayList<JButton>();
    private List<Map.Entry<Point, Point>> lineMap;

    public Level(final GameLevel level) {

        List<Map.Entry<JButton, JButton>> deviceConnections = new ArrayList<>();
        BiMap<String, JButton> devices = HashBiMap.create();
        String temp;
        String deviceType;
        NetworkDevice tempDevice;
        JButton button;

        this.setLayout(null);
        setBackground(Color.DARK_GRAY);

        for(int i = 0; i < level.getLevelMap().length; i++) {
            for(int k = 0; k < level.getLevelMap()[i].length; k++) {
                temp = level.getLevelMap()[i][k];
                if(temp != null && !temp.equals("-")) {
                    tempDevice = level.getIdToDeviceObject().get(temp);
                    deviceType = tempDevice.getClass().toString();
                    deviceType = tempDevice.getClass().toString().substring(deviceType.lastIndexOf(".")+1).toLowerCase();
                    final String tempNetworkDevice = deviceType;
                    final String deviceTeams = tempDevice.getTeam();
                    button = new JButton(scaleImage(imagePath + deviceType + "/" + deviceType + tempDevice.getTeam() + ".png"));
                    devices.put(temp, button);
                    button.addActionListener(e-> {
                        setButtonUsage(tempNetworkDevice, deviceTeams);
                        transferFocusBackward();
                    });
                    this.add(button);
                    button.setBounds(i*125, k*125, 70,70);
                    button.setContentAreaFilled(false);
                    button.setFocusPainted(false);
                    JLabel test = new JLabel();
                    this.add(test);
                    test.setBounds(i*125 + 70, k*125, 20, 20);
                    test.setText("10");
                    test.setFont(test.getFont().deriveFont(12.0F));
                    test.setForeground(Color.WHITE);
                }
            }
        }

        for(Map.Entry<String, String> connection : level.getConnections()) {
            deviceConnections.add(new AbstractMap.SimpleEntry<>(devices.get(connection.getKey()), devices.get(connection.getValue())));
        }

        listCoordinates(deviceConnections);
        createSideComponent();
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
            tempA = new Point((int)tempA.getX() + 35, (int)tempA.getY() + 35);
            tempB = new Point((int)tempB.getX() + 35, (int)tempB.getY() + 35);
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
                transferFocusBackward();
                //TODO Jared add function here
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
     * @param device String variable for the type of device that was clicked
     * @param team String variable for the team of the selected device
     */
    public void setButtonUsage(String device, String team){
        if(device.equalsIgnoreCase("Switch")){
            packetButtons.get(0).setEnabled(false);
        }
        if(team.equalsIgnoreCase("Blue")){
            packetButtons.get(0).setEnabled(true);
        }
    }
}

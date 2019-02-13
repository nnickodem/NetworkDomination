package GUIs;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
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
import java.util.HashMap;
import java.util.Map;


public class Tutorial extends JPanel {
    private String imageFile = "resources/objects/NetworkDevices/";
    public JButton testPacket1;
    public JButton testPacket2;
    public JButton upgradeButton1;
    public JButton upgradeButton2;

    public JButton pc1;
    public JButton pc2;
    public JButton gameSwitch;
    public JButton server;
    public Map<JButton, JButton> connectedDevices = new HashMap();
    public Map<Point,Point> lineMap= new HashMap<>();
    public Dimension buttonSize = new Dimension(100,60);

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();


    public Tutorial(){
        this.setLayout(null);

        server = new JButton(scaleImage(imageFile + "server/serverWhite.png"));
        this.add(server);
        server.setBounds((int)Math.round(screenWidth)/2,0,60,60);

        gameSwitch = new JButton(scaleImage(imageFile + "switch/switchWhite.png"));
        this.add(gameSwitch);
        gameSwitch.setBounds((int)Math.round(screenWidth)/2, 300, 60, 60);

        pc1 = new JButton(scaleImage(imageFile + "pc/pcRed.png"));
        pc1.addActionListener(e -> {
            createSideComponent();
        });
        this.add(pc1);
        pc1.setBounds((int)Math.round(screenWidth)/2 - 150, 500, 60, 60);

        pc2 = new JButton(scaleImage(imageFile + "pc/pcBlue.png"));
        this.add(pc2);
        pc2.setBounds((int)Math.round(screenWidth)/2 + 150, 500, 60, 60);

        setBackground(Color.DARK_GRAY);
        connectedDevices.put(pc1,gameSwitch);
        connectedDevices.put(pc2, gameSwitch);
        connectedDevices.put(gameSwitch, server);
        listCoordinates(connectedDevices);
    }

    //Scales the image files that are loaded in to the proper game image size wanted
    public ImageIcon scaleImage(String imageFile){
        ImageIcon imageIcon = new ImageIcon(imageFile);
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(60,60, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);
        return imageIcon;
    }


    public void listCoordinates(Map<JButton,JButton> connections){
        Point tempA;
        Point tempB;
        for(Map.Entry<JButton, JButton> connection : connections.entrySet()){
            tempA = connection.getKey().getLocation();
            tempB = connection.getValue().getLocation();
            tempA = new Point((int)tempA.getX() + 30, (int)tempA.getY() + 30);
            tempB = new Point((int)tempB.getX() + 30, (int)tempB.getY() + 30);
            lineMap.put(tempA, tempB);

        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics g2d = (Graphics2D) g;
        Line2D line2D;
        ((Graphics2D) g2d).setStroke(new BasicStroke(4));
        for(Map.Entry<Point, Point> entry : lineMap.entrySet() ){
            line2D = new Line2D.Double(entry.getKey(), entry.getValue());
            ((Graphics2D) g2d).draw(line2D);
        }
    }

    protected void createSideComponent(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JPanel packetTypes = new JPanel();
        JPanel upgradeButtons = new JPanel();
        this.add(panel);
        panel.add(packetTypes);
        panel.add(upgradeButtons);

        //TODO Add if checks to determine what network device you are creating the buttons for

        //Base framework for packet type buttons
        testPacket1 = new JButton("Packet1");
        testPacket2 = new JButton("Packet2");

        testPacket1.setPreferredSize(buttonSize);
        packetTypes.add(testPacket1);

        testPacket2.setPreferredSize(buttonSize);
        packetTypes.add(testPacket2);
        packetTypes.setBorder(BorderFactory.createEtchedBorder());


        upgradeButton1 = new JButton("Upgrade 1");
        upgradeButton2 = new JButton("Upgrade 2");

        upgradeButton1.setPreferredSize(buttonSize);
        upgradeButtons.add(upgradeButton1);

        upgradeButton2.setPreferredSize(buttonSize);
        upgradeButtons.add(upgradeButton2);
        upgradeButtons.setBorder(BorderFactory.createEtchedBorder());

        JFrame gameFrame = (JFrame) SwingUtilities.getRoot(panel);
        gameFrame.requestFocus();
        gameFrame.setFocusable(true);
        panel.setBackground(Color.lightGray);
        //reset container to read the new components added
        revalidate();
        panel.setBounds((int)screenWidth - 150, 0, 150, (int)screenHeight);


    }
}

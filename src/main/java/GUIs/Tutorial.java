package GUIs;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Tutorial extends JPanel {
    private String imageFile = "resources/objects/NetworkDevices/";
    public JLabel pc1;
    public JLabel pc2;
    public JLabel gameSwitch;
    public JLabel server;
    public List<JLabel> networkDevices = new ArrayList();
    public Map<JLabel, JLabel> connectedDevices = new HashMap();
    public Map<Point,Point> lineMap= new HashMap<>();

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();


    public Tutorial(){
        this.setLayout(null);
        server = new JLabel(scaleImage(imageFile + "server/serverWhite.png"));
        this.add(server);
        server.setBounds((int)Math.round(screenWidth)/2,0,60,60);

        gameSwitch = new JLabel(scaleImage(imageFile + "switch/switchWhite.png"));
        this.add(gameSwitch);
        gameSwitch.setBounds((int)Math.round(screenWidth)/2, 300, 60, 60);

        pc1 = new JLabel(scaleImage(imageFile + "pc/pcRed.png"));
        this.add(pc1);
        pc1.setBounds((int)Math.round(screenWidth)/2 - 150, 500, 60, 60);

        pc2 = new JLabel(scaleImage(imageFile + "pc/pcBlue.png"));
        this.add(pc2);
        pc2.setBounds((int)Math.round(screenWidth)/2 + 150, 500, 60, 60);

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

    public void listCoordinates(Map<JLabel,JLabel> connections){
        Point tempA;
        Point tempB;
        for(Map.Entry<JLabel, JLabel> connection : connections.entrySet()){
            tempA = connection.getKey().getLocation();
            tempB = connection.getValue().getLocation();
            tempA = new Point((int)tempA.getX() + 30, (int)tempA.getY() + 30);
            tempB = new Point((int)tempB.getX() + 30, (int)tempB.getY() + 30);
            lineMap.put(tempA, tempB);

        }
        System.out.println(lineMap);
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
}

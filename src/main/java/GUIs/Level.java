package GUIs;

import GameHandlers.DeviceHandler;
import Objects.NetworkDevices.NetworkDevice;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level extends JPanel {

    private final String imageFile = "resources/objects/NetworkDevices/";
    public Map<Point,Point> lineMap= new HashMap<>();

    public Level(final String[][] levelMap, final Map<String, String> connections) {

        Map<JLabel, JLabel> deviceConnections = new HashMap<>();
        Map<String, JLabel> devices = new HashMap<>();
        String temp;
        String deviceType;
        NetworkDevice tempDevice;

        this.setLayout(null);
        setBackground(Color.DARK_GRAY);

        for(int i = 0; i < levelMap.length; i++) {
            for(int k = 0; k < levelMap[i].length; k++) {
                temp = levelMap[k][i];
                if(temp != null && !temp.equals("-")) {
                    tempDevice = DeviceHandler.getNetworkDeviceById(temp);
                    deviceType = tempDevice.getClass().toString();
                    deviceType = deviceType.substring(deviceType.lastIndexOf(".")+1).toLowerCase();
                    devices.put(temp ,new JLabel(scaleImage(imageFile + deviceType + "/" + deviceType + tempDevice.getTeam() + ".png")));
                    this.add(devices.get(temp));
                    devices.get(temp).setBounds(i*100, k*100, 60,60);
                }
            }
        }

        for(Map.Entry<String, String> connection : connections.entrySet()) {
            deviceConnections.put(devices.get(connection.getKey()), devices.get(connection.getValue()));
        }

        listCoordinates(deviceConnections);
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

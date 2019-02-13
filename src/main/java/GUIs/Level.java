package GUIs;

import Objects.GameLevel;
import Objects.NetworkDevices.NetworkDevice;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Level extends JPanel {

    private final String imagePath = "resources/objects/NetworkDevices/";
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
                    deviceType = deviceType.substring(deviceType.lastIndexOf(".")+1).toLowerCase();
                    button = new JButton(scaleImage(imagePath + deviceType + "/" + deviceType + tempDevice.getTeam() + ".png"));
                    devices.put(temp, button);
                    this.add(button);
                    button.setBounds(i*125, k*125, 70,70);
                    button.setContentAreaFilled(false);
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
    }

    //Scales the image files that are loaded in to the proper game image size wanted
    public ImageIcon scaleImage(String imageFile){
        ImageIcon imageIcon = new ImageIcon(imageFile);
        Image image = imageIcon.getImage();
        //TODO: adjust scaling?
        Image newImage = image.getScaledInstance(60,60, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);
        return imageIcon;
    }

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
}

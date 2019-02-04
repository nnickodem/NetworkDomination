package GUIs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Tutorial extends JPanel {
    public JButton test1 = new JButton("Object1");
    public JButton test2 = new JButton("Object2");
    private String imageFile = "src/resources/objects/NetworkDevices/";
    public JLabel pc1;
    public JLabel pc2;
    public JLabel gameSwitch;
    public JLabel server;


    public Tutorial(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        GridBagConstraints constraints1 = new GridBagConstraints();

        constraints1.gridx = 3;
        constraints1.gridy = 1;
        constraints1.insets = new Insets(30,30,100,30);
        server = new JLabel(scaleImage(imageFile + "router/routerWhite.png"));
        panel.add(server, constraints1);

        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.gridx = 3;
        constraints2.gridy = 3;
        constraints2.insets = new Insets(100,30,30,30);
        gameSwitch = new JLabel(scaleImage(imageFile + "switch/switchBlue.png"));
        panel.add(gameSwitch, constraints2);

        GridBagConstraints constraints3 = new GridBagConstraints();
        constraints3.gridx = 2;
        constraints3.gridy = 4;
        constraints3.insets = new Insets(100, 0, 0, 75);
        pc1 = new JLabel(scaleImage(imageFile + "pc/pc starter.png"));
        panel.add(pc1, constraints3);

        GridBagConstraints constraints4 = new GridBagConstraints();
        constraints4.gridx = 4;
        constraints4.gridy = 4;
        constraints4.insets = new Insets(100, 75, 0, 0);
        pc2 = new JLabel(scaleImage(imageFile + "pc/pc starter.png"));
        panel.add(pc2, constraints4);

//        panel.repaint();
        add(panel);

    }

    //Scales the image files that are loaded in to the proper game image size wanted
    public ImageIcon scaleImage(String imageFile){
        ImageIcon imageIcon = new ImageIcon(imageFile);
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(60,60, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);
        return imageIcon;
    }

//    @Override
//    public void paintComponent(Graphics g){
//        Graphics2D g2 = (Graphics2D) g;
//        Line2D lin = new Line2D.Float(1, 1, 2, 2);
//        g2.draw(lin);
//    }
}

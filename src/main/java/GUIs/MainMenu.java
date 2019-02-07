package GUIs;

import GameHandlers.DeviceHandler;
import Objects.NetworkDevices.NetworkDevice;
import Objects.NetworkDevices.PC;
import Objects.NetworkDevices.Router;
import Objects.NetworkDevices.Switch;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

public class MainMenu extends JFrame {
    private JButton playButton = new JButton("Play");
    private JButton loadButton = new JButton("Load Game");
    private JButton optionsButton = new JButton("Settings");
    private JButton exitButton = new JButton("Exit");

    private int buttonHeight = 40;
    private int buttonWidth = 150;

    private Dimension dimMax = Toolkit.getDefaultToolkit().getScreenSize();

    //Add each button to the JPanel
    public MainMenu(){

        String[][] level = new String[][]{
                {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "router1", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "switch1", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "pc1", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"}
        };
        Map<String, NetworkDevice> idToDevice = new HashMap<>();
        idToDevice.put("router1", new Router());
        idToDevice.get("router1").setTeam("White");
        idToDevice.put("pc1", new PC());
        idToDevice.get("pc1").setTeam("Blue");
        idToDevice.put("switch1", new Switch());
        idToDevice.get("switch1").setTeam("Red");

        Map<String, String> connections = new HashMap<>();
        connections.put("router1", "switch1");
        connections.put("switch1", "pc1");

        DeviceHandler.setDeviceIdToDevice(idToDevice);

        JPanel mainMenu = new JPanel(new GridBagLayout());
        mainMenu.setBorder(BorderFactory.createEmptyBorder());
        mainMenu.setBackground(Color.DARK_GRAY);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(3,0,3,0);
        constraints.fill = GridBagConstraints.CENTER;
        playButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        playButton.addActionListener(e -> {
            add(new Level(level, connections));
            mainMenu.setVisible(false);
        });
        mainMenu.add(playButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(3,0,3,0);
        loadButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        mainMenu.add(loadButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = new Insets(3,0,3,0);
        optionsButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        mainMenu.add(optionsButton, constraints);

        exitButton.addActionListener(e-> exit(0));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = new Insets(3,0,3,0);
        exitButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
        mainMenu.add(exitButton, constraints);

        //Add the mainMenu panel to the frame and set the window size
        getContentPane().add(mainMenu);
        getContentPane().setBackground(Color.DARK_GRAY);

        setMaximumSize(dimMax);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

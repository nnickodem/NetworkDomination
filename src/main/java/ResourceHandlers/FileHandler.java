package ResourceHandlers;

import Objects.GameLevel;
import Objects.NetworkDevices.Firewall;
import Objects.NetworkDevices.NetworkDevice;
import Objects.NetworkDevices.PC;
import Objects.NetworkDevices.Router;
import Objects.NetworkDevices.Server;
import Objects.NetworkDevices.Switch;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles reading/writing of various external resources including save files, level files, and fonts
 */
public class FileHandler { //TODO: implement save file handling, any others that are needed

    private static final Logger errorLogger = Logger.getLogger("errorLogger");
    private static final String levelFilePath = "resources/levels/";
    private static Font gameFont;

    /**
     * Creates and registers the game's custom font
     * @return font
     */
    public static void loadFont() {
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/gameFont.ttf"));
            System.out.println(font.toString());
            ge.registerFont(font);
            gameFont = font;
        } catch (IOException| FontFormatException e) {
            //Handle exception
        }
    }

    public static Font getGameFont() {
        return gameFont;
    }

    /**
     * Writes level file
     */
    //TODO: Temporary, make generic for level creator and perhaps save file
    public static void writeLevel() {
        String[][] level = new String[10][10];
        for(String[] row : level) {
            Arrays.fill(row, "-");
        }
        level[5][5] = "R1";
        level[5][4] = "E0";
        level[5][3] = "PC0";
        level[5][6] = "E0";
        level[5][7] = "S1";

        try(Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("level1.txt"), "utf-8"))) {
            String line = "";
            for(String[] row : level) {
                for(String entry : row) {
                    line += entry + " ";
                }
                writer.write(line);
                ((BufferedWriter) writer).newLine();
                line = "";
            }
        } catch (Exception e) {
            errorLogger.log(Level.SEVERE,"Error writing level file", e);
        }

    }

    /**
     * Reads a given level file and converts the information to a GameLevel object
     * @param levelNum level number TODO: change to level String?
     * @return GameLevel object
     */
    //TODO: throws exception to stop program from attempting to continue?
    public static GameLevel readLevel(int levelNum) {
        List<List<String>> levelMap = new ArrayList<>();
        List<Map.Entry<String, String>> mapConnections = new ArrayList<>();
        List<String> file;
        Map<String, NetworkDevice> idToDeviceObject = new HashMap<>();
        Map<String, Map.Entry<Integer, Integer>> deviceToInfo = new HashMap<>();

        try {
            file = Files.readAllLines(Paths.get(levelFilePath + "level"+levelNum+".txt"));
            String line = file.get(0);
            while(line != null && !line.equals("*")) {
                levelMap.add(Arrays.asList(line.split(",")));
                file.remove(0);
                line = file.get(0);
            }
            file.remove(0);
            line = file.get(0);
            while(line != null && !line.equals("*")) {
                mapConnections.add(new AbstractMap.SimpleEntry<>(line.substring(0, line.indexOf(",")), line.substring(line.indexOf(",") + 1)));
                file.remove(0);
                line = file.get(0);
            }
            file.remove(0);
            for(String l : file) {
                deviceToInfo.put(l.substring(0, l.indexOf(",")),
                        new AbstractMap.SimpleEntry<>(Integer.valueOf(l.substring(l.indexOf(",")+1, l.lastIndexOf(","))),
                                Integer.valueOf(l.substring(l.lastIndexOf(",")+1))));
            }
            GameLevel level = new GameLevel();
            String[][] mapArray = new String[levelMap.get(0).size()][levelMap.size()];
            NetworkDevice device;
            List<String> deviceConnections;
            Map.Entry<Integer, Integer> deviceInfo;
            for(int y = 0; y < levelMap.size(); y++) {
                for(int x = 0; x < levelMap.get(0).size(); x++) {
                    mapArray[x][y] = levelMap.get(y).get(x);
                    deviceConnections = getDeviceConnections(mapConnections, mapArray[x][y]);
                    deviceInfo = deviceToInfo.get(mapArray[x][y]);
                    if(mapArray[x][y] != null && !mapArray[x][y].equals("-")) {
                        switch(mapArray[x][y].substring(0, mapArray[x][y].indexOf("."))) {
                            case "Switch":
                                device = new Switch(deviceInfo.getKey(), deviceConnections, false, deviceInfo.getValue(), mapArray[x][y]);
                                break;
                            case "Router":
                                device = new Router(deviceInfo.getKey(), deviceConnections, false, deviceInfo.getValue(), mapArray[x][y]);
                                break;
                            case "Firewall":
                                device = new Firewall(deviceInfo.getKey(), deviceConnections, false, deviceInfo.getValue(), mapArray[x][y]);
                                break;
                            case "Server":
                                device = new Server(deviceInfo.getKey(), deviceConnections, false, deviceInfo.getValue(), mapArray[x][y]);
                                break;
                            case "PC":
                                device = new PC(deviceInfo.getKey(), deviceConnections, false, deviceInfo.getValue(), mapArray[x][y]);
                                break;
                            default:
                                device = new NetworkDevice(deviceInfo.getKey(), deviceConnections, false, deviceInfo.getValue(), mapArray[x][y]);
                                break;
                        }
                        idToDeviceObject.put(mapArray[x][y], device);
                    }
                }
            }
            level.setLevelMap(mapArray);
            level.setConnections(mapConnections);
            level.setIdToDeviceObject(idToDeviceObject);

            return level;
        } catch (Exception e) {
            errorLogger.log(Level.SEVERE, "Error reading level file", e);
            return null;
        }
    }

    private static List<String> getDeviceConnections(List<Map.Entry<String, String>> mapConnections, String deviceId) {
        List<String> deviceConnections = new ArrayList<>();
        for(Map.Entry<String, String> connection : mapConnections) {
            if(connection.getKey().equals(deviceId)) {
                deviceConnections.add(connection.getValue());
            } else if(connection.getValue().equals(deviceId)) {
                deviceConnections.add(connection.getKey());
            }
        }
        return deviceConnections;
    }

}

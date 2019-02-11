package ResourceHandlers;

import Objects.GameLevel;
import Objects.NetworkDevices.Firewall;
import Objects.NetworkDevices.NetworkDevice;
import Objects.NetworkDevices.PC;
import Objects.NetworkDevices.Router;
import Objects.NetworkDevices.Server;
import Objects.NetworkDevices.Switch;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
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
 *
 */
public class FileHandler { //TODO: implement save file handling, level file handling, any others that are needed

    private static final Logger errorLogger = Logger.getLogger("errorLogger");

    //TODO: Temporary, make generic
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

    //TODO: throws exception?
    public static GameLevel readLevel(int levelNum) {
        List<List<String>> levelMap = new ArrayList<>();
        List<Map.Entry<String, String>> connections = new ArrayList<>();
        List<String> file;
        Map<String, NetworkDevice> idToDeviceObject = new HashMap<>();

        try {
            file = Files.readAllLines(Paths.get("level"+levelNum+".txt"));
            String line = file.get(0);
            while(line != null && !line.equals("*")) {
                levelMap.add(Arrays.asList(line.split(",")));
                file.remove(0);
                line = file.get(0);
            }
            file.remove(0);
            for(String l : file) {
                connections.add(new AbstractMap.SimpleEntry<>(l.substring(0, l.indexOf(",")), l.substring(l.indexOf(",") + 1)));
            }
            GameLevel level = new GameLevel();
            String[][] mapArray = new String[levelMap.get(0).size()][levelMap.size()];
            NetworkDevice device;
            for(int y = 0; y < levelMap.size(); y++) {
                for(int x = 0; x < levelMap.get(0).size(); x++) {
                    mapArray[x][y] = levelMap.get(y).get(x);
                    if(mapArray[x][y] != null && !mapArray[x][y].equals("-")) {
                        switch(mapArray[x][y].substring(0, mapArray[x][y].indexOf("."))) {
                            case "Switch":
                                device = new Switch();
                                break;
                            case "Router":
                                device = new Router();
                                break;
                            case "Firewall":
                                device = new Firewall();
                                break;
                            case "Server":
                                device = new Server();
                                break;
                            case "PC":
                                device = new PC();
                                break;
                            default:
                                device = new NetworkDevice();
                                break;
                        }
                        device.setTeam(mapArray[x][y].substring(mapArray[x][y].indexOf(".") + 1, mapArray[x][y].lastIndexOf(".")));
                        device.setId(mapArray[x][y]);
                        idToDeviceObject.put(mapArray[x][y], device);
                    }
                }
            }
            level.setLevelMap(mapArray);
            level.setConnections(connections);
            level.setIdToDeviceObject(idToDeviceObject);

            return level;
        } catch (Exception e) {
            errorLogger.log(Level.SEVERE, "Error reading level file", e);
            return null;
        }
    }

}

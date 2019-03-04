package GameHandlers;

import GUIs.Level;
import Objects.GameLevel;
import Objects.NetworkDevices.NetworkDevice;

import javax.swing.Timer;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Keeps tracks of game ticks and calls for game updates with every tick
 */
public class GameHandler extends Thread {

    private static final Logger logger = Logger.getLogger("errorLogger");
    private GameLevel level;
    private Level levelGui;
    private Integer tick = 0;
    private Timer timer;

    public GameHandler(final GameLevel level, final Level levelGui) {
        this.level = level;
        this.levelGui = levelGui;
    }

    /**
     * Runs throughout the game checking if enough time has passed to call for a tick
     */
    @Override
    public void run() {
        timer = new Timer(200, e -> tick());
        timer.start();
    }

    public void stopTimer(){
        timer.stop();
    }

    /**
     * Calls for game updates
     */
    private void tick() {
        tick++;
        for(Map.Entry<String, NetworkDevice> device : level.getIdToDeviceObject().entrySet()) {
            if(!device.getValue().getTeam().equals("White") && device.getValue().getSpeed() != 0 && tick%device.getValue().getSpeed() == 0) {
                levelGui.updatePacketCounter(device.getKey(), device.getValue().getTeam(),1);
            }
        }
    }

}

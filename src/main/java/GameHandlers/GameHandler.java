package GameHandlers;

import GUIs.Level;
import Objects.GameLevel;

import javax.swing.Timer;
import java.util.logging.Logger;

/**
 * Keeps tracks of game ticks and calls for game updates with every tick
 */
public class GameHandler extends Thread {

    private static final Logger logger = Logger.getLogger("errorLogger");
    private GameLevel level;
    private Level levelGui;
    private Integer tick = 0;

    public GameHandler(final GameLevel level, final Level levelGui) {
        this.level = level;
        this.levelGui = levelGui;
    }

    /**
     * Runs throughout the game checking if enough time has passed to call for a tick
     */
    @Override
    public void run() {
        Timer timer = new Timer(200, e -> {
            tick();
        });
        timer.start();
    }

    /**
     * Calls for game updates
     */
    private void tick() {
        if(++tick%5 == 0) {
            //levelGui.updatePacketCounter("Switch.White.1", 1);
        }
        levelGui.updatePacketCounter("PC.Blue.1", 1);
        //TODO: implement
    }

}

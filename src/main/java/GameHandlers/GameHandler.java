package GameHandlers;

import java.util.logging.Logger;

/**
 * Keeps tracks of game ticks and calls for game updates with every tick
 */
public class GameHandler extends Thread {

    private static final Logger logger = Logger.getLogger("errorLogger");
    private final Double timePerTick = 0.2;
    private Double time;

    /**
     * Runs throughout the game checking if enough time has passed to call for a tick
     */
    public void run() {
        Double timePassed = 0.0;
        boolean running = true;

        while(running) {
            while (timePassed > timePerTick) {
                tick();
                timePassed -= timePerTick;
            }
            timePassed += getTimePassed();
        }
    }

    /**
     * Checks how much time has passed since the last getTimePassed() call
     * @return how much time has passed
     */
    private Double getTimePassed() {
        Double timePassed;
        Double tempTime = Double.valueOf(System.currentTimeMillis())/1000;
        if(time == null) {
            time = tempTime;
        }
        timePassed = tempTime - time;
        time = tempTime;
        return timePassed;
    }

    /**
     * Calls for game updates
     */
    private void tick() {
        //TODO: implement
        logger.severe("tick");
    }

}

package GameHandlers;

import java.util.logging.Logger;

public class GameHandler extends Thread {

    private static final Logger logger = Logger.getLogger("errorLogger");
    private final Double timePerTick = 0.2;
    private Double time;

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

    private void tick() {
        //TODO: implement
        logger.severe("tick");
    }

}

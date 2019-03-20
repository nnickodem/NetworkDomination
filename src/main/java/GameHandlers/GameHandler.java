package GameHandlers;

import GUIs.LevelGUI;
import Objects.GameLevel;
import Objects.NetworkDevices.NetworkDevice;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Keeps tracks of game ticks and calls for game updates with every tick
 */
public class GameHandler extends Thread {

	private static final Logger logger = Logger.getLogger("errorLogger");
	private GameLevel level;
	private LevelGUI levelGui;
	private Integer tick = 0;
	private Timer timer;

	public GameHandler(final GameLevel level, final LevelGUI levelGui) {
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
	 * Updates packet counters with generated packets and sends a packet when a device has a target
	 */
	private void tick() {
		tick++;
		for(Map.Entry<String, NetworkDevice> device : level.getIdToDeviceObject().entrySet()) {
			if(!device.getValue().getTeam().equals("White") && device.getValue().getSpeed() != 0 && tick%device.getValue().getSpeed() == 0) {
				levelGui.updatePacketCounter(device.getKey(), device.getValue().getTeam(),1);
			}
			if(device.getValue().getTarget() != null && !device.getValue().getTarget().equals(device.getValue().getId())) {
				levelGui.sendPacket("botnet", device.getValue().getId(), device.getValue().getTarget(), device.getValue().getTeam());
			}
			if(tick%5 == 0) {
				updateAI();
			}
		}
	}

	/**
	 * Updates all devices on teams designated AI with nearest enemy target if they aren't already attacking
	 */
	private void updateAI() {
		List<NetworkDevice> devices = new ArrayList<>();
		for(Map.Entry<String, NetworkDevice> device : level.getIdToDeviceObject().entrySet()) {
			if(device.getValue().getTeam().equals("Red") || device.getValue().getTeam().equals("Blue")) {
				devices.add(device.getValue());
			}
		}

		for(NetworkDevice device : devices) {
			if(device.getTarget() == null || level.getIdToDeviceObject().get(device.getTarget()).getTeam().equals(device.getTeam())) {
				device.setTarget(DeviceHandler.getNearestEnemy(device, level));
			}
		}
	}
}
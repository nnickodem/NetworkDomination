package GameHandlers;

import GUIs.GUIUtils;
import GUIs.LevelGUI;
import GUIs.MainGui;
import Objects.GameLevel;
import Objects.NetworkDevices.NetworkDevice;
import ResourceHandlers.FileHandler;

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
	private MainGui mainGui;
	private Integer tick = 0;
	private Timer timer;

	public GameHandler(final GameLevel level, final LevelGUI levelGui, final MainGui mainGui) {
		this.level = level;
		this.levelGui = levelGui;
		this.mainGui = mainGui;
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
			if(device.getValue().isSending() && device.getValue().getTarget() != null && !device.getValue().getTarget().equals(device.getValue().getId())) {
				levelGui.sendPacket("botnet", device.getValue().getId(), device.getValue().getTarget(), device.getValue().getTeam());
			}
			if(tick%5 == 0) {
				updateAI();
			}
		}

		if(level.getWinConditions().stream().map(NetworkDevice::getTeam).allMatch(t -> t.equals("Blue"))) {
			endLevel("win");
			FileHandler.updateSave(level.getLevelID(), "1");
		} else if(level.getIdToDeviceObject().values().stream().map(NetworkDevice::getTeam).noneMatch(t -> t.equals("Blue"))) {
			endLevel("lost");
			FileHandler.updateSave(level.getLevelID(), "-1");
		}
	}

	private void endLevel(final String result) {
		stopTimer();
		levelGui.stopPacketTimer();
		GUIUtils.displayLevelEndMessage(mainGui, result);
	}

	/**
	 * Updates all devices on teams designated AI with nearest enemy target if they aren't already attacking
	 */
	private void updateAI() {
		List<NetworkDevice> devices = new ArrayList<>();
		for(Map.Entry<String, NetworkDevice> device : level.getIdToDeviceObject().entrySet()) {
			if(device.getValue().getTeam().equals("Red") || device.getValue().getTeam().equals("Yellow")) {
				devices.add(device.getValue());
			}
		}

		for(NetworkDevice device : devices) {
			if(device.getTarget() == null || level.getIdToDeviceObject().get(device.getTarget()).getTeam().equals(device.getTeam())) {
				device.setTarget(DeviceHandler.getNearestEnemy(device, level));
				device.setSending(true);
			}
		}
	}
}
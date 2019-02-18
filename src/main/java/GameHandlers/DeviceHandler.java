package GameHandlers;

import Objects.NetworkDevices.NetworkDevice;

import java.util.HashMap;
import java.util.Map;

/**
 * Unfinished, not even sure if this is gonna remain or what it will do
 */
public class DeviceHandler {

    private static Map<String, NetworkDevice> deviceIdToDevice = new HashMap<>();

    public static void setDeviceIdToDevice(final Map<String, NetworkDevice> deviceIdToDev) {
        deviceIdToDevice = deviceIdToDev;
    }

    public static NetworkDevice getNetworkDeviceById(final String id) {
        return deviceIdToDevice.get(id);
    }
}

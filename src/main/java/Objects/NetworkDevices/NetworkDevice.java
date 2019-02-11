package Objects.NetworkDevices;

import java.util.ArrayList;

/**
 * Parent class for all physical network objects
 */
public class NetworkDevice {

    private String id;
    private String team;
    private ArrayList<Integer> connections;

    public void setId(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTeam(final String team) {
        this.team = team;
    }

    public String getTeam() {
        return team;
    }
    //TODO: other universal values?

}

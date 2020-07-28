package wrappers;

import transpool.logic.map.structure.Station;

import java.util.LinkedList;
import java.util.List;

public class PartWrapper {
    private StationWrapper station;
    private List<String> pickedUp;
    private List<String> gettingOff;
    private int takenPlaces = 0;
    private int totalPlaces;
    private String time;
    private boolean containsTremists = false;

    public PartWrapper(Station station, String time) {
        this.station = new StationWrapper(station);
        this.pickedUp = new LinkedList<>();
        this.gettingOff = new LinkedList<>();
        this.time = time;
    }

    public void addPickedUp(String userName){
        this.pickedUp.add(userName);
        this.takenPlaces++;
        this.containsTremists = true;
    }

    public void setGettingOff(List<String> gettingOff) {
        this.gettingOff = gettingOff;
        this.containsTremists = this.gettingOff.size() > 0 || this.containsTremists;
    }

    public void setTakenPlaces(int takenPlaces) {
        this.takenPlaces = takenPlaces;
    }

    public void setTotalPlaces(int totalPlaces) {
        this.totalPlaces = totalPlaces;
    }
}

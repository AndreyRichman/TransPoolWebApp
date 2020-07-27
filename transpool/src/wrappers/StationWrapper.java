package wrappers;

import transpool.logic.map.structure.Coordinate;
import transpool.logic.map.structure.Station;

public class StationWrapper {

    private Coordinate coordinate;
    private String name;

    public StationWrapper(Station station) {
        this.coordinate = station.getCoordinate();
        this.name = station.getName();
    }
}

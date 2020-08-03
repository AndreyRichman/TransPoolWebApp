package wrappers;

import transpool.logic.map.structure.Coordinate;
import transpool.logic.map.structure.Station;

import java.util.List;
import java.util.stream.Collectors;

public class StationWrapper {

    private Coordinate coordinate;
    private String name;
    private List<String> reachableStations;

    public StationWrapper(Station station) {
        this.coordinate = station.getCoordinate();
        this.name = station.getName();
        this.reachableStations =  station.getStationsAccessedFromCurrentStation().stream().map(Station::getName)
                .collect(Collectors.toList());
    }
}

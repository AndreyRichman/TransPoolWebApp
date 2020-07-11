package exception;

import transpool.logic.map.structure.Station;

public class StationNameAlreadyExistsException extends Exception {
    private Station station;

    public StationNameAlreadyExistsException(Station station){
        this.station = station;
    }

    public Station getStation() {
        return station;
    }
}

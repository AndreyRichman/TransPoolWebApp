package exception;

import transpool.logic.map.structure.Station;

public class StationCoordinateoutOfBoundriesException extends Exception {

    private Station station;

    public StationCoordinateoutOfBoundriesException(Station station){
        this.station = station;
    }

    public Station getStation() {
        return station;
    }
}

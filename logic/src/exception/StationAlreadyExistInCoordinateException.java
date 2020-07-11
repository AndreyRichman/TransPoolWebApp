package exception;

import transpool.logic.map.structure.Station;

public class StationAlreadyExistInCoordinateException extends Exception {

    private Station station;

    public StationAlreadyExistInCoordinateException(Station station){
        this.station = station;
    }

    public Station getStation() {
        return station;
    }
}

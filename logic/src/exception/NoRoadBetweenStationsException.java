package exception;

import transpool.logic.map.structure.Station;

public class NoRoadBetweenStationsException extends Exception {
    private Station fromStation;
    private  Station toStation;

    public NoRoadBetweenStationsException(Station from, Station to) {
        this.fromStation = from;
        this.toStation = to;
    }

    public Station getFromStation() {
        return fromStation;
    }

    public Station getToStation() {
        return toStation;
    }
}

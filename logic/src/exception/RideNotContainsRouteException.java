package exception;

import transpool.logic.map.structure.Station;

public class RideNotContainsRouteException extends Exception {
    private Station from;
    private Station to;

    public RideNotContainsRouteException(Station from, Station to) {
        this.from = from;
        this.to = to;
    }

    public Station getFrom() {
        return from;
    }

    public Station getTo() {
        return to;
    }
}

package wrappers;

import transpool.logic.map.structure.Coordinate;
import transpool.logic.map.structure.Road;

public class RoadWrapper {
    private Coordinate fromCoordinate;
    private Coordinate toCoordinate;

    public RoadWrapper(Road road) {
        this.fromCoordinate = road.getStartStation().getCoordinate();
        this.toCoordinate = road.getEndStation().getCoordinate();
    }
}

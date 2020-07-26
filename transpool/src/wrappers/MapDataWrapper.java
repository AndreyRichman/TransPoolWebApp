package wrappers;

import transpool.logic.handler.LogicHandler;
import java.util.List;
import java.util.stream.Collectors;

public class MapDataWrapper {

    private List<StationWrapper> allStations;
    private List<RoadWrapper> allRoads;
    private List<RideWrapper> allRides;
    private List<TrempRequestWrapper> allTrempRequests;
    private int width;
    private int height;

    public MapDataWrapper(LogicHandler logicHandler) {
        this.width = logicHandler.getMap().getWidth();
        this.height = logicHandler.getMap().getHeight();
        this.allStations = logicHandler.getAllStations().stream().map(StationWrapper::new).collect(Collectors.toList());
        this.allRoads = logicHandler.getAllRoads().stream().map(RoadWrapper::new).collect(Collectors.toList());
        this.allRides = logicHandler.getAllRides().stream().map(RideWrapper::new).collect(Collectors.toList());
        this.allTrempRequests = logicHandler.getAllTrempRequests().stream().map(TrempRequestWrapper::new).collect(Collectors.toList());
    }
}

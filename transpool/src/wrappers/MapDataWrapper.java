package wrappers;

import transpool.logic.handler.LogicHandler;
import transpool.logic.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class MapDataWrapper {

    private List<StationWrapper> allStations;
    private List<RoadWrapper> allRoads;
    private List<RideWrapper> allRides;
    private List<TrempRequestWrapper> allTrempRequests;
    private int width;
    private int height;
    private User mapOwner;
    private String mapName;
    int numOfRides;
    int numOfTremps;
    int numOfMatchedTremps;
    int id;

    public MapDataWrapper(LogicHandler logicHandler) {
        this.width = logicHandler.getMap().getWidth();
        this.height = logicHandler.getMap().getHeight();
        this.allStations = logicHandler.getAllStations().stream().map(StationWrapper::new).collect(Collectors.toList());
        this.allRoads = logicHandler.getAllRoads().stream().map(RoadWrapper::new).collect(Collectors.toList());
        this.allRides = logicHandler.getAllRides().stream().map(RideWrapper::new).collect(Collectors.toList());
        this.allTrempRequests = logicHandler.getAllTrempRequests().stream().map(TrempRequestWrapper::new).collect(Collectors.toList());
        this.mapOwner = logicHandler.getMapOwner();
        this.mapName = logicHandler.getMapName();
        this.numOfRides = this.allRides.size();
        this.numOfTremps = this.allTrempRequests.size();
        this.numOfMatchedTremps = ((int) this.allTrempRequests.stream().filter(TrempRequestWrapper::trempIsNotMatched).count());
        this.id = logicHandler.getId();
    }
}

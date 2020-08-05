package wrappers;

import transpool.logic.handler.LogicHandler;
import transpool.logic.traffic.item.Ride;
import transpool.logic.traffic.item.TrempRequest;
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
    private User currentUser;

    public MapDataWrapper(LogicHandler logicHandler, User user) {
        this.width = logicHandler.getMap().getWidth();
        this.height = logicHandler.getMap().getHeight();
        this.allStations = logicHandler.getAllStations().stream().map(StationWrapper::new).collect(Collectors.toList());
        this.allRoads = logicHandler.getAllRoads().stream().map(RoadWrapper::new).collect(Collectors.toList());

        this.allRides = getListOfRidesAccordingToUserType(logicHandler, user);
//        this.allRides =  logicHandler.getAllRides().stream().map(RideWrapper::new).collect(Collectors.toList());
        this.allTrempRequests = getListOfTrempRequestsByUser(logicHandler, user);
//        this.allTrempRequests = logicHandler.getAllTrempRequests().stream().map(TrempRequestWrapper::new).collect(Collectors.toList());
        this.mapOwner = logicHandler.getMapOwner();
        this.mapName = logicHandler.getMapName();
        this.numOfRides = this.allRides.size();
        this.numOfTremps = this.allTrempRequests.size();
        this.numOfMatchedTremps = ((int) this.allTrempRequests.stream().filter(TrempRequestWrapper::trempIsNotMatched).count());
        this.id = logicHandler.getId();
        this.currentUser = user;
    }

    private List<TrempRequestWrapper> getListOfTrempRequestsByUser(LogicHandler logicHandler, User user) {
        List<TrempRequest> tremps;

        if (user == null || user.getType().equals(User.Type.DRIVER)){
            tremps = logicHandler.getAllTrempRequests();
        }
        else {
            tremps = logicHandler.getAllTrempRequests().stream().filter(tremp -> tremp.getUser().getName()
                    .equals(user.getName())).collect(Collectors.toList());
        }

        return tremps.stream().map(TrempRequestWrapper::new).collect(Collectors.toList());
    }

    private List<RideWrapper> getListOfRidesAccordingToUserType(LogicHandler logicHandler, User user) {
        List<Ride> rides;
        if (user == null || user.getType().equals(User.Type.TREMPIST)){
            rides = logicHandler.getAllRides();
        }
        else {
            rides = logicHandler.getAllRides().stream().filter(ride -> ride.getRideOwner().getUser().getName()
                    .equals(user.getName())).collect(Collectors.toList());
        }

        return rides.stream().map(RideWrapper::new).collect(Collectors.toList());
    }
}

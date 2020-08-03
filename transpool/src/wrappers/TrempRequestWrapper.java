package wrappers;

import transpool.logic.time.RequestSchedule;
import transpool.logic.traffic.item.RideForTremp;
import transpool.logic.traffic.item.TrempRequest;
import transpool.logic.user.User;

public class TrempRequestWrapper {
    private final int id;
    private final StationWrapper startStation;
    private final StationWrapper endStation;
    private final User user;
//    private int userID;
//    private String userName;
    private int maxNumberOfConnections;
    private RideForTrempWrapper selectedRide = null;
    private boolean rideAssigned = false;
    private RequestSchedule schedule;
    private String desiredTimeType;
    private String desiredTime;
    private int matchID;

    public TrempRequestWrapper(TrempRequest trempRequest) {
        this.id = trempRequest.getID();
        this.startStation = new StationWrapper(trempRequest.getStartStation());
        this.endStation = new StationWrapper(trempRequest.getEndStation());
        this.user = trempRequest.getUser();
//        this.userID = trempRequest.getUser().getID();
//        this.userName = trempRequest.getUser().getName();
        this.maxNumberOfConnections = trempRequest.getMaxNumberOfConnections();
        this.schedule = trempRequest.getSchedule();
        this.desiredTimeType = trempRequest.getSchedule().getDesiredTimeType().name();
        this.desiredTime = trempRequest.getSchedule().getDesiredDateTimeAccordingToTimeType().toLocalTime().toString();
        RideForTremp matchedTremp = trempRequest.getSelectedRide();
        if (matchedTremp != null)
            this.selectedRide = new RideForTrempWrapper(matchedTremp);
        this.rideAssigned = matchedTremp != null;
        this.matchID = trempRequest.getSelectedRide() != null ? trempRequest.getSelectedRide().getID() : 0;

    }

    public boolean trempIsNotMatched(){
        return this.selectedRide == null;
    }
}

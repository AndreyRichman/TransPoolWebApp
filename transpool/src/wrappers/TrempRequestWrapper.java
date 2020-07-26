package wrappers;

import transpool.logic.time.RequestSchedule;
import transpool.logic.traffic.item.RideForTremp;
import transpool.logic.traffic.item.TrempRequest;
import transpool.logic.user.User;

public class TrempRequestWrapper {
    private final int trempRequestID;
    private final StationWrapper startStation;
    private final StationWrapper endStation;
    private final User user;
//    private int userID;
//    private String userName;
    private int maxNumberOfConnections = 1;
    private RideForTrempWrapper selectedRide = null;
    private RequestSchedule schedule;

    public TrempRequestWrapper(TrempRequest trempRequest) {
        this.trempRequestID = trempRequest.getID();
        this.startStation = new StationWrapper(trempRequest.getStartStation());
        this.endStation = new StationWrapper(trempRequest.getEndStation());
        this.user = trempRequest.getUser();
//        this.userID = trempRequest.getUser().getID();
//        this.userName = trempRequest.getUser().getName();
        this.maxNumberOfConnections = trempRequest.getMaxNumberOfConnections();
        this.schedule = trempRequest.getSchedule();
        RideForTremp matchedTremp = trempRequest.getSelectedRide();
        this.selectedRide = matchedTremp != null ? new RideForTrempWrapper(matchedTremp) : null;
    }
}

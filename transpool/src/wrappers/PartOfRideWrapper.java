package wrappers;


import transpool.logic.time.Schedule;
import transpool.logic.traffic.item.PartOfRide;
import transpool.logic.user.Trempist;

import java.util.List;

public class PartOfRideWrapper {

    private RoadWrapper road;
//    private Schedule schedule;
//    private int currentCapacity;
    private int totalCapacity;
    private String startTime;
    private String endTime;
    private List<Trempist> joinedTrempists;
    private List<Trempist> leavingTrempists;

    public PartOfRideWrapper(PartOfRide partOfRide) {
        this.road = new RoadWrapper(partOfRide.getRoad());
//        this.schedule = partOfRide.getSchedule();
//        this.currentCapacity = partOfRide.getCurrentCapacity();
        this.totalCapacity = partOfRide.getTotalCapacity();
        this.startTime = partOfRide.getSchedule().getStartTime().toString();
        this.endTime = partOfRide.getSchedule().getEndTime().toString();
//        this.joinedTrempists = partOfRide.getTrempistsManager().getJustJoinedTrempistsAllDays();
//        partOfRide.getTrempistsManager().getJustJoinedTrempistsAllDays()
    }
}

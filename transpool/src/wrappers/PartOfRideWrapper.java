package wrappers;


import transpool.logic.time.Schedule;
import transpool.logic.traffic.item.PartOfRide;

public class PartOfRideWrapper {

    private RoadWrapper road;
    private Schedule schedule;
//    private int currentCapacity;
    private int totalCapacity;

    public PartOfRideWrapper(PartOfRide partOfRide) {
        this.road = new RoadWrapper(partOfRide.getRoad());
        this.schedule = partOfRide.getSchedule();
//        this.currentCapacity = partOfRide.getCurrentCapacity();
        this.totalCapacity = partOfRide.getTotalCapacity();
    }
}

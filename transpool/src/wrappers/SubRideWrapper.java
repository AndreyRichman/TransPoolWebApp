package wrappers;

import transpool.logic.map.structure.Station;
import transpool.logic.time.Schedule;
import transpool.logic.traffic.item.PartOfRide;
import transpool.logic.traffic.item.Ride;
import transpool.logic.traffic.item.SubRide;

import java.util.List;
import java.util.stream.Collectors;

public class SubRideWrapper {
    private final RideWrapper originalRide;
    private final StationWrapper startStation;
    private final StationWrapper endStation;
    List<PartOfRideWrapper> selectedPartsOfRide;
    private Schedule schedule;

    public SubRideWrapper(SubRide subRide) {
        this.originalRide = new RideWrapper(subRide.getOriginalRide());
        this.startStation = new StationWrapper(subRide.getStartStation());
        this.endStation = new StationWrapper(subRide.getEndStation());
        this.schedule = subRide.getSchedule();
        this.selectedPartsOfRide = subRide.getSelectedPartsOfRide().stream().map(PartOfRideWrapper::new).collect(Collectors.toList());
    }
}

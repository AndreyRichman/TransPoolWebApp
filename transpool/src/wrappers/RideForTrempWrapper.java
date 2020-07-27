package wrappers;

import transpool.logic.time.Schedule;
import transpool.logic.traffic.item.RideForTremp;

import java.util.List;
import java.util.stream.Collectors;

public class RideForTrempWrapper {
    private int id;
    private Schedule schedule;
    private List<SubRideWrapper> subRides;

    public RideForTrempWrapper(RideForTremp rideForTremp) {
        this.id = rideForTremp.getID();
        this.schedule = rideForTremp.getSchedule();
        this.subRides = rideForTremp.getSubRides().stream().map(SubRideWrapper::new).collect(Collectors.toList());
    }
}

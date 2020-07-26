package wrappers;

import transpool.logic.time.Schedule;
import transpool.logic.traffic.item.Ride;
import transpool.logic.user.Driver;

import java.util.List;
import java.util.stream.Collectors;

public class RideWrapper {

    private final int rideID;
    private final Driver driver;
    private Schedule schedule;
    private List<StationWrapper> stations;
    private List<PartOfRideWrapper> partsOfRide;

    public RideWrapper(Ride ride) {
        this.rideID = ride.getID();
        this.driver = ride.getRideOwner();
        this.schedule = ride.getSchedule();
        this.stations = ride.getAllStations().stream().map(StationWrapper::new).collect(Collectors.toList());
        this.partsOfRide = ride.getPartsOfRide().stream().map(PartOfRideWrapper::new).collect(Collectors.toList());
    }
}

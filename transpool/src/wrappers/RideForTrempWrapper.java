package wrappers;

import transpool.logic.time.Schedule;
import transpool.logic.traffic.item.RideForTremp;

import java.util.List;
import java.util.stream.Collectors;

public class RideForTrempWrapper {
    private int id;
    private Schedule schedule;
    private List<SubRideWrapper> subRides;
    private String startTime;
    private String endTime;
    private int stationSwaps;
    private double totalPrice;
    private double averageFuel;
    private double averageRank;

    public RideForTrempWrapper(RideForTremp rideForTremp) {
        this.id = rideForTremp.getID();
        this.schedule = rideForTremp.getSchedule();
        this.subRides = rideForTremp.getSubRides().stream().map(SubRideWrapper::new).collect(Collectors.toList());

        this.startTime = rideForTremp.getSchedule().getStartTime().toString();
        this.endTime = rideForTremp.getSchedule().getEndTime().toString();
        this.stationSwaps = rideForTremp.getSubRides().size() - 1;
        this.totalPrice = rideForTremp.getTotalCost();
        this.averageFuel = rideForTremp.getAverageFuelUsage();
        this.averageRank = rideForTremp.getAverageRank();
    }
}

package transpool.logic.traffic.item;

import enums.DesiredTimeType;
import enums.RepeatType;
import transpool.logic.map.structure.Station;
import transpool.logic.time.RequestSchedule;
import transpool.logic.user.Driver;
import transpool.logic.user.User;

import java.time.LocalTime;

public class TrempRequest {
    private static int unique_id = 4000;
    private final int id;
    private final Station startStation;
    private final Station endStation;
    private User user;
    private int maxNumberOfConnections = 1; //TODO: set defualt to 0 and update according to user preference
    private RideForTremp selectedRide = null;

    private RequestSchedule schedule;

    public TrempRequest(Station startStation, Station endStation) {
        this.id = unique_id++;
        this.startStation = startStation;
        this.endStation = endStation;
    }

    public RideForTremp getSelectedRide() {
        return selectedRide;
    }

    public boolean isNotAssignedToRides(){
        return this.selectedRide == null;
    }

    public void setMaxNumberOfConnections(int maxNumberOfConnections) {
        this.maxNumberOfConnections = maxNumberOfConnections;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void setDesiredDayAndTime(int day, LocalTime time, DesiredTimeType desiredTimeType){
        this.schedule = new RequestSchedule(time, day, RepeatType.ONE_TIME, desiredTimeType);
    }

    public void setMaxDiffMinutes(int minutes){
        this.schedule.setMaxDiffInMinutes(minutes);
    }

    public int getID(){
        return this.id;
    }

    public User getUser() {
        return user;
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public int getMaxNumberOfConnections() {
        return maxNumberOfConnections;
    }

    public RequestSchedule getSchedule() {
        return schedule;
    }

    public DesiredTimeType getDesiredTimeType() {
        return this.schedule.getDesiredTimeType();
    }

    public void assignRides(RideForTremp ridesToAssign){
        this.selectedRide = ridesToAssign;

        if (this.schedule.getDesiredTimeType() == DesiredTimeType.DEPART)
            this.schedule.setEndDateTime(ridesToAssign.getSchedule().getEndDateTime());
        else
            this.schedule.setStartDateTime(ridesToAssign.getSchedule().getStartDateTime());
    }

    public int getDesiredDay() {
        return this.schedule.getDesiredDayAccordingToTimeType();
    }

    public RequestSchedule getDesiredSchedule(){
        return this.schedule;
    }

    public boolean assignedRideWasRanked(){
        boolean ranked = false;
        if (this.selectedRide != null){
            ranked = this.selectedRide.isRankedByUser(this.user);
        }

        return  ranked;
    }

    public void setRank(Driver.Rank rank) {
        if(this.selectedRide != null)
            this.selectedRide.addRank(this.getUser(), rank);
    }
}

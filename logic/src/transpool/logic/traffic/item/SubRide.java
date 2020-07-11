package transpool.logic.traffic.item;

import enums.RepeatType;
import enums.TrempPartType;
import transpool.logic.time.Schedule;
import transpool.logic.user.Trempist;
import transpool.logic.user.User;
import transpool.logic.map.structure.Station;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicBoolean;

public class SubRide {

    private final Ride originalRide;
    private final Station startStation;
    private final Station endStation;
    List<PartOfRide> selectedPartsOfRide;
    private Schedule schedule;

    public SubRide(Ride originalRide, Station startStation, Station endStation) {
        this.originalRide = originalRide;
        this.startStation = startStation;
        this.endStation = endStation;
        this.selectedPartsOfRide = getRelevantPartsOfRide(startStation, endStation);
        initGeneralScheduleAccordingToParts();
    }


    public SubRide(Ride originalRide, Station startStation, Station endStation, int onDay) {
        this.originalRide = originalRide;
        this.startStation = startStation;
        this.endStation = endStation;
        this.selectedPartsOfRide = getRelevantPartsOfRide(startStation, endStation);
        initOneTimeScheduleAccordingToParts(onDay);
    }

    private void initGeneralScheduleAccordingToParts() {
        PartOfRide firstPart = selectedPartsOfRide.get(0);
        PartOfRide lastPart = selectedPartsOfRide.get(selectedPartsOfRide.size() - 1);
        this.schedule = Schedule.createScheduleMixFromSchedules(firstPart.getSchedule(), lastPart.getSchedule());
    }

    public void initOneTimeScheduleAccordingToParts(int onDay){
        PartOfRide firstPart = selectedPartsOfRide.get(0);
        PartOfRide lastPart = selectedPartsOfRide.get(selectedPartsOfRide.size() - 1);

        this.schedule = new Schedule(firstPart.getSchedule().getStartTime(), onDay, RepeatType.ONE_TIME); //firstPart.getSchedule().createClone();
        this.schedule.setEndDateTime(lastPart.getSchedule().getEndDateTime());
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Ride getOriginalRide() {
        return originalRide;
    }

    private List<PartOfRide> getRelevantPartsOfRide(Station start, Station end){
        int indexOfPartsFrom = originalRide.getAllStations().indexOf(start);
        int indexOfPartsTo = originalRide.getAllStations().indexOf(end);

        return originalRide.getPartsOfRide().subList(indexOfPartsFrom, indexOfPartsTo);
    }

    public void applyTrempistToAllPartsOfRide(User user, int onDay) {
        //TODO: add here a reference from Ride to Trempist
        this.originalRide.addTrempistToRide(new Trempist(user, this), onDay);

        selectedPartsOfRide.forEach( partOfRide -> {
            TrempPartType fromStation = TrempPartType.MIDDLE, toStation = TrempPartType.MIDDLE;

            if (partOfRide == selectedPartsOfRide.get(0))
                fromStation = TrempPartType.FIRST;

            if (partOfRide == selectedPartsOfRide.get(selectedPartsOfRide.size() - 1))
                toStation = TrempPartType.LAST;

            partOfRide.addTrempist(new Trempist(user, fromStation, toStation, this), onDay);
        });
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public double getTotalCost(){
        return getTotalDistance() * originalRide.getPricePerKilometer();
    }

    public double getTotalDistance(){
        return this.selectedPartsOfRide.stream()
                .mapToDouble(PartOfRide::getLengthOfRoad)
                .sum();
    }

    public List<Station> getAllStations(){
        LinkedHashSet<Station> allStations = new LinkedHashSet<>();
        this.selectedPartsOfRide.forEach(partOfRide -> {
            allStations.add(partOfRide.getRoad().getStartStation());
            allStations.add(partOfRide.getRoad().getEndStation());
        });

        return new ArrayList<>(allStations);
    }

    public double getAverageFuelUsage(){
        OptionalDouble average = this.selectedPartsOfRide.stream().mapToDouble(PartOfRide::getFuelUsage).average();
        return average.isPresent() ? average.getAsDouble(): 0;
    }

    public boolean hasSpaceOnScheduledDays(){    //valid = have empty space in all parts
        boolean hasFreeSpace = true;
        LocalDateTime dateTimeToCheck = this.schedule.getStartDateTime();

        for (PartOfRide partOfRide: this.selectedPartsOfRide) {
            if (!partOfRide.canAddTrempistOnDateTime(dateTimeToCheck))
                hasFreeSpace = false;

            dateTimeToCheck = dateTimeToCheck.plusMinutes(((int) partOfRide.getPeriodInMinutes()));

        }
        return hasFreeSpace;
//        return this.selectedPartsOfRide.stream()
//                .allMatch(partOfRide -> partOfRide.canAddTrempist(this.schedule.getStartDay()));
    }

    public boolean hasNotRankedUsers(){
        return this.originalRide.isNotRankedBySomeTrempists();
    }

    public List<PartOfRide> getSelectedPartsOfRide() {
        return selectedPartsOfRide;
    }
}

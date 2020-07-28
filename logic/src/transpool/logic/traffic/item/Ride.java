package transpool.logic.traffic.item;

import enums.RepeatType;
import transpool.logic.map.structure.Road;
import transpool.logic.map.structure.Station;
import transpool.logic.time.Schedule;
import transpool.logic.user.Driver;
import transpool.logic.user.Trempist;
import transpool.logic.user.TrempistsManager;
import transpool.logic.user.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.*;
import java.util.stream.Collectors;

public class Ride {



    private static int unique_id = 6000;
    private final int id;
    private final Driver rideOwner;
    private int pricePerKilometer = 0;


    private List<PartOfRide> partsOfRide;
    private List<Station> allStations;
    private LinkedHashMap<Station, PartOfRide> mapFromStationToRoad;
    private Schedule schedule;
    TrempistsManager allTrempistsManager;
    private int carCapacity;

    private Ride(User rideOwner, List<Road> allRoads, int carCapacity) {
        this.id = unique_id++;
        this.rideOwner = new Driver(rideOwner);
        this.allTrempistsManager = new TrempistsManager();
        this.carCapacity = carCapacity;
        initDataStructures(allRoads, carCapacity);
    }

    private void initDataStructures(List<Road> roads, int carCapacity){
        this.allStations = new ArrayList<>();
        this.partsOfRide = new ArrayList<>();
        this.mapFromStationToRoad = new LinkedHashMap<>();

        this.allStations.add(roads.get(0).getStartStation());
        roads.forEach( road -> {
            this.allStations.add(road.getEndStation());
            PartOfRide partOfRide = new PartOfRide(this, road, carCapacity);
            this.partsOfRide.add(partOfRide);
            this.mapFromStationToRoad.put(road.getStartStation(), partOfRide);
        });

    }

    public int getCarCapacity() {
        return carCapacity;
    }

    public boolean isTrempsAssignedToRide(int onDay) {
        for(PartOfRide pride: this.getPartOfRide())
            if(!pride.getTrempistsManager().getAllTrempists(onDay).isEmpty())
                return true;
        return false;
    }

    public List<PartOfRide> getPartOfRide()
    {
        return this.partsOfRide;
    }

    public void setSchedule(LocalTime time, int day, RepeatType repeatType) {
        this.schedule = new Schedule(time, day, repeatType);
        updateTimesOfAllPartsOfRide(this.schedule);
        this.schedule.setEndDateTime(this.partsOfRide.get(partsOfRide.size() - 1).getSchedule().getEndDateTime());
    }

    public Schedule getSchedule() {
        return schedule;
    }

    private void updateTimesOfAllPartsOfRide(Schedule schedule){
        Schedule scheduleToSet = schedule.createClone();
        for (PartOfRide part : this.partsOfRide){

            part.setStartSchedule(scheduleToSet);
            part.updateEndDateTime();
            LocalDateTime endDateTime = part.getSchedule().getEndDateTime();
            scheduleToSet = scheduleToSet.createCloneWithNewStartDateTime(endDateTime);
        }
    }

    public static Ride createRideFromRoads(User rideOwner, List<Road> roads, int capacity){

        return new Ride(rideOwner, roads, capacity);
    }

    public double getTotalTimeOfRide(){
        //TODO consider substract start time from end time
        return this.partsOfRide
                .stream()
                .mapToDouble(PartOfRide::getPeriodInMinutes)
                .sum();
    }

    public double getTotalCostOfRide(){
        return this.partsOfRide
                .stream()
                .mapToDouble(PartOfRide::getLengthOfRoad)
                .map((length) -> length * this.pricePerKilometer)
                .sum();
    }

    public double getAverageFuelUsage(){
        return this.partsOfRide.size() == 0 ? 0 : this.partsOfRide
                .stream()
                .mapToDouble(PartOfRide::getFuelUsage)
                .average()
                .orElse(0);

    }

    public SubRide getSubRide(Station fromStation, Station toStation, int onDay){
        return new SubRide(this, fromStation, toStation, onDay);
    }

    public boolean containsValidRouteDepartingOn(int onDay, Station from, Station to){    //valid = have empty space in all parts
        boolean containsRoute = false;

        if (rideContainsStations(from, to)){
            boolean hasSpaceInRoad = true;

            while(from != to && hasSpaceInRoad){
                PartOfRide partRoad = mapFromStationToRoad.get(from);
                hasSpaceInRoad = partRoad.canAddTrempist(onDay);
                from = partRoad.getRoad().getEndStation();
                onDay = partRoad.getSchedule().getEndDay();
            }
            if (from == to){
                containsRoute = true;
            }
        }

        return containsRoute;
    }

    public boolean containsValidRouteArrivingOn(int arriveOnDay, Station from, Station to){    //valid = have empty space in all parts
        int dayOfDepart = mapFromStationToRoad.get(from).getSchedule().getStartDay();
        Station originalFromStation = from;
        int dayOfArrival = dayOfDepart;
        if (rideContainsStations(from, to)){

            while(from != to){
                PartOfRide partRoad = mapFromStationToRoad.get(from);
                from = partRoad.getRoad().getEndStation();
                dayOfArrival = partRoad.getSchedule().getEndDay();
            }
        }

        return dayOfArrival == arriveOnDay && containsValidRouteDepartingOn(dayOfDepart, originalFromStation, to);
    }

    public boolean containsValidRoute(Station from, Station to, int onDay){    //valid = have empty space in all parts
        boolean containsRoute = false;

        if (rideContainsStations(from, to)){
            boolean hasSpaceInRoad = true;

            while(from != to && hasSpaceInRoad){
                PartOfRide partRoad = mapFromStationToRoad.get(from);
                hasSpaceInRoad = partRoad.canAddTrempist(onDay);
                from = partRoad.getRoad().getEndStation();
            }
            if (from == to){
                containsRoute = true;
            }
        }

        return containsRoute;
    }

    public List<List<Station>> getListsOfAllStationsStillRelevantForTremps(int onDay) {
        List<List<Station>> routesWithFreeSpace = new ArrayList<>();
        List<Station> subRoute = new ArrayList<>();

//        for(Map.Entry<Station, PartOfRide> station2Part: this.mapFromStationToRoad.entrySet()){
//            subRoute.add(station2Part.getKey());
//
//            if (station2Part.getValue().canAddTrempist()
//                    && station2Part.getValue() == partOfRides.get(partOfRides.size() - 1)){
//                subRoute.add(station2Part.getValue().getRoad().getEndStation());
//                routesWithFreeSpace.add(subRoute);
//            }
//            else{
//                if(subRoute.size() > 1)
//                    routesWithFreeSpace.add(subRoute);
//                subRoute = new ArrayList<>();
//            }
//
//        }

        for(PartOfRide part: partsOfRide){
            subRoute.add(part.getRoad().getStartStation());
            if (part.canAddTrempist(onDay) && part == partsOfRide.get(partsOfRide.size() - 1)) {
                subRoute.add(part.getRoad().getEndStation());
                routesWithFreeSpace.add(subRoute);
            }
            else{
                if (subRoute.size() > 1)
                    routesWithFreeSpace.add(subRoute);
                subRoute = new ArrayList<>();
            }
        }

        return routesWithFreeSpace;
    }

    public boolean rideContainsStations(Station a, Station b){
        return allStations.contains(a)
                && allStations.contains(b)
                && allStations.indexOf(a) < allStations.indexOf(b);
    }


    public boolean departingFrom(Station station){
        return this.allStations.contains(station)
                && this.allStations.indexOf(station) != this.allStations.size() - 1;
    }

    public boolean arrivingTo(Station station){
        return this.allStations.contains(station)
                && this.allStations.indexOf(station) != 0;
    }

    private boolean hasFreeSpaceFromStation(Station station, int onDay){
        return this.mapFromStationToRoad.get(station).canAddTrempist(onDay);
    }

    public void setPricePerKilometer(int pricePerKilometer) {
        this.pricePerKilometer = pricePerKilometer;
    }

    public int getID(){
        return this.id;
    }

    public Driver getRideOwner() {
        return rideOwner;
    }

    public List<PartOfRide> getPartsOfRide() {return partsOfRide;}

    public List<Station> getAllStations(){
        return this.allStations;
    }

    public List<Road> getAllRoads(){
        return this.partsOfRide.stream().map(PartOfRide::getRoad).collect(Collectors.toList());
    }

    public List<Station> getAllStationsAfter(Station fromStation){
        List<Station> stationsAfterStation = new LinkedList<>();
        this.allStations.stream().forEach(station -> {
            if (this.allStations.indexOf(fromStation) < this.allStations.indexOf(station))
                stationsAfterStation.add(station);
        });
        return stationsAfterStation;
    }

    public int getPricePerKilometer() {
        return pricePerKilometer;
    }

    public void addTrempistToRide(Trempist trempistToAdd, int onDay){
        this.allTrempistsManager.addTrempistToAllTrempists(onDay, trempistToAdd);
    }

    public boolean hasAnyTrempistsOnAnyDay(){
        return this.allTrempistsManager.haveTrempistsOnAnyDay();
    }

    public boolean isNotRankedBySomeTrempists(){
        return getNotRankedTrempists().size() > 0;
    }

    public List<Trempist> getNotRankedTrempists(){
        return this.allTrempistsManager.getAllTrempists().values().stream()
                .filter(trempists -> trempists.size() > 0)
                .flatMap(List::stream)
                .filter(Trempist::notRankedRide)
                .collect(Collectors.toList());
    }

    public Station getStartStation(){
        return this.allStations.get(0);
    }
    public Station getEndStation(){
        return this.allStations.get(this.allStations.size() - 1);
    }

}

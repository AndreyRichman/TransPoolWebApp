package transpool.logic.traffic;

import enums.DesiredTimeType;
import exception.RideNotExistsException;
import exception.TrempRequestNotExist;
import transpool.logic.map.structure.Station;
import transpool.logic.time.RequestSchedule;
import transpool.logic.time.Schedule;
import transpool.logic.traffic.item.RideForTremp;
import transpool.logic.traffic.item.SubRide;
import transpool.logic.traffic.item.Ride;
import transpool.logic.traffic.item.TrempRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class TrafficManager {


    List<Ride> allRides;
    List<TrempRequest> allTrempRequests;

    Map<Integer, Ride> mapIdToRide;
    Map<Integer, TrempRequest> mapIdToTrempReuqest;

    private Map<Integer, List<RideForTremp>> cachedRidesForTremp;
    private Map<Integer, RideForTremp> id2TrempOption;

    public TrafficManager() {

        this.allRides = new ArrayList<>();
        this.allTrempRequests = new ArrayList<>();
        this.mapIdToRide = new HashMap<>();
        this.mapIdToTrempReuqest = new HashMap<>();

        this.cachedRidesForTremp = new HashMap<>();
        this.id2TrempOption = new HashMap<>();
    }

    public List<Ride> getAllRides() {
        return allRides;
    }

    public Ride getRideByID(int idOfRide) throws RideNotExistsException {
        if (!this.mapIdToRide.containsKey(idOfRide)){
            throw new RideNotExistsException(idOfRide);
        }

        return this.mapIdToRide.get(idOfRide);
    }

    public void addRide(Ride ride){
        this.allRides.add(ride);
        this.mapIdToRide.put(ride.getID(), ride);
        this.cachedRidesForTremp.clear();
    }

    public List<TrempRequest> getAllTrempRequests() {
        return allTrempRequests;
    }

    public void addTrempRequest(TrempRequest trempRequest){
        this.allTrempRequests.add(trempRequest);
        this.mapIdToTrempReuqest.put(trempRequest.getID(), trempRequest);
    }

    public TrempRequest getTrempRequestById(int idOfTrempRequest) throws TrempRequestNotExist{
        if (!this.mapIdToTrempReuqest.containsKey(idOfTrempRequest))
            throw new TrempRequestNotExist(idOfTrempRequest);

        return this.mapIdToTrempReuqest.get(idOfTrempRequest);
    }

//    private List<RideForTremp> getRideOptionsWithNoConnections(Station from, Station to, int onDay){
//        private List<RideForTremp> getRideOptionsWithNoConnections(TrempRequest trempRequest){
//        Station from = trempRequest.getStartStation();
//        Station to = trempRequest.getEndStation();
//        int onDay = trempRequest.getDesiredDay();
//        LocalTime time = trempRequest.getDesiredTime();
//        int maxMinutesDiff = trempRequest.getMaxTimeDiff();
//        DesiredTimeType timeType = trempRequest.getDesiredTimeType();
//
//        List<RideForTremp> noConnectionOptions = new LinkedList<>();
//        this.allRides.stream()
//                .filter(ride -> ride.getSchedule().scheduleIsRelevantForTimeAndDay(time, onDay, timeType ,maxMinutesDiff))
//                .filter(ride -> ride.containsValidRoute(from, to, onDay)) //TODO compare day by schedule
//                .forEach(ride -> {
//                    ArrayList<SubRide> subRidesOption = new ArrayList<>();
//                    subRidesOption.add(ride.getSubRide(from, to, onDay));
//                    noConnectionOptions.add(new RideForTremp(subRidesOption));
//                });
//
//        return noConnectionOptions;
//    }


    private List<RideForTremp> getRideOptionsWithNoConnections(TrempRequest trempRequest){
        Station from = trempRequest.getStartStation();
        Station to = trempRequest.getEndStation();
//        int onDay = trempRequest.getDesiredDay();
//        LocalTime time = trempRequest.getDesiredTime();
        //int maxMinutesDiff = trempRequest.getMaxTimeDiff();
        DesiredTimeType timeType = trempRequest.getDesiredTimeType();

        RequestSchedule requestSchedule = trempRequest.getDesiredSchedule();

//        List<RideForTremp> noConnectionOptions = new LinkedList<>();

        return requestSchedule.getDesiredTimeType() == DesiredTimeType.DEPART ?
                getRideOptionsWithNoConnectionsDepartingOnDay(from, to, requestSchedule) :
                getRideOptionsWithNoConnectionsArrivingOnDay(from, to, requestSchedule);


//        this.allRides.stream()
//                .filter(ride -> ride.getSchedule().scheduleIsRelevantForTimeAndDay(time, onDay, timeType ,maxMinutesDiff))
//                .filter(ride -> ride.containsValidRoute(from, to, onDay)) //TODO compare day by schedule
//                .forEach(ride -> {
//                    ArrayList<SubRide> subRidesOption = new ArrayList<>();
//                    subRidesOption.add(ride.getSubRide(from, to, onDay));
//                    noConnectionOptions.add(new RideForTremp(subRidesOption));
//                });
//
//        return noConnectionOptions;
    }



    private List<RideForTremp> getRideOptionsWithNoConnectionsDepartingOnDay(Station from, Station to, RequestSchedule departSchedule){

        int onDay = departSchedule.getStartDay();

        List<RideForTremp> noConnectionOptions = new LinkedList<>();
        this.allRides.stream()
                .filter(ride -> ride.getSchedule().scheduleIsRelevantForRequestSchedule(departSchedule))
                .filter(ride -> ride.containsValidRouteDepartingOn(onDay, from, to))
                .forEach(ride -> {
                    ArrayList<SubRide> subRidesOption = new ArrayList<>();
                    subRidesOption.add(ride.getSubRide(from, to, onDay));
                    noConnectionOptions.add(new RideForTremp(subRidesOption));
                });

        return noConnectionOptions;
    }

    private List<RideForTremp> getRideOptionsWithNoConnectionsArrivingOnDay(Station from, Station to, RequestSchedule arriveSchedule){
        int onDay = arriveSchedule.getEndDay();

        List<RideForTremp> noConnectionOptions = new LinkedList<>();
        this.allRides.stream()
                .filter(ride -> ride.getSchedule().scheduleIsRelevantForRequestSchedule(arriveSchedule))
                .filter(ride -> ride.containsValidRouteArrivingOn(onDay, from, to))
                .forEach(ride -> {
                    ArrayList<SubRide> subRidesOption = new ArrayList<>();
                    subRidesOption.add(ride.getSubRide(from, to, ride.getSchedule().getStartDay()));
                    noConnectionOptions.add(new RideForTremp(subRidesOption));
                });

        return noConnectionOptions;
    }

//    private List<RideForTremp> getRideOptionsWithConnections(int maxConnections, Station from, Station to, int onDay){
//    private List<RideForTremp> getRideOptionsWithConnections(TrempRequest trempRequest){
//        Station from = trempRequest.getStartStation();
//        Station to = trempRequest.getEndStation();
//        int maxNumberOfConnections = trempRequest.getMaxNumberOfConnections();
//        int onDay = trempRequest.getDesiredDay();
//
//        List<RideForTremp> rideOptions = new LinkedList<>();
//        List<Ride> relevantRides = this.allRides.stream()
//                .filter((ride)-> !ride.containsValidRoute(from, to, onDay)).collect(Collectors.toList());
//
//        //TODO: write algorithm to groups of rides and filter by max connections
//
//        return rideOptions;
//    }

    private List<RideForTremp> getAllRideOptions(TrempRequest trempRequest){
        Station from = trempRequest.getStartStation();
        Station to = trempRequest.getEndStation();
        int maxNumberOfConnections = trempRequest.getMaxNumberOfConnections();

        List<List<SubRide>> relevantRidesByLocation = recursivlyGetAllRideOptions(from, to, maxNumberOfConnections);
        List<List<SubRide>> relevantRidesByLocationWithCorrectConn = filterRidesWithNotCorrectConnections(relevantRidesByLocation);
        List<List<SubRide>> relevantRidesByTime = filterRidesBySchedule(relevantRidesByLocationWithCorrectConn, trempRequest.getSchedule());
        List<List<SubRide>> relevantByFreeSpace = filterRIdesBySpace(relevantRidesByTime);

        List<RideForTremp> matchedRides = new LinkedList<>();

        relevantByFreeSpace.forEach(subRides -> {
            matchedRides.add(new RideForTremp(subRides));
        });

        return matchedRides;
    }

    private List<List<SubRide>> filterRidesWithNotCorrectConnections(List<List<SubRide>> relevantRidesByLocation) {
        List<List<SubRide>> subRidesToRemove = new LinkedList<>();
        for(List<SubRide> subRideOption: relevantRidesByLocation){
            SubRide firstSub = subRideOption.get(0);
            Station current = firstSub.getEndStation();
            for(SubRide subRide : subRideOption){
                if (subRide != firstSub && subRide.getStartStation() != current){
                    subRidesToRemove.add(subRideOption);
                    break;
                }
                current = subRide.getEndStation();
            }
        }

        return relevantRidesByLocation.stream().filter(option -> !subRidesToRemove.contains(option)).collect(Collectors.toList());
    }

    private List<List<SubRide>> filterRIdesBySpace(List<List<SubRide>> subRidesToCheckForFreeSpace) {
        return subRidesToCheckForFreeSpace.stream()
                .filter(listOfSubRideOptions -> listOfSubRideOptions
                        .stream()
                        .allMatch(SubRide::hasSpaceOnScheduledDays))
                .collect(Collectors.toList());
    }

    private List<List<SubRide>> filterRidesBySchedule(List<List<SubRide>> ridesToFilter, RequestSchedule schedule) {
        return ridesToFilter.stream().filter(subRides -> subRidesMatchSchedule(subRides, schedule)).collect(Collectors.toList());
    }

    private boolean subRidesMatchSchedule(List<SubRide> subRides, RequestSchedule schedule) {

        List<Schedule> subRidesSchedules = new LinkedList<>();
        subRides.forEach(subRide -> subRidesSchedules.add(subRide.getSchedule()));
//        List<Schedule> schedules = subRides.stream().map(SubRide::getSchedule).collect(Collectors.toList());

        return schedulesAreOrderedAndFitRequestedSchedule(subRidesSchedules, schedule);

        //create total schedule using RideForTremp and verify free space on each part
        //check there is free space according to the new schedule

    }

    private boolean schedulesAreOrderedAndFitRequestedSchedule(List<Schedule> schedules, RequestSchedule requestSchedule){
        return requestSchedule.getDesiredTimeType() == DesiredTimeType.ARRIVE ?
                schedulesMatchesArrivingScheduleRequest(schedules, requestSchedule) :
                schedulesMatchesDepartingScheduleRequest(schedules, requestSchedule);
    }
    private boolean schedulesMatchesArrivingScheduleRequest(List<Schedule> schedules, RequestSchedule requestSchedule){
        Schedule depart = schedules.get(schedules.size() - 1);
        boolean isRelevant =  depart.scheduleIsRelevantForRequestSchedule(requestSchedule);
        LocalDateTime relevantDateTime = requestSchedule.getDesiredDateTimeAccordingToTimeType();
        ListIterator<Schedule> scheduleIterator = schedules.listIterator(schedules.size());

        while(isRelevant && scheduleIterator.hasPrevious()){
            Schedule currentSchedule = scheduleIterator.previous();

            if(currentSchedule.hasInstanceEndsBeforeDateTime(relevantDateTime)){
                Duration scheduleDuration = Duration.between(currentSchedule.getStartDateTime(), currentSchedule.getEndDateTime()).abs();
                LocalDateTime newEndDateTime = currentSchedule.getClosestDateTimeBefore(relevantDateTime, currentSchedule.getEndDateTime());
                currentSchedule.setEndDateTime(newEndDateTime);
                LocalDateTime newStartDateTime = newEndDateTime.minus(scheduleDuration);
                currentSchedule.setStartDateTime(newStartDateTime);
                relevantDateTime = newStartDateTime;
            }
            else {
                isRelevant = false;
            }
        }

        return isRelevant;
    }
    private boolean schedulesMatchesDepartingScheduleRequest(List<Schedule> schedules, RequestSchedule requestSchedule){
        Schedule depart = schedules.get(0);
        boolean isRelevant = depart.scheduleIsRelevantForRequestSchedule(requestSchedule);

        if (isRelevant) {
            LocalDateTime relevantDateTime = depart.getStartDateTime();//requestSchedule.getDesiredDateTimeAccordingToTimeType();
            for (Schedule currentSchedule : schedules) {
                if (currentSchedule.hasInstanceStartsAfterDateTime(relevantDateTime)) {
                    Duration scheduleDuration = Duration.between(currentSchedule.getStartDateTime(), currentSchedule.getEndDateTime()).abs();
                    LocalDateTime newStartDateTime = currentSchedule.getClosestDateTimeAfter(relevantDateTime, currentSchedule.getStartDateTime());
                    currentSchedule.setStartDateTime(newStartDateTime);
                    LocalDateTime newEndDateTime = newStartDateTime.plus(scheduleDuration);
                    currentSchedule.setEndDateTime(newEndDateTime);
                    relevantDateTime = newEndDateTime;
                } else {
                    isRelevant = false;
                    break;
                }
            }
        }

        return isRelevant;
    }

//    private List<List<SubRide>> recursivlyGetAllRideOptions(Station from, Station to, int swaps){
//        List<List<SubRide>> options = new LinkedList<>();
//
//        List<Ride> direct = this.allRides.stream().filter(ride -> ride.rideContainsStations(from, to)).collect(Collectors.toList());
//        direct.stream()
//                .map(ride -> new SubRide(ride, from, to))
//                .map(subRide -> new ArrayList<SubRide>(){{ add(subRide); }})
//                .forEach(options::add);
//
//        List<Ride> allOther = this.allRides.stream().filter(ride -> !direct.contains(ride)).collect(Collectors.toList());
//        if (swaps > 0) {
//            List<List<SubRide>> matchedCouples = getCouplesOfRidesTogetherContainStations(from, to, allOther);
//
//        }
//
//        if (swaps > 1){
//            List<List<SubRide>> matchedGroups = getGroupsOfRidesTogetherContainStations(from, to, allOther);
//        }
//
//
////        if (swaps > 0)
////            foundTremps.addAll(recursivlyGetAllRideOptions(from, to, swaps - 1));
////
////        return foundTremps;
//        return options;
//    }

//    private List<List<SubRide>> getCouplesOfRidesTogetherContainStations(Station from, Station to, List<Ride> allOther){
//        List<List<SubRide>> matchedSubRides = new ArrayList<>();
//        List<Ride> starters = allOther.stream().filter(ride -> ride.departingFrom(from)).collect(Collectors.toList());
//        List<Ride> enders = allOther.stream().filter(ride -> ride.arrivingTo(to)).collect(Collectors.toList());
//        for (Ride startRide : starters) {
//            for (Ride endRide: enders ) {
//                for (Station sharedStation: startRide.getAllStationsAfter(from)){
//                    if (endRide.rideContainsStations(sharedStation, to)){
//                        List<SubRide> matchedCouple = new ArrayList<SubRide>(){{
//                            add(new SubRide(startRide, from, sharedStation));
//                            add(new SubRide(endRide, sharedStation, to));
//                        }};
//                        matchedSubRides.add(matchedCouple);
//                        break;
//                    }
//                }
//
//            }
//        }
//        return matchedSubRides;
//    }

//    private List<List<SubRide>> getGroupsOfRidesTogetherContainStations(Station from, Station to, List<Ride> allOther){
//        List<List<SubRide>> matchedSubRides = new ArrayList<>();
//        List<Ride> starters = allOther.stream().filter(ride -> ride.departingFrom(from)).collect(Collectors.toList());
//        List<Ride> enders = allOther.stream().filter(ride -> ride.arrivingTo(to)).collect(Collectors.toList());
//
//        List<Ride> nonCommonStarters
//    }
//
//    private boolean ridesContainSharedStationBetweenStations(Station from, Station to, Ride rideFrom, Ride rideTo) {
//        boolean containsSameStation = false;
//
//        for (Station sharedStation : rideFrom.getAllStationsAfter(from)) {
//            if (rideTo.rideContainsStations(sharedStation, to)) {
//                containsSameStation = true;
//                break;
//            }
//        }
//
//        return containsSameStation;
//    }

//    private List<List<SubRide>> recursivlyGetAllRideOptions(Station from, Station to, int swaps){
//        //TODO make sure from != to
//        List<List<SubRide>> options = new LinkedList<>();
//
//        List<List<SubRide>> directOptions = this.allRides.stream()
//                .filter(ride -> ride.rideContainsStations(from, to))
//                .map(ride -> new SubRide(ride, from, to))
//                .map(subRide -> new LinkedList<SubRide>(){{add(subRide);}})
//                .collect(Collectors.toList());
//
//        if (directOptions.size() > 0)
//            options.addAll(directOptions);
//
//        List<Ride> allOtherStarters = this.allRides.stream().filter(ride -> !ride.rideContainsStations(from, to))
//                .filter(ride -> ride.departingFrom(from)).collect(Collectors.toList());
//        if (swaps > 0 && allOtherStarters.size() > 0) {
//
//            allOtherStarters.forEach(starterRide -> {
//                starterRide.getAllStationsAfter(from).forEach(stationForSwap -> {
//                    List<List<SubRide>> matchedSubrides = recursivlyGetAllRideOptions(stationForSwap, to, swaps - 1);
//                    matchedSubrides.forEach(subRidesList -> subRidesList.add(0, new SubRide(starterRide, from, stationForSwap)));
//                    options.addAll(matchedSubrides);
//                });
//            });
//        }
//
//        return options;
//    }
//

    private List<List<SubRide>> recursivlyGetAllRideOptions(Station from, Station to, int swaps){
        //TODO make sure from != to
        List<List<SubRide>> options = new LinkedList<>();

        List<List<SubRide>> directOptions = this.allRides.stream()
                .filter(ride -> ride.rideContainsStations(from, to))
                .map(ride -> new SubRide(ride, from, to))
                .map(subRide -> new LinkedList<SubRide>(){{add(subRide);}})
                .collect(Collectors.toList());

        if (directOptions.size() > 0)
            options.addAll(directOptions);

        List<Ride> allOtherStarters = this.allRides.stream().filter(ride -> !ride.rideContainsStations(from, to))
                .filter(ride -> ride.departingFrom(from)).collect(Collectors.toList());
        if (swaps > 0 && allOtherStarters.size() > 0) {

            for (Ride starterRide : allOtherStarters) {
                for (Station stationForSwap : starterRide.getAllStationsAfter(from)) {
                    List<List<SubRide>> matchedSubrides = recursivlyGetAllRideOptions(stationForSwap, to, swaps - 1);

                    for (List<SubRide> subRidesList : matchedSubrides) {
                        if (subRidesList.size() > 0 && subRidesList.get(0).getOriginalRide() != starterRide) {

                            subRidesList.add(0, new SubRide(starterRide, from, stationForSwap));
                            options.add(subRidesList);

                        }
                    }
                }
            }
        }
        return options;
    }


//    public List<RideForTremp> getRideOptions(int maxConnections, Station from, Station to, int onDay){
//    public List<RideForTremp> getRideOptions(TrempRequest trempRequest){
//        Station from = trempRequest.getStartStation();
//        Station to = trempRequest.getEndStation();
//        int maxNumberOfConnections = trempRequest.getMaxNumberOfConnections();
////        int dayOfTrempRequest = trempRequest.getDesiredDay();
//
//        List<RideForTremp> allRideOptions = new ArrayList<>();
//
//
//        allRideOptions.addAll(getRideOptionsWithNoConnections(trempRequest));
//        allRideOptions.addAll(getRideOptionsWithConnections(trempRequest));
////
////        allRideOptions.addAll(getRideOptionsWithNoConnections(from, to, onDay));
////        allRideOptions.addAll(getRideOptionsWithConnections(maxConnections, from, to, onDay));
//
//        return allRideOptions;
//    }

    private void storeTrempOptions(int trempRequestID, List<RideForTremp> foundOptions){
        cachedRidesForTremp.put(trempRequestID, foundOptions);

        if (foundOptions.size() > 0){
            foundOptions.forEach(rideForTremp -> id2TrempOption.put(rideForTremp.getID(), rideForTremp));
        }
    }

    public RideForTremp getRideForTrempById(int idOfRideForTremp){
        return id2TrempOption.get(idOfRideForTremp);
    }

    public List<RideForTremp> getAllPossibleTrempsForTrempRequest(TrempRequest trempRequest){

        if (cachedRidesForTremp.containsKey(trempRequest.getID())){
            return cachedRidesForTremp.get(trempRequest.getID());
        } else {


//        List<RideForTremp> relevantByRouteOptions = getRideOptions(maxNumberOfConnections, start, end, dayOfTrempRequest);
            List<RideForTremp> relevantByRouteOptions = getAllRideOptions(trempRequest);//getRideOptions(trempRequest);
            relevantByRouteOptions.sort(Comparator.comparingInt(RideForTremp::getNumOfParts));


//        Function<RideForTremp, LocalDateTime> rideCorrectTimeGetter = trempRequest.getDesiredTimeType() == DesiredTimeType.DEPART ?
//                RideForTremp::getDepartDateTime :  RideForTremp::getArriveDateTime;

//        return relevantByRouteOptions.stream()
//                .filter(rideForTremp -> rideCorrectTimeGetter.apply(rideForTremp).equals(trempRequest.getDesiredTime()))
//                .collect(Collectors.toList());
            storeTrempOptions(trempRequest.getID(), relevantByRouteOptions);
            return relevantByRouteOptions;
        }
    }

    public List<Ride> getAllRidesRunningOn(LocalDateTime currentDateTime) {
        return this.getAllRides()
                .stream()
                .filter(ride -> ride.getSchedule().hasInstanceContainingDateTime(currentDateTime))
                .collect(Collectors.toList());

    }
}

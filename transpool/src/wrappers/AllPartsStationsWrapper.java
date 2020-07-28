package wrappers;

import transpool.logic.map.structure.Station;
import transpool.logic.traffic.item.PartOfRide;
import transpool.logic.traffic.item.Ride;
import transpool.logic.user.Trempist;
import transpool.logic.user.User;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AllPartsStationsWrapper {

    private List<PartWrapper> partsArray;

    public AllPartsStationsWrapper(Ride ride) {
        this.partsArray = new LinkedList<>();
        initPartsArray(ride.getPartsOfRide());
        updatePartsArrayWithGettingOffTrempists(ride.getPartsOfRide());
    }

    private void updatePartsArrayWithGettingOffTrempists(List<PartOfRide> partsOfRide) {
        int stationIndex = 1;

        for (PartOfRide partOfRide : partsOfRide) {
            List<String> leavingUsers = partOfRide.getTrempistsManager().getLeavingTrempistsAllDays().stream()
                    .map(Trempist::getUser).map(User::getName).collect(Collectors.toList());
            this.partsArray.get(stationIndex).setGettingOff(leavingUsers);
            stationIndex++;
        }
    }

    private void initPartsArray(List<PartOfRide> allParts){
        for (PartOfRide part: allParts) {
            Station station = part.getRoad().getStartStation();
            String time = part.getSchedule().getStartTime().toString();
            PartWrapper partWrapper = new PartWrapper(station, time);
            partWrapper.setTotalPlaces(part.getRide().getCarCapacity());
            part.getTrempistsManager().getJustJoinedTrempistsAllDays().stream().map(Trempist::getUser)
                    .map(User::getName).forEach(partWrapper::addPickedUp);
            partsArray.add(partWrapper);
        }
        //last station
        PartOfRide partOfRide = allParts.get(allParts.size() - 1);
        Station station = partOfRide.getRoad().getEndStation();
        String time = partOfRide.getSchedule().getEndTime().toString();
        PartWrapper partWrapper = new PartWrapper(station, time);
        partWrapper.setTotalPlaces(partOfRide.getRide().getCarCapacity());
        partsArray.add(partWrapper);
    }
}

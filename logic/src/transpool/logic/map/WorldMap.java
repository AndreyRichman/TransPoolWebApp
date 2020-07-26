package transpool.logic.map;

import exception.*;
import transpool.logic.map.structure.Road;
import transpool.logic.map.structure.Station;
import transpool.logic.map.structure.Coordinate;

import javax.management.InstanceAlreadyExistsException;
import java.util.*;
import java.util.stream.Collectors;

public class WorldMap {

    private final int width;
    private final int height;
    List<Station> allStations;
    List<Road> allRoads;
    Map<String, Station> stationName2Station;
    Map<Coordinate, String> coordinateStationNameMap;

    public WorldMap(int width, int height) {

        this.width = width;
        this.height = height;
        this.allStations = new ArrayList<>();
        this.allRoads = new ArrayList<>();
        this.stationName2Station = new HashMap<>();
        this.coordinateStationNameMap = new HashMap<>();
    }

    public List<Station> getAllStations(){
        return allStations;
    }

    public void addNewStation(Station station)
            throws InstanceAlreadyExistsException, StationNameAlreadyExistsException, StationAlreadyExistInCoordinateException, StationCoordinateoutOfBoundriesException {
        validateStation(station);
        this.allStations.add(station);
        this.stationName2Station.put(station.getName(), station);
        this.coordinateStationNameMap.put(station.getCoordinate(), station.getName());
    }

    public Station getStationByName(String name) throws StationNotFoundException{
        Station station = this.stationName2Station.get(name);
        if (station == null)
            throw new StationNotFoundException(name);

        return station;
    }

    private void validateStation(Station station)
            throws InstanceAlreadyExistsException, StationNameAlreadyExistsException, StationAlreadyExistInCoordinateException, StationCoordinateoutOfBoundriesException {

       for(Station s: allStations){

           if(s.getName().equals(station.getName())){
               throw new StationNameAlreadyExistsException(station);
           }
           if(s.getCoordinate().equals(station.getCoordinate())){
               throw new StationAlreadyExistInCoordinateException(station);
           }
       }

        if (!coordinateInBoundaries(station.getCoordinate())) {
            throw new StationCoordinateoutOfBoundriesException(station);
        }

        if (this.allStations.contains(station)) {
            throw new InstanceAlreadyExistsException(station.toString());
        }
    }

    private boolean coordinateInBoundaries(Coordinate coord){
        return 0 <= coord.getX() &&
                coord.getX() < this.width &&
                0 <= coord.getY() &&
                coord.getY() < this.height;
    }

    public void addNewRoad(Road road) throws InstanceAlreadyExistsException, StationNotFoundException{
        validateNewRoad(road);
        this.allRoads.add(road);
    }

    private void validateNewRoad(Road road) throws InstanceAlreadyExistsException, StationNotFoundException{
        Station start = road.getStartStation();
        Station end = road.getEndStation();

        if (this.allRoads.contains(road))
            throw new InstanceAlreadyExistsException(road.toString());

        if (!this.allStations.contains(start))
            throw new StationNotFoundException(start.getName());

        if (!this.allStations.contains(end)) {
            throw new StationNotFoundException(end.getName());
        }

    }

    public List<Road> getRoadsFromStationsNames(List<String> stationsNames) throws NoRoadBetweenStationsException, StationNotFoundException {
        List<Road> roads = new ArrayList<>();

        Iterator<Station> stationsIterator = getStationsFromNames(stationsNames).iterator();
        Station currentStation = stationsIterator.next();

        while(stationsIterator.hasNext()){
            Station nextStation = stationsIterator.next();
            Road road = currentStation.getRoadToStation(nextStation);
            roads.add(road);
            currentStation = nextStation;
        }

        return roads;
    }

    private List<Station> getStationsFromNames(List<String> stationsNames){
        return stationsNames.stream()
                .map(this::getStationByName)
                .collect(Collectors.toList());
    }

    public List<Road> getAllRoads() {
        return allRoads;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

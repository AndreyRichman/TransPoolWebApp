package exception;

public class StationNotFoundException extends RuntimeException {
    String stationName;

    public StationNotFoundException(String stationName) {
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }
}

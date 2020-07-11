package transpool.logic.map.structure;

public class Road {

    private Station startStation;
    private Station endStation;
    private double lengthInKM;
    private double fuelUsagePerKilometer;
    private int maxSpeed;

    public Road(Station startStation, Station endStation){
        this.startStation = startStation;
        this.endStation = endStation;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setLengthInKM(int lengthInKM) {
        this.lengthInKM = lengthInKM;
    }

    public void setFuelUsagePerKilometer(double fuelUsagePerKilometer) {
        this.fuelUsagePerKilometer = fuelUsagePerKilometer;
    }


    public int getMaxSpeed() {
        return maxSpeed;
    }

    public double getLengthInKM() {
        return lengthInKM;
    }

    public double getFuelUsagePerKilometer() {
        return fuelUsagePerKilometer;
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public int getDurationInMinutes(){
        return (int) (lengthInKM * 60) / maxSpeed;
    }

    public boolean sharesOppositeStations(Road other){
        return this.startStation == other.endStation && this.endStation == other.startStation;
    }
}

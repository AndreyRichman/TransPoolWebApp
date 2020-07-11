package exception;

public class RideNotExistsException extends Exception {
    int idOfRide;

    public RideNotExistsException(int idOfRide) {
        this.idOfRide = idOfRide;
    }

    public int getIdOfRide() {
        return idOfRide;
    }
}

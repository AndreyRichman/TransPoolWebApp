package exception;

public class NotSupportedRideRepeatTimeException extends Exception {
    String type;

    public NotSupportedRideRepeatTimeException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

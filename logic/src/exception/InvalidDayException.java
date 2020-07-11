package exception;

public class InvalidDayException extends Exception {
    int day;

    public InvalidDayException(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }
}

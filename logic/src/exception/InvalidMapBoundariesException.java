package exception;

public class InvalidMapBoundariesException extends Exception {
    int width;
    int length;

    public InvalidMapBoundariesException(int width, int length ){
        this.width = width;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }
}

package transpool.logic.map.structure;

public class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Coordinate cord) {
        return ((this.getX() == cord.getX()) && this.getY() == cord.getY());
    }
}

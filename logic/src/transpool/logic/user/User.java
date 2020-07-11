package transpool.logic.user;

public class User {

    private static int uniqueID = 100;
    private final int id;
    private String name;

    public User(String name) {
        this.id = uniqueID++;
        this.name = name;
    }

    public int getID() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}

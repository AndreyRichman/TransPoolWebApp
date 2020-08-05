package transpool.logic.user;

public class User {

    private static int uniqueID = 100;
    private final int id;
    private String name;
    private Walet walet;

    private Type type;

    public enum Type {
        DRIVER,
        TREMPIST
    }

    public User(String name) {
        this.id = uniqueID++;
        this.name = name;
        this.walet = new Walet();
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Walet getWalet() {
        return walet;
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

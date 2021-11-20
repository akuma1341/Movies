public class Actor {
    private final int id;
    private final String name;
    private static int currentID = 0;

    public Actor(String name) {
        this.id = currentID;
        this.name = name;
        currentID++;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "id=" + id + " - " + name;
    }
}

public class Director {
    private final String name;
    private final int id;
    private static int currentID = 0;

    public Director(String name) {
        this.name = name;
        this.id = currentID;
        currentID++;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}

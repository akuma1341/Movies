public class Genre {
    private final int id;
    private final String name;
    private static int currentID = 0;

    public Genre(String name) {
        this.id = currentID;
        currentID++;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

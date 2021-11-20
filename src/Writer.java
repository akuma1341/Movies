public class Writer {
    private final int id;
    private final String name;
    private static int currentID = 0;

    public Writer(String name) {
        this.id = getCurrentID();
        currentID++;
        this.name = name;
    }

    private int getCurrentID() {
        return currentID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

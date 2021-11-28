import java.util.Objects;

public class Writer {
    private final int id;
    private final String name;
    private static int currentID = 0;

    public Writer(String name) {
        this.id = getCurrentID();
        currentID++;
        this.name = name;
    }

    public Writer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int getCurrentID() {
        return currentID;
    }

    public int getID() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return id == writer.id && Objects.equals(name, writer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

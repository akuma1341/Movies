import java.util.Objects;

public class Director {
    private final String name;
    private final int id;
    private static int currentID = 0;

    public Director(String name) {
        this.name = name;
        this.id = currentID;
        currentID++;
    }

    public Director(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return id == director.id && Objects.equals(name, director.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
